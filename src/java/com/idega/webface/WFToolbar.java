/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import javax.faces.component.UIComponent;


/**
 * A menu component with toolbar buttons. 
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFToolbar extends WFMenu {
	
	public static String RENDERER_TYPE="wf_toolbar";
	public static String DEFAULT_STYLE_CLASS=RENDERER_TYPE;
	public static String DEFAULT_BUTTON_STYLE_CLASS=DEFAULT_STYLE_CLASS+"_button";
	public static String DEFAULT_HEADER_STYLE_CLASS=DEFAULT_STYLE_CLASS+"_header";
	
	public WFToolbar(){
		setRendererType(RENDERER_TYPE);
		setToolbarStyle();
	}
	
	/**
	 * Sets the menu to be "toolbar" style
	 */
	protected void setToolbarStyle(){
		setMenuStyleClass(DEFAULT_STYLE_CLASS);
		setSelectedMenuItemStyleClass(DEFAULT_BUTTON_STYLE_CLASS);
		setDeselectedMenuItemStyleClass(DEFAULT_BUTTON_STYLE_CLASS);
		setMenuHeaderStyleClass(DEFAULT_HEADER_STYLE_CLASS);
		setRendererType(RENDERER_TYPE);
	}

	
	public void addButton(WFToolbarButton button){
		String menuItemId = getNextMenuItemId();
		setMenuItem(menuItemId,button);
	}
	
	public void addButton(UIComponent button){
		String menuItemId = getNextMenuItemId();
		setMenuItem(menuItemId,button);
	}

}
