/*
 * Created on 3.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.theme;

import java.util.logging.Logger;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class ThemeManager {
	
	Logger log = Logger.getLogger(ThemeManager.class.getName());
	private static ThemeManager instance;
	Class defaultThemeClass=WFDefaultTheme.class;
	
	private ThemeManager(){
		initialize();
	}
	
	public static ThemeManager getInstance(){
		if(instance==null){
			instance = new ThemeManager();
		}
		return instance;
	}

	/**
	 * 
	 */
	private void initialize() {
		try {
			log.info("Initializing ThemeManager");
			defaultThemeClass.newInstance();
			log.info("Done Initializing ThemeManager");
		}
		catch (InstantiationException e) {
			log.warning(e.getMessage());
		}
		catch (IllegalAccessException e) {
			log.warning(e.getMessage());
		}
	}
	
}
