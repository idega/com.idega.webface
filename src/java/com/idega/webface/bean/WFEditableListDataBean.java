/*
 * $Id: WFEditableListDataBean.java,v 1.2 2005/01/10 13:52:19 gummi Exp $
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
 *  Last modified: $Date: 2005/01/10 13:52:19 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.2 $
 */
public interface WFEditableListDataBean {

	public Object[] getSelectItemListArray();
	
	public Object[] getValues();

}