/*
 * $Id: WFToolbarTag.java,v 1.1 2005/05/11 17:52:51 gummi Exp $
 * Created on 26.4.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import org.apache.myfaces.taglib.UIComponentTagBase;


/**
 * 
 *  Last modified: $Date: 2005/05/11 17:52:51 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.1 $
 */
public class WFToolbarTag extends UIComponentTagBase {

	/**
	 * 
	 */
	public WFToolbarTag() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "WFToolbar";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return "wf_toolbar";
	}
}
