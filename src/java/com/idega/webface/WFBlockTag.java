/*
 * $Id: WFBlockTag.java,v 1.2 2004/12/30 12:38:18 gimmi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * JSP tag for WFBlock
 * <p>
 * Last modified: $Date: 2004/12/30 12:38:18 $ by $Author: gimmi $
 *
 * @author tryggvil
 * @version $Revision: 1.2 $
 */
public class WFBlockTag extends UIComponentTag {
	
	private String title;
	
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
		return "WFBlock";
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void release() {      
		super.release();      
		title = null ;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			component.getAttributes().put("title", title);
		}
	}

}
