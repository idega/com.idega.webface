/*
 * $Id: SearchArticlePageTag.java,v 1.1 2004/06/11 13:46:31 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.webapp.UIComponentTag;

/**
 * JSP tag for search article test/demo page. 
 * <p>
 * Last modified: $Date: 2004/06/11 13:46:31 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class SearchArticlePageTag extends UIComponentTag {
	
	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return null;
	}
		
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "SearchArticlePage";
	}
}
