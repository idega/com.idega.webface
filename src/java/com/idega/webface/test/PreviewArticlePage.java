/*
 * $Id: PreviewArticlePage.java,v 1.1 2004/06/23 13:22:42 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.idega.webface.WFContainer;
import com.idega.webface.WFUtil;

/**
 * Preview article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/23 13:22:42 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class PreviewArticlePage extends CMSPage {
	
	private String _articleItemId = null;
	
	/**
	 * Creates the page content. 
	 */
	protected void createContent() {
		_articleItemId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(PREVIEW_ARTICLE_ITEM_ID);

		WFUtil.invoke(PREVIEW_ARTICLE_BEAN_ID, "clear");
		WFUtil.invoke(PREVIEW_ARTICLE_BEAN_ID, "setHeadline", "Headline, id=" + _articleItemId);
		WFUtil.invoke(PREVIEW_ARTICLE_BEAN_ID, "setBody", "Body text...");
		
		add(getArticlePreviewContent());		
	}
	
	/**
	 * Returns the article preview content. 
	 */
	protected UIComponent getArticlePreviewContent() {
		String ref = PREVIEW_ARTICLE_BEAN_ID + ".";
		WFContainer c = new WFContainer();
		c.setStyleAttribute("width", "400px");
		c.setStyleAttribute("padding", "14px");
		c.add(WFUtil.getHeaderTextVB(ref + "headline"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getTextVB(ref + "body"));
		c.add(WFUtil.getBreak(2));
		return c;
	}
}
