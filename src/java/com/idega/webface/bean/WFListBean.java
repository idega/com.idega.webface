/*
 * $Id: WFListBean.java,v 1.2 2004/06/11 13:56:02 anders Exp $
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
 * Last modified: $Date: 2004/06/11 13:56:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public abstract class WFListBean {
	
	/**
	 * Updates the data model for this list bean.
	 * If the rows parameter is 0 then all rows shall be updated with new data. 
	 * @param start the start row
	 * @param rows the number of rows to update with new data.
	 */
	public abstract void updateDataModel(int start, int rows);
		
	/**
	 * Returns UIColumn components for the list.
	 * @var the var attribute for the list
	 */
	public abstract UIColumn[] createColumns(String var);
		
	/**
	 * Returns the data model for this list bean.
	 */
	public abstract DataModel getDataModel();
		
	/**
	 * Sets the data model for this list bean.
	 */
	public abstract void setDataModel(DataModel dataModel);
}
