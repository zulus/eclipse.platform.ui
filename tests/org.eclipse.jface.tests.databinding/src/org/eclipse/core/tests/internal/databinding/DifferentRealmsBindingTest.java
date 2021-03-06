/*******************************************************************************
 * Copyright (c) 2015, 2016 Conrad Groth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Conrad Groth - Testing my fix, that validation status is set in the correct realm
 ******************************************************************************/
package org.eclipse.core.tests.internal.databinding;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.ObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.set.ObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.util.ILogger;
import org.eclipse.core.databinding.util.Policy;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.tests.databinding.observable.ThreadRealm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DifferentRealmsBindingTest {

	ThreadRealm targetAndModelRealm = new ThreadRealm();
	ThreadRealm validationRealm = new ThreadRealm();

	List<IStatus> errorStatusses = new ArrayList<>();

	DataBindingContext dbc;
	ILogger logger = new ILogger() {
		@Override
		public void log(IStatus status) {
			if (!status.isOK()) {
				errorStatusses.add(status);
			}
		}
	};

	@Before
	public void setUp() throws Exception {
		errorStatusses.clear();
		new Thread() {
			@Override
			public void run() {
				targetAndModelRealm.init(Thread.currentThread());
				targetAndModelRealm.block();
			}
		}.start();

		validationRealm.init(Thread.currentThread());
		dbc = new DataBindingContext(validationRealm);
		Policy.setLog(logger);
	}

	@After
	public void tearDown() throws Exception {
		dbc.dispose();
	}

	@Test
	public void testListBindingValidationRealm() throws Throwable {
		final ObservableList model = new WritableList(targetAndModelRealm);
		final ObservableList target = new WritableList(targetAndModelRealm);

		dbc.bindList(target, model);
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}

	@Test
	public void testSetBindingValidationRealm() throws Throwable {
		final ObservableSet model = new WritableSet(targetAndModelRealm);
		final ObservableSet target = new WritableSet(targetAndModelRealm);

		dbc.bindSet(target, model);
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}
}
