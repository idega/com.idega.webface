/*
 * $Id: WFViewMenu.java,v 1.4 2004/06/18 14:11:02 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Menu with buttons for switching the view root. 
 * <p>
 * Last modified: $Date: 2004/06/18 14:11:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class WFViewMenu extends WFContainer implements ActionListener {
	
	private String _selectedButtonId = null;
	private List _buttonIds = null;
	private String _viewMenuStyleClass = null;
	private String _buttonSelectedStyleClass = null;
	private String _buttonDeselectedStyleClass = null;
	
	/**
	 * Default contructor.
	 */
	public WFViewMenu() {
		_buttonIds = new ArrayList();
		setViewMenuStyleClass("wf_viewmenu");
		setButtonSelectedStyleClass("wf_viewmenubuttonselected");
		setButtonDeselectedStyleClass("wf_viewmenubuttondeselected");
	}

	/**
	 * Returns the css class for this view menu.
	 */
	public String getViewMenuStyleClass() {
		return _viewMenuStyleClass;
	}

	/**
	 * Returns the css class for selected view menu buttons.
	 */
	public String getButtonSelectedStyleClass() {
		return _buttonSelectedStyleClass;
	}

	/**
	 * Returns the css class for deselected view menu buttons.
	 */
	public String getButtonDeselectedStyleClass() {
		return _buttonDeselectedStyleClass;
	}

	/**
	 * Returns the id for the selected button.
	 */
	public String getSelectedButtonId() {
		return _selectedButtonId;
	}

	/**
	 * Sets the css class for this view menu. 
	 */
	public void setViewMenuStyleClass(String taskMenuStyleClass) {
		_viewMenuStyleClass = taskMenuStyleClass;
	}

	/**
	 * Sets the css class for selected view menu button. 
	 */
	public void setButtonSelectedStyleClass(String buttonSelectedStyleClass) {
		_buttonSelectedStyleClass = buttonSelectedStyleClass;
	}

	/**
	 * Sets the css class for deselected view menu button. 
	 */
	public void setButtonDeselectedStyleClass(String buttonDeselectedStyleClass) {
		_buttonDeselectedStyleClass = buttonDeselectedStyleClass;
	}

	/**
	 * Sets the id for the selected button.
	 */
	public void setSelectedButtonId(String selectedButtonId) {
		_selectedButtonId = selectedButtonId;
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
	 * @param input the input component shown when the button is deselected 
	 */
	public WFViewMenuButton addButton(String buttonId, String buttonLabel, String viewId) {
		if (getFacet("viewselector") == null) {
			getFacets().put("viewselector", new WFViewSelector());
		}
		WFViewMenuButton button = new WFViewMenuButton(buttonLabel);
		button.setId(buttonId);
		button.addActionListener(this);
		button.setImmediate(true);
		_buttonIds.add(buttonId);
		if (_selectedButtonId == null) {
			_selectedButtonId = buttonId;
		}
		getFacets().put("button_" + buttonId, button);
		getAttributes().put("view_" + buttonId, viewId);
		return button; 
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		
		String viewId = context.getViewRoot().getViewId();
		viewId = viewId.substring(0, viewId.indexOf('.')) + ".jsf";
		for (Iterator iter = _buttonIds.iterator(); iter.hasNext();) {
			String buttonId = (String) iter.next();
			String buttonViewId = (String) getAttributes().get("view_" + buttonId);
			if (buttonViewId.equals(viewId)) {
				_selectedButtonId = buttonId;
				break;
			}
		}
		
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
		Object values[] = new Object[6];
		values[0] = super.saveState(ctx);
		values[1] = _selectedButtonId;
		values[2] = _buttonIds;
		values[3] = _viewMenuStyleClass;
		values[4] = _buttonSelectedStyleClass;
		values[5] = _buttonDeselectedStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_selectedButtonId = (String) values[1];
		_buttonIds = (List) values[2];
		_viewMenuStyleClass = (String) values[3];
		_buttonSelectedStyleClass = (String) values[4];
		_buttonDeselectedStyleClass = (String) values[5];
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		WFViewMenuButton button = (WFViewMenuButton) event.getComponent();
		WFViewMenu taskmenu = (WFViewMenu) button.getParent();
		taskmenu.setSelectedButtonId(button.getId());
	}
}
