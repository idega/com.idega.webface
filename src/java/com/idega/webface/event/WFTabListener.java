/*
 * $Id: WFTabListener.java,v 1.1 2004/10/19 11:09:29 tryggvil Exp $
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
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public interface WFTabListener extends FacesListener {
	
	/**
	 * Taskbar button pressed. 
	 */
	public void tabPressed(WFTabEvent e);
}
