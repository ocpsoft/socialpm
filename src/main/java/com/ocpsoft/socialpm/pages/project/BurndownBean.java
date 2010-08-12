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

package com.ocpsoft.socialpm.pages.project;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.ocpsoft.socialpm.pages.PageBean;

@Named
@RequestScoped
public class BurndownBean extends PageBean
{
   private static final long serialVersionUID = -3946850488149351643L;
   /*

   public String getFeatureStack()
   {
      PrettyChart chart = PrettyChart.getInstance().setTitleText("Feature Allocations").setBackgroundColor("#FAFAFA");
      return chart.toJSON();
   }

   public String getBurndown()
   {
      PrettyChart chart = PrettyChart.getInstance().setTitleText("Sprint Burndown").setBackgroundColor("#FAFAFA");
      Iteration iteration = currentIterationBean.getIteration();

      if (!iteration.isUpcoming() && !iteration.isDefault())
      {
         Date startDate = iteration.getStartDate();

         int max = 1;

         List<Integer> values = new ArrayList<Integer>();
         List<Integer> idealValues = new ArrayList<Integer>();
         List<String> axisLabels = new ArrayList<String>();

         DateFormat fmt = new SimpleDateFormat("MM/dd");
         Date curr = startDate;
         Date end = iteration.getEndDate();
         int count = 0;

         IterationStatistics lastStats = null;
         while (curr.before(end) || Dates.isSameDay(curr, end))
         {
            if (count % 3 == 0)
            {
               axisLabels.add(fmt.format(curr));
            }
            else
            {
               axisLabels.add("");
            }
            count++;

            IterationStatistics stats;
            try
            {
               stats = iteration.getStatistics(curr);
            }
            catch (NoSuchObjectException e)
            {
               stats = lastStats;
            }

            if ((stats != null) && curr.before(Dates.now()))
            {
               values.add(stats.getTotalHours());
               max = Math.max(stats.getTotalHours(), max);
            }
            else
            {
               values.add(null);
            }

            if (curr.equals(startDate) && iteration.isCommitted())
            {
               idealValues.add(iteration.getCommitmentStats().getTotalHours());
               max = Math.max(iteration.getCommitmentStats().getTotalHours(), max);
            }
            else if (curr.equals(end) && iteration.isCommitted())
            {
               idealValues.add(0);
            }
            else if (iteration.isCommitted())
            {
               idealValues.add(null);
            }
            lastStats = stats;
            curr = Dates.addDays(curr, 1);
         }

         chart.getYAxis().setMax(max + 1);
         chart.getXAxis().setLabels(axisLabels.toArray());
         chart.getXAxis().setRotate(300);

         LineSeries ideal = new LineSeries().setLabel("Ideal").setColour("#00b8bf").setDotStyle(DotStyle.DOT).setAnimation(LineEffect.FADE_IN).setValues(idealValues.toArray());

         LineSeries burndown = new LineSeries().setLabel("Hours Remain").setValues(values.toArray()).setDotStyle(DotStyle.SOLID_DOT);

         chart.addSeries(burndown);

         if (iteration.isCommitted())
         {
            chart.addSeries(ideal);
         }

      }
      return chart.toJSON();
   }

   public String getProgress()
   {
      int inProgress = 0;
      int completed = 0;
      int notStarted = 0;
      int impeded = 0;

      PrettyChart chart = PrettyChart.getInstance().setTitleText("Sprint Progress").setBackgroundColor("#FAFAFA");
      Iteration iteration = currentIterationBean.getIteration();
      if (!iteration.isUpcoming() && !iteration.isDefault())
      {
         List<Story> stories = currentIterationBean.getFrontBurnerStories(iteration);
         for (Story s : stories)
         {
            if (s.isImpeded())
            {
               impeded++;
            }
            if (s.isStarted() && s.isOpen())
            {
               inProgress++;
            }
            if (!s.isOpen())
            {
               completed++;
            }
            if (!s.isStarted() && s.isOpen())
            {
               notStarted++;
            }
         }

         Object[] pieValues = { new PieValue(completed, "completed", "#57D482"), new PieValue(impeded, "impeded", "#ff0000"), new PieValue(inProgress, "in-progress", "#3587E4"), new PieValue(notStarted, "not-started", "#D249C5") };
         PieSeries ps = new PieSeries().setLabel("Ideal").setValues(pieValues).setColour("#00b8bf").setDotStyle(DotStyle.DOT).setToolTip("#val# of " + stories.size());

         chart.addSeries(ps);
      }
      return chart.toJSON();
   }

   class BurndownDay
   {
      Date date;
      Integer hoursRemain;
      Integer idealHours;

      public BurndownDay(final Date curr, final Integer hoursRemain)
      {
         date = curr;
         this.hoursRemain = hoursRemain;
      }

      public Date getDate()
      {
         return date;
      }

      public void setDate(final Date date)
      {
         this.date = date;
      }

      public Integer getHoursRemain()
      {
         return hoursRemain;
      }

      public void setHoursRemain(final Integer hoursRemain)
      {
         this.hoursRemain = hoursRemain;
      }
   }
   
   */
}