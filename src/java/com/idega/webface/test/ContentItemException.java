/*
 * $Id: ContentItemException.java,v 1.1 2004/06/07 07:51:19 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.FacesException;

/**
 * Exception for content items.   
 * <p>
 * Last modified: $Date: 2004/06/07 07:51:19 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class ContentItemException extends FacesException {
	
	private String _localizationKey = null;
	
	/**
	 * Constructs a new content item exception. 
	 * 
	 * @param localizationKey the localization key for the exception message
	 */
	public ContentItemException (String localizationKey) {
		super();
		_localizationKey = localizationKey;
	}

	/**
	 * Returns the localization key for the exception message. 
	 */
	public String getLocalizationKey() {
		return _localizationKey;
	}
}
