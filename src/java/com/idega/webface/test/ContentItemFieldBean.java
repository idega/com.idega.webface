/*
 * $Id: ContentItemFieldBean.java,v 1.1 2004/06/07 07:51:19 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;

/**
 * Bean for idegaWeb content item fields.   
 * <p>
 * Last modified: $Date: 2004/06/07 07:51:19 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class ContentItemFieldBean implements Serializable {
	
	public final static String FIELD_TYPE_STRING = "STRING";
	
	private int _contentItemFieldId = 0;
	private int _versionId = 0;
	private String _key = null;
	private String _value = null;
	private int _orderNo = 0;
	private String _fieldType = null;
	
	/**
	 * Default constructor.
	 */
	public ContentItemFieldBean() {}
	
	/**
	 * Constructs a new content item field bean with the specified parameters. 
	 */
	public ContentItemFieldBean(
			int contentItemFieldId,
			int versionId,
			String key,
			String value,
			int orderNo,
			String fieldType) {
		_contentItemFieldId = contentItemFieldId;
		_versionId = versionId;
		_key = key;
		_value = value;
		_orderNo = orderNo;
		_fieldType = fieldType;
	}
		
	public int getContentItemFieldId() { return _contentItemFieldId; }
	public int getVersionId() { return _versionId; }
	public String getKey() { return _key; }
	public String getValue() { return _value; }
	public int getOrderNo() { return _orderNo; }
	public String getFieldType() { return _fieldType; }

	public void setContentItemFieldId(int id) { _contentItemFieldId = id; } 
	public void setVersionId(int id) { _versionId = id; }
	public void setKey(String s) { _key = s; }
	public void setValue(String s) { _value = s; }
	public void setOrderNo(int n) { _orderNo = n; }
	public void setFieldType(String s) { _fieldType = s; }
}
