/*
 * $Id: WFBlockTag.java,v 1.4 2005/05/17 16:12:12 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * JSP tag for WFBlock
 * <p>
 * Last modified: $Date: 2005/05/17 16:12:12 $ by $Author: tryggvil $
 *
 * @author tryggvil
 * @version $Revision: 1.4 $
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
			if(title!=null){
				if(isValueReference(title)){
					ValueBinding vb = getFacesContext().getApplication().createValueBinding(title);
					component.setValueBinding("title", vb);
				} else {
					component.getAttributes().put("title", title);
				}
			}
		}
	}

}
