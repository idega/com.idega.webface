/*
 * $Id: WFFrame.java,v 1.2 2004/10/25 15:51:58 tryggvil Exp $
 * Created on 20.10.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import javax.faces.context.FacesContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.ui.IFrame;


/**
 * 
 *  Last modified: $Date: 2004/10/25 15:51:58 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class WFFrame extends WFBlock {

	
	private String frameURL;
	
	/**
	 * 
	 */
	public WFFrame() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param titleBarText
	 */
	public WFFrame(String titleBarText) {
		super(titleBarText);
		// TODO Auto-generated constructor stub
	}
	
	public WFFrame(String titleBarText,String frameURL){
		this(titleBarText);
		this.setFrameURL(frameURL);
	}
	
	
	/* (non-Javadoc)
	 * @see com.idega.webface.WFBlock#initializeContent()
	 */
	protected void initializeContent() {
		IFrame frame = new IFrame(this.getId()+"subframe",this.getFrameURL());
		frame.setWidth("800");
		frame.setHeight("550");
		add(frame);
	}
	/**
	 * @return Returns the frameURL.
	 */
	public String getFrameURL() {
		return frameURL;
	}
	/**
	 * @param frameURL The frameURL to set.
	 */
	public void setFrameURL(String frameURL) {
		this.frameURL = frameURL;
	}
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = this.frameURL;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		frameURL = ((String) values[1]);
	}
}
