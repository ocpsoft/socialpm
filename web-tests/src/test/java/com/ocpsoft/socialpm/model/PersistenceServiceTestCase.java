package com.ocpsoft.socialpm.model;

import com.sun.tools.javac.util.DefaultFileManager.Archive;

@RunWith(Arquillian.class)
public class PersistenceServiceTestCase
{
   @Deployment
   public static Archive<?> createDeployment()
   {
      return ShrinkWrap.create(JavaArchive.class)
               .addClasses(Record.class, PersistenceService.class, PersistenceServiceBean.class)
               .addManifestResource("test-persistence.xml", "persistence.xml");
   }

   @EJB
   PersistenceService service;

   @Test
   public void queryShouldFindSeedRecord()
   {
      service.seed();
      List<Record> results = service.selectAll();
      assertEquals("Should have found one record", 1, results.size());
   }
}