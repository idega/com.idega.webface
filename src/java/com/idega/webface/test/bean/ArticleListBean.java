/*
 * $Id: ArticleListBean.java,v 1.1 2004/06/28 09:09:50 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.Serializable;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;

import com.idega.webface.WFUtil;
import com.idega.webface.bean.WFListBean;
import com.idega.webface.model.WFDataModel;

/**
 * Bean for article list rows.   
 * <p>
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class ArticleListBean implements WFListBean, Serializable {
	
	public final static String ARTICLE_ID = "article_id";
	
	private WFDataModel _dataModel = null;
	private ActionListener _articleLinkListener = null;

	private String _id = null;
	private String _headline = null;
	private String _published = null;
	private String _author = null;
	private String _status = null;
	private String _testStyle = null;
	
	private String[] testColumnHeaders = { "Headline", "Published", "Author", "Status" };				
	
	private String[] testHeadlines = {
		"Electronic Reykjavik built with IdegaWeb eGov",
		"Idega represented in the Baltic",
		"Idega and Agura IT at the Markus Evans eGovernment Europe 2004...",
		"Täby Municipality in Sweden now using IdegaWeb eGOV",
		"Code name : Check & Peng",
		"Conference sucess 'Electric Community - Here and now !'",
		"Vinbud.is (idegaWeb) voted best corporate website 2003 in Iceland",
		"The new Landsteinar-Strengur website implemented in IdegaWeb"
	};
	
	private String[] testPublished = {
		"4/20/04 3:04 PM",
		"4/20/04 3:00 PM",
		"4/14/04 2:48 PM",
		"4/14/04 2:32 PM",
		"4/14/04 12:17 PM",
		"12/5/03 3:02 PM",
		"10/30/03 3:10 PM",
		"10/27/03"				
	};
	
	private String[] testAuthors = {
		"Anderson",
		"Isildur",
		"Rappson",
		"Trappson",
		"Snap",
		"Rappson",
		"Anderson",
		"Trapp"
	};
	
	private String[] testStatus = {
		"Published",
		"Published",
		"Published",
		"Published",
		"Published",
		"Expired", // red text
		"Published",
		"Published"
	};

	/**
	 * Default constructor.
	 */
	public ArticleListBean() {}

	/**
	 * Constructs a new article list bean with the specified article link listener.
	 */
	public ArticleListBean(ActionListener l) {
		setArticleLinkListener(l);
	}
	
	/**
	 * Constructs a new article list bean with the specified parameters. 
	 */
	public ArticleListBean(String id, String headline, String published, String author, String status) {
		_id = id;
		_headline = headline;
		_published = published;
		_author = author;
		_status = status;
		_testStyle = "";
	}
		
	public String getId() { return _id; }
	public String getHeadline() { return _headline; }
	public String getPublished() { return _published; }
	public String getAuthor() { return _author; }
	public String getStatus() { return _status; }
	public String getTestStyle() { return _testStyle; }

	public void setId(String s) { _id = s; }
	public void setHeadline(String s) { _headline = s; }
	public void setPublished(String s) { _published = s; }
	public void setAuthor(String s) { _author = s; }
	public void setStatus(String s) { _status = s; }
	public void setTestStyle(String s) { _testStyle = s; }
	
	public ActionListener getArticleLinkListener() { return _articleLinkListener; }
	public void setArticleLinkListener(ActionListener l) { _articleLinkListener = l; }
	
	/**
	 * @see com.idega.webface.bean.WFListBean#updateDataModel() 
	 */
	public void updateDataModel(Integer start, Integer rows) {
		if (_dataModel == null) {
			_dataModel = new WFDataModel();
		}
		int availableRows = testHeadlines.length;
		int nrOfRows = rows.intValue();
		if (nrOfRows == 0) {
			nrOfRows = availableRows;
		}
		int maxRow = start.intValue() + nrOfRows;
		if (maxRow > availableRows) {
			maxRow = availableRows;
		}
		for (int i = start.intValue(); i < maxRow; i++) {
			ArticleListBean a = new ArticleListBean(String.valueOf(i), testHeadlines[i], testPublished[i], testAuthors[i], testStatus[i]);
			if (i == 5) {
				// set test style red
				a.setTestStyle("color:red");
			}
			_dataModel.set(a, i);
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
			c.setHeader(WFUtil.getText(testColumnHeaders[i]));
			columns[i] = c;
		}
		
		String styleAttr =  var + ".testStyle";
		HtmlCommandLink l = WFUtil.getListLinkVB(var + ".headline");
		l.setId(ARTICLE_ID);
		WFUtil.setValueBinding(l, "style", styleAttr);
		l.addActionListener(_articleLinkListener);
		WFUtil.addParameterVB(l, "id", var + ".id");
		columns[0].getChildren().add(l);
		HtmlOutputText t = WFUtil.getListTextVB(var + ".published");
		WFUtil.setValueBinding(t, "style", styleAttr);
		columns[1].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".author");
		WFUtil.setValueBinding(t, "style", styleAttr);
		columns[2].getChildren().add(t);
		t = WFUtil.getListTextVB(var + ".status");
		WFUtil.setValueBinding(t, "style", styleAttr);
		columns[3].getChildren().add(t);		
		
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
