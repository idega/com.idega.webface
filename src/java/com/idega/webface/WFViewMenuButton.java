/*
 * $Id: WFViewMenuButton.java,v 1.5 2004/10/19 11:09:29 tryggvil Exp $
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
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.5 $
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
	public WFViewMenuButton(String buttonLabel, boolean isLocalized) {
		super(buttonLabel, isLocalized);
		setImmediate(true);
	}
	
}
