/*
 * $Id: WFEditableListDataBean.java,v 1.3 2005/01/18 17:44:33 gummi Exp $
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
 *  Last modified: $Date: 2005/01/18 17:44:33 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.3 $
 */
public interface WFEditableListDataBean {

	public Object[] getSelectItemListArray();
	
	public Object[] getValues();
	
	public Boolean getRendered();

}