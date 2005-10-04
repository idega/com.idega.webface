/*
 * $Id: WFWebViewTag.java,v 1.3 2005/10/04 11:49:49 gimmi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;


/**
 * JSP tag for WFBlock
 * <p>
 * Last modified: $Date: 2005/10/04 11:49:49 $ by $Author: gimmi $
 *
 * @author tryggvil
 * @version $Revision: 1.3 $
 */
public class WFWebViewTag extends WFBlockTag {
	
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
		return "WFWebView";
	}
	
	
}
