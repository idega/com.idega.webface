/*
 * $Id: ContentItemCaseBean.java,v 1.2 2004/06/08 16:14:47 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Bean for idegaWeb content item cases.   
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */

public class ContentItemCaseBean implements Serializable {
	
	public final static String CASE_CODE = "CONTIM";
	
	public final static String STATUS_NEW = "SNEW";
	public final static String STATUS_READY_FOR_REVIEW = "RFRV";
	public final static String STATUS_UNDER_REVIEW = "UNRV";
	public final static String STATUS_REWRITE = "RWRT";
	public final static String STATUS_PENDING_PUBLISHING = "PPUB";
	public final static String STATUS_PUBLISHED = "PUBL";
	public final static String STATUS_EXPIRED = "EXPD";
	public final static String STATUS_DELETED = "DELE";

	public final static String STATUS_LOC_KEY_PREFIX = "case_status_";

	private int _caseId = 0;
	private String _caseCode = null;
	private String _caseStatus = null;
	private Timestamp _createdTimestamp = null;
	private Timestamp _publishedFromDate = null;
	private Timestamp _publishedToDate = null;
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
	public Timestamp getCreatedTimestamp() { return _createdTimestamp; }
	public Timestamp getPublishedFromDate() { return _publishedFromDate; }
	public Timestamp getPublishedToDate() { return _publishedToDate; }
	public int getVersionedContentItemId() { return _versionedContentItemId; }

	public void setCaseId(int id) { _caseId = id; } 
	public void setCaseCode(String s) { _caseCode = s; }
	public void setCaseStatus(String s) { _caseStatus = s; }
	public void setCreatedTimestamp(Timestamp t) { _createdTimestamp = t; }
	public void setPublishedFromDate(Timestamp t) { _publishedFromDate = t; }
	public void setPublishedToDate(Timestamp t) { _publishedToDate = t; }
}
