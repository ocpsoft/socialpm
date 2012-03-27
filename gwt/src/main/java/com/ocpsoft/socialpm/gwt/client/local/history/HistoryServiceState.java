package com.ocpsoft.socialpm.gwt.client.local.history;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.errai.ioc.client.api.InitBallot;
import org.ocpsoft.rewrite.gwt.client.history.ContextPathListener;
import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

/**
 * Ensure that the context path must be set before we are allowed to continue loading the application.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Singleton
public class HistoryServiceState
{
   // TODO This should probably go into some kind of rewrite-integration-errai package.
   @Inject
   InitBallot<HistoryServiceState> ballot;

   @PostConstruct
   public void doVote()
   {
      if (HistoryStateImpl.isInitialized())
      {
         ballot.voteForInit();
      }
      else
      {
         HistoryStateImpl.addContextPathListener(new ContextPathListener() {
            @Override
            public void onContextPathSet(String contextPath)
            {
               System.out.println("HistoryServiceState voted for init.");
               ballot.voteForInit();
            }
         });
      }
   }
}