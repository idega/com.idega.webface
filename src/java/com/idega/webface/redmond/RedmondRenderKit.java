/*
 * Created on 3.8.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.redmond;

import javax.faces.render.RenderKit;
import com.idega.faces.renderkit.BaseRenderKit;
import com.idega.webface.WFBezel;
import com.idega.webface.WFBlock;
import com.idega.webface.WFConstants;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFToolbar;

/**
 * @author tryggvil
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RedmondRenderKit extends BaseRenderKit {

	public static final String RENDERKIT_ID="webface";
	
	
	public RedmondRenderKit(RenderKit oldRenderKit){
		super(oldRenderKit);
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFContainer.RENDERER_TYPE,new ContainerRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFBezel.RENDERER_TYPE,new BezelRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFTabBar.RENDERER_TYPE,new TabBarRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFBlock.RENDERER_TYPE,new BlockRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFTitlebar.RENDERER_TYPE,new TitlebarRenderer());
		this.addRenderer(WFConstants.FAMILY_WEBFACE,WFToolbar.RENDERER_TYPE,new ToolbarRenderer());
	}

}
