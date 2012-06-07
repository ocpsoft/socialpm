package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.ocpsoft.socialpm.gwt.client.local.util.Gravatar;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
@Templated
public class GravatarImage extends Composite
{
   @Inject
   @DataField
   ProfileLink imageLink;

   @Inject
   @DataField
   Image image;

   private int size = 32;

   @PostConstruct
   public final void init()
   {}

   public int getSize()
   {
      return size;
   }

   public void setSize(int size)
   {
      image.setHeight(size + "px");
      image.setWidth(size + "px");
      this.size = size;
   }

   public void setProfile(Profile profile)
   {
      imageLink.setProfile(profile);
      image.setUrl(Gravatar.forEmail(profile.getEmail(), getSize()));
      imageLink.getElement().setInnerHTML("");
      imageLink.getElement().appendChild(image.getElement());
   }

   public void clear()
   {
      image.setUrl("");
      imageLink.clear();
   }

   public Image getImage()
   {
      return image;
   }

}
