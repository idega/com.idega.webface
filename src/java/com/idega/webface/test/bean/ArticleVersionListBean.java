/*
 * $Id: ArticleVersionListBean.java,v 1.2 2004/06/30 13:34:57 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;

import com.idega.webface.WFPage;
import com.idega.webface.WFUtil;
import com.idega.webface.bean.WFListBean;
import com.idega.webface.model.WFDataModel;

/**
 * Bean for article version list rows.   
 * <p>
 * Last modified: $Date: 2004/06/30 13:34:57 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */

public class ArticleVersionListBean implements WFListBean, Serializable {
	
	public final static String ARTICLE_VERSION_ID = "article_version_id";
	
	private WFDataModel _dataModel = null;
	private ActionListener _articleLinkListener = null;

	private int _versionId = 0;
	private String _revision = null;
	private Date _created = null;
	private String _comment = null;
	private String _author = null;
	private String _publishedBy = null;
	
	private String[] testColumnHeaders = { 
		WFPage.CONTENT_BUNDLE + ".version", 
		WFPage.CONTENT_BUNDLE + ".created",
		WFPage.CONTENT_BUNDLE + ".comment",
		WFPage.CONTENT_BUNDLE + ".author",
		WFPage.CONTENT_BUNDLE + ".published_by"
	};				
	
	private String[] testRevisions = {
		"1.4",
		"1.3",
		"1.2",
		"1.1",
		"1.0"
	};
	
	private Date[] testCreated = {
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date()
	};
	
	private String[] testComments = {
		"Modified link url",
		"Removed section about...",
		"Added icon. image",
		"Added links",
		"First version"
	};
	
	private String[] testAuthors = {
		"Anderson",
		"Anderson",
		"Anderson",
		"Anderson",
		"Anderson"
	};
	
	private String[] testPublishedBy = {
		"Sam",
		"Sam",
		"Sam",
		"Sam",
		"Sam"
	};

	/**
	 * Default constructor.
	 */
	public ArticleVersionListBean() {}

	/**
	 * Constructs a new article version list bean with the specified article link listener.
	 */
	public ArticleVersionListBean(ActionListener l) {
		setArticleLinkListener(l);
	}
	
	/**
	 * Constructs a new article version list bean with the specified parameters. 
	 */
	public ArticleVersionListBean(int versionId, String revision, Date created, String comment, String author, String publishedBy) {
		_versionId = versionId;
		_revision = revision;
		_created = created;
		_comment = comment;
		_author = author;
		_publishedBy = publishedBy;
	}
		
	public int getVersionId() { return _versionId; }
	public String getRevision() { return _revision; }
	public Date getCreated() { return _created; }
	public String getComment() { return _comment; }
	public String getAuthor() { return _author; }
	public String getPublishedBy() { return _publishedBy; }

	public void setVersionId(int id) { _versionId = id; }
	public void setRevision(String s) { _revision = s; }
	public void setCreated(Date d) { _created = d; }
	public void setComment(String s) { _comment = s; }
	public void setAuthor(String s) { _author = s; }
	public void setPublishedBy(String s) { _publishedBy = s; }
	
	public ActionListener getArticleLinkListener() { return _articleLinkListener; }
	public void setArticleLinkListener(ActionListener l) { _articleLinkListener = l; }
	
	/**
	 * @see com.idega.webface.bean.WFListBean#updateDataModel() 
	 */
	public void updateDataModel(Integer start, Integer rows) {
		if (_dataModel == null) {
			_dataModel = new WFDataModel();
		}
		int availableRows = testRevisions.length;
		int nrOfRows = rows.intValue();
		if (nrOfRows == 0) {
			nrOfRows = availableRows;
		}
		int maxRow = start.intValue() + nrOfRows;
		if (maxRow > availableRows) {
			maxRow = availableRows;
		}
		for (int i = start.intValue(); i < maxRow; i++) {
			ArticleVersionListBean bean = new ArticleVersionListBean(i, testRevisions[i], testCreated[i], testComments[i], testAuthors[i], testPublishedBy[i]);
			_dataModel.set(bean, i);
		}
		_dataModel.setRowCount(availableRows);
	}
	
	/**
	 * @see com.idega.webface.bean.WFListBean#createColumns() 
	 */
	public UIColumn[] createColumns(String var) {
		int cols = testColumnHeaders.length;
		UIColumn[] columns = new UIColumn[cols];

		for (int i = 0; i < cols; i++) {
			UIColumn c = new UIColumn();
			c.setHeader(WFUtil.getTextVB(testColumnHeaders[i]));
			columns[i] = c;
		}
		
		HtmlCommandLink l = WFUtil.getListLinkVB(var + ".revision");
		l.setId(ARTICLE_VERSION_ID);
		l.addActionListener(_articleLinkListener);
		WFUtil.addParameterVB(l, "id", var + ".versionId");
		columns[0].getChildren().add(l);
		HtmlOutputText t = WFUtil.getListTextVB(var + ".created");
		columns[1].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".comment");
		columns[2].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".author");
		columns[3].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".publishedBy");
		columns[4].getChildren().add(t);		
		
		return columns;
	}
	
	/**
	 * @see com.idega.webface.bean.WFListBean#getDataModel() 
	 */
	public DataModel getDataModel() {
		return _dataModel;
	}
	
	/**
	 * @see com.idega.webface.bean.WFListBean#setDataModel() 
	 */
	public void setDataModel(DataModel dataModel) {
		_dataModel = (WFDataModel) dataModel;
	}
}
