/*
 * Created on 13.7.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.workspace;

import java.io.IOException;
import javax.faces.context.FacesContext;
import com.idega.core.localisation.presentation.LocalePresentationUtil;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Page;
import com.idega.presentation.Table;
import com.idega.presentation.app.IWControlCenter;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.webface.WFBezel;


public class WorkspaceLoginPage extends Page {
	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private IWBundle iwb;
	private IWResourceBundle iwrb;
	String backgroundColor = "#B0B29D";

	public WorkspaceLoginPage() {
		setTransient(false);
	}
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	public void main(IWContext iwc) {
		iwb = this.getBundle(iwc);
		iwrb = this.getResourceBundle(iwc);

		Page thePage = this;
		thePage.setTitle("idegaWeb Applications");

		Table frameTable = new Table(1, 1);
		frameTable.setWidth("100%");
		frameTable.setHeight("100%");
		frameTable.setCellpadding(0);
		frameTable.setCellspacing(0);
		frameTable.setAlignment(1, 1, "center");
		frameTable.setVerticalAlignment(1, 1, "middle");
		
		WFBezel mainTable = new WFBezel();
		mainTable.setWidth("400px");
		mainTable.setHeight("300px");

		frameTable.add(mainTable, 1, 1);

		Table dropdownTable = new Table(1, 1);
		dropdownTable.setWidth(148);
		dropdownTable.setCellpadding(0);
		dropdownTable.setCellspacing(0);
		dropdownTable.setAlignment(1, 1, "center");
		//mainTable.setAlignment(1, 3, Table.HORIZONTAL_ALIGN_RIGHT);
		mainTable.add(dropdownTable);

		Form myForm = new Form();
		myForm.setEventListener(com.idega.core.localisation.business.LocaleSwitcher.class.getName());
		DropdownMenu dropdown = LocalePresentationUtil.getAvailableLocalesDropdown(iwc);
		dropdown.setStyleAttribute("font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; border-style:solid; border-width:1; border-color: #000000");
		myForm.add(dropdown);
		dropdownTable.add(myForm);


		boolean isLoggedOn = false;
		try {
			isLoggedOn = iwc.isLoggedOn();
		}
		catch (Exception e) {
			isLoggedOn = false;
		}

		if (isLoggedOn) {
			IWControlCenter iwcc = new IWControlCenter();
			mainTable.add(iwcc);
			//headerImage = iwrb.getImage("login/header_app_suite.jpg", "", 323, 196);

			try {
				WFLogin login = new WFLogin();
				login.setHeight("60");
				login.setWidth("70");
				login.setAllowCookieLogin(true);
				//MethodInvoker invoker = MethodInvoker.getInstance();
				//invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", iwrb.getImageURI("login/logout.gif"));
				//invoker.invokeMethodWithStringParameter(login, "setHeight", "60");
				//invoker.invokeMethodWithStringParameter(login, "setWidth", "70");
				//invoker.invokeMethodWithStringParameter(login, "setLoginAlignment", "center");
				//invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", true);
				//invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", true);
				mainTable.add(login);
			}
			catch (Exception e) {
				add(iwrb.getLocalizedString("login.init.error", "There was an error initialising the login component, most likely it is missing"));
				e.printStackTrace();
			}

		}

		else {
			try {
				WFLogin login = new WFLogin();
				login.setHeight("60");
				login.setWidth("70");
				login.setAllowCookieLogin(true);
				//MethodInvoker invoker = MethodInvoker.getInstance();
				//invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", iwrb.getImageURI("login/logout.gif"));
				//invoker.invokeMethodWithStringParameter(login, "setHeight", "60");
				//invoker.invokeMethodWithStringParameter(login, "setWidth", "70");
				//invoker.invokeMethodWithStringParameter(login, "setLoginAlignment", "center");
				//invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", true);
				//invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", true);
				
				mainTable.add(login);
			}
			catch (Exception e) {
				add(iwrb.getLocalizedString("login.init.error", "There was an error initialising the login component, most likely it is missing"));
				e.printStackTrace();
			}
		}

		thePage.add(frameTable);
		//thePage.add(mainTable);
	}

	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException{
		super.encodeChildren(context);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
}