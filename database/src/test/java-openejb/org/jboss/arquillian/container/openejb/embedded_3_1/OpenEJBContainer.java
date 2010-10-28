/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.container.openejb.embedded_3_1;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.apache.openejb.NoSuchApplicationException;
import org.apache.openejb.OpenEJBException;
import org.apache.openejb.UndeployException;
import org.apache.openejb.assembler.classic.AppInfo;
import org.apache.openejb.assembler.classic.Assembler;
import org.apache.openejb.assembler.classic.SecurityServiceInfo;
import org.apache.openejb.assembler.classic.TransactionServiceInfo;
import org.jboss.arquillian.protocol.local.LocalMethodExecutor;
import org.jboss.arquillian.spi.Configuration;
import org.jboss.arquillian.spi.ContainerMethodExecutor;
import org.jboss.arquillian.spi.Context;
import org.jboss.arquillian.spi.DeployableContainer;
import org.jboss.arquillian.spi.DeploymentException;
import org.jboss.arquillian.spi.LifecycleException;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.openejb.config.ShrinkWrapConfigurationFactory;

/**
 * Arquillian {@link DeployableContainer} adaptor for a target OpenEJB environment; responible for lifecycle and
 * deployment operations
 * 
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class OpenEJBContainer implements DeployableContainer
{

   // -------------------------------------------------------------------------------------||
   // Class Members ----------------------------------------------------------------------||
   // -------------------------------------------------------------------------------------||

   /**
    * Logger
    */
   private static final Logger log = Logger.getLogger(OpenEJBContainer.class.getName());

   /**
    * The field name of the deployment path changed between OpenEJB 3.1 and OpenEJB 3.1. Use reflection hack to support
    * both APIs.
    */
   private static String[] APP_INFO_PATH_FIELD_NAMES = { "jarPath", "path" };

   // -------------------------------------------------------------------------------------||
   // Instance Members -------------------------------------------------------------------||
   // -------------------------------------------------------------------------------------||

   /**
    * OpenEJB Assembler
    */
   private Assembler assembler;

   /**
    * OpenEJB Configuration backing the Container
    */
   private ShrinkWrapConfigurationFactory config;

   /**
    * The deployment
    */
   private AppInfo deployment;

   private Field appInfoPathField;

   private OpenEJBConfiguration configuration;

   // -------------------------------------------------------------------------------------||
   // Required Implementations -----------------------------------------------------------||
   // -------------------------------------------------------------------------------------||

   public void setup(final Context context, final Configuration configuration)
   {
      this.configuration = configuration.getContainerConfig(OpenEJBConfiguration.class);
      appInfoPathField = null;
      for (String candidate : APP_INFO_PATH_FIELD_NAMES)
      {
         try
         {
            appInfoPathField = AppInfo.class.getField(candidate);
            break;
         }
         catch (NoSuchFieldException e)
         {
         }
      }

      if (appInfoPathField == null)
      {
         // perhaps make a ConfigurationException for this type of problem?
         throw new RuntimeException(
                  "Incompatible version of OpenEJB container. Cannot determine field for application path.");
      }
   }

   public ContainerMethodExecutor deploy(final Context context, final Archive<?> archive) throws DeploymentException
   {
      // Deploy as an archive
      final AppInfo appInfo;
      try
      {
         appInfo = config.configureApplication(archive);
         context.add(AppInfo.class, appInfo);
         this.deployment = appInfo;
      }
      catch (final OpenEJBException e)
      {
         throw new DeploymentException("Could not configure application in OpenEJB", e);
      }
      try
      {
         assembler.createApplication(appInfo);
      }
      catch (final Exception ne)
      {
         throw new DeploymentException("Could not create the application", ne);
      }

      // Invoke locally
      return new LocalMethodExecutor();
   }

   public void start(final Context context) throws LifecycleException
   {
      final ShrinkWrapConfigurationFactory config = new ShrinkWrapConfigurationFactory();
      final Assembler assembler = new Assembler();
      try
      {
         // These two objects pretty much encompass all the EJB Container
         assembler.createTransactionManager(config.configureService(TransactionServiceInfo.class));
         assembler.createSecurityService(config.configureService(SecurityServiceInfo.class));
      }
      catch (final OpenEJBException e)
      {
         throw new LifecycleException("Could not configure the OpenEJB Container", e);
      }

      // Set
      this.assembler = assembler;
      this.config = new ShrinkWrapConfigurationFactory();
   }

   public void stop(final Context context) throws LifecycleException
   {
      assembler.destroy();
   }

   public void undeploy(final Context context, final Archive<?> archive) throws DeploymentException
   {
      // Undeploy the archive
      try
      {
         String path;
         try
         {
            path = String.class.cast(appInfoPathField.get(deployment));
         }
         catch (Exception e)
         {
            throw new UndeployException("Could not access field " + appInfoPathField.getName() + " on AppInfo class");
         }
         assembler.destroyApplication(path);
      }
      catch (final UndeployException e)
      {
         throw new DeploymentException("Error in undeployment of " + archive.getName(), e);
      }
      catch (final NoSuchApplicationException e)
      {
         throw new DeploymentException("Application was not deployed; cannot undeploy: " + archive.getName(), e);
      }
   }
}
