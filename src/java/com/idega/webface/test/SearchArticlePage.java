/*
 * $Id: SearchArticlePage.java,v 1.2 2004/06/28 09:32:09 anders Exp $
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
 * Search article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/28 09:32:09 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class SearchArticlePage extends CMSPage {

	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		add(WFUtil.getBannerBox());
		add(getSearchArticleBlock());		
	}
	
	/**
	 * Returns a search article block.
	 */
	protected UIComponent getSearchArticleBlock() {
		HtmlPanelGrid ap = WFPanelUtil.getApplicationPanel();
		ap.getChildren().add(getFunctionBlock());
		SearchArticleBlock sab = new SearchArticleBlock("Search articles");
		ap.getChildren().add(sab);
		return ap;
	}
}
