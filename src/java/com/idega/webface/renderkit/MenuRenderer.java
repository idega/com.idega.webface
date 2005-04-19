/*
 * $Id: MenuRenderer.java,v 1.6 2005/04/19 19:24:27 gummi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.webface.WFContainer;
import com.idega.webface.WFMenu;
import com.idega.webface.WFTab;

/**
 *  The renderer for the TabBar component.
 * 
 *  Last modified: $Date: 2005/04/19 19:24:27 $ by $Author: gummi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.6 $
 */
public class MenuRenderer extends ContainerRenderer {

	//setTaskbarStyleClass("wf_taskbar");
	//setMainAreaStyleClass("wf_taskbarmainarea");
	//setButtonSelectedStyleClass("wf_taskbarbuttonselected");
	//setButtonDeselectedStyleClass("wf_taskbarbuttondeselected");
	
	
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent comp)
			throws IOException {
//		super.encodeBegin(context,comp);
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext ctx, UIComponent comp)
			throws IOException {
		super.encodeChildren(ctx, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent comp)
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
			
			
	
			Iterator iter = menu.getMenuItemIds().iterator();
			while (iter.hasNext()) {
				String buttonId = (String) iter.next();
				String buttonStyleClass = menu.getDeselectedMenuItemStyleClass();
				
				UIComponent menuItem = menu.getMenuItem(buttonId);
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
