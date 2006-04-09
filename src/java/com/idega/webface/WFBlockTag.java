/*
 * $Id: WFBlockTag.java,v 1.7 2006/04/09 11:59:21 laddi Exp $
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
 * Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 *
 * @author tryggvil
 * @version $Revision: 1.7 $
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
		this.title = null ;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			if(this.title!=null){
				/*if(isValueReference(title)){
					ValueBinding vb = getFacesContext().getApplication().createValueBinding(title);
					component.setValueBinding("title", vb);
				} else {
					component.getAttributes().put("title", title);
				}*/
				WFBlock block = (WFBlock)component;
				block.setTitle(this.title);
			}
			if(getMaximizedVertically()!=null){
				WFBlock block = (WFBlock)component;
				boolean maximized = Boolean.valueOf(getMaximizedVertically()).booleanValue();
				block.setMaximizedVertically(maximized);
			}
		}
	}

}
