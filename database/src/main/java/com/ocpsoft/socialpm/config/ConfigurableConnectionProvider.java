/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.ocpsoft.socialpm.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class ConfigurableConnectionProvider implements ConnectionProvider
{
   private static final long serialVersionUID = -2071693725398034335L;

   @Override
   @SuppressWarnings("rawtypes")
   public boolean isUnwrappableAs(final Class unwrapType)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public <T> T unwrap(final Class<T> unwrapType)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Connection getConnection() throws SQLException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void closeConnection(final Connection conn) throws SQLException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean supportsAggressiveRelease()
   {
      // TODO Auto-generated method stub
      return false;
   }

}
