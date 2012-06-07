package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;
import com.ocpsoft.socialpm.model.user.Profile;

@Templated
public class WelcomeBar extends Composite
{
   @Inject
   @DataField
   Span greeting;

   @Inject
   @DataField
   GravatarImage gravatar;

   @Inject
   @DataField
   ProfileLink profileNameLink;

   @PostConstruct
   public final void init()
   {
      gravatar.setSize(90);
      greeting.setInnerText("Everything is fine.");
   }

   public void setProfile(Profile profile)
   {
      gravatar.setProfile(profile);
      profileNameLink.setProfile(profile);
   }

}
