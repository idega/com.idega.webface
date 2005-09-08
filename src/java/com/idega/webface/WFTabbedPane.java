/*
 * $Id: WFTabbedPane.java,v 1.4 2005/09/08 23:06:05 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.event.WFTabEvent;
import com.idega.webface.event.WFTabListener;

/**
 * This is a class for a "Tab bar" which is event controlled and manages its Tabs and
 * each tabs "perspective".
 * A perspective can be any component that is rendered when
 * its tab bar button is pressed.   
 * <p>
 * Last modified: $Date: 2005/09/08 23:06:05 $ by $Author: tryggvil $
 *
 * @author Anders Lindman,Tryggvi Larusson
 * @version $Revision: 1.4 $
 */
public class WFTabbedPane extends WFMenu implements ActionListener {
	
	//public static String RENDERER_TYPE="wf_tabbar";
	
	

	/*
	private String _taskbarStyleClass = "wf_tabbar";
	private String _buttonSelectedStyleClass = "wf_tabbarbuttonselected";
	private String _buttonDeselectedStyleClass = "wf_tabbarbuttondeselected";
	*/
	private String _mainAreaStyleClass = "wf_tabbarmainarea";
	
	/**
	 * Default contructor.
	 */
	public WFTabbedPane() {
		setTabStyle();
	}

	/**
	 * Returns the css class for the main container perspective area.
	 */
	public String getMainAreaStyleClass() {
		return _mainAreaStyleClass;
	}



	/**
	 * Sets the css class for the perspecitive main area container. 
	 */
	public void setMainAreaStyleClass(String mainAreaStyleClass) {
		_mainAreaStyleClass = mainAreaStyleClass;
	}

	/**
	 * Adds a tab button with its corresponding perspective component.
	 */
	public WFTab addTab(String menuItemId, String buttonLabel, UIComponent perspective, boolean isValueRef) {
		WFTab tab = new WFTab(buttonLabel, isValueRef);
		tab.setId(menuItemId);
//		tab.setValue(buttonLabel);
		tab.addActionListener(this);
		tab.setImmediate(true);
		
		tab.setStyleClass(menuItemId);
		//getFacets().put("button_" + menuItemId, button);
		setMenuItem(menuItemId,tab);
		//getFacets().put("perspective_" + menuItemId, c);
		setPerspective(menuItemId,perspective);
		return tab;
	}
	
	/**
	 * Adds a tastbar button with its corresponding perspective component.
	 */
	public WFTab addTab(String menuItemId, String buttonLabel, UIComponent perspective) {
		return addTab(menuItemId, menuItemId, perspective, false);
	}
	
	/**
	 * Adds a tastbar button with value bin ding label and its corresponding perspective component.
	 */
	public WFTab addTabVB(String menuItemId, String buttonLabelRef, UIComponent perspective) {
		return addTab(menuItemId, buttonLabelRef, perspective, true);
	}
	
	public WFTab getTab(String menuItemId){
		return (WFTab)getMenuItem(menuItemId);
	}
	
	/**
	 * Sets the perspective component for the given menuItemId.<br>
	 * This perpective component is rendered when the button with id menuItemId is pressed.
	 * @param menuItemId
	 * @param perspective
	 */
	public void setPerspective(String menuItemId,UIComponent perspective){
		WFContainer container = new WFContainer();
		container.setStyleClass(getMainAreaStyleClass());
		container.add(perspective);
		
		getFacets().put("perspective_" + menuItemId, container);
	}
	
	
	public UIComponent getPerspective(String menuItemId){
		return (UIComponent) getFacets().get("perspective_" + menuItemId);
	}
	
	/*
	 *
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		ResponseWriter out = context.getResponseWriter();
		// Taskbar buttons
		out.startElement("table", null);
		if (getTaskbarStyleClass() != null) {
			out.writeAttribute("class", getTaskbarStyleClass(), null);
		}
		out.writeAttribute("id", "" + getId(), null);
		out.startElement("tr", null);
		Iterator iter = _buttonIds.iterator();
		while (iter.hasNext()) {
			String buttonId = (String) iter.next();
			String buttonStyleClass = getButtonDeselectedStyleClass();
			WFTab button = (WFTab) getFacet("button_" + buttonId);
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
		}
		out.endElement("tr");
		out.endElement("table");
	}
	*/
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		renderChild(context, getPerspective(getSelectedMenuItemId()));
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	public String getRendererType(){
		return RENDERER_TYPE;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = _mainAreaStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_mainAreaStyleClass = (String) values[1];
	}
	
	/**
	 * Register the specified listener for taskbar events.
	 */
	public void addTabListener(WFTabListener listener) {
		addFacesListener(listener);
	}

	/**
	 * Remove the specified j‡listener for taskbar events.
	 */
	public void removeTabListener(WFTabListener listener) {
		removeFacesListener(listener);
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		WFTab button = (WFTab) event.getComponent();
		WFTabbedPane taskbar = (WFTabbedPane) button.getParent();
		taskbar.setSelectedMenuItemId(button.getId());
		WFTabEvent e = new WFTabEvent(taskbar);
		//(JJ) Cant do this since it will create a concurrent modification exception
//		taskbar.queueEvent(e);
        try
        {
        	taskbar.broadcast(e);
        }
        catch (AbortProcessingException e1)
        {
        	e1.printStackTrace();
        }
	}
	
}
