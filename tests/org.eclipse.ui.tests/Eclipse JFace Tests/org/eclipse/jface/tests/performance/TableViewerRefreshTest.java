/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jface.tests.performance;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.tests.performance.ViewPerformanceSuite;

/**
 * The TableViewerRefreshTest is a test for refreshing the TableViewer.
 */
public class TableViewerRefreshTest extends ViewerTest {

	private class TestTableViewer extends TableViewer {

		public TestTableViewer(Composite parent, int style) {
			super(parent, style);
		}

		public TestTableViewer(Composite parent) {
			super(parent);
		}

		public TestTableViewer(Table table) {
			super(table);
		}

		public void testUpdateItem(Widget widget, Object element) {
			updateItem(widget, element);
		}
	};

	TestTableViewer viewer;

	private RefreshTestContentProvider contentProvider;

	public TableViewerRefreshTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public TableViewerRefreshTest(String testName) {
		super(testName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.tests.performance.ViewerTest#createViewer(org.eclipse.swt.widgets.Shell)
	 */
	protected StructuredViewer createViewer(Shell shell) {
		viewer = new TestTableViewer(shell);
		contentProvider = new RefreshTestContentProvider(
				RefreshTestContentProvider.ELEMENT_COUNT);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(getLabelProvider());
		return viewer;
	}

	/**
	 * Test the time for doing a refresh.
	 * 
	 * @throws Throwable
	 */
	public void testRefresh() throws Throwable {
		openBrowser();

		for (int i = 0; i < ViewPerformanceSuite.ITERATIONS; i++) {
			startMeasuring();
			viewer.refresh();
			processEvents();
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}
	
	/**
	 * Test the time for doing a refresh.
	 * 
	 * @throws Throwable
	 */
	public void testRefreshSorted() throws Throwable {
		openBrowser();
		viewer.setSorter(new ViewerSorter());

		for (int i = 0; i < ViewPerformanceSuite.ITERATIONS; i++) {
			startMeasuring();
			viewer.refresh();
			processEvents();
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}
	
	/**
	 * Test the time for doing a refresh.
	 * 
	 * @throws Throwable
	 */
	public void testRefreshPreSorted() throws Throwable {
		openBrowser();
		ViewerSorter sorter = new ViewerSorter();
		viewer.setSorter(sorter);
		

		for (int i = 0; i < ViewPerformanceSuite.ITERATIONS; i++) {
			contentProvider.refreshElements();
			startMeasuring();
			contentProvider.cloneElements();
			contentProvider.preSortElements(viewer,sorter);
			viewer.refresh();
			processEvents();
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}

	/**
	 * Test the time for doing a refresh.
	 * 
	 * @throws Throwable
	 */
	public void testUpdate() throws Throwable {
		openBrowser();

		for (int i = 0; i < ViewPerformanceSuite.ITERATIONS; i++) {
			TableItem[] items = viewer.getTable().getItems();
			startMeasuring();
			for (int j = 0; j < items.length; j++) {
				TableItem item = items[j];
				Object element = RefreshTestContentProvider.allElements[j];
				
				viewer.testUpdateItem(item, element);
				
			}
			processEvents();
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}

}
