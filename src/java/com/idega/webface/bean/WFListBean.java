/*
 * $Id: WFListBean.java,v 1.3 2004/06/18 14:11:02 anders Exp $
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
 * Interface for beans representing data for a WFList component.   
 * <p>
 * Last modified: $Date: 2004/06/18 14:11:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public interface WFListBean {
	
	/**
	 * Updates the data model for this list bean.
	 * If the rows parameter is 0 then all rows shall be updated with new data. 
	 * @param start the start row
	 * @param rows the number of rows to update with new data.
	 */
	public void updateDataModel(Integer start, Integer rows);
		
	/**
	 * Returns UIColumn components for the list.
	 * @var the var attribute for the list
	 */
	public UIColumn[] createColumns(String var);
		
	/**
	 * Returns the data model for this list bean.
	 */
	public DataModel getDataModel();
		
	/**
	 * Sets the data model for this list bean.
	 */
	public void setDataModel(DataModel dataModel);
}
