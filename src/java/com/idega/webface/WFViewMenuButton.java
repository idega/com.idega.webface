/*
 * $Id: WFViewMenuButton.java,v 1.6 2007/05/30 15:09:18 gediminas Exp $
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
 * Last modified: $Date: 2007/05/30 15:09:18 $ by $Author: gediminas $
 *
 * @author Anders Lindman
 * @version $Revision: 1.6 $
 */
public class WFViewMenuButton extends WFTab {

	/**
	 * Default constructor. 
	 */
	public WFViewMenuButton() {
		super();
	}

	/**
	 * Constructs a view menu button with the specified label text. 
	 */
	public WFViewMenuButton(String buttonLabel) {
		super(buttonLabel);
		setImmediate(true);
	}
	
	/**
	 * Constructs a view menu button with the specified label text.
	 */
	public WFViewMenuButton(String buttonLabel, boolean isVB) {
		super(buttonLabel, isVB);
		setImmediate(true);
	}
	
}
