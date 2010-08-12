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

import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.time.DateUtils;

import com.ocpsoft.pretty.time.BasicTimeFormat;
import com.ocpsoft.pretty.time.PrettyTime;
import com.ocpsoft.pretty.time.units.Day;

@FacesConverter("prettyDaysConverter")
public class PrettyDaysConverter implements Converter
{
   private static PrettyTime prettyTime = new PrettyTime();

   static
   {
      Day day = new Day(Locale.ENGLISH);
      day.setFormat(new BasicTimeFormat().setFuturePrefix("in ").setPastSuffix(" ago").setPattern("%n %u"));
      prettyTime.setUnits(day);
   }

   @Override
   public Object getAsObject(final FacesContext context, final UIComponent comp, final String value)
   {
      throw new ConverterException("Does not yet support converting String to Date");
   }

   @Override
   public String getAsString(final FacesContext context, final UIComponent comp, final Object value)
   {
      if (value instanceof Date)
      {
         // TODO fix date padding in iteration to avoid this mess...
         Date date = (Date) value;
         if (date.after(new Date()))
         {
            date = DateUtils.addDays(date, 1);
         }
         else if (date.before(new Date()))
         {
            date = DateUtils.addDays(date, -1);
         }
         return prettyTime.format(date);
      }
      throw new ConverterException("May only be used to convert java.util.Date objects. Got: " + value.getClass());
   }

}
