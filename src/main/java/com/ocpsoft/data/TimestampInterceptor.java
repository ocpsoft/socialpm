/*
 * Copyright 2010 - Lincoln Baxter, III (lincoln@ocpsoft.com) - Licensed under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at http://www.apache.org/licenses/LICENSE-2.0 - Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.ocpsoft.data;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.ocpsoft.util.Dates;

public class TimestampInterceptor extends EmptyInterceptor
{
   private static final long serialVersionUID = 457705452362972847L;

   @Override
   public boolean onSave(final Object entity, final Serializable id, final Object[] state,
            final String[] propertyNames, final Type[] types)
   {
      if (entity instanceof PersistentObject<?>)
      {
         setValue(state, propertyNames, "createdOn", Dates.now());
         return true;
      }
      return false;
   }

   @Override
   public boolean onFlushDirty(final Object entity, final Serializable id, final Object[] currentState,
            final Object[] previousState, final String[] propertyNames, final Type[] types)
   {
      if (entity instanceof PersistentObject<?>)
      {
         setValue(currentState, propertyNames, "lastUpdate", Dates.now());
      }
      return false;
   }

   private void setValue(final Object[] currentState, final String[] propertyNames, final String propertyToSet,
            final Object value)
   {
      int index = -1;
      for (int i = 0; i < propertyNames.length; i++)
      {
         if (propertyToSet.equals(propertyNames[i]))
         {
            index = i;
            currentState[index] = value;
            break;
         }
      }
      if (index == -1)
      {
         throw new IllegalArgumentException("Property [" + propertyToSet
                    + "] not found in Hibernate Object current state array");
      }
   }
}
