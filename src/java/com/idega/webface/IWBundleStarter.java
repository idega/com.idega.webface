/*
 * Created on 3.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import com.idega.core.view.DefaultViewNode;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.repository.data.SingletonRepository;
import com.idega.webface.theme.ThemeManager;


/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class IWBundleStarter implements IWBundleStartable {

	/* (non-Javadoc)
	 * @see com.idega.idegaweb.IWBundleStartable#start(com.idega.idegaweb.IWBundle)
	 */
	public void start(IWBundle starterBundle) {
		//Initialize the ThemeManager;
		ThemeManager.getInstance();
		addViews(starterBundle);
	}

	/* (non-Javadoc)
	 * @see com.idega.idegaweb.IWBundleStartable#stop(com.idega.idegaweb.IWBundle)
	 */
	public void stop(IWBundle starterBundle) {
		SingletonRepository.getRepository().unloadInstance(ThemeManager.class);
	}
	
	public void addViews(IWBundle bundle){

		ViewManager viewManager = ViewManager.getInstance(bundle.getApplication());
		ViewNode node = viewManager.getWorkspaceRoot();
		
		DefaultViewNode img = new DefaultViewNode("imagechooser", node);
		img.setVisibleInMenus(false);
		img.setJspUri(bundle.getJSPURI("imageChooser.jsp"));

		DefaultViewNode temp = new DefaultViewNode("linkchooser", node);
		temp.setVisibleInMenus(false);
		temp.setJspUri(bundle.getJSPURI("linkChooser.jsp"));
		
		DefaultViewNode t2 = new DefaultViewNode("edit", temp);
		t2.setVisibleInMenus(false);
		t2.setJspUri(bundle.getJSPURI("linkChooserEdit.jsp"));

	}
	
}
