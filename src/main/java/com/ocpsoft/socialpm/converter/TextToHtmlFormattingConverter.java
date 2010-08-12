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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Convert paragraph text into nice HTML text.
 * <p>
 * <ul>
 * <li>Lines beginning with * are converted to unordered lists</li>
 * <li>Carriage returns are converted to paragraph tags</li>
 * </ul>
 * 
 * @author lb3
 */
// TODO test this
@FacesConverter("textToHtml")
public class TextToHtmlFormattingConverter implements Converter
{
	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
	{
		throw new ConverterException("Does not yet support converting links to text");
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value)
	{
		String text = value.toString();
		text = text.replaceAll("(?m)^\\s*\\*\\s*(.*)$", "<li>$1</li>");
		text = text.replaceAll("\\s*(<li>.*</li>)\\s*\\n", "$1");
		text = text.replaceAll("(<li>.*</li>)+", "<ul>$0</ul>");
		text = text.replaceAll("(?m)\\n", "<p>");
		return text;
	}
}