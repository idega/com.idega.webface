/*
 * $Id: WFBlockTag.java,v 1.8 2006/12/15 10:53:54 valdas Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.idega.presentation.ComponentTag;

/**
 * JSP tag for WFBlock
 * <p>
 * Last modified: $Date: 2006/12/15 10:53:54 $ by $Author: valdas $
 *
 * @author tryggvil
 * @version $Revision: 1.8 $
 */
public class WFBlockTag extends ComponentTag {

	private Object title;
	private Object maximizedVertically;

	private String styleClass;

	@Override
	public String getRendererType() {
		return null;
	}

	@Override
	public String getComponentType() {
		return "WFBlock";
	}

	public void setTitle(Object title) {
		this.title = title;
	}
	public void setTitle(ValueExpression title) {
		this.title = title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public void setMaximizedVertically(String maximizedVertically) {
		this.maximizedVertically = maximizedVertically;
	}
	public void setMaximizedVertically(ValueExpression maximizedVertically) {
		this.maximizedVertically = maximizedVertically;
	}
	public void setMaximizedVertically(Object maximizedVertically) {
		this.maximizedVertically = maximizedVertically;
	}

	@Override
	public void release() {
		super.release();

		this.title = null;
		this.maximizedVertically = null;
		this.styleClass = null;
	}

	@Override
	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		if (component instanceof WFBlock) {
			WFBlock block = (WFBlock) component;

			String title = getValue(this.title);
			if (title != null) {
				block.setTitle(title);
			}

			String maxVertically = getValue(this.maximizedVertically);
			if (maxVertically != null) {
				block.setMaximizedVertically(Boolean.valueOf(maxVertically));
			}
			if (styleClass != null) {
				block.setStyleClass(styleClass);
			}
		}
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}