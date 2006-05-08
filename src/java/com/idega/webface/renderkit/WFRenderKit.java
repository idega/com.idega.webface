/*
 * Created on 3.8.2004
 */
package com.idega.webface.renderkit;

import javax.faces.render.RenderKit;

import com.idega.faces.renderkit.BaseRenderKit;
import com.idega.webface.WFBezel;
import com.idega.webface.WFBlock;
import com.idega.webface.WFConstants;
import com.idega.webface.WFContainer;
import com.idega.webface.WFMenu;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFToolbar;
import com.idega.webface.htmlarea.HTMLArea;
import com.idega.webface.htmlarea.HTMLAreaRenderer;

/**
 * The base renderkit for WebFace
 * @author tryggvil
 */
public class WFRenderKit extends BaseRenderKit {

	public static final String RENDERKIT_ID="webface";
	
	
	public WFRenderKit(RenderKit oldRenderKit){
		super(oldRenderKit);
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFContainer.getWFRendererType(),new ContainerRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFBezel.getWFRendererType(),new BezelRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFMenu.getWFRendererType(),new MenuRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFBlock.getWFRendererType(),new BlockRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFTitlebar.getWFRendererType(),new TitlebarRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFToolbar.getWFRendererType(),new ToolbarRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,HTMLArea.getWFRendererType(), new HTMLAreaRenderer());
	}

}
