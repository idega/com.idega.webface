/*
 * $Id: WFListBean.java,v 1.1 2004/05/27 12:36:15 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.bean;

import javax.faces.component.UIColumn;
import javax.faces.model.DataModel;

/**
 * Abstract presentation bean representing data for a WFList component.   
 * <p>
 * Last modified: $Date: 2004/05/27 12:36:15 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public abstract class WFListBean {
	
	/**
	 * Returns a data model with an updated collection of beans to be listed.
	 * If the rows parameter is 0 then all rows shall be updated with new data. 
	 * @param start the start row
	 * @param rows the number of rows to update with new data.
	 */
	public abstract DataModel updateDataModel(int start, int rows);
		
	/**
	 * Returns UIColumn components for the list.
	 * @var the var attribute for the list
	 */
	public abstract UIColumn[] createColumns(String var);

}
