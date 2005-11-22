/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * A working set holds a number of IAdaptable elements. 
 * A working set is intended to group elements for presentation to 
 * the user or for operations on a set of elements.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @since 2.0 initial version
 * @since 3.0 now extends {@link org.eclipse.ui.IPersistableElement}
 * @since 3.2 now extends {@link org.eclipse.core.runtime.IAdaptable}
 */
public interface IWorkingSet extends IPersistableElement, IAdaptable {
    /**
     * Returns the elements that are contained in this working set.
     * 
     * @return	the working set's elements
     */
    public IAdaptable[] getElements();

    /**
     * Returns the working set id. Returns <code>null</code> if no
     * working set id has been set.
     * This is one of the ids defined by extensions of the 
     * org.eclipse.ui.workingSets extension point.
     * It is used by the workbench to determine the page to use in 
     * the working set edit wizard. The default resource edit page
     * is used if this value is <code>null</code>.
     * 
     * @return the working set id. May be <code>null</code>
     * @since 2.1 
     */
    public String getId();

    /**
     * Returns the working set icon.
     * Currently, this is one of the icons specified in the extensions 
     * of the org.eclipse.ui.workingSets extension point. 
     * The extension is identified using the value returned by
     * <code>getId()</code>. 
     * Returns <code>null</code> if no icon has been specified in the 
     * extension or if <code>getId()</code> returns <code>null</code>. 
     * 
     * @return the working set icon or <code>null</code>.
     * @since 2.1 
     */
    public ImageDescriptor getImage();

    /**
     * Returns the name of the working set.
     * 
     * @return	the name of the working set
     */
    public String getName();

    /**
     * Sets the elements that are contained in this working set.
     * 
     * @param elements the elements to set in this working set
     */
    public void setElements(IAdaptable[] elements);

    /**
     * Sets the working set id.
     * This is one of the ids defined by extensions of the 
     * org.eclipse.ui.workingSets extension point.
     * It is used by the workbench to determine the page to use in 
     * the working set edit wizard. The default resource edit page
     * is used if this value is <code>null</code>.
     * 
     * @param id the working set id. May be <code>null</code>
     * @since 2.1 
     */
    public void setId(String id);

    /**
     * Sets the name of the working set. 
     * The working set name should be unique.
     * The working set name must not have leading or trailing 
     * whitespace.
     * 
     * @param name the name of the working set
     */
    public void setName(String name);
    
    /**
     * Returns whether this working set can be edited or not. To make
     * a working set editable the attribute <code>pageClass</code> of
     * the extension defining a working set must be provided.
     * 
     * @return <code>true</code> if the working set can be edited; otherwise
     *  <code>false</code>
     *  
     * @since 3.1
     */
    public boolean isEditable();
 
    /**
	 * Returns whether this working set should be shown in user interface
	 * components that list working sets by name.
	 * 
	 * @return <code>true</code> if the working set should be shown in the
	 *         user interface; otherwise <code>false</code>
	 * 
	 * @since 3.2
	 */
	public boolean isVisible();
    
    /**
	 * Return the name of this working set, formated for the end user. Often this value is
	 * the same as the one returned from {@link #getName()}.
	 * 
	 * <p>
	 * <em>Please note: This API is experiemental and may change before 3.2 ships.</em>
	 * </p>
	 * 
	 * @return the name of this working set, formated for the end user
	 * @since 3.2
	 */
    public String getLabel();
    
    /**
	 * Set the name of this working set, formated for the end user.
	 * 
	 * <p><em>Please note: This API is experiemental and may change before 3.2 ships.</em></p>
	 * 
	 * @param label the unique id for this working set
	 * @since 3.2
	 */
	public void setLabel(String label);
	
	/**
	 * Returns <code>true</code> if this working set is capable of updating
	 * itself and reacting to changes in the state of its members. For
	 * non-aggregate working sets this means that the working set has an
	 * {@link IWorkingSetUpdater} installed while for aggregates it means that
	 * all component sets have {@link IWorkingSetUpdater}s installed. Otherwise
	 * returns <code>false</code>.
	 * 
	 * <p>
	 * <em>Please note: This API is experiemental and may change before 3.2 ships.</em>
	 * </p>
	 * 
	 * @return whether the set is self-updating or not
	 * @since 3.2
	 */
	public boolean isSelfUpdating();
	
	/**
	 * Returns whether this working set is an aggregate working set or not.
	 * 
	 * <p><em>Please note: This API is experiemental and may change before 3.2 ships.</em></p>
	 * 
	 * @return whether this working set is an aggregate working set or not
	 * @since 3.2
	 */
	public boolean isAggregateWorkingSet();	
}
