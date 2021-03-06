/*******************************************************************************
 * Copyright (c) 2013 Tobias Baumann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Tobias Baumann - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.views.startwibutton;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;

@Creatable
public class ShortcutActivityNamesCreator {

	@Inject private WorkTrackerPreferences prefs;
	@Inject private WorkTrackingService service;
	private int nrOfButtons;

	public ShortcutActivityNamesCreator() {
	}

	@Inject
	@Optional
	public void updateNrOfButtons(@Preference(value = WorkTrackerPreferences.STARTWI_VIEW_NUMBER_OF_BUTTONS) int nrOfButtons) {
		if (this.nrOfButtons != nrOfButtons) {
			this.nrOfButtons = nrOfButtons;
		}
	}

	public List<String> create() {
		if (prefs.customWorkItemStartButtonLabelsAvailable()) {
			return prefs.getWorkItemStartButtonLabels();
		}
		List<Activity> activities = service.getMostUsedActivities(nrOfButtons);
		List<String> mua = newArrayList();
		for (Activity a : activities) {
			mua.add(a.getName());
		}
		return mua;
	}
}
