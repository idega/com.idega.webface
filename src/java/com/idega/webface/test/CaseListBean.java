/*
 * $Id: CaseListBean.java,v 1.1 2004/06/08 16:13:46 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;
import java.util.Date;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;

import com.idega.webface.WFUtil;
import com.idega.webface.bean.WFListBean;
import com.idega.webface.model.WFDataModel;

/**
 * Bean for content item case list rows.   
 * <p>
 * Last modified: $Date: 2004/06/08 16:13:46 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class CaseListBean extends WFListBean implements Serializable {

	public final static String CASE_ID = "case_id";
	
	private WFDataModel _dataModel = null;
	private ActionListener _caseLinkListener = null;

	private String _id = null;
	private String _description = null;
	private Date _created = null;
	private Date _lastModified = null;
	private String _author = null;
	private String _status = null;
	
	private String[] testColumnHeaders = { "Description", "Created", "Last modified", "Author", "Status" };				
	
	private String[] testDescriptions = {
		"Idega represented in the Baltics",
		"The new Hekla Rejser website is up-and running",
		"Idega Multimedia becomes Idega Software",
		"Idega featured in PC World!",
		"The new ÁTVR website is up-and running",
		"Lifidn Pension Fund selects idegaWeb Builder...",
		"Idega Software signs an agreement with the...",
		"Nacka elected the IT municipality 2003 in Sweden"
	};
	
	private Date[] testCreated = {
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date()
	};
	
	private Date[] testLastModified = {
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date(),
		new Date()
	};
	
	private String[] testAuthors = {
		"Smith",
		"Andersson",
		"Rappson",
		"Trappson",
		"Smith",
		"Rappson",
		"Anderson",
		"Andersson"
	};
	
	private String[] testStatus = {
		"Under review",
		"Rewrite",
		"Under review",
		"Ready for review",
		"Ready for review",
		"Ready for review",
		"Ready for review",
		"Ready for review"
	};

	/**
	 * Default constructor.
	 */
	public CaseListBean() {}

	/**
	 * Constructs a new case list bean with the specified case link listener.
	 */
	public CaseListBean(ActionListener l) {
		setCaseLinkListener(l);
	}
	
	/**
	 * Constructs a new case list bean with the specified parameters. 
	 */
	public CaseListBean(String id, String description, Date created, Date lastModified, String author, String status) {
		_id = id;
		_description = description;
		_created = created;
		_lastModified = lastModified;
		_author = author;
		_status = status;
	}
		
	public String getId() { return _id; }
	public String getDescription() { return _description; }
	public Date getCreated() { return _created; }
	public Date getLastModified() { return _lastModified; }
	public String getAuthor() { return _author; }
	public String getStatus() { return _status; }

	public void setId(String s) { _id = s; }
	public void setDescription(String s) { _description = s; }
	public void setCreated(Date d) { _created = d; }
	public void setLastModified(Date d) { _lastModified = d; }
	public void setAuthor(String s) { _author = s; }
	public void setStatus(String s) { _status = s; }
	
	public ActionListener getCaseLinkListener() { return _caseLinkListener; }
	public void setCaseLinkListener(ActionListener l) { _caseLinkListener = l; }
	
	/**
	 * @see com.idega.webface.bean.WFListBean#updateDataModel() 
	 */
	public DataModel updateDataModel(int start, int rows) {
		if (_dataModel == null) {
			_dataModel = new WFDataModel();
		}
		int availableRows = testDescriptions.length;
		if (rows == 0) {
			rows = availableRows;
		}
		int maxRow = start + rows;
		if (maxRow > availableRows) {
			maxRow = availableRows;
		}
		for (int i = start; i < maxRow; i++) {
			CaseListBean c = new CaseListBean(String.valueOf(i), testDescriptions[i], testCreated[i], testLastModified[i], testAuthors[i], testStatus[i]);
			_dataModel.set(c, i);
		}
		_dataModel.setRowCount(availableRows);
		return _dataModel;
	}
	
	/**
	 * @see com.idega.webface.bean.WFListBean#createColumns() 
	 */
	public UIColumn[] createColumns(String var) {
		int cols = testColumnHeaders.length;
		UIColumn[] columns = new UIColumn[cols];

		for (int i = 0; i < cols; i++) {
			UIColumn c = new UIColumn();
			c.setHeader(WFUtil.getText(testColumnHeaders[i]));
			columns[i] = c;
		}
		
		HtmlCommandLink l = WFUtil.getListLinkVB(var + ".description");
		l.setId(CASE_ID);
		l.addActionListener(_caseLinkListener);
		WFUtil.addParameterVB(l, "id", var + ".id");
		columns[0].getChildren().add(l);
		HtmlOutputText t = WFUtil.getListTextVB(var + ".created");
		columns[1].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".lastModified");
		columns[2].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".author");
		columns[3].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".status");
		columns[4].getChildren().add(t);		
		
		return columns;
	}
}
