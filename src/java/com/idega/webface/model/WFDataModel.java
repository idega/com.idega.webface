/*
 * $Id: WFDataModel.java,v 1.2 2004/11/16 00:51:35 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.model;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

/**
 * WFDataModel is an extension of DataModel that supports setting row values
 * at any row index. The size of the model is dynamically expanded when needed.   
 * <p>
 * Last modified: $Date: 2004/11/16 00:51:35 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 * @see javax.faces.model.DataModel
 */
public class WFDataModel extends DataModel implements Serializable {

	private final static int INITIAL_DATA_SIZE = 10;
	
	private int _rowCount = -1;
	private int _rowIndex = 0;
	private Object[] _rowData = null;
	
	/**
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	public int getRowCount() {
		return _rowCount;
	}

	/**
	 * @see javax.faces.model.DataModel#getRowData()
	 */
	public Object getRowData() {
		if (_rowData != null && _rowIndex < _rowData.length && _rowIndex >= 0) {
			return _rowData[_rowIndex];
		} else {
			return null;
		}
	}

	/**
	 * @see javax.faces.model.DataModel#getRowIndex()
	 */
	public int getRowIndex() {
		return _rowIndex;
	}

	/**
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		return _rowData;
	}

	/**
	 * @see javax.faces.model.DataModel#isRowAvailable()
	 */
	public boolean isRowAvailable() {
		if(_rowData!=null){
			return (_rowIndex < _rowData.length);
		}
		return false;
	}

	/**
	 * @see javax.faces.model.DataModel#setRowIndex()
	 */
	public void setRowIndex(int rowIndex) {
		if (rowIndex < -1) {
			throw new IllegalArgumentException();
		}
		int old = _rowIndex;
		_rowIndex = rowIndex;
		if (_rowData == null) {
			return;
		}
		DataModelListener listeners[] = getDataModelListeners();
		if (old != _rowIndex && listeners != null) {
			Object rowData = null;
			if (isRowAvailable()) {
				rowData = getRowData();
			}
			DataModelEvent event = new DataModelEvent(this, rowIndex, rowData);
			int n = listeners.length;
			for(int i = 0; i < n; i++) {
				if (listeners[i] != null) {
					listeners[i].rowSelected(event);
				}
			}
		}
	}

	/**
	 * @see javax.faces.model.DataModel#setWrappedData()
	 */
	public void setWrappedData(Object data) {
		if (data == null) {
			_rowData = null;
			setRowIndex(-1);
		} else {
			_rowData = (Object[]) data;
			_rowIndex = -1;
			setRowIndex(0);
		}
	}

	/**
	 * Sets the row count for this data model. 
	 */
	public void setRowCount(int rowCount) {
		_rowCount = rowCount;
	}

	/**
	 * Sets the data for the specified row. 
	 */
	public void set(Object data, int row) {
		if (_rowData == null) {
			_rowData = new Object[INITIAL_DATA_SIZE + row];
		} else if (row >= _rowData.length) {
			Object[] newData = new Object[_rowData.length * 2];
			System.arraycopy(_rowData, 0, newData, 0, _rowData.length);
			_rowData = newData;
		}
		_rowData[row] = data;
		if (row + 1 > _rowCount) {
			_rowCount = row + 1;
		}
	}
}