/*
 * $Id: CMSTest.java,v 1.1 2004/05/27 12:36:56 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;

import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFList;
import com.idega.webface.WFPage;
import com.idega.webface.WFPanel;
import com.idega.webface.WFTaskbar;
import com.idega.webface.WFUtil;
import com.idega.webface.WFViewMenu;

/**
 * Content management system test/demo. 
 * <p>
 * Last modified: $Date: 2004/05/27 12:36:56 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class CMSTest {

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
		tb.addButton("content", "Content", getContentPerspective());
		tb.addButton("edit", "Content", getEditPerspective());
		return tb;
	}
	
	/**
	 * Returns the content admin perspective.
	 */
	protected UIComponent getContentPerspective() {
		WFPanel p = new WFPanel();
		p.setStyleAttribute("padding", "0px");
		p.set(getFunctionBlock(), 1, 1, "200px");
		WFContainer c = new WFContainer();
		c.add(getArticleBlock());
		c.add(getCaseBlock());
		p.set(c, 2, 1);
		return p;
	}
	
	/**
	 * Returns the content edit perspective.
	 */
	protected UIComponent getEditPerspective() {
		return WFUtil.getText("Edit");
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
	 * Returns the article block.
	 */
	protected UIComponent getArticleBlock() {
		WFBlock b = new WFBlock("Article list");
		b.setWidth("700px");
		WFList l = new WFList(new ArticleListBean(), "abc", 0, 3);
		b.add(l);
		return b;
	}

	/**
	 * Returns the article block.
	 */
	protected UIComponent getCaseBlock() {
		WFBlock b = new WFBlock("Case list");
		b.setWidth("700px");
		return b;
	}
}
