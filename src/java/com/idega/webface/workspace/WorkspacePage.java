/*
 * Created on 13.7.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.workspace;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import com.idega.faces.view.ViewManager;
import com.idega.faces.view.ViewNode;
import com.idega.faces.view.node.FramedApplicationViewNode;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Page;
import com.idega.presentation.app.IWControlCenter;
import com.idega.webface.WFFrame;


public class WorkspacePage extends Page {


	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private IWBundle iwb;
	private IWResourceBundle iwrb;
	private List specialList;
	//String backgroundColor = "#B0B29D";

	public WorkspacePage() {
		IWContext iwc = IWContext.getInstance();
		init(iwc);
	}
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	public void init(IWContext iwc) {
		iwb = this.getBundle(iwc);
		iwrb = this.getResourceBundle(iwc);

		Page thePage = this;
		//thePage.setBackgroundColor(backgroundColor);
		//thePage.setAllMargins(0);

		thePage.setTitle("idegaWeb Applications");


		HtmlForm form = new HtmlForm();
		specialList = new SpecialChildList(this,form);
		
		add(form);
		
		//String requestUri = iwc.getExternalContext().getRequestPathInfo();
		ViewNode node = ViewManager.getInstance(iwc.getIWMainApplication()).getViewNodeForContext(iwc);
		if(node instanceof FramedApplicationViewNode){
			FramedApplicationViewNode frameNode = (FramedApplicationViewNode)node;
			WFFrame frame = new WFFrame(node.getName(),frameNode.getFrameUrl());
			add(frame);
		}
		
		
		//UISaveState savestate = new UISaveState();
		//form.getChildren().add(savestate);
		
		WorkspaceBar bar = new WorkspaceBar();
		//form.getChildren().add(bar);
		add(bar);
		
		/*Table frameTable = new Table(1, 1);
		frameTable.setWidth("100%");
		frameTable.setHeight("100%");
		frameTable.setCellpadding(0);
		frameTable.setCellspacing(0);
		frameTable.setAlignment(1, 1, "center");
		frameTable.setVerticalAlignment(1, 1, "middle");*/

		
		
		
		//Table mainTable = new Table(1, 4);

		//		WFBezel mainTable = new WFBezel();
		//mainTable.setWidth("400px");
		//mainTable.setHeight("300px");
