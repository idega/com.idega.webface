/*
 * Created on 9.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.test.bean;

import java.util.Date;

/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public interface ContentItemCase {

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

	public abstract int getCaseId();

	public abstract String getCaseCode();

	public abstract String getCaseStatus();

	public abstract Date getCreatedTimestamp();

	public abstract Date getPublishedFromDate();

	public abstract Date getPublishedToDate();

	public abstract int getVersionedContentItemId();

	public abstract void setCaseId(int id);

	public abstract void setCaseCode(String s);

	public abstract void setCaseStatus(String s);

	public abstract void setCreatedTimestamp(Date d);

	public abstract void setPublishedFromDate(Date d);

	public abstract void setPublishedToDate(Date d);
}