/*
 * $Id: WFViewMenu.java,v 1.7 2004/11/01 15:00:47 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Menu with buttons for switching the view root. 
 * <p>
 * Last modified: $Date: 2004/11/01 15:00:47 $ by $Author: tryggvil $
 *
 * @author Anders Lindman,Tryggvi Larusson
 * @version $Revision: 1.7 $
 */
public class WFViewMenu extends WFMenu implements ActionListener {
	
	//private String _selectedButtonId = null;
	//private List _buttonIds = null;
	//private String _viewMenuStyleClass = null;
	//private String _buttonSelectedStyleClass = null;
	//private String _buttonDeselectedStyleClass = null;
	
	/**
	 * Default contructor.
	 */
	public WFViewMenu() {
		setVerticalStyle();
	}

	/**
	 * Sets the id for the selected button.
	 */
	public void setSelectedMenuItemId(String selectedButtonId) {
		//_selectedButtonId = selectedButtonId;
		super.setSelectedMenuItemId(selectedButtonId);
		WFViewSelector vs = (WFViewSelector) getFacet("viewselector");
		if (vs != null) {
			String viewId = (String) getAttributes().get("view_" + selectedButtonId);
			if (viewId != null) {
				vs.setViewId(viewId);
			}
		}
	}
	
	/**
	 * Adds a view menu button with its corresponding view id.
	 */
	public WFViewMenuButton addButton(String buttonId, String buttonLabel, String viewId, boolean isValueRef) {
		if (getFacet("viewselector") == null) {
			getFacets().put("viewselector", new WFViewSelector());
		}
		WFViewMenuButton button = new WFViewMenuButton(buttonLabel, isValueRef);
		button.setId(buttonId);
		button.addActionListener(this);
		button.setImmediate(true);
		getMenuItemIds().add(buttonId);
		if (getSelectedMenuItemId() == null) {
			setSelectedMenuItemId(buttonId);
		}
		getFacets().put("button_" + buttonId, button);
		getAttributes().put("view_" + buttonId, viewId);
		return button; 
	}
	
	/**
	 * Adds a view menu button with its corresponding view id.
	 */
	public WFViewMenuButton addButton(String buttonId, String buttonLabel, String viewId) {
		return addButton(buttonId, buttonLabel, viewId, false);
	}
	
	/**
	 * Adds a view menu button with value binding label and its corresponding view id.
	 */
	public WFViewMenuButton addButtonVB(String buttonId, String buttonLabelRef, String viewId) {
		return addButton(buttonId, buttonLabelRef, viewId, true);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		
		String viewId = context.getViewRoot().getViewId();
		int indexOfDot = viewId.indexOf('.');
		if(indexOfDot!=-1){
			viewId = viewId.substring(0, indexOfDot) + ".jsf";
			for (Iterator iter = getMenuItemIds().iterator(); iter.hasNext();) {
				String buttonId = (String) iter.next();
				String buttonViewId = (String) getAttributes().get("view_" + buttonId);
				if (buttonViewId.equals(viewId)) {
					setSelectedMenuItemId(buttonId);
					break;
				}
			}
		}
		super.encodeBegin(context);
		
		/*
		ResponseWriter out = context.getResponseWriter();
		// Task menu buttons
		out.startElement("table", null);
		if (getViewMenuStyleClass() != null) {
			out.writeAttribute("class", getViewMenuStyleClass(), null);
		}
		Iterator iter = _buttonIds.iterator();
		while (iter.hasNext()) {
			String buttonId = (String) iter.next();
			out.startElement("tr", null);
			String buttonStyleClass = getButtonDeselectedStyleClass();
			WFViewMenuButton button = (WFViewMenuButton) getFacet("button_" + buttonId);
			if (buttonId.equals(_selectedButtonId)) {
				button.setSelected(true);
				buttonStyleClass = getButtonSelectedStyleClass();
			} else {
				button.setSelected(false);
			}
			out.startElement("td", null);
			if (buttonStyleClass != null) {
				out.writeAttribute("class", buttonStyleClass, null);
			}
			renderFacet(context, "button_" + buttonId);
			out.endElement("td");
			out.endElement("tr");
		}
		out.endElement("table");
		*/
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		return super.saveState(ctx);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		super.restoreState(ctx,state);
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		WFViewMenuButton button = (WFViewMenuButton) event.getComponent();
		WFViewMenu taskmenu = (WFViewMenu) button.getParent();
		taskmenu.setSelectedMenuItemId(button.getId());
	}
}
