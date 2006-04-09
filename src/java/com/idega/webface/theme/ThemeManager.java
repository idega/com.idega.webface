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
import com.idega.repository.data.Instantiator;
import com.idega.repository.data.Singleton;
import com.idega.repository.data.SingletonRepository;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class ThemeManager implements Singleton {
		
	Logger log = Logger.getLogger(ThemeManager.class.getName());
	
	private static Instantiator instantiator = new Instantiator() { public Object getInstance() { return new ThemeManager();}};
	
	Class defaultThemeClass=WFDefaultTheme.class;
	
	protected ThemeManager(){
		initialize();
	}
	
	public static ThemeManager getInstance(){
		return (ThemeManager) SingletonRepository.getRepository().getInstance(ThemeManager.class, instantiator);
	}

	/**
	 * 
	 */
	private void initialize() {
		try {
			this.log.info("Initializing ThemeManager");
			this.defaultThemeClass.newInstance();
			this.log.info("Done Initializing ThemeManager");
		}
		catch (InstantiationException e) {
			this.log.warning(e.getMessage());
		}
		catch (IllegalAccessException e) {
			this.log.warning(e.getMessage());
		}
	}
	
}
