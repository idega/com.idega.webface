/*
 * $Id: ContentItemCaseBean.java,v 1.2 2004/10/19 11:09:29 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Bean for idegaWeb content item cases.   
 * <p>
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */

public class ContentItemCaseBean implements Serializable, ContentItemCase {
	
	private int _caseId = 0;
	private String _caseCode = null;
	private String _caseStatus = null;
	private Date _createdTimestamp = null;
	private Date _publishedFromDate = null;
	private Date _publishedToDate = null;
	private int _versionedContentItemId = 0;
	
	/**
	 * Default constructor.
	 */
	public ContentItemCaseBean() {}
	
	/**
	 * Constructs a new content item case bean with the specified parameters. 
	 */
	public ContentItemCaseBean(
			int caseId,
			String caseStatus,
			Timestamp createdTimestamp,
			Timestamp publishedFromDate,
			Timestamp publishedToDate,
			int versionedContentItemId) {
		_caseId = caseId;
		_caseCode = CASE_CODE;
		_caseStatus = caseStatus;
		_createdTimestamp = createdTimestamp;
		_publishedFromDate = publishedFromDate;
		_publishedToDate = publishedToDate;
		_versionedContentItemId = versionedContentItemId;
	}
		
	public int getCaseId() { return _caseId; }
	public String getCaseCode() { return _caseCode; }
	public String getCaseStatus() { return _caseStatus; }
	public Date getCreatedTimestamp() { return _createdTimestamp; }
	public Date getPublishedFromDate() { return _publishedFromDate; }
	public Date getPublishedToDate() { return _publishedToDate; }
	public int getVersionedContentItemId() { return _versionedContentItemId; }

	public void setCaseId(int id) { _caseId = id; } 
	public void setCaseCode(String s) { _caseCode = s; }
	public void setCaseStatus(String s) { _caseStatus = s; }
	public void setCreatedTimestamp(Date d) { _createdTimestamp = d; }
	public void setPublishedFromDate(Date d) { _publishedFromDate = d; }
	public void setPublishedToDate(Date d) { _publishedToDate = d; }
}
