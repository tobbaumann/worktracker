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

import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;

public class WorkItemsView {

	private TableViewer tableViewer;
	private WorkTrackingService service;
	@Inject
	private ESelectionService selectionService;

	@Inject
	public WorkItemsView(WorkTrackingService service) {
		this.service = service;
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
				selectionService.setSelection(selectedObject);
			}
		});

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		List<String> columnNames = Arrays.asList("Activity", "Start", "End", "Duration");
		for (String colName : columnNames) {
			TableColumn tcol = new TableColumn(table, SWT.LEFT);
			tcol.setText(colName);
		}
		new TableColumn(table, SWT.LEFT); // empty column
		packColumns();
	}

	private void packColumns() {
		for (TableColumn c : tableViewer.getTable().getColumns()) {
			c.pack();
		}
	}

	@Inject
	public void updateDate(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional String date) {
		if (date == null) {
			return;
		}
		List<WorkItem> workItems = service.readWorkItems(date);
		tableViewer.setInput(workItems);
		packColumns();
	}


	@PreDestroy
	public void dispose() {
	}

	private static final class LabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(ViewerCell cell) {
			WorkItem wi = (WorkItem) cell.getElement();
			switch (cell.getColumnIndex()) {
			case 0:
				cell.setText(wi.getActivityName());
				break;
			case 1:
				cell.setText(DateFormat.getTimeInstance().format(wi.getStart()));
				break;
			case 2:
				cell.setText(DateFormat.getTimeInstance().format(wi.getEnd()));
				break;
			case 3:
				cell.setText(wi.getDuration().asString());
				break;
			default:
				break;
			}
			super.update(cell);
		}
	}
}