/*
 * $Id: CMSPage.java,v 1.6 2004/06/28 09:32:09 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFList;
import com.idega.webface.WFPage;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFTaskbar;
import com.idega.webface.WFUtil;
import com.idega.webface.WFViewMenu;
import com.idega.webface.event.WFTaskbarEvent;
import com.idega.webface.event.WFTaskbarListener;
import com.idega.webface.test.bean.*;
import com.idega.webface.test.component.*;

/**
 * Content management system test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/28 09:32:09 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.6 $
 */
public class CMSPage extends WFPage implements  ManagedContentBeans, WFTaskbarListener, ActionListener, Serializable {
	
	private final static String P = "cms_page_"; // Parameter prefix
	
	private final static String TASK_ID_CONTENT = P + "t_content";
	private final static String TASK_ID_EDIT = P + "t_edit";

	private final static String MAIN_TASKBAR_ID = P + "main_taskbar";
	private final static String ARTICLE_LIST_ID = P + "article_list";
	private final static String CASE_LIST_ID = P + "case_list";
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		if (getChildren().size() == 0) {
			createContent();
		}
		super.encodeBegin(context);
	}

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		boolean isArticleBeanUpdated = WFUtil.getBooleanValue(ARTICLE_ITEM_BEAN_ID, "updated");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setUpdated", new Boolean(false));
		
		WFUtil.invoke(ARTICLE_LIST_BEAN_ID, "setArticleLinkListener", this, ActionListener.class);
		
		WFUtil.invoke(CASE_LIST_BEAN_ID, "setCaseLinkListener", this, ActionListener.class);
		
		add(WFUtil.getBannerBox());
		add(getMainTaskbar());
		
		if (isArticleBeanUpdated) {
			setEditMode();
		}
	}
	
	/**
	 * Returns the main task bar selector. 
	 */
	protected UIComponent getMainTaskbar() {
		WFTaskbar tb = new WFTaskbar();
		tb.setMainAreaStyleClass(null);
		tb.setId(MAIN_TASKBAR_ID);
		tb.addButton(TASK_ID_CONTENT, "Content", getContentPerspective());
		tb.addButton(TASK_ID_EDIT, "Edit", getEditPerspective());
		return tb;
	}
	
	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getContentPerspective() {
		HtmlPanelGrid p = WFPanelUtil.getApplicationPanel();
		p.getChildren().add(getFunctionBlock());		
		WFContainer c = new WFContainer();
		c.add(getArticleList());
		c.add(getCaseList());
		p.getChildren().add(c);
		return p;
	}
	
	/**
	 * Returns the content edit perspective.
	 */
	protected UIComponent getEditPerspective() {
		HtmlPanelGrid ap = WFPanelUtil.getApplicationPanel();
		ap.getChildren().add(getFunctionBlock());
		WFContainer c = new WFContainer();
		ArticleBlock ab = new ArticleBlock("edit_article", this);
		c.add(ab);
		ArticleVersionBlock av = new ArticleVersionBlock("Previous article versions");
		av.setRendered(false);
		c.add(av);
		ap.getChildren().add(c);
		return ap;
	}
	
	/**
	 * Returns the function block containing a view menu.
	 */
	protected UIComponent getFunctionBlock() {
		WFBlock b = new WFBlock("Functions");
		WFViewMenu vm = new WFViewMenu();
		b = WFUtil.setBlockStyle(b, vm);
		vm.addButton("news_items", "Content Home", "/cmspage.jsf");
		vm.addButton("create_article", "Create Article", "/createarticle.jsf");
		vm.addButton("list_articles", "List Articles", "/listarticles.jsf");
		vm.addButton("search_articles", "Search Articles", "/searcharticle.jsf");
		vm.addButton("users_groups", "Users and Groups", "UserApplication.jsf");
		vm.addButton("create_page", "Create Page", "CreatePage.jsf");
		vm.addButton("configure", "Configure", "Configure.jsf");
		b.add(vm);
		return b;
	}
	
	/**
	 * Returns the article list.
	 */
	protected UIComponent getArticleList() {
		WFBlock b = new WFBlock("Article list");
		WFList l = new WFList(ARTICLE_LIST_BEAN_ID, 0, 3);
		l.setId(ARTICLE_LIST_ID);
		b.add(l);
		return b;
	}

	/**
	 * Returns the case list.
	 */
	protected UIComponent getCaseList() {
		WFBlock b = new WFBlock("Case list");
		WFList l = new WFList(CASE_LIST_BEAN_ID, 0, 3);
		l.setId(CASE_LIST_ID);
		b.add(l);
		return b;
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) {
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		WFTaskbar tb = (WFTaskbar) link.getParent().getParent().getParent().findComponent(MAIN_TASKBAR_ID);
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
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_PUBLISHED);
		} else {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_UNDER_REVIEW);
		}
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setMainCategoryId", new Integer(3));

		ab.updateEditButtons();
	}
	
	/**
	 * Sets the page in edit mode.
	 */
	public void setEditMode() {
		WFTaskbar tb = (WFTaskbar) findComponent(MAIN_TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_EDIT);
		ArticleBlock ab = (ArticleBlock) tb.findComponent(ArticleBlock.ARTICLE_BLOCK_ID);
		ab.setEditMode();		
	}
	
	/**
	 * Called when the edit mode in the article block changes.
	 * @see com.idega.webface.event.WFTaskbarListener#taskbarButtonPressed() 
	 */
	public void taskbarButtonPressed(WFTaskbarEvent e) {
		WFTaskbar t = e.getTaskbar();
		UIComponent articleVersionBlock = t.findComponent(ArticleVersionBlock.ARTICLE_VERSION_BLOCK_ID);
		if (t.getSelectedButtonId().equals(ArticleBlock.TASK_ID_PREVIEW)) {
			articleVersionBlock.setRendered(true);
		} else {
			articleVersionBlock.setRendered(false);			
		}
	}
}
