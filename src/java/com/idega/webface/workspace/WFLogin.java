/*
 * Created on 6.8.2004 by tryggvil in com.idega.webface
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.workspace;

import javax.faces.component.UIComponent;
import com.idega.presentation.PresentationObject;
import com.idega.util.reflect.MethodInvoker;
import com.idega.webface.WFContainer;


/**
 * This is a wrapper around the com.idega.block.login Login component, because of dependency problems.
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public class WFLogin extends WFContainer {

	public static String STYLE_CLASS="wf_login";
	MethodInvoker invoker = MethodInvoker.getInstance();
	
	/**
	 * 
	 */
	public WFLogin() {
		super();
		this.setStyleClass(STYLE_CLASS);
		PresentationObject login=null;
		try {
			login = (PresentationObject) Class.forName("com.idega.block.login.presentation.Login").newInstance();
			add(login);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", iwrb.getImageURI("login/logout.gif"));
			//invoker.invokeMethodWithStringParameter(login, "setHeight", "60");
			//invoker.invokeMethodWithStringParameter(login, "setWidth", "70");
			//invoker.invokeMethodWithStringParameter(login, "setLoginAlignment", "center");
			//invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", true);
			//invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", true);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	private UIComponent getEmbeddedLogin(){
		return (UIComponent)this.getChildren().get(0);
	}
	
	public void setHeight(String height){
		super.setHeight(height);
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithStringParameter(login, "setHeight", height);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setWidth(String width){
		super.setWidth(width);
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithStringParameter(login, "setWidth", width);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void setAllowCookieLogin(boolean ifAllow){
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", ifAllow);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLogoutButtonImageURL(String url){
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setViewOnlyLogoutButton(boolean ifView){
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", ifView);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static final int LAYOUT_VERTICAL = 1;
	public static final int LAYOUT_HORIZONTAL = 2;
	public static final int LAYOUT_STACKED = 3;
	public static final int SINGLE_LINE = 4;
	public static final int LAYOUT_FORWARD_LINK = 5;
	
	public void setLayout(int layout){
		UIComponent login = this.getEmbeddedLogin();
		try {
			invoker.invokeMethodWithIntParameter(login, "setLayout", layout);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLayoutVertical(){
		setLayout(LAYOUT_VERTICAL);
	}

	public void setLayoutHorizontal(){
		setLayout(LAYOUT_HORIZONTAL);
	}
	
	public void setLayoutStacked(){
		setLayout(LAYOUT_STACKED);
	}

	public void setLayoutSingleLine(){
		setLayout(SINGLE_LINE);
	}
	
	public void setLayoutForwardLink(){
		setLayout(LAYOUT_FORWARD_LINK);
	}	
	
}
