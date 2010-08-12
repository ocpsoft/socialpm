package com.ocpsoft.socialpm.domain.project.iteration;

import java.util.Comparator;
import java.util.Date;

public class IterationStartDateComparator implements Comparator<Iteration>
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
