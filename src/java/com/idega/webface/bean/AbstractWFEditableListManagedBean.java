/*
 * $Id: AbstractWFEditableListManagedBean.java,v 1.1 2005/01/07 19:45:30 gummi Exp $
 * Created on 29.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.model.DataModel;
import com.idega.webface.WFUtil;
import com.idega.webface.model.WFDataModel;


/**
 * 
 *  Last modified: $Date: 2005/01/07 19:45:30 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractWFEditableListManagedBean implements WFListBean {

	private WFDataModel dataModel = new WFDataModel();
	private boolean dataModelInitialized = false;
	
	/**
	 * 
	 */
	public AbstractWFEditableListManagedBean() {
		super();
	}
	
	public abstract Object[] getData();
	public abstract String[] getBindingNames();
	public abstract UIComponent getUIComponent(String var, String binding);
	public abstract UIComponent getHeader(String binding);
	
	/**
	 * Updates the datamodel, definded by WFList
	 * @param first Number of first element
	 * @param rows Total number of rows
	 */
	public void updateDataModel(Integer start, Integer rows) {
		if (dataModel == null) {
			dataModel = new WFDataModel();
		}
		
		Object[] beans = getData();
		
		int availableRows = beans.length;
		 
		int nrOfRows = rows.intValue();
		if (nrOfRows == 0) {
			nrOfRows = availableRows;
		}
		int maxRow = Math.min(start.intValue() + nrOfRows,availableRows);
		for (int i = start.intValue(); i < maxRow; i++) {
			dataModel.set(beans[i], i);
		}

		dataModel.setRowCount(availableRows);
		dataModelInitialized=true;
	}	
	
	
	/* (non-Javadoc)
	 * @see com.idega.webface.bean.WFListBean#createColumns(java.lang.String)
	 */
	public UIColumn[] createColumns(String var) {
		
		List l = new ArrayList();
		
		String[] binding = getBindingNames();
		
		for (int i = 0; i < binding.length; i++) {
			UIColumn col2 = new UIColumn();
			col2.setHeader(getHeader(binding[i]));		
			UIComponent component = getUIComponent(var,binding[i]);
			String ref = var + "." + binding[i];
			component.setValueBinding("value", WFUtil.createValueBinding("#{" + ref + "}"));
			col2.getChildren().add(component);
			l.add(col2);
		}

		return (UIColumn[])l.toArray(new UIColumn[l.size()]);
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see com.idega.webface.bean.WFListBean#getDataModel()
	 */
	public DataModel getDataModel() {
		if(!dataModelInitialized){
			updateDataModel(new Integer(0),new Integer(0));
		}
		return dataModel;
	}

	/* (non-Javadoc)
	 * @see com.idega.webface.bean.WFListBean#setDataModel(javax.faces.model.DataModel)
	 */
	public void setDataModel(DataModel model) {
		this.dataModel = (WFDataModel)model;
	}
}
