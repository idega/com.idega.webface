/*
 * $Id: WFBlockTag.java,v 1.5 2006/02/28 12:03:16 tryggvil Exp $
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
 * Last modified: $Date: 2006/02/28 12:03:16 $ by $Author: tryggvil $
 *
 * @author tryggvil
 * @version $Revision: 1.5 $
 */
public class WFBlockTag extends UIComponentTag {
	
	private String title;
	private String maximizedVertically;
	
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
	
	/**
	 * @return Returns the maximizedVertically.
	 */
	public String getMaximizedVertically() {
		return maximizedVertically;
	}

	
	/**
	 * @param maximizedVertically The maximizedVertically to set.
	 */
	public void setMaximizedVertically(String maximizedVertically) {
		this.maximizedVertically = maximizedVertically;
	}

	
	public void release() {      
		super.release();      
		title = null ;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			if(title!=null){
				/*if(isValueReference(title)){
					ValueBinding vb = getFacesContext().getApplication().createValueBinding(title);
					component.setValueBinding("title", vb);
				} else {
					component.getAttributes().put("title", title);
				}*/
				WFBlock block = (WFBlock)component;
				block.setTitle(title);
			}
			if(getMaximizedVertically()!=null){
				WFBlock block = (WFBlock)component;
				boolean maximized = Boolean.valueOf(getMaximizedVertically()).booleanValue();
				block.setMaximizedVertically(maximized);
			}
		}
	}

}
