/*
 * $Id: WFMenu.java,v 1.2 2004/11/14 23:38:39 tryggvil Exp $
 * Created on 27.10.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 *  This is an abstract base class for all "menu" style components, 
 *  such as Tab bars, Task bars, Vertical "side" menus etc.<br>
 *  These are usually rendered as an unordered list in HTML.
 * 
 *  Last modified: $Date: 2004/11/14 23:38:39 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.2 $
 */
public class WFMenu extends WFContainer {

	private String menuStyleClass = "wf_tabbar";
	private String _buttonSelectedStyleClass = "wf_tabbarbuttonselected";
	private String _buttonDeselectedStyleClass = "wf_tabbarbuttondeselected";
	private String _menuHeaderStyleClass = "wf_tabbarbuttondeselected";
	private Set _buttonIds = null;
	private String _selectedButtonId = null;
	
	public static String RENDERER_TYPE="wf_menu";
	
	// Standard styles
	
	/**
	 *The "tab" style classes
	 */
	private static String TAB_MENUSTYLECLASS = "wf_tabbar";
	private static String TAB_ITEMSELECTEDSTYLECLASS = "wf_tabbarbuttonselected";
	private static String TAB_ITEMDESELECTEDSTYLECLASS = "wf_tabbarbuttondeselected";
	private static String TAB_MENUHEADERSTYLECLASS = "wf_menuheader";
	
	/**
	 *The "vertical" style classes
	 */
	private static String VERTICAL_MENUSTYLECLASS = "wf_viewmenu";
	private static String VERTICAL_MENUHEADERSTYLECLASS = "wf_menuheader";
	private static String VERTICAL_ITEMSELECTEDSTYLECLASS = "wf_viewmenubuttonselected";
	private static String VERTICAL_ITEMDESELECTEDSTYLECLASS = "wf_viewmenubuttondeselected";
	

	public WFMenu() {
		super();
		setVerticalStyle();
	}
	
	public UIComponent getMenuHeader(){
		return this.getFacet("menu_header");
	}
	
	public void setMenuHeader(UIComponent menuHeader){
		if(menuHeader instanceof WFContainer){
			getFacets().put("menu_header",menuHeader);
		}
		else{
			WFContainer cont = new WFContainer();
			cont.setStyleClass(this.getMenuHeaderStyleClass());
			cont.getChildren().add(menuHeader);
			getFacets().put("menu_header",cont);
		}
	}
	
	public String getRendererType(){
		return RENDERER_TYPE;
	}
	
	/**
	 * Sets the menu to be "tab" style
	 */
	public void setTabStyle(){
		setMenuStyleClass(TAB_MENUSTYLECLASS);
		setSelectedMenuItemStyleClass(TAB_ITEMSELECTEDSTYLECLASS);
		setDeselectedMenuItemStyleClass(TAB_ITEMDESELECTEDSTYLECLASS);
		setMenuHeaderStyleClass(TAB_MENUHEADERSTYLECLASS);
	}
	
	/**
	 * Sets the menu to be "vertical menu" style
	 */
	public void setVerticalStyle(){
		setMenuStyleClass(VERTICAL_MENUSTYLECLASS);
		setSelectedMenuItemStyleClass(VERTICAL_ITEMSELECTEDSTYLECLASS);
		setDeselectedMenuItemStyleClass(VERTICAL_ITEMDESELECTEDSTYLECLASS);
		setMenuHeaderStyleClass(VERTICAL_MENUHEADERSTYLECLASS);
	}
	
	/**
	 * @return Returns the _menuHeaderStyleClass.
	 */
	public String getMenuHeaderStyleClass() {
		return _menuHeaderStyleClass;
	}
	/**
	 * @param headerStyleClass The _menuHeaderStyleClass to set.
	 */
	public void setMenuHeaderStyleClass(String headerStyleClass) {
		_menuHeaderStyleClass = headerStyleClass;
	}
	/**
	 * Returns the css class for this menu.
	 */
	public String getMenuStyleClass() {
		return menuStyleClass;
	}

