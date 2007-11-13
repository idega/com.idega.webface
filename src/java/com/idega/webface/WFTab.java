/*
 * $Id: WFTab.java,v 1.7 2007/11/13 09:31:10 laddi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * <p>
 * Component for each "Tab" and is used by the "WFTabbedPane" component.
 * <p>
 * Last modified: $Date: 2007/11/13 09:31:10 $ by $Author: laddi $
 *
 * @author Anders Lindman
 * @version $Revision: 1.7 $
 */
public class WFTab extends HtmlCommandLink {

	private boolean _selected = false;

	/**
	 * Default constructor. 
	 */
	public WFTab() {
		super();
	}

	/**
	 * Constructs a taskbar button with the specified label text. 
	 */
	public WFTab(String buttonLabel) {
		this();
		setLabel(buttonLabel);
	}

	public void setLabel(String buttonLabel) {
		if (WFUtil.isValueBinding(buttonLabel)) {
			ValueBinding vb = WFUtil.createValueBinding(buttonLabel);
			setValueBinding(WFUtil.VALUE_STRING, vb);
		}
		else {
			setValue(buttonLabel);
		}
	}
	
	/**
	 * Constructs a taskbar button with the specified label text.
	 */
	public WFTab(String buttonLabel, boolean isValueRef) {
		this();
		if (isValueRef) {
			getChildren().add(WFUtil.getTextVB(buttonLabel));
		} else {
			setLabel(buttonLabel);			
		}
	}
	
	/**
	 * Returns the selected status for this button.
	 */
	public boolean getSelected() {
		return this._selected;
	}
	
	/**
	 * Sets the selected status for this button.
	 */
	public void setSelected(boolean isSelected) {
		this._selected = isSelected;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
//		if (!this._selected) {
			super.encodeBegin(context);
//		} else {
//			if (getChildren().size() > 0) {
//				UIComponent c = (UIComponent) getChildren().get(0);
//				if (c != null) {
//					ResponseWriter out = context.getResponseWriter();
//					out.startElement("span",null);
//					String styleClass = getStyleClass();
//					if (styleClass != null) {
//						out.writeAttribute("class", styleClass, null);
//					}
//					c.encodeBegin(context);
//					c.encodeChildren(context);
//					c.encodeEnd(context);
//					out.endElement("span");
//				}
//			}
//		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		if (!this._selected) {
			super.encodeChildren(context);
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		if (!this._selected) {
			super.encodeEnd(context);
		}
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(this._selected);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this._selected = ((Boolean) values[1]).booleanValue();
	}
}
