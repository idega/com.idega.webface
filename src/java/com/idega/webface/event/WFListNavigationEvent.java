/*
 * $Id: WFListNavigationEvent.java,v 1.1 2004/05/13 13:55:36 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.event;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import com.idega.webface.WFList;

/**
 * Event fired from a WFList component when the user navigates the list.  
 * <p>
 * Last modified: $Date: 2004/05/13 13:55:36 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFListNavigationEvent extends FacesEvent {
	
	/**
	 * Constructs a list navigation event with the specified list component.
	 * @param toolbar
	 */
	public WFListNavigationEvent(WFList list) {
		super(list);
	}
	
	/**
	 * Returns the WFList source for this event.
	 */
	public WFList getList() {
		return (WFList) this.getSource();
	}

	/**
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof WFListNavigationListener);
	}

	/**
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public void processListener(FacesListener listener) {
		WFListNavigationListener l = (WFListNavigationListener) listener;
		l.updateList(this);
	}
}
