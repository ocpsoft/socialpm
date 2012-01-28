/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM 
 * under the terms of the GPL, as follows:
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
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *  
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.rewrite;

import com.ocpsoft.rewrite.config.Condition;
import com.ocpsoft.rewrite.config.ConditionBuilder;
import com.ocpsoft.rewrite.context.EvaluationContext;
import com.ocpsoft.rewrite.event.Rewrite;
import com.ocpsoft.rewrite.servlet.config.Path;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public abstract class SocialPMResources extends ConditionBuilder
{
   public static Condition excluded()
   {
      return new SocialPMResources() {
         @Override
         public boolean evaluate(final Rewrite event, final EvaluationContext context)
         {
            return Path.matches(".*")
                     .andNot(Path.matches(".*javax\\.faces\\.resource.*"))
                     .andNot(Path.matches("/openid/.*"))
                     .andNot(Path.matches("/logout"))
                     .andNot(Path.matches("/rfRes/.*")).evaluate(event, context);
         }
      };
   }

   public static Condition included()
   {
      return new SocialPMResources() {
         @Override
         public boolean evaluate(final Rewrite event, final EvaluationContext context)
         {
            return Path.matches(".*javax\\.faces\\.resource.*")
                     .or(Path.matches("/openid/.*"))
                     .or(Path.matches("/logout"))
                     .or(Path.matches("/rfRes/.*")).evaluate(event, context);
         }
      };
   }
}
