package com.ocpsoft.socialpm.gwt.client.local;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.IOCBeanManager;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LogoutActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.NewProjectActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProjectActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.SignupActivity;
import com.ocpsoft.socialpm.gwt.client.local.history.CurrentHistory;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LogoutPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.NewProjectPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProjectPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.SignupPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@ApplicationScoped
public class AppPlaceHistoryMapper implements PlaceHistoryMapper, ActivityMapper
{
   String delimiter = "/";

   private final ClientFactory clientFactory;
   private final List<TypedPlaceTokenizer<?>> tokenizers = new ArrayList<TypedPlaceTokenizer<?>>();

   private final IOCBeanManager manager;

   @Inject
   public AppPlaceHistoryMapper(IOCBeanManager manager, ClientFactory clientFactory)
   {
      this.manager = manager;
      this.clientFactory = clientFactory;

      /*
       * Constant Tokenizers
       */
      tokenizers.add(new HomePlace.Tokenizer());
      tokenizers.add(new SignupPlace.Tokenizer());
      tokenizers.add(new LoginPlace.Tokenizer());
      tokenizers.add(new LogoutPlace.Tokenizer());

      tokenizers.add(new NewProjectPlace.Tokenizer());

      /*
       * Single segment Tokenizers
       */
      tokenizers.add(new ProfilePlace.Tokenizer());

      /*
       * Double segment Tokenizers
       */
      tokenizers.add(new ProjectPlace.Tokenizer());
   }

   @Override
   public Activity getActivity(Place place)
   {
      Activity result = null;

      /*
       * Static Activities
       */
      if (place instanceof HomePlace)
         result = manager.lookupBean(HomeActivity.class).getInstance();
      if (place instanceof LoginPlace)
         result = manager.lookupBean(LoginActivity.class).getInstance();
      if (place instanceof LogoutPlace)
         result = manager.lookupBean(LogoutActivity.class).getInstance();
      if (place instanceof SignupPlace)
         result = manager.lookupBean(SignupActivity.class).getInstance();

      /*
       * Dynamic Activities
       */
      if (place instanceof ProfilePlace)
         result = manager.lookupBean(ProfileActivity.class).getInstance();
      if (place instanceof ProjectPlace)
         result = manager.lookupBean(ProjectActivity.class).getInstance();
      if (place instanceof NewProjectPlace)
         result = manager.lookupBean(NewProjectActivity.class).getInstance();

      if (result instanceof AuthenticationAware && (App.getLoggedInProfile() != null))
      {
         ((AuthenticationAware) result).handleLogin(new LoginEvent(App.getLoggedInProfile()));
      }

      if (result == null)
      {
         throw new RuntimeException("Failed to get Activity from Place [" + place + "]");
      }
      return result;
   }

   @Override
   public Place getPlace(String token)
   {
      Place result = null;
      token = token.replaceFirst("[^/]+://[^/]+/", "");
      String contextPath = HistoryStateImpl.getContextPath();

      if (token.startsWith(contextPath))
      {
         token = token.substring(contextPath.length());
      }

      for (PlaceTokenizer<?> t : tokenizers) {
         result = t.getPlace(token);
         if (result != null)
         {
            break;
         }
      }

      if (result == null)
      {
         throw new RuntimeException("Could not map token [" + token + "] to place");
      }

      System.out.println("Mapped token [" + token + "] to place [" + result + "]");
      return result;
   }

   @Produces
   @CurrentHistory
   public Place getCurrentHistoryPlace()
   {
      return clientFactory.getPlaceController().getWhere();
   }

   @Override
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public String getToken(Place place)
   {
      String result = null;

      for (TypedPlaceTokenizer t : tokenizers) {
         if (t.getPlaceType().getName().equals(place.getClass().getName()))
         {
            result = t.getToken(place);
         }
      }

      if (result == null)
      {
         throw new RuntimeException("Failed to get token from Place [" + place + "]");
      }

      return result;
   }
}