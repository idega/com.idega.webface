/*
 * $Id: CMSPageTag.java,v 1.1 2004/06/08 16:14:21 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.webapp.UIComponentTag;

/**
 * JSP tag for content system test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:21 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class CMSPageTag extends UIComponentTag {
	
	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return null;
	}
		
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "CMSPage";
	}
}
