/*
 * $Id: WFToolbarButtonTag.java,v 1.2 2006/04/09 11:59:21 laddi Exp $
 * Created on 26.4.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import org.apache.myfaces.taglib.UIComponentTagBase;


/**
 * 
 *  Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.2 $
 */
public class WFToolbarButtonTag extends UIComponentTagBase {

	private String styleClass;
	private String displayText;
	/**
	 * 
	 */
	public WFToolbarButtonTag() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "WFToolbarButton";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return null;
	}
	
	public String getStyleClass() {
		return this.styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public void setClass(String styleClass) {
		setStyleClass(styleClass);
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	

	
	public void release() {      
		super.release();      
		this.styleClass = null;
		this.displayText = null;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			WFToolbarButton button = (WFToolbarButton)component;
			if(this.styleClass!=null) {
				button.setStyleClass(this.styleClass);
			}
			if(this.displayText!=null) {
				if (isValueReference(this.displayText)) {
	                ValueBinding vb = getFacesContext().getApplication().createValueBinding(this.displayText);
	                button.setValueBinding("displayText", vb);
	            } else {
	            		button.setDisplayText(this.displayText);
	            }
			}
		}
	}
}
