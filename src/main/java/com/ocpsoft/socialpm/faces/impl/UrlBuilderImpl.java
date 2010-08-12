/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.faces.impl;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;
import com.ocpsoft.socialpm.faces.UrlBuilder;

public class UrlBuilderImpl implements UrlBuilder
{
   @Inject
   private ExternalContext ec;

   private final PrettyURLBuilder builder = new PrettyURLBuilder();

   @Override
   public String build(final String mappingId, final UIParameter... params)
   {
      return build(mappingId, Arrays.asList(params));
   }

   @Override
   public String build(final String mappingId, final List<UIParameter> params)
   {
      UrlMapping mapping = PrettyContext.getCurrentInstance().getConfig().getMappingById(mappingId);
      StringBuilder buf = new StringBuilder(getRootUrl());
      buf.append(builder.build(mapping, params));
      return buf.toString();
   }

   public String getRootUrl()
   {
      StringBuilder buf = new StringBuilder();
      buf.append("http://");
      buf.append(ec.getRequestServerName());
      int port = ec.getRequestServerPort();
      if (port != 80)
      {
         buf.append(":").append(port);
      }
      buf.append(ec.getRequestContextPath());
      return buf.toString();
   }
}
