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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringEscapeUtils;

import com.ocpsoft.util.StringValidations;

/**
 * Convert URLs into hyper-links in converted OutputText components
 * 
 * @author lb3
 */
// TODO test this
@FacesConverter("webLinkConverter")
public class WebLinkConverter implements Converter
{
    private static final String URL_PREFIXES = "(((news|(ht|f)tp(s?))\\://)|www\\.)";
    private static final String NON_URL_CHARS = "\\.|,|\\s";
    private static final String URL_CHARS = "[A-Za-z0-9\\/$\\-_.+!?#=*',@]";
    private static final String PARENS = "\\((" + URL_CHARS + "|[()])*\\)";
    private static final String ALLOWED_CHARS = "((" + URL_CHARS + ")|(" + PARENS + "))+(?<!\\.)";

    private static final String URL_REGEX = "((" + URL_PREFIXES + ")(" + ALLOWED_CHARS + ")(?<!" + NON_URL_CHARS + "))";
    private static final String MAIL_REGEX = "(mailto\\:)?(" + StringValidations.EMAIL_REGEX + ")";

    private static final Pattern urlPattern = Pattern.compile(URL_REGEX);
    private static final Pattern mailPattern = Pattern.compile(MAIL_REGEX);

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
    {
        throw new ConverterException("Does not yet support converting links to text");
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value)
    {
        String text = StringEscapeUtils.escapeHtml(value.toString());

        if (value instanceof String)
        {
            text = processURLs(text);
            text = processMails(text);
        }
        return text;
    }

    private String processMails(final String text)
    {
        Matcher matcher = mailPattern.matcher(text);
        StringBuffer result = new StringBuffer(text.length());
        while (matcher.find())
        {
            String group = matcher.group(0);
            if (group.startsWith("mailto"))
            {
                matcher.appendReplacement(result, "<a target=\"_blank\" href=\"$0\">$2</a>");
            }
            else
            {
                matcher.appendReplacement(result, "<a target=\"_blank\" href=\"mailto:$0\">$2</a>");
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private String processURLs(final String text)
    {
        Matcher matcher = urlPattern.matcher(text);
        StringBuffer result = new StringBuffer(text.length());
        while (matcher.find())
        {
            String group = matcher.group(0);
            if (group.startsWith("www"))
            {
                matcher.appendReplacement(result, "<a target=\"blank\" href=\"http://$0\">$0</a>");
            }
            else
            {
                matcher.appendReplacement(result, "<a target=\"blank\" href=\"$0\">$0</a>");
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