//		add(mainTable);
		
		
		//mainTable.setCellspacing(0);
		//mainTable.setCellpadding(0);
		//mainTable.setBackgroundImage(1,1,iwb.getImage("logintiler.gif"));
		//mainTable.setAlignment(1, 2, "right");
		//mainTable.setAlignment(1, 3, "right");
		//mainTable.setAlignment(1, 4, "center");
		//mainTable.setVerticalAlignment(1, 1, "top");
		//mainTable.setVerticalAlignment(1, 2, "top");
		//mainTable.setVerticalAlignment(1, 3, "top");
		//mainTable.setVerticalAlignment(1, 4, "bottom");
		//mainTable.setHeight(4, "12");
		//mainTable.setColor("#FFFFFF");
		
		
		//frameTable.add(mainTable, 1, 1);
		//form.getChildren().add(mainTable);
		
		
		//mainTable.setStyleAttribute("border", "1px solid #000000");


		boolean isLoggedOn = false;
		try {
			isLoggedOn = iwc.isLoggedOn();
		}
		catch (Exception e) {
			isLoggedOn = false;
		}

		if (isLoggedOn) {
			IWControlCenter iwcc = new IWControlCenter();
			//mainTable.setHeight(2, "165");
			//mainTable.setAlignment(1, 2, "center");
			//mainTable.setAlignment(1, 3, "right");
			//mainTable.setVerticalAlignment(1, 2, "middle");
			//mainTable.setVerticalAlignment(1, 3, "middle");
			//mainTable.add(iwcc);
			//headerImage = iwrb.getImage("login/header_app_suite.jpg", "", 323, 196);


		}

		else {
			//mainTable.setHeight(2, "175");
			//mainTable.setHeight(3, "50");
			//mainTable.setAlignment(1, 2, Table.HORIZONTAL_ALIGN_RIGHT);


			/*Table dropdownTable = new Table(1, 1);
			dropdownTable.setWidth(148);
			dropdownTable.setCellpadding(0);
			dropdownTable.setCellspacing(0);
			dropdownTable.setAlignment(1, 1, "center");*/
			//mainTable.setAlignment(1, 3, Table.HORIZONTAL_ALIGN_RIGHT);
			//mainTable.add(dropdownTable);

			//Form myForm = new Form();
			//myForm.setEventListener(com.idega.core.localisation.business.LocaleSwitcher.class.getName());
			
			/*
			DropdownMenu dropdown = LocalePresentationUtil.getAvailableLocalesDropdown(iwc);
			dropdown.setStyleAttribute("font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; border-style:solid; border-width:1; border-color: #000000");
			//myForm.add(dropdown);
			mainTable.add(dropdown);
			*/
			
			//dropdownTable.add(myForm);
			//mainTable.add(myForm);
			//headerImage = iwrb.getImage("/login/header.jpg", "", 323, 196);
		}
		//Link lheaderLink = new Link(headerImage, iwc.getIWMainApplication().getApplicationContextURI());
		//mainTable.add(lheaderLink, 1, 1);
		
		//form.getChildren().add(frameTable);
	}
	
	public List getChildren(){
		if(specialList==null){
			return super.getChildren();
		}
		return specialList;
	}
	
	/**
	 * 
	 *  Last modified: $Date: 2004/10/25 14:45:46 $ by $Author: tryggvil $
	 * 
	 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
	 * @version $Revision: 1.2 $
	 */
	public class SpecialChildList implements List{
		
		UIComponent parent;
		UIComponent child;
		List list;
		
		public SpecialChildList(UIComponent parent,UIComponent child){
			this.parent=parent;
			this.child=child;
			List children = parent.getChildren();
			this.list=children;
		}
		
		/**
		 * @param arg0
		 * @param arg1
		 */
		public void add(int arg0, Object arg1) {
			if(arg1.equals(child)){
				list.add(arg0, arg1);
			}
			else{
				child.getChildren().add(arg0,arg1);
			}
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean add(Object arg0) {
			if(arg0.equals(child)){
				return list.add(arg0);
			}
			else{
				return child.getChildren().add(arg0);
			}
		}
		/**
		 * @param arg0
		 * @param arg1
		 * @return
		 */
		public boolean addAll(int arg0, Collection arg1) {
			return list.addAll(arg0, arg1);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean addAll(Collection arg0) {
			return list.addAll(arg0);
		}
		/**
		 * 
		 */
		public void clear() {
			list.clear();
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean contains(Object arg0) {
			return list.contains(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean containsAll(Collection arg0) {
			return list.containsAll(arg0);
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object arg0) {
			return list.equals(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public Object get(int arg0) {
			return list.get(arg0);
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return list.hashCode();
		}
		/**
		 * @param arg0
		 * @return
		 */
		public int indexOf(Object arg0) {
			return list.indexOf(arg0);
		}
		/**
		 * @return
		 */
		public boolean isEmpty() {
			return list.isEmpty();
		}
		/**
		 * @return
		 */
		public Iterator iterator() {
			return list.iterator();
		}
		/**
		 * @param arg0
		 * @return
		 */
		public int lastIndexOf(Object arg0) {
			return list.lastIndexOf(arg0);
		}
		/**
		 * @return
		 */
		public ListIterator listIterator() {
			return list.listIterator();
		}
		/**
		 * @param arg0
		 * @return
		 */
		public ListIterator listIterator(int arg0) {
			return list.listIterator(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public Object remove(int arg0) {
			return list.remove(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean remove(Object arg0) {
			return list.remove(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean removeAll(Collection arg0) {
			return list.removeAll(arg0);
		}
		/**
		 * @param arg0
		 * @return
		 */
		public boolean retainAll(Collection arg0) {
			return list.retainAll(arg0);
		}
		/**
		 * @param arg0
		 * @param arg1
		 * @return
		 */
		public Object set(int arg0, Object arg1) {
			return list.set(arg0, arg1);
		}
		/**
		 * @return
		 */
		public int size() {
			return list.size();
		}
		/**
		 * @param arg0
		 * @param arg1
		 * @return
		 */
		public List subList(int arg0, int arg1) {
			return list.subList(arg0, arg1);
		}
		/**
		 * @return
		 */
		public Object[] toArray() {
			return list.toArray();
		}
		/**
		 * @param arg0
		 * @return
		 */
		public Object[] toArray(Object[] arg0) {
			return list.toArray(arg0);
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return list.toString();
		}
	}
	

}