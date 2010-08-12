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

package com.ocpsoft.socialpm.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.pages.params.CurrentProjectBean;

/**
 * Converts #23 into &lt;a href="link to story 23"&gt;Story#23&lt;/a&gt;. This
 * can be escaped by using ##23, or double pound. This link is context sensitive
 * to the CurrentProject request value
 * 
 * @author lb3
 */
// TODO test this
@FacesConverter("storyLinkConverter")
public class StoryLinkConverter implements Converter
{
   private static final String STORY_REGEX = "(?<!#)#(\\d+)";
   private static final Pattern storyPattern = Pattern.compile(STORY_REGEX);
   private static final PrettyURLBuilder urlBuilder = new PrettyURLBuilder();

   @Inject
   CurrentProjectBean cpb;

   @Override
   public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
   {
      throw new ConverterException("Does not yet support converting links to text");
   }

   @Override
   public String getAsString(final FacesContext context, final UIComponent component, final Object value)
   {
      String text = value.toString();
      if (value instanceof String)
      {
         Matcher matcher = storyPattern.matcher(text);
         StringBuffer result = new StringBuffer(text.length());
         while (matcher.find())
         {
            int storyNumber = Integer.valueOf(matcher.group(1));
            matcher.appendReplacement(result, "<a href=\"" + buildStoryUrl(storyNumber) + "\">Story$0</a>");
         }
         matcher.appendTail(result);
         return result.toString().replaceAll("#(#\\d+)", "$1");
      }
      return text;
   }

   private String buildStoryUrl(final int storyNumber)
   {
      Project project = cpb.getProject();

      UrlMapping mapping = PrettyContext.getCurrentInstance().getConfig().getMappingById(UrlConstants.VIEW_STORY);
      List<UIParameter> params = new ArrayList<UIParameter>();

      UIParameter param = new UIParameter();
      param.setValue(project);
      params.add(param);

      param = new UIParameter();
      param.setValue(storyNumber);
      params.add(param);

      return buildUrl(mapping, params);
   }

   private String buildUrl(final UrlMapping mapping, final List<UIParameter> params)
   {
      return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + urlBuilder.build(mapping, params);
   }
}
