/*
 * $Id: AbstractWFEditableListManagedBean.java,v 1.4 2005/01/18 17:44:33 gummi Exp $
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
 *  Last modified: $Date: 2005/01/18 17:44:33 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.4 $
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
	
	public abstract WFEditableListDataBean[] getData();
	public abstract int getNumberOfColumns();
	public abstract UIComponent getUIComponent(String var, int columnIndex);
	public abstract UIComponent getHeader(int columnIndex);
	
	/**
	 * @param columnIndex
	 * @return
	 */
	public UIComponent getFooter(int columnIndex) {
		return null;
	}


	
	
	/**
	 * Updates the datamodel, definded by WFList
	 * @param first Number of first element
	 * @param rows Total number of rows
	 */
	public void updateDataModel(Integer start, Integer rows) {
		if (dataModel == null) {
			dataModel = new WFDataModel();
		}
		
		WFEditableListDataBean[] beans = getData();
		
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
		
		for (int i = 0; i < getNumberOfColumns(); i++) {
			UIColumn column = new UIColumn();
			
			UIComponent header = getHeader(i);
			if(header != null){
				column.setHeader(header);
			}
			
			UIComponent footer = getFooter(i);
			if(footer != null){
				column.setFooter(footer);
			}
			
			UIComponent component = createCellWrapper(var,i);
			column.getChildren().add(component);
			l.add(column);
		}
		
		

		return (UIColumn[])l.toArray(new UIColumn[l.size()]);
	}

	/**
	 * This method invokes #constructWFEditableListCellWrapper(...) to get the component and then adds value-binding
	 * between WFEditableListCellWrapper#(value, rendered and selectItemList) and WFEditableListDataBean#(values, rendered and selectItemListArray)
	 * 
	 * @param parentBeanID
	 * @param var
	 * @param columnIndex from 0 to ...
	 * @return
	 */
	public UIComponent createCellWrapper(String var, int columnIndex){
		WFEditableListCellWrapper component = constructWFEditableListCellWrapper(var, columnIndex);
		WFUtil.setValueBindingToArray(component,"value",var+".values",columnIndex);
		WFUtil.setValueBindingToArray(component,"selectItemList",var+".selectItemListArray",columnIndex);
		WFUtil.setValueBinding(component,"rendered",var+".rendered");
		return component;
	}
	
	
	/**
	 * 
	 * @param uiComponentFactoryBeanID
	 * @param var
	 * @param columnID
	 * @return
	 */
	public WFEditableListCellWrapper constructWFEditableListCellWrapper(String var, int columnIndex){
		return new WFEditableListCellWrapper(getUIComponent(var,columnIndex));
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
	
	public class EmptyRow implements WFEditableListDataBean {

		private Object[] NullElementObjectArray;
		
		public EmptyRow(){
			NullElementObjectArray = new Object[getNumberOfColumns()];
		}
		
		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getSelectItemListArray()
		 */
		public Object[] getSelectItemListArray() {
			return NullElementObjectArray;
		}

		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getValues()
		 */
		public Object[] getValues() {
			return NullElementObjectArray;
		}

		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getRendered()
		 */
		public Boolean getRendered() {
			return Boolean.FALSE;
		}
		
	}

}
