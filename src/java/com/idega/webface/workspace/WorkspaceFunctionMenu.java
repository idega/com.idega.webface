/*
 * $Id: WorkspaceFunctionMenu.java,v 1.1 2004/11/14 23:38:39 tryggvil Exp $
 * Created on 2.11.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.workspace;

import java.util.Iterator;
import javax.faces.context.FacesContext;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFLinkMenu;
import com.idega.webface.WFUtil;
import com.idega.webface.WFVerticalMenu;


/**
 * This class holds a "function menu" in the workspace environment for the current selected tab.
 * This menu is usually displayd to the left on the page.
 *  Last modified: $Date: 2004/11/14 23:38:39 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.1 $
 */
public class WorkspaceFunctionMenu extends WFBlock {

	/**
	 * 
	 */
	public WorkspaceFunctionMenu() {
		this("Functions");
	}

	/**
	 * @param titleBarText
	 */
	public WorkspaceFunctionMenu(String titleBarText) {
		super(titleBarText);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.idega.webface.WFBlock#initializeContent()
	 */
	protected void initializeContent() {

		FacesContext context = FacesContext.getCurrentInstance();
		ViewManager viewmanager = ViewManager.getInstance(context);
		
		ViewNode workspace = viewmanager.getWorkspaceRoot();
		ViewNode node = workspace.getChild(getApplication());
		
		WFVerticalMenu menu = new WFVerticalMenu();
		add(menu);
	
		populateMenu(menu,node);
	}
	
	public void setApplication(String application){
		this.getAttributes().put("application_menu",application);
	}
	
	public String getApplication(){
		return (String)getAttributes().get("application_menu");
	}
	
	public void populateMenu(WFLinkMenu menu,ViewNode node){
		for (Iterator iter = node.getChildren().iterator(); iter.hasNext();) {
			ViewNode childNode = (ViewNode) iter.next();
			if(childNode.getChildren().size()>0){
				WFVerticalMenu subMenu = new WFVerticalMenu();
				subMenu.setMenuHeader(WFUtil.getText(childNode.getName()));
				menu.addMenuItem(subMenu);
				
				populateMenu(subMenu,childNode);
				
			}
			else{
				String url = childNode.getURI();
				String name = childNode.getName();
				menu.addLink(name,url);
			}
			
		}
	}
}
