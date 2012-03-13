package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.util.Gravatar;
import com.ocpsoft.socialpm.model.user.Profile;

public class GravatarImage extends Composite
{
   interface GravatarImageBinder extends UiBinder<Widget, GravatarImage>
   {
   }

   private static GravatarImageBinder binder = GWT.create(GravatarImageBinder.class);

   @UiField
   Span gravatarHolder;
   
   Image image = new Image();
   ProfileLink imageLink = new ProfileLink();

   private int size = 32;
   
   public GravatarImage()
   {
      initWidget(binder.createAndBindUi(this));
      gravatarHolder.add(imageLink);
   }

   public GravatarImage(int size)
   {
      this();
      this.size = size;
      setSize(size);
   }
   
   public GravatarImage(int size, Profile profile)
   {
      this();
      setSize(size);
      setProfile(profile);
   }
   
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
