/*
 * Created on 6.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.workspace;

import java.io.Serializable;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.text.Text;
import com.idega.webface.WFContainer;
import com.idega.webface.WFMenu;
import com.idega.webface.WFPlainOutputText;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTabbedPane;
import com.idega.webface.WFUtil;
import com.idega.webface.event.WFTabEvent;


/**
 * This class holds a "tab" or "task" style bar in the Workspace environment for 
 * all available applications/perspectives for a user.
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class WorkspaceBar extends WFContainer implements  Serializable{

	private final static String P = "cms_page_"; // Parameter prefix
	
	private final static String TASK_ID_CONTENT = P + "t_content";
	private final static String TASK_ID_EDIT = P + "t_edit";
	private final static String TASK_ID_BUILDER = P + "t_builder";
	private final static String TASK_ID_WEBVIEW = P + "t_webview";
	
	private final static String MAIN_TASKBAR_ID = P + "main_taskbar";
	private final static String ARTICLE_LIST_ID = P + "article_list";
	private final static String CASE_LIST_ID = P + "case_list";	
	
	
	private static String STYLE_CLASS="wf_workspacebar";
	
	/**
	 * 
	 */
	public WorkspaceBar() {
		super();
	}
	
	public void initializeContent(){
		setStyleClass(STYLE_CLASS);
		addApplicationDecoration();
		addTabbar();
		addLogin();
	}

	/**
	 * 
	 */
	private void addLogin() {
		//WFLogin login = new WFLogin();
		//login.setHeight("60");
		//login.setWidth("70");
		//login.setAllowCookieLogin(true);
		//login.setLayoutSingleLine();
		
		//this.add(login);
	}

	/**
	 * 
	 */
	private void addTabbar() {
		WFMenu bar = getMainTaskbar();
		add(bar);
	}

	/**
	 * 
	 */
	private void addApplicationDecoration() {
		WFContainer div = new WFContainer();
		div.setStyleClass("wf_appdecor");
		add(div);
		WFPlainOutputText text = new WFPlainOutputText();
		text.setValue("<i>e</i>Platform");
		div.add(text);
	}




	/**
	 * Returns the main task bar selector. 
	 */
	protected WFMenu getMainTaskbar() {

		WFTabBar tb = new WFTabBar();
		tb.setId(MAIN_TASKBAR_ID);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		ViewManager viewManager = ViewManager.getInstance(context);
		
		ViewNode workspaceNode = viewManager.getWorkspaceRoot();
		IWMainApplication iwma = IWMainApplication.getIWMainApplication(context);
		
		for (Iterator iter = workspaceNode.getChildren().iterator(); iter.hasNext();) {
			ViewNode subNode = (ViewNode) iter.next();
			String url = subNode.getURI();
			tb.addLink(subNode.getName(),url);
		}
		
		return tb;
	}
	
	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getContentPerspective() {
		UIComponent perspective = new Text("Content");
		return perspective;
	}

	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getWebViewPerspective() {
		UIComponent perspective = new Text("Webview");
		return perspective;
	}

	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getBuilderPerspective() {
		UIComponent perspective = new Text("Builder");
		return perspective;
	}	
	
	
	/**
	 * Called when the edit mode in the article block changes.
	 * @see com.idega.webface.event.WFTabListener#taskbarButtonPressed() 
	 */
	public void tabPressed(WFTabEvent e) {
		WFTabbedPane t = e.getTaskbar();
		/*UIComponent articleVersionBlock = t.findComponent(ArticleVersionBlock.ARTICLE_VERSION_BLOCK_ID);
		if (t.getSelectedButtonId().equals(ArticleBlock.TASK_ID_PREVIEW)) {
			articleVersionBlock.setRendered(true);
		} else {
			articleVersionBlock.setRendered(false);			
		}*/
	}

	
	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) {
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		/*WFTabBar tb = (WFTabBar) link.getParent().getParent().getParent().findComponent(MAIN_TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_EDIT);
		ArticleBlock ab = (ArticleBlock) tb.findComponent(ArticleBlock.ARTICLE_BLOCK_ID);
		ab.setEditMode();

		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "clear");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setLocaleId", "sv");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setHeadline", "headline");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setBody", id);
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setAuthor", "author");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setComment", "comment");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setDescription", "description");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setSource", "source");
		if (link.getId().equals(ArticleListBean.ARTICLE_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCase.STATUS_PUBLISHED);
		} else {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCase.STATUS_UNDER_REVIEW);
		}
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setMainCategoryId", new Integer(3));

		ab.updateEditButtons();*/
	}
		

}