/*
 * $Id: WFWebView.java,v 1.3 2005/06/02 18:08:07 tryggvil Exp $
 * Created on 15.11.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import javax.faces.context.FacesContext;
import com.idega.idegaweb.IWMainApplication;


/**
 * 
 *  Last modified: $Date: 2005/06/02 18:08:07 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.3 $
 */
public class WFWebView extends WFFrame {

	/**
	 * 
	 */
	public WFWebView() {
		this("Pages");
		FacesContext context = FacesContext.getCurrentInstance();
		IWMainApplication iwma = IWMainApplication.getIWMainApplication(context);
		String pagesUrl = iwma.getBuilderPagePrefixURI();
		this.setFrameURL(pagesUrl);
	}

	/**
	 * @param titleBarText
	 */
	public WFWebView(String titleBarText) {
		super(titleBarText);
		setFrameHeight(0,122);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param titleBarText
	 * @param frameURL
	 */
	public WFWebView(String titleBarText, String frameURL) {
		super(titleBarText, frameURL);
		setFrameHeight(0,122);
		// TODO Auto-generated constructor stub
	}
}
