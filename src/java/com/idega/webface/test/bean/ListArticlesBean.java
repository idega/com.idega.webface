/*
 * $Id: ListArticlesBean.java,v 1.1 2004/06/28 09:09:50 anders Exp $
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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;

import com.idega.webface.WFUtil;
import com.idega.webface.bean.WFListBean;
import com.idega.webface.model.WFDataModel;

/**
 * Bean for listing articles.   
 * <p>
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class ListArticlesBean implements WFListBean, Serializable {
	
	public final static String ARTICLE_ID = "article_id";
	
	private WFDataModel _dataModel = null;
	private ActionListener _articleLinkListener = null;

	private String _id = null;
	private String _headline = null;
	private String _published = null;
	
	private Date _searchPublishedFrom = null;
	private Date _searchPublishedTo = null;
	private String _searchCategoryId = null;
	
	private Map _allCategories = null;
	
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

	/**
	 * Default constructor.
	 */
	public ListArticlesBean() {}

	/**
	 * Constructs a new list articles bean with the specified article link listener.
	 */
	public ListArticlesBean(ActionListener l) {
		this();
		setArticleLinkListener(l);
	}
	
	/**
	 * Constructs a new list articles bean with the specified parameters. 
	 */
	public ListArticlesBean(String id, String headline, String published) {
		_id = id;
		_headline = headline;
		_published = published;
	}
		
	public String getId() { return _id; }
	public String getHeadline() { return _headline; }
	public String getPublished() { return _published; }

	public Date getSearchPublishedFrom() { return _searchPublishedFrom; }
	public Date getSearchPublishedTo() { return _searchPublishedTo; }
	public String getSearchCategoryId() { return _searchCategoryId; }

	public void setId(String s) { _id = s; }
	public void setHeadline(String s) { _headline = s; }
	public void setPublished(String s) { _published = s; }

	public void setSearchPublishedFrom(Date d) { _searchPublishedFrom = d; }
	public void setSearchPublishedTo(Date d) { _searchPublishedTo = d; }
	public void setSearchCategoryId(String s) { _searchCategoryId = s; }
	
	public ActionListener getArticleLinkListener() { return _articleLinkListener; }
	public void setArticleLinkListener(ActionListener l) { _articleLinkListener = l; }
	
	/**
	 * Returns all categories available for articles.
	 */
	public Map getCategories() {
		if (_allCategories == null) {
			_allCategories = new LinkedHashMap();
			_allCategories.put("All categories", "" + new Integer(-1));
			_allCategories.put("Public news", "" + new Integer(1));
			_allCategories.put("Business news", "" + new Integer(2));
			_allCategories.put("Company info", "" + new Integer(3));
			_allCategories.put("General info", "" + new Integer(4));
			_allCategories.put("IT stuff", "" + new Integer(5));
			_allCategories.put("Press releases", "" + new Integer(6));
			_allCategories.put("Internal info", "" + new Integer(7));
		}
		return _allCategories;
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
			ListArticlesBean bean = new ListArticlesBean(String.valueOf(i), testHeadlines[i], testPublished[i]);
			_dataModel.set(bean, i);
		}
		_dataModel.setRowCount(availableRows);
	}
	
	/**
	 * @see com.idega.webface.bean.WFListBean#createColumns() 
	 */
	public UIColumn[] createColumns(String var) {
		UIColumn col = new UIColumn();
		col.getChildren().add(WFUtil.getTextVB(var + ".published"));
		col.getChildren().add(WFUtil.getBreak());
		HtmlCommandLink l = WFUtil.getLinkVB(ARTICLE_ID, var + ".headline", _articleLinkListener);
		WFUtil.addParameterVB(l, "id", var + ".id");
		col.getChildren().add(l);
		col.getChildren().add(WFUtil.getBreak(2));
		
		return new UIColumn[] { col };
	}
	
	/**
	 * Generates a list result from the current bean search values. 
	 */
	public void list() {
		ListArticlesBean bean = new ListArticlesBean("100", "Headline", "--");
		_dataModel.set(bean, _dataModel.getRowCount());
	}
}
