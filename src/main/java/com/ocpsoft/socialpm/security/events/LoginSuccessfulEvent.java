/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package com.ocpsoft.socialpm.security.events;

import com.ocpsoft.socialpm.domain.user.User;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class LoginSuccessfulEvent
{
   private boolean redirect = false;
   private final User user;

   public LoginSuccessfulEvent(final User user)
   {
      this.user = user;
   }

   public LoginSuccessfulEvent(final User user, final boolean redirect)
   {
      this.user = user;
      this.redirect = redirect;
   }

   public User getUser()
   {
      return user;
   }

   public boolean isRedirect()
   {
      return redirect;
   }

   public void setRedirect(final boolean redirect)
   {
      this.redirect = redirect;
   }
}
