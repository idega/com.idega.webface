/*
 * $Id: CreateArticlePage.java,v 1.4 2004/06/28 09:32:09 anders Exp $
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
import com.idega.webface.test.component.*;

/**
 * Created article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/28 09:32:09 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class CreateArticlePage extends CMSPage {

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		add(WFUtil.getBannerBox());
		add(getMainTaskbar());
		
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "clear");
	}
	
	/**
	 * Returns the main task bar selector. 
	 */
	protected UIComponent getMainTaskbar() {
		return getEditPerspective();
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