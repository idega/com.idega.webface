/*
 * Created on 3.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.theme;

import javax.faces.render.RenderKit;
import com.idega.idegaweb.include.GlobalIncludeManager;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class RedmondTheme extends WFDefaultTheme{
	
	private String STYLE_SHEET_URL = "/style/redmondTheme.css";
	private String BUNDLE_IDENTIFIER="com.idega.webface";
	private RenderKit renderKit;

	public RedmondTheme(){
		installRenderKit();
		//Add the stylesheet:
		GlobalIncludeManager.getInstance().addBundleStyleSheet(BUNDLE_IDENTIFIER,STYLE_SHEET_URL);
	}

	
}
