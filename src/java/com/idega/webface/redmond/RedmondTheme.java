/*
 * Created on 3.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.redmond;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import com.idega.idegaweb.GlobalIncludeManager;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class RedmondTheme {
	
	private String STYLE_SHEET_URL = "/style/webfacestyle.css";
	private String BUNDLE_IDENTIFIER="com.idega.webface";
	private RenderKit renderKit;

	public RedmondTheme(){
		installRenderKit();
		//Add the stylesheet:
		GlobalIncludeManager.getInstance().addBundleStyleSheet(BUNDLE_IDENTIFIER,STYLE_SHEET_URL);
	}
	
	public void installRenderKit(){
		RenderKit oldKit;
		FacesContext ctx;
		ctx = FacesContext.getCurrentInstance();
		oldKit = getRenderKitFactory().getRenderKit(ctx,RenderKitFactory.HTML_BASIC_RENDER_KIT);
		getRenderKitFactory().addRenderKit(RenderKitFactory.HTML_BASIC_RENDER_KIT,getRenderKit(oldKit));
	}
	
	
	protected RenderKitFactory getRenderKitFactory(){
		return (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
	}
	
	public RenderKit getRenderKit(RenderKit previousKit){
		if(renderKit==null){
			renderKit = new RedmondRenderKit(previousKit);
		}
		return renderKit;
	}
	
}
