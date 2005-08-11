/*
 * $Id: WFWebViewTag.java,v 1.2 2005/08/11 18:42:43 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.faces.webapp.UIComponentTag;

/**
 * JSP tag for WFBlock
 * <p>
 * Last modified: $Date: 2005/08/11 18:42:43 $ by $Author: tryggvil $
 *
 * @author tryggvil
 * @version $Revision: 1.2 $
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
