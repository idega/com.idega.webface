/*
 * $Id: CMSTest.java,v 1.3 2004/06/08 16:14:47 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;

/**
 * Content management system test/demo. 
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class CMSTest {

	/**
	 * Returns test/demo page. 
	 */
	public UIComponent createContent() {
		return new CMSPage();
	}	
}
