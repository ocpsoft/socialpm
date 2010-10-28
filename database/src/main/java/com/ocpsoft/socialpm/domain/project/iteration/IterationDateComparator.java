/** 
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.ocpsoft.socialpm.domain.project.iteration;

import java.util.Comparator;
import java.util.Date;

public class IterationDateComparator implements Comparator<Iteration>
{
   @Override
   public int compare(final Iteration left, final Iteration right)
   {
      Date leftStartDate = left.getStartDate();
      Date rightStartDate = right.getStartDate();

      if ((leftStartDate != null) && (rightStartDate != null))
      {
         return leftStartDate.compareTo(rightStartDate);
      }
      else if (leftStartDate != null)
      {
         return -1;
      }
      else if (rightStartDate != null)
      {
         return 1;
      }

      return 0;
   }
}
