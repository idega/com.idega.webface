/*
 * $Id: ToolbarRenderer.java,v 1.8 2006/04/09 11:59:21 laddi Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.webface.WFContainer;
import com.idega.webface.WFMenu;
import com.idega.webface.WFMenuItems;
import com.idega.webface.WFTab;



/**
 * <p>
 * Default Renderer for the WFToolbar component.
 * </p>
 *  Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.8 $
 */
public class ToolbarRenderer extends MenuRenderer {
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent comp)
			throws IOException {
//		super.encodeBegin(context,comp);
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext ctx, UIComponent comp)
			throws IOException {
//		super.encodeChildren(ctx, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent comp)
			throws IOException {
		WFMenu menu = (WFMenu)comp;
		
		//super.encodeBegin(context,comp);
		
		ResponseWriter out = context.getResponseWriter();
		
		UIComponent menuHeader = menu.getMenuHeader();
		if(menuHeader!=null){
			this.renderChild(context,menuHeader);
		}
		
		if(!menu.isEmpty()){
			out.startElement("div",null);
			String mainStyle = menu.getMenuStyleClass();
			if (mainStyle != null) {
				out.writeAttribute("class", mainStyle+"container", null);
			}
			out.startElement("ul", null);
			if (mainStyle != null) {
				out.writeAttribute("class", mainStyle, null);
			}
			out.writeAttribute("id", "" + comp.getId(), null);
			//out.startElement("tr", null);
			//MenuItems:
			
			
			WFMenuItems menuItems = null;
			List itemList = null;
			if(menu.getChildCount() > 1){
				itemList = menu.getChildren();
			} else {
				Iterator childs = menu.getChildren().iterator();
				while (childs.hasNext()) {
					Object obj = childs.next();
					if(obj instanceof WFMenuItems){
						menuItems = (WFMenuItems)obj;
						break;
					}
				}
				
				if(menuItems != null){
					itemList = (List)menuItems.getValue();
				} else {
					itemList = new ArrayList();
					Map menuFacetMap = menu.getFacets();
					Set keys = menuFacetMap.keySet();
					Iterator iter = keys.iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						if(key.startsWith("menuitem_")) {
							itemList.add(menuFacetMap.get(key));
						}
					}
				}
			}
			
			
			
			
			Iterator iter = itemList.iterator();
			boolean isFirstItem = true;
			boolean hasNext = iter.hasNext();
			
			while (hasNext) {
				UIComponent menuItem =  (UIComponent) iter.next();
				hasNext=iter.hasNext();
				String buttonId = menuItem.getId();
				String buttonStyleClass = menu.getDeselectedMenuItemStyleClass();
				
				if(menuItem.isRendered()){
					if(menuItem instanceof WFTab){
						WFTab tab = (WFTab) menuItem;
						if (buttonId.equals(menu.getSelectedMenuItemId())) {
							tab.setSelected(true);
							buttonStyleClass = menu.getSelectedMenuItemStyleClass();
						} else {
							tab.setSelected(false);
						}
					}
					if (buttonId.equals(menu.getSelectedMenuItemId())) {
						buttonStyleClass = menu.getSelectedMenuItemStyleClass();
					} 
					out.startElement("li", null);
					if (buttonStyleClass != null) {
						if(isFirstItem){
							buttonStyleClass+=" first";
							isFirstItem=false;
						}
						
						if(!hasNext){
							//this is the last item:
							buttonStyleClass+=" last";
							isFirstItem=false;							
						}
						
						out.writeAttribute("class", buttonStyleClass, null);
					}
					renderChild(context,menuItem);
					//renderFacet(context,menu, "button_" + buttonId);
					out.endElement("li");
				}
			}
			//out.endElement("tr");
			out.endElement("ul");
			out.endElement("div");
		}
		
//		super.encodeEnd(context,comp);
	}
	
	protected String getStyleClass(WFContainer container){
		//Overrided from superclass:
		//return WFConstants.STYLE_CLASS_BOX;
		return super.getStyleClass(container);
	}
	
	/* (non-Javadoc)
	 * @see com.idega.webface.redmond.ContainerRenderer#getStyleAttributes(com.idega.webface.WFContainer)
	 */
	protected String getStyleAttributes(WFContainer container) {
		// TODO Auto-generated method stub
		WFMenu bar = (WFMenu)container;
		String attr = super.getStyleAttributes(container);
		String backgroundColor= this.getCssHelper().getBackgroundColorAttribute(bar.getBackgroundColor());
		if(backgroundColor!=null) {
			if(attr==null) {
				attr=backgroundColor;
			}
			else {
				attr+=backgroundColor;
			}
		}
		return attr;
	}
}
