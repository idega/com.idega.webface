/*
 * $Id: CMSTest.java,v 1.2 2004/06/07 07:52:20 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
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
 * Content management system test/demo. 
 * <p>
 * Last modified: $Date: 2004/06/07 07:52:20 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class CMSTest implements ActionListener, Serializable {

	/**
	 * Returns test/demo page. 
	 */
	public UIComponent createContent() {
		WFPage p = new WFPage();		
		p.add(WFUtil.getBannerBox());
		p.add(getMainTaskbar());		
		return p;
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
		vm.addButton("news_items", "News Items", "NewsItems.jsf");
		vm.addButton("users_groups", "Users and Groups", "UserApplication.jsf");
		vm.addButton("create_article", "Create Article", "CreateArticle.jsf");
		vm.addButton("create_page", "Create Page", "CreatePage.jsf");
		vm.addButton("list_articles", "List Articles", "ListArticles.jsf");
		vm.addButton("search_articles", "Search Articles", "SearchArticles.jsf");
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
		WFList l = new WFList(new ArticleListBean(this), "abc", 0, 3);
		b.add(l);
		return b;
	}

	/**
	 * Returns the case list.
	 */
	protected UIComponent getCaseList() {
		WFBlock b = new WFBlock("Case list");
		b.setWidth("700px");
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
		bean.setHeadline("headline");
		bean.setTeaser("teaser");
		bean.setBody(id);
		bean.setAuthor("author");
		bean.setComment("comment");
		bean.setDescription("description");
		bean.setSource("source");
		bean.setMainCategoryId(3);
		ab.setArticleItem(bean);
	}
}
