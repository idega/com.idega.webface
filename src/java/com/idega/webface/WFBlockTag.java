/*
 * $Id: WFBlockTag.java,v 1.7.2.1 2007/01/24 08:22:45 gediminas Exp $
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
 * Last modified: $Date: 2007/01/24 08:22:45 $ by $Author: gediminas $
 *
 * @author tryggvil
 * @version $Revision: 1.7.2.1 $
 */
public class WFBlockTag extends UIComponentTag {
	
	private String title;
	private String maximizedVertically;
	private String styleClass;
	
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
		return this.maximizedVertically;
	}

	
	/**
	 * @param maximizedVertically The maximizedVertically to set.
	 */
	public void setMaximizedVertically(String maximizedVertically) {
		this.maximizedVertically = maximizedVertically;
	}

	
	public void release() {      
		super.release();      
		this.title = null;
		this.styleClass = null;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component instanceof WFBlock) {
			WFBlock block = (WFBlock) component;
			if(this.title!=null){
				/*if(isValueReference(title)){
					ValueBinding vb = getFacesContext().getApplication().createValueBinding(title);
					component.setValueBinding("title", vb);
				} else {
					component.getAttributes().put("title", title);
				}*/
				block.setTitle(this.title);
			}
			if(getMaximizedVertically()!=null){
				boolean maximized = Boolean.valueOf(getMaximizedVertically()).booleanValue();
				block.setMaximizedVertically(maximized);
			}
			if (this.styleClass != null) {
				block.setStyleClass(this.styleClass);
			}
		}
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
