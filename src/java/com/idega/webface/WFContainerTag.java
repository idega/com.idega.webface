/*
 * Created on Mar 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;


/**
 * @author thomas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WFContainerTag extends UIComponentTag {

	private String styleClass;
	private String _style;
	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "WFContainer";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		// TODO Auto-generated method stub
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
	
	public String getStyle() {
		return _style;
	}
	
	public void setStyle(String style) {
		this._style = style;
	}
	
	public void release() {      
		super.release();      
		styleClass = null ;
		_style=null;
	}

	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		WFContainer container = (WFContainer)component;
		if (container != null) {
			if(this.styleClass!=null) {
				container.setStyleClass(styleClass);
			}
			if(this._style!=null) {
				container.setStyleAttribute(_style);
			}
		}
	}
	
}
