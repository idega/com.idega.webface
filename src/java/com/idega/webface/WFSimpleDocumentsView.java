/*
 * $Id: WFSimpleDocumentsView.java,v 1.2 2004/11/15 22:19:54 tryggvil Exp $
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
 *  Last modified: $Date: 2004/11/15 22:19:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.2 $
 */
public class WFSimpleDocumentsView extends WFFrame {

	/**
	 * 
	 */
	public WFSimpleDocumentsView() {
		this("Documents");
		FacesContext context = FacesContext.getCurrentInstance();
		IWMainApplication iwma = IWMainApplication.getIWMainApplication(context);
		//TODO: Remove hardcoding:
		String documentsUrl = iwma.getTranslatedURIWithContext("/servlet/webdav");
		this.setFrameURL(documentsUrl);
	}

	/**
	 * @param titleBarText
	 */
	public WFSimpleDocumentsView(String titleBarText) {
		super(titleBarText);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param titleBarText
	 * @param frameURL
	 */
	public WFSimpleDocumentsView(String titleBarText, String frameURL) {
		super(titleBarText, frameURL);
		// TODO Auto-generated constructor stub
	}
}
