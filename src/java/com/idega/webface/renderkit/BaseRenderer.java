/*
 * Created on 4.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.idega.faces.renderkit.CssHelper;
import com.idega.util.RenderUtils;

/**
 * Base class for Renderer. With some helper functions.<br>
 *
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class BaseRenderer extends Renderer {
	private CssHelper cssHelper = new CssHelper();

	/**
	 * @return Returns the cssHelper.
	 */
	public CssHelper getCssHelper() {
		return this.cssHelper;
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		if (getRendersChildren()) {
			Collection<UIComponent> children = component.getChildren();
			for (Iterator<UIComponent> iter = children.iterator(); iter.hasNext();) {
				UIComponent child = iter.next();
				renderChild(context,child);
			}
		}
	}

	protected void renderChild(FacesContext context,UIComponent child) throws IOException{
		RenderUtils.renderChild(context,child);
	}
}
