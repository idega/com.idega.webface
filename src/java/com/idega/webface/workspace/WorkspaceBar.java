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
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.idega.webface.WFContainer;
import com.idega.webface.WFPage;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFPlainOutputText;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFUtil;
import com.idega.webface.event.WFTabEvent;
import com.idega.webface.event.WFTabListener;
import com.idega.webface.test.ContentPerspective;
import com.idega.webface.test.bean.ArticleListBean;
import com.idega.webface.test.bean.ContentItemCase;
import com.idega.webface.test.bean.ManagedContentBeans;
import com.idega.webface.test.component.ArticleBlock;
import com.idega.webface.test.component.ArticleVersionBlock;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class WorkspaceBar extends WFContainer implements  ManagedContentBeans, WFTabListener, ActionListener, Serializable{

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
		init();
	}
	
	public void init(){
		
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
		WFTabBar bar = getMainTaskbar();
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
		text.setValue("idega<i>Web</i>");
		div.add(text);
	}




	/**
	 * Returns the main task bar selector. 
	 */
	protected WFTabBar getMainTaskbar() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		WFTabBar tb = new WFTabBar();
		
		//tb.setMainAreaStyleClass(null);
		
		tb.setId(MAIN_TASKBAR_ID);
		tb.addButton(TASK_ID_WEBVIEW,  "WebView", getWebViewPerspective());
		tb.addButton(TASK_ID_CONTENT, "Content", getContentPerspective());
		//tb.addButtonVB(TASK_ID_EDIT, bref + "edit", getEditPerspective());
		tb.addButton(TASK_ID_BUILDER, "Builder", getBuilderPerspective());
		return tb;
	}
	
	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getContentPerspective() {
		UIComponent perspective = new ContentPerspective();
		return perspective;
	}

	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getWebViewPerspective() {
		UIComponent perspective = new ContentPerspective();
		return perspective;
	}

	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getBuilderPerspective() {
		UIComponent perspective = new ContentPerspective();
		return perspective;
	}	
	
	/**
	 * Returns the content edit perspective.
	 */
	protected UIComponent getEditPerspective() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		HtmlPanelGrid ap = WFPanelUtil.getApplicationPanel();
		/*ap.getChildren().add(getFunctionBlock());
		WFContainer c = new WFContainer();
		ArticleBlock ab = new ArticleBlock(bref + "edit_article", this);
		c.add(ab);
		ArticleVersionBlock av = new ArticleVersionBlock(bref + "previous_article_versions");
		av.getTitlebar().setValueRefTitle(true);
		av.setRendered(false);
		c.add(av);
		ap.getChildren().add(c);*/
		return ap;
	}
	
	/**
	 * Called when the edit mode in the article block changes.
	 * @see com.idega.webface.event.WFTabListener#taskbarButtonPressed() 
	 */
	public void tabPressed(WFTabEvent e) {
		WFTabBar t = e.getTaskbar();
		UIComponent articleVersionBlock = t.findComponent(ArticleVersionBlock.ARTICLE_VERSION_BLOCK_ID);
		if (t.getSelectedButtonId().equals(ArticleBlock.TASK_ID_PREVIEW)) {
			articleVersionBlock.setRendered(true);
		} else {
			articleVersionBlock.setRendered(false);			
		}
	}

	
	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) {
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		WFTabBar tb = (WFTabBar) link.getParent().getParent().getParent().findComponent(MAIN_TASKBAR_ID);
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

		ab.updateEditButtons();
	}
		

}