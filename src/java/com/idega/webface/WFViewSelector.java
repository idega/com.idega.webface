/*
 * $Id: WFViewSelector.java,v 1.4 2004/10/21 11:45:23 joakim Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * Component for selecting the view root. 
 * <p>
 * Last modified: $Date: 2004/10/21 11:45:23 $ by $Author: joakim $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class WFViewSelector extends UICommand implements ActionListener {
	
	private String _selectedViewId = null;
	private boolean _actionListenerAdded = false;
	
	/**
	 * Default contructor.
	 */
	public WFViewSelector() {
		super();
		setImmediate(true);
	}

	/**
	 * Returns the id for the selected view.
	 */
	public String getViewId() {
		return _selectedViewId;
	}

	/**
	 * Sets the id for the selected view.
	 */
	public void setViewId(String selectedViewId) {
		if (!_actionListenerAdded) {
			addActionListener(this);
			_actionListenerAdded = true;			
		}
		_selectedViewId = selectedViewId;
		//ActionEvent event = new ActionEvent(this);
		//queueEvent(event);
		
		//String viewId = this.getViewId();
		//WFUtil.setViewRoot(selectedViewId);
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch(selectedViewId);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = _selectedViewId;
		values[2] = new Boolean(_actionListenerAdded);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_selectedViewId = (String) values[1];
		_actionListenerAdded = ((Boolean) values[2]).booleanValue();
	}
	
	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) {
		WFViewSelector vs = (WFViewSelector) event.getComponent();
		String viewId = vs.getViewId();
		WFUtil.setViewRoot(viewId);
	}
}
