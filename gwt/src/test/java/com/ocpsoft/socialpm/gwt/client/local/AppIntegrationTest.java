package com.ocpsoft.socialpm.gwt.client.local;

import org.jboss.errai.enterprise.client.cdi.AbstractErraiCDITest;
import org.junit.Test;

/**
 * Tests features of the quickstart application. The primary purpose of this test case is to serve as an example for how
 * to set up real functional/integration tests in a real application.
 * 
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
public class AppIntegrationTest extends AbstractErraiCDITest
{

   /**
    * Reference to the CDI-managed application object {@code CDITestHelper.instance.app}. Initialized in each individual
    * test case in a {@code CDI.addPostInitTask()} block.
    */
   // private App app;

   @Override
   public String getModuleName()
   {
      return "com.ocpsoft.socialpm.gwt.App";
   }

   @Override
   public void gwtSetUp() throws Exception
   {
      super.gwtSetUp();
   }

   @Test
   public void testInitialSetup()
   {
      CDITestHelper.afterCdiInitialized(new Runnable() {
         @Override
         public void run()
         {
            // app = CDITestHelper.instance.app;
            // assertNotNull(app.getMessageBox());
            // assertNotNull(app.getResponseLabel());
            // assertNotNull(app.getSendButton());
            // assertEquals("", app.getResponseLabel().getText());
            finishTest();
         }
      });

      // This call tells GWT's test runner to wait 20 seconds after the test returns.
      // We need this delay to give the HelloMessage time to come back from the server.
      delayTestFinish(20000);
   }

   public void testButtonClickUpdatesLabel() throws Exception
   {

      CDITestHelper.afterCdiInitialized(new Runnable() {
         @Override
         public void run()
         {
            // app = CDITestHelper.instance.app;
            // app.getResponseLabel().setText("label value before click");
            // app.getMessageBox().setText("moocow");
            // app.fireMessage();
         }
      });

      CDITestHelper.afterResponseEvent(new Runnable() {
         @Override
         public void run()
         {

            // // this is what we sent
            // String messageBoxText = app.getMessageBox().getText();
            //
            // // this is composed from the server response message
            // String labelText = app.getResponseLabel().getText();

            // assertTrue(
            // "Unexpected label contents after pressing button: \"" + labelText + "\"",
            // labelText.startsWith("HelloMessage from Server: " + messageBoxText.toUpperCase()
            // + " @ TIMEMILLIS: "));
            finishTest();
         }
      });

      // This call tells GWT's test runner to wait 3000ms after the test returns.
      // We need this delay to give the HelloMessage time to come back from the server.
      delayTestFinish(3000);
   }
}
