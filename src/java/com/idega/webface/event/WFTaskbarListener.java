/*
 * $Id: WFTaskbarListener.java,v 1.1 2004/06/07 07:49:59 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.event;

import javax.faces.event.FacesListener;

/**
 * Event listener interface for detecting from a WFTaskbar component.  
 * <p>
 * Last modified: $Date: 2004/06/07 07:49:59 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public interface WFTaskbarListener extends FacesListener {
	
	/**
	 * Taskbar button pressed. 
	 */
	public void taskbarButtonPressed(WFTaskbarEvent e);
}
