/*
 * $Id: WFLinkMenu.java,v 1.10 2007/11/23 15:03:56 laddi Exp $
 * Created on 2.11.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import com.idega.util.FacesUtil;


/**
 * A menu whose menu items are plain html links.
 * 
 *  Last modified: $Date: 2007/11/23 15:03:56 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.10 $
 */
public class WFLinkMenu extends WFMenu {


	
	private Map links;
	
	/**
	 * 
	 */
	public WFLinkMenu() {
	}
	
	
	public HtmlOutputLink setMenuHeader(String text,String url){
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(url);
		HtmlOutputText outputText = WFUtil.getText(text);
		outputText.setStyleClass("menuItemSpan");
		link.getChildren().add(outputText);
		setMenuHeader(link);
		return link;
	}
	
	
	public HtmlOutputLink addLink(String text,String url){
		//boolean isSelected = isUrlSelected(url);
		boolean isSelected = false;
		return addLink(text,url,isSelected);
	}
	
	public HtmlOutputLink addLink(String text,String url,boolean selected){
		String menuItemId = getNextMenuItemId();
		return addLink(text,url,menuItemId,selected);
	}
	
	public HtmlOutputLink addLink(String text,String url,String menuItemId){
		//boolean isSelected = isUrlSelected(url);
		boolean isSelected = false;
		return addLink(text,url,menuItemId,isSelected);
	}
	
	public HtmlOutputLink addLink(String text,String url,String menuItemId,boolean selected){
		return addLink(WFUtil.getText(text), url, menuItemId, selected);
	}	
	
	public HtmlOutputLink addLink(HtmlOutputText text,String url,String menuItemId,boolean selected){
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(url);
		//link.setId(menuItemId);
		text.setStyleClass("menuItemSpan");
		link.getChildren().add(text);
		addToLinkMap(menuItemId,url);
		this.setMenuItem(menuItemId,link);
		if(selected){
			setSelectedMenuItemId(menuItemId);
		}
		return link;
	}
	
	public Map getLinks(){
		if(this.links==null){
			this.links=new HashMap();
		}
		return this.links;
	}
	
	
	protected void addToLinkMap(String menuItemId,String url){
		getLinks().put(menuItemId,url);
	}
	
	@Override
	public Set getMenuItemIds(){
		//return getLinks().keySet();
		return super.getMenuItemIds();
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = this.links;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this.links = ((Map) values[1]);
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException{
		detectSelectedMenuItem(context);
		super.encodeBegin(context);
	}
	
	protected void detectSelectedMenuItem(FacesContext context){
		String requestUrl = FacesUtil.getRequestUri(context,true);
		
		//String requestServletPath = context.getExternalContext().getRequestServletPath();
		//String requestInfo = context.getExternalContext().getRequestPathInfo();
		//HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		//String requestUrl = req.getRequestURI();
		for (Iterator iter = getMenuItemIds().iterator(); iter.hasNext();) {

			String id = (String) iter.next();
			UIComponent menuItem = getMenuItem(id);
			String url = null;
			if(menuItem instanceof HtmlOutputLink){
				HtmlOutputLink link = (HtmlOutputLink)menuItem;
				url = (String)link.getValue();
			}
			else if(menuItem instanceof WFLinkMenu){
				WFLinkMenu subMenu = (WFLinkMenu)menuItem;
				UIComponent menuHeader = subMenu.getMenuHeader();
				if(menuHeader instanceof HtmlOutputLink){
					HtmlOutputLink link = (HtmlOutputLink)menuHeader;
					url = (String)link.getValue();
				}
				else if(menuHeader instanceof WFContainer){
					WFContainer container = (WFContainer)menuHeader;
					for (Iterator iterator = container.getChildren().iterator(); iterator.hasNext();) {
						UIComponent element = (UIComponent) iterator.next();
						if(element instanceof HtmlOutputLink){
							HtmlOutputLink link = (HtmlOutputLink)element;
							url = (String)link.getValue();
						}
					}
				}
			}
			//String url = (String)getLinks().get(id);
			if(url!=null){
				if(requestUrl.startsWith(url)){
				//if(url.equals(requestUrl)){
					this.setSelectedMenuItemId(id);
				}
			}
		}
	}
	
	
}
