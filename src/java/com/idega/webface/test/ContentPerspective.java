/*
 * Created on 12.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFList;
import com.idega.webface.WFPage;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFUtil;
import com.idega.webface.WFViewMenu;
import com.idega.webface.test.bean.ArticleListBean;
import com.idega.webface.test.bean.ContentItemCase;
import com.idega.webface.test.bean.ManagedContentBeans;
import com.idega.webface.test.component.ArticleBlock;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class ContentPerspective extends WFContainer implements ManagedContentBeans{
	
	private final static String P = "cms_page_"; // Parameter prefix
	
	private final static String TASK_ID_CONTENT = P + "t_content";
	private final static String TASK_ID_EDIT = P + "t_edit";

	private final static String MAIN_TASKBAR_ID = P + "main_taskbar";
	private final static String ARTICLE_LIST_ID = P + "article_list";
	private final static String CASE_LIST_ID = P + "case_list";
		
	
	public ContentPerspective(){
		
			//HtmlPanelGrid p = WFPanelUtil.getApplicationPanel();
			//p.getChildren().add(getFunctionBlock());		
			//WFContainer c = new WFContainer();
			//c.add(getArticleList());
			//this.add(p);
			//c.add(getCaseList());
			//p.getChildren().add(c);
	}
	
	
	/**
	 * Returns the function block containing a view menu.
	 */
	protected UIComponent getFunctionBlock() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		WFBlock b = new WFBlock(bref + "functions");
		b.getTitlebar().setValueRefTitle(true);

		WFViewMenu vm = new WFViewMenu();
		b = WFUtil.setBlockStyle(b, vm);
		vm.addButtonVB("content_home", bref + "content_home", "/cmspage.jsf");
		vm.addButtonVB("create_article", bref + "create_article", "/createarticle.jsf");
		vm.addButtonVB("list_articles", bref + "list_articles", "/listarticles.jsf");
		vm.addButtonVB("search_articles", bref + "search_articles", "/searcharticle.jsf");
		vm.addButtonVB("users_groups", bref + "users_and_groups", "UserApplication.jsf");
		vm.addButtonVB("create_page", bref + "create_page", "CreatePage.jsf");
		vm.addButtonVB("configure", bref + "configure", "Configure.jsf");
		b.add(vm);
		return b;
	}
	
	/**
	 * Returns the article list.
	 */
	protected UIComponent getArticleList() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		WFBlock b = new WFBlock(bref + "article_list");
		b.getTitlebar().setValueRefTitle(true);
		WFList l = new WFList(ARTICLE_LIST_BEAN_ID, 0, 3);
		l.setId(ARTICLE_LIST_ID);
		b.add(l);
		return b;
	}

	/**
	 * Returns the case list.
	 */
	protected UIComponent getCaseList() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		WFBlock b = new WFBlock(bref + "case_list");
		b.getTitlebar().setValueRefTitle(true);
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
	
	/**
	 * Sets the page in edit mode.
	 */
	public void setEditMode() {
		WFTabBar tb = (WFTabBar) findComponent(MAIN_TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_EDIT);
		ArticleBlock ab = (ArticleBlock) tb.findComponent(ArticleBlock.ARTICLE_BLOCK_ID);
		ab.setEditMode();		
	}
	
}
