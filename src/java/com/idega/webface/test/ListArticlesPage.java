/*
 * $Id: ListArticlesPage.java,v 1.3 2004/06/30 13:34:56 anders Exp $
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

import com.idega.webface.WFPage;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFUtil;
import com.idega.webface.test.component.ListArticlesBlock;

/**
 * Search article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/30 13:34:56 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class ListArticlesPage extends CMSPage {

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		add(WFUtil.getBannerBox());
		add(getListArticlesBlock());
	}
	
	/**
	 * Returns a list articles block.
	 */
	protected UIComponent getListArticlesBlock() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		HtmlPanelGrid ap = WFPanelUtil.getApplicationPanel();
		ap.getChildren().add(getFunctionBlock());
		ListArticlesBlock block = new ListArticlesBlock(bref + "list_articles");
		ap.getChildren().add(block);
		return ap;
	}
}
