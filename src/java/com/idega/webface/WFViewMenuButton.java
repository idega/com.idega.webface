/*
 * $Id: WFViewMenuButton.java,v 1.4 2004/06/30 13:35:21 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

/**
 * Button for WFViewMenu components.  
 * <p>
 * Last modified: $Date: 2004/06/30 13:35:21 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class WFViewMenuButton extends WFTaskbarButton {

	/**
	 * Default constructor. 
	 */
	public WFViewMenuButton() {
		super();
	}

	/**
	 * Constructs a view menu button with the specified label text. 
	 */
	public WFViewMenuButton(String buttonLabel, boolean isLocalized) {
		super(buttonLabel, isLocalized);
		setImmediate(true);
	}
	
}
