/*
 * $Id: WFTabBar.java,v 1.3 2004/11/01 15:00:47 tryggvil Exp $
 * Created on 27.10.2004
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
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 * This is a "Tab bar" that has its tabs as plain links.
 *  Last modified: $Date: 2004/11/01 15:00:47 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.3 $
 */
public class WFTabBar extends WFMenu {

	
	private Map links;
	
	/**
	 * 
	 */
	public WFTabBar() {
		setTabStyle();
	}
	
	public HtmlOutputLink addTab(String text,String url){

		String menuItemId = getNextMenuItemId();
		return addTab(text,url,menuItemId);
	}
	
	public HtmlOutputLink addTab(String text,String url,String menuItemId){
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(url);
		link.getChildren().add(WFUtil.getText(text));
		addLink(menuItemId,url);
		this.setMenuItem(menuItemId,link);
		
		return link;
	}
	
	public Map getLinks(){
		if(links==null){
			links=new HashMap();
		}
		return links;
	}
	
	
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
	
	protected void addLink(String menuItemId,String url){
		getLinks().put(menuItemId,url);
	}
	
	public Set getMenuItemIds(){
		return getLinks().keySet();
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = links;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		links = ((Map) values[1]);
	}
	
	public void encodeBegin(FacesContext context) throws IOException{
		detectSelectedTab(context);
		super.encodeBegin(context);
	}
	
	public void detectSelectedTab(FacesContext context){
		//String requestUrl = context.getExternalContext().getRequestServletPath();
		//String requestInfo = context.getExternalContext().getRequestPathInfo();
		HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		String requestUrl = req.getRequestURI();
		
		for (Iterator iter = getMenuItemIds().iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			String url = (String)getLinks().get(id);
			if(url.equals(requestUrl)){
				this.setSelectedMenuItemId(id);
			}
		}
		
	}
	
}
