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
package org.tobbaumann.wt.core;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.tobbaumann.wt.domain.WorkItem;

public interface WorkTrackingService {

	void createWorkItems(Iterable<WorkItem> workItems);

	void updateWorkItems(Iterable<WorkItem> workItems);

	void deleteWorkItems(Iterable<WorkItem> workItems);

	WorkItem readWorkItem(String id);

	List<WorkItem> readWorkItems(Date date);

	List<WorkItem> readWorkItems();

	Set<String> readDates();
}
