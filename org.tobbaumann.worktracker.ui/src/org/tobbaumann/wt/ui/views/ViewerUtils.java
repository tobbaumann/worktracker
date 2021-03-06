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
package org.tobbaumann.wt.ui.views;

import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.StructuredViewer;

public class ViewerUtils {

	private ViewerUtils() {
	}

	public static void refreshViewerPeriodically(final StructuredViewer viewer) {
		refreshViewerPeriodically(viewer, 30, TimeUnit.SECONDS);
	}

	private static void refreshViewerPeriodically(final StructuredViewer viewer, final long delay, final TimeUnit timeUnit) {
		Long millis = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
		viewer.getControl().getDisplay().timerExec(millis.intValue(), new Runnable() {
			@Override
			public void run() {
				if (!viewer.getControl().isDisposed()) {
					viewer.refresh(true);
					refreshViewerPeriodically(viewer, delay, timeUnit);
				}
			}
		});
	}
}
