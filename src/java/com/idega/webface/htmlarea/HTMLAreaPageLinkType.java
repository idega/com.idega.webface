/*
 * $Id: HTMLAreaPageLinkType.java,v 1.1 2005/03/08 17:04:21 gimmi Exp $
 * Created on 8.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import java.lang.reflect.InvocationTargetException;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.TreeViewer;
import com.idega.util.reflect.MethodInvoker;
import com.idega.webface.WFBlock;
import com.idega.webface.WFTitlebar;


public class HTMLAreaPageLinkType implements HTMLAreaLinkType {

	public String getStartingURL() {
		return null;
	}

	public String getStartingTitle() {
		return null;
	}

	public String getStartingTarget() {
		return null;
	}

	public ValueExpression getLinkTypeName(IWBundle iwb) {
		return iwb.getValueExpression("link_type_pages");
	}

	public UIComponent getLinkCreation() {
		IWContext iwc = IWContext.getInstance();
		try {
			WFBlock block = new WFBlock();
			WFTitlebar header = new WFTitlebar();
			IWBundle bundle = iwc.getIWMainApplication().getBundle(HTMLAreaLinkCreator.IW_BUNDLE_IDENTIFIER);
			header.addTitleText(bundle.getLocalizedText("page_chooser"));
			block.setTitlebar(header);

			Object instance = MethodInvoker.getInstance().invokeStaticMethodWithNoParameters("com.idega.builder.business.IBPageHelper", "getInstance");
			TreeViewer viewer = (TreeViewer) MethodInvoker.getInstance().invokeMethodWithIWContextParameter(instance, "getPageTreeViewer", iwc);
			viewer.setOnNodeClickEvent("Set");
			viewer.setToMaintainParameter(HTMLAreaLinkCreator.PARAMETER_CREATOR, iwc);
			block.add(viewer);
			return block;
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLinkType() {
		return "page";
	}
}
