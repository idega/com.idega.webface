/*
 * $Id: WFEditableListDataBean.java,v 1.1 2005/01/07 19:45:30 gummi Exp $
 * Created on 7.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.bean;

/**
 * 
 *  Last modified: $Date: 2005/01/07 19:45:30 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.1 $
 */
public interface WFEditableListDataBean {
	
	public static final String COLUMN_TYPE_TEXT = "text";
	public static final String COLUMN_TYPE_UI_SELECT_INPUT = "ui_select_input";
//	public static final String COLUMN_TYPE_UI_TEXT_INPUT = "ui_text_input";

	public Object[] getSelectItemListArray();
	
	public Object[] getColumnValue();
	
	public Object[] getColumnType();
	
	public Boolean getDoRender();

}