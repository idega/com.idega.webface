/*
 * $Id: CMSPage.java,v 1.8 2004/10/19 11:09:29 tryggvil Exp $
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
import com.idega.webface.WFTabBar;
import com.idega.webface.WFUtil;
import com.idega.webface.WFViewMenu;
import com.idega.webface.event.WFTabEvent;
import com.idega.webface.event.WFTabListener;
import com.idega.webface.test.bean.*;
import com.idega.webface.test.component.*;

/**
 * Content management system test/demo page. 
 * <p>
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.8 $
 */
public class CMSPage extends WFPage implements  ManagedContentBeans, WFTabListener, ActionListener, Serializable {
	
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

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(arg0);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return super.getRendersChildren();
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
		String bref = WFPage.CONTENT_BUNDLE + ".";
		WFTabBar tb = new WFTabBar();
		tb.setMainAreaStyleClass(null);
		tb.setId(MAIN_TASKBAR_ID);
		tb.addButtonVB(TASK_ID_CONTENT, bref + "content", getContentPerspective());
		tb.addButtonVB(TASK_ID_EDIT, bref + "edit", getEditPerspective());
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
		String bref = WFPage.CONTENT_BUNDLE + ".";
		HtmlPanelGrid ap = WFPanelUtil.getApplicationPanel();
		ap.getChildren().add(getFunctionBlock());
		WFContainer c = new WFContainer();
		ArticleBlock ab = new ArticleBlock(bref + "edit_article", this);
		c.add(ab);
		ArticleVersionBlock av = new ArticleVersionBlock(bref + "previous_article_versions");
		av.getTitlebar().setValueRefTitle(true);
		av.setRendered(false);
		c.add(av);
		ap.getChildren().add(c);
		return ap;
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
}
