/*
 * $Id: CMSPage.java,v 1.2 2004/06/11 13:56:02 anders Exp $
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

import javax.faces.component.NamingContainer;
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

/**
 * Content management system test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/11 13:56:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class CMSPage extends WFPage implements ActionListener, Serializable {

	public final static String ARTICLE_LIST_BEAN_ID = "article_list_bean";
	public final static String CASE_LIST_BEAN_ID = "case_list_bean";
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		if (getChildren().size() == 0) {
//			context.getViewRoot().setLocale(new Locale("sv", "SE"));
			createContent();
		}
		super.encodeBegin(context);
	}

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {		
		boolean isArticleBeanUpdated = false;
		ArticleItemBean bean = (ArticleItemBean) WFUtil.getSessionBean(ArticleBlock.ARTICLE_ITEM_BEAN_ID);
		if (bean != null) {
			isArticleBeanUpdated = bean.isUpdated();
			bean.setUpdated(false);
		}
		
		if (WFUtil.getSessionBean(ARTICLE_LIST_BEAN_ID) == null) {
			WFUtil.setSessionBean(ARTICLE_LIST_BEAN_ID, new ArticleListBean(this));
		}
		
		if (WFUtil.getSessionBean(CASE_LIST_BEAN_ID) == null) {
			WFUtil.setSessionBean(CASE_LIST_BEAN_ID, new CaseListBean(this));
		}
		
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
		tb.setId("main_taskbar");
		tb.addButton("content", "Content", getContentPerspective());
		tb.addButton("edit", "Edit", getEditPerspective());
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
		ArticleBlock ab = new ArticleBlock("edit_article");
		ab.setId("article_block");
		ap.getChildren().add(ab);
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
		vm.addButton("list_articles", "List Articles", "ListArticles.jsf");
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
		b.setWidth("700px");
		WFList l = new WFList(ARTICLE_LIST_BEAN_ID, 0, 3);
		l.setId("articlelist");
		b.add(l);
		return b;
	}

	/**
	 * Returns the case list.
	 */
	protected UIComponent getCaseList() {
		WFBlock b = new WFBlock("Case list");
		b.setWidth("700px");
		WFList l = new WFList(CASE_LIST_BEAN_ID, 0, 3);
		l.setId("caselist");
		b.add(l);
		return b;
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) {
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		WFTaskbar tb = (WFTaskbar) link.findComponent(NamingContainer.SEPARATOR_CHAR + "testScreen" +
				NamingContainer.SEPARATOR_CHAR + "main_taskbar");
		tb.setSelectedButtonId("edit");
		ArticleBlock ab = (ArticleBlock) tb.findComponent("article_block");
		ab.setEditMode();
		ArticleItemBean bean = new ArticleItemBean();
		bean.setLocaleId("sv");
		bean.setHeadline("headline");
		bean.setTeaser("teaser");
		bean.setBody(id);
		bean.setAuthor("author");
		bean.setComment("comment");
		bean.setDescription("description");
		bean.setSource("source");
		if (link.getId().equals(ArticleListBean.ARTICLE_ID)) {
			bean.setStatus(ContentItemCaseBean.STATUS_PUBLISHED);
		} else {
			bean.setStatus(ContentItemCaseBean.STATUS_UNDER_REVIEW);			
		}
		bean.setMainCategoryId(3);
		ab.setArticleItemBean(bean);
	}
	
	/**
	 * Sets the page in edit mode.
	 */
	public void setEditMode() {
		WFTaskbar tb = (WFTaskbar) findComponent(NamingContainer.SEPARATOR_CHAR + "testScreen" +
				NamingContainer.SEPARATOR_CHAR + "main_taskbar");
		tb.setSelectedButtonId("edit");
		ArticleBlock ab = (ArticleBlock) tb.findComponent("article_block");
		ab.setEditMode();		
	}
}
