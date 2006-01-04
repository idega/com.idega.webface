/*
 * $Id: WFBlockTabbed.java,v 1.1 2006/01/04 14:43:11 tryggvil Exp $
 * Created on 21.12.2005 in project com.idega.webface
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import javax.faces.context.FacesContext;


/**
 * <p>
 * This class allows to use the TabbedPane component conveniently within
 * the WFBlock structure. This means that the tabmenu of the WFTabbedPane component
 * becomes ths toolbar of this block and the content of the tabbedpanes tabview becomes 
 * rendered within this blocks mainarea.
 * </p>
 *  Last modified: $Date: 2006/01/04 14:43:11 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public abstract class WFBlockTabbed extends WFBlock{

	/**
	 * 
	 */
	public WFBlockTabbed() {
		super();
	}

	public WFBlockTabbed(String titleBarText) {
		super(titleBarText);
	}
	
	public WFBlockTabbed(String titleBarText, boolean titleIsVB) {
		super(titleBarText,titleIsVB);
	}
	
	protected abstract WFTabbedPane initializeTabbedPane(FacesContext context);

	public WFTabbedPane getTabbedPane(){
		return (WFTabbedPane)getToolbar();
	}
	
	/* (non-Javadoc)
	 * @see com.idega.presentation.IWBaseComponent#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		// TODO Auto-generated method stub
		
		
		WFTabbedPane tabbedpane= initializeTabbedPane(context);
		tabbedpane.setRenderSelectedTabViewAsChild(false);
		tabbedpane.setMenuStyleClass(WFToolbar.DEFAULT_STYLE_CLASS);
		tabbedpane.setStyleClass(WFToolbar.DEFAULT_CONTAINER_STYLE_CLASS);
		setToolbar(tabbedpane);
		
		super.initializeComponent(context);
	}
	
}
