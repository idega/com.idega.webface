/*
 * $Id: AbstractWFEditableListManagedBean.java,v 1.5 2006/04/09 11:59:21 laddi Exp $
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

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.repository.RepositoryService;
import com.idega.util.expression.ELUtil;
import com.idega.webface.WFUtil;
import com.idega.webface.model.WFDataModel;


/**
 *
 *  Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 *
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.5 $
 */
public abstract class AbstractWFEditableListManagedBean implements WFListBean {

	private WFDataModel dataModel = new WFDataModel();
	private boolean dataModelInitialized = false;

	@Autowired
	private RepositoryService repository;

	/**
	 *
	 */
	public AbstractWFEditableListManagedBean() {
		super();

		ELUtil.getInstance().autowire(this);
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
	@Override
	public void updateDataModel(Integer start, Integer rows) {
		if (this.dataModel == null) {
			this.dataModel = new WFDataModel();
		}

		WFEditableListDataBean[] beans = getData();

		int availableRows = beans.length;

		int nrOfRows = rows.intValue();
		if (nrOfRows == 0) {
			nrOfRows = availableRows;
		}
		int maxRow = Math.min(start.intValue() + nrOfRows,availableRows);
		for (int i = start.intValue(); i < maxRow; i++) {
			this.dataModel.set(beans[i], i);
		}

		this.dataModel.setRowCount(availableRows);
		this.dataModelInitialized=true;
	}

	@Override
	public UIColumn[] createColumns(String var) {
		List<UIColumn> l = new ArrayList<UIColumn>();
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

		return l.toArray(new UIColumn[l.size()]);
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
	@Override
	public DataModel getDataModel() {
		if(!this.dataModelInitialized){
			updateDataModel(new Integer(0),new Integer(0));
		}
		return this.dataModel;
	}

	/* (non-Javadoc)
	 * @see com.idega.webface.bean.WFListBean#setDataModel(javax.faces.model.DataModel)
	 */
	@Override
	public void setDataModel(DataModel model) {
		this.dataModel = (WFDataModel)model;
	}

	public class EmptyRow implements WFEditableListDataBean {

		private Object[] NullElementObjectArray;

		public EmptyRow(){
			this.NullElementObjectArray = new Object[getNumberOfColumns()];
		}

		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getSelectItemListArray()
		 */
		@Override
		public Object[] getSelectItemListArray() {
			return this.NullElementObjectArray;
		}

		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getValues()
		 */
		@Override
		public Object[] getValues() {
			return this.NullElementObjectArray;
		}

		/* (non-Javadoc)
		 * @see com.idega.webface.bean.WFEditableListDataBean#getRendered()
		 */
		@Override
		public Boolean getRendered() {
			return Boolean.FALSE;
		}

	}

	public RepositoryService getRepository() {
		if (repository == null)
			ELUtil.getInstance().autowire(this);
		return repository;
	}
}