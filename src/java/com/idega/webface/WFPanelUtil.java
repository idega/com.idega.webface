/*
 * $Id: WFPanelUtil.java,v 1.1 2004/06/07 07:51:47 anders Exp $
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
 * Last modified: $Date: 2004/06/07 07:51:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFPanelUtil {

	/**
	 * Returns an application two columns main panel. 
	 */
	public static HtmlPanelGrid getApplicationPanel() {
		HtmlPanelGrid p = new HtmlPanelGrid();
		p.setColumns(2);
		p.setStyleClass("wf_applicationpanel");
		p.setColumnClasses("wf_applicationleftcolumn");
		return p;
	}

	/**
	 * Returns an application two columns main panel. 
	 */
	public static HtmlPanelGrid getFormPanel(int columns, int rows) {
		HtmlPanelGrid p = new HtmlPanelGrid();
		p.setColumns(2);
		p.setStyleClass("wf_formpanel");
		String rowClasses = "";
		for (int i = 0; i < rows; i++) {
			if (i % 2 == 0) {
				rowClasses += "wf_inputheaderrow";
			} else {
				rowClasses += "wf_inputcomponentrow";
			}
			if (i != rows - 1) {
				rowClasses += ",";
			}
		}
		p.setRowClasses(rowClasses); // Note: rowClasses not implemented in myFaces grid renderer (2002-05-28)
		return p;
	}
}
