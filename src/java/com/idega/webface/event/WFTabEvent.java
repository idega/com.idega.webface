/*
 * $Id: WFTabEvent.java,v 1.3 2006/01/04 14:43:11 tryggvil Exp $
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

import com.idega.webface.WFTabbedPane;

/**
 * Event fired from a WFTaskbar component when the user clicks a taskbar button.  
 * <p>
 * Last modified: $Date: 2006/01/04 14:43:11 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class WFTabEvent extends FacesEvent {
	
	/**
	 * Constructs a taskbar event with the specified taskbar component.
	 * @param toolbar
	 */
	public WFTabEvent(WFTabbedPane taskbar) {
		super(taskbar);
	}
	
	/**
	 * Returns the WFTaskbar source for this event.
	 */
	public WFTabbedPane getTabbedPane() {
		return (WFTabbedPane) this.getComponent();
	}

	/**
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof WFTabListener);
	}

	/**
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public void processListener(FacesListener listener) {
		WFTabListener l = (WFTabListener) listener;
		l.tabPressed(this);
	}
}
