/*
 * $Id: WFPanelUtil.java,v 1.3 2004/06/23 13:23:43 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.faces.component.html.HtmlPanelGrid;

/**
 * Utility class for creating default application panels.
 * <p>
 * Last modified: $Date: 2004/06/23 13:23:43 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class WFPanelUtil {

	/**
	 * Returns an application two columns main panel. 
	 */
	public static HtmlPanelGrid getApplicationPanel() {
		HtmlPanelGrid p = new HtmlPanelGrid();
		p.setColumns(2);
		p.setStyleClass("wf_applicationpanel");
		p.setColumnClasses("wf_applicationleftcolumn,wf_applicationrightcolumn");
		return p;
	}

	/**
	 * Returns a form panel with css style for alternating header and input rows. 
	 */
	public static HtmlPanelGrid getFormPanel(int columns) {
		HtmlPanelGrid p = getPlainFormPanel(columns);
		String rowClasses = "wf_inputheaderrow,wf_inputcomponentrow";
		p.setRowClasses(rowClasses); // Note: rowClasses not implemented in myFaces grid renderer (2004-05-28)
		return p;
	}

	/**
	 * Returns a plain form panel without css style for rows. 
	 */
	public static HtmlPanelGrid getPlainFormPanel(int columns) {
		HtmlPanelGrid p = new HtmlPanelGrid();
		p.setColumns(columns);
		p.setStyleClass("wf_paneltable");
		return p;
	}
}
