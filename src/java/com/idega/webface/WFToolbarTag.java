/*
 * $Id: WFToolbarTag.java,v 1.2 2006/05/11 15:13:01 eiki Exp $
 * Created on 26.4.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;


/**
 * 
 *  Last modified: $Date: 2006/05/11 15:13:01 $ by $Author: eiki $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.2 $
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
