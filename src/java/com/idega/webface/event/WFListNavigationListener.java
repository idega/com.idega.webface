/*
 * $Id: WFListNavigationListener.java,v 1.1 2004/05/13 13:55:36 anders Exp $
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
 * Event listener interface for detecting navigation events from a WFList component.  
 * <p>
 * Last modified: $Date: 2004/05/13 13:55:36 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public interface WFListNavigationListener extends FacesListener {
	
	/**
	 * Updates the WFList component for the specified event. 
	 */
	public void updateList(WFListNavigationEvent e);
}
