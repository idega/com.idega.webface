/*
 * $Id: WFSimpleDocumentsViewTag.java,v 1.1 2004/11/15 21:38:09 tryggvil Exp $
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
 * Last modified: $Date: 2004/11/15 21:38:09 $ by $Author: tryggvil $
 *
 * @author tryggvil
 * @version $Revision: 1.1 $
 */
public class WFSimpleDocumentsViewTag extends UIComponentTag {
	
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
		return "WFSimpleDocumentsView";
	}
	
	
}
