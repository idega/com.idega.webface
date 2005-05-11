/*
 * $Id: WFToolbarButtonTag.java,v 1.1 2005/05/11 17:52:51 gummi Exp $
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
 *  Last modified: $Date: 2005/05/11 17:52:51 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.1 $
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
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public void setClass(String styleClass) {
		setStyleClass(styleClass);
	}
	
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	

	
	public void release() {      
		super.release();      
		styleClass = null;
		displayText = null;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			WFToolbarButton button = (WFToolbarButton)component;
			if(this.styleClass!=null) {
				button.setStyleClass(styleClass);
			}
			if(this.displayText!=null) {
				if (isValueReference(displayText)) {
	                ValueBinding vb = getFacesContext().getApplication().createValueBinding(displayText);
	                button.setValueBinding("displayText", vb);
	            } else {
	            		button.setDisplayText(displayText);
	            }
			}
		}
	}
}