	/**
	 * Returns the css class for selected task bar button.
	 */
	public String getSelectedMenuItemStyleClass() {
		return _buttonSelectedStyleClass;
	}

	/**
	 * Returns the css class for deselected task bar buttons.
	 */
	public String getDeselectedMenuItemStyleClass() {
		return _buttonDeselectedStyleClass;
	}

	/**
	 * Sets the css class for this menu. 
	 */
	public void setMenuStyleClass(String taskbarStyleClass) {
		menuStyleClass = taskbarStyleClass;
	}

	/**
	 * Sets the css class for selected task bar button. 
	 */
	public void setSelectedMenuItemStyleClass(String buttonSelectedStyleClass) {
		_buttonSelectedStyleClass = buttonSelectedStyleClass;
	}

	/**
	 * Sets the css class for deselected task bar button. 
	 */
	public void setDeselectedMenuItemStyleClass(String buttonDeselectedStyleClass) {
		_buttonDeselectedStyleClass = buttonDeselectedStyleClass;
	}
	
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[7];
		values[0] = super.saveState(ctx);
		values[1] = menuStyleClass;
		values[2] = _buttonSelectedStyleClass;
		values[3] = _buttonDeselectedStyleClass;
		values[4] = _selectedButtonId;
		values[5] = _buttonIds;
		values[6] = _menuHeaderStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		menuStyleClass = (String) values[1];
		_buttonSelectedStyleClass = (String) values[2];
		_buttonDeselectedStyleClass = (String) values[3];
		_selectedButtonId = (String) values[4];
		_buttonIds = (Set) values[5];
		_menuHeaderStyleClass = (String)values[6];
	}

	public Set getMenuItemIds() {
		if(_buttonIds==null){
			_buttonIds = new LinkedHashSet();
		}
		return this._buttonIds;
	}
	
	/**
	 * Returns the id for the selected button.
	 */
	public String getSelectedMenuItemId() {
		return _selectedButtonId;
	}

	/**
	 * Sets the id for the selected button.
	 */
	public void setSelectedMenuItemId(String selectedButtonId) {
		_selectedButtonId = selectedButtonId;
	}

	/**
	 * Adds a new menuItem and generates the id of the item.
	 * @param menuItemComponent The item to add to the menu.
	 * @return Return the generated id for the item.
	 */
	public String addMenuItem(UIComponent menuItemComponent){
		String nextMenuItemId = getNextMenuItemId();
		setMenuItem(nextMenuItemId,menuItemComponent);
		return nextMenuItemId;
	}	

	/**
	 * Generates a unique id for a new menu item.
	 * @return
	 */
	protected String getNextMenuItemId(){
		int maxValue = 0;
		for (Iterator iter = getMenuItemIds().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			int intValue = maxValue;
			try{
				intValue = Integer.parseInt(element);
			}
			catch(NumberFormatException nfe){}
			if(intValue>maxValue){
				maxValue=intValue;
			}
		}
		return String.valueOf(++maxValue);
	}
	
	
	
	/**
	 * Sets the menuComponent instance with the id given with menuItemId
	 * @param menuItemId
	 * @param menuItemComponent
	 */
	public void setMenuItem(String menuItemId,UIComponent menuItemComponent){
		/*getMenuItemIds().add(menuItemId);
		if (getSelectedMenuItemId() == null) {
			setSelectedMenuItemId(menuItemId);
		}*/
		if(!getMenuItemIds().contains(menuItemId)){
			getMenuItemIds().add(menuItemId);
		}
		this.getFacets().put("menuitem_"+menuItemId,menuItemComponent);
		//this.getFacets().put("button_"+menuItemId,menuItemComponent);
	}
	
	public UIComponent getMenuItem(String menuItemId){
		//return this.getFacet("button_"+menuItemId);
		return this.getFacet("menuitem_"+menuItemId);
	}
}
