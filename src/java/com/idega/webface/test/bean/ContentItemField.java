/*
 * Created on 9.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.test.bean;

/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public interface ContentItemField {

	public final static String FIELD_TYPE_STRING = "STRING";

	public final static String FIELD_TYPE_BINARY = "BINARY";

	public abstract int getContentItemFieldId();

	public abstract int getVersionId();

	public abstract String getKey();

	public abstract String getValue();

	public abstract byte[] getBinaryValue();

	public abstract int getOrderNo();

	public abstract String getOrderNoString();

	public abstract String getFieldType();

	public abstract void setContentItemFieldId(int id);

	public abstract void setVersionId(int id);

	public abstract void setKey(String s);

	public abstract void setValue(String s);

	public abstract void setBinaryValue(byte[] binaryValue);

	public abstract void setOrderNo(int n);

	public abstract void setFieldType(String s);

	public abstract String getImageURI();

	public abstract String getName();

	public abstract void setName(String s);
}