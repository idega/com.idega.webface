/*
 * $Id: CreateArticlePage.java,v 1.1 2004/06/11 13:45:01 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;

import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFUtil;

/**
 * Created article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/11 13:45:01 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class CreateArticlePage extends CMSPage {

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		add(WFUtil.getBannerBox());
		add(getMainTaskbar());
		
		ArticleItemBean bean = new ArticleItemBean();
		bean.setLocaleId("sv");
		bean.setHeadline("");
		bean.setBody("");
		bean.setTeaser("");
		bean.setAuthor("");
		bean.setComment("");
		bean.setSource("");
		bean.setStatus(ContentItemCaseBean.STATUS_NEW);
		
		WFUtil.setSessionBean(ArticleBlock.ARTICLE_ITEM_BEAN_ID, bean);
	}
	
	/**
	 * Returns the main task bar selector. 
	 */
	protected UIComponent getMainTaskbar() {
		return getEditPerspective();
//		WFTaskbar tb = new WFTaskbar();
//		tb.setId("main_taskbar");
//		tb.addButton("edit", "Edit", getEditPerspective());
//		return tb;
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
}
