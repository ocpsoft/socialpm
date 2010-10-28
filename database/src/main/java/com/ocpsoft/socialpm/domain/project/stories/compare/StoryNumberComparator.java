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

package com.ocpsoft.socialpm.domain.project.stories.compare;

import java.util.Comparator;

import com.ocpsoft.socialpm.domain.project.stories.Story;

// TODO test me (easy)
/**
 * Sort on number - the lower the number, the greater the sort value eg: the
 * priority value 1 > 2
 */
public class StoryNumberComparator implements Comparator<Story>
{
   @Override
   public int compare(final Story l, final Story r)
   {
      Long left = l.getId();
      Long right = r.getId();
      if ((left != null) && (right != null))
      {
         return left.compareTo(right);
      }
      else if (left != null)
      {
         return -1;
      }
      else if (right != null)
      {
         return 1;
      }
      return 0;
   }
}