/*
 * $Id: WFFormItem.java,v 1.1 2006/01/04 14:43:11 tryggvil Exp $
 * Created on 22.12.2005 in project com.idega.webface
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;


/**
 * <p>
 * Component to use as a container (div) around each form item (i.e. label and input) in a css pure form.
 * </p>
 *  Last modified: $Date: 2006/01/04 14:43:11 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class WFFormItem extends WFContainer {

	public static String DEFAULT_STYLE_CLASS="formitem";
	
	/**
	 * 
	 */
	public WFFormItem() {
		super();
		setStyleClass(DEFAULT_STYLE_CLASS);
	}
}
