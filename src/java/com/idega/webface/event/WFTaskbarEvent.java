/*
 * $Id: WFTaskbarEvent.java,v 1.2 2004/06/18 14:11:02 anders Exp $
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

import com.idega.webface.WFTaskbar;

/**
 * Event fired from a WFTaskbar component when the user clicks a taskbar button.  
 * <p>
 * Last modified: $Date: 2004/06/18 14:11:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class WFTaskbarEvent extends FacesEvent {
	
	/**
	 * Constructs a taskbar event with the specified taskbar component.
	 * @param toolbar
	 */
	public WFTaskbarEvent(WFTaskbar taskbar) {
		super(taskbar);
	}
	
	/**
	 * Returns the WFTaskbar source for this event.
	 */
	public WFTaskbar getTaskbar() {
		return (WFTaskbar) this.getComponent();
	}

	/**
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof WFTaskbarListener);
	}

	/**
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public void processListener(FacesListener listener) {
		WFTaskbarListener l = (WFTaskbarListener) listener;
		l.taskbarButtonPressed(this);
	}
}