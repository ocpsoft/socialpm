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

package com.ocpsoft.socialpm.model.project.iteration.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ocpsoft.socialpm.model.project.iteration.Iteration;

public class DailyStatsChain implements DailyStatsUpdater
{
	private List<DailyStatsUpdater> updaters = new ArrayList<DailyStatsUpdater>();

	public DailyStatsChain(DailyStatsUpdater... updaters)
	{
		this.updaters = Arrays.asList(updaters);
	}

	public static DailyStatsChain getInstance()
	{
		return new DailyStatsChain(new BeforeIteration(), new DuringIteration(), new AfterIteration(),
				new DefaultIteration());
	}

	public void update(Iteration iteration)
	{
		for (DailyStatsUpdater u : updaters)
		{
			if (u.shouldUpdate(iteration))
			{
				u.update(iteration);
				return;
			}
		}
		throw new IllegalStateException("Could not update stats for iteration: " + iteration.getTitle());
	}

	@Override
	public boolean shouldUpdate(Iteration iteration)
	{
		return true;
	}
}
