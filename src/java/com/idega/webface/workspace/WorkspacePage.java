/*
 * Created on 13.7.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.workspace;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
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
	private boolean embedForm=false;
	private boolean isInitalized=false;
	//String backgroundColor = "#B0B29D";
	private UIForm form;
	
	public WorkspacePage() {
		//IWContext iwc = IWContext.getInstance();
		//init(iwc);
		//initalizeEmbeddedForm();
		if(embedForm){
			initalizeEmbeddedForm();
		}
	}
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	public void initializeContent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		
		iwb = this.getBundle(iwc);
		iwrb = this.getResourceBundle(iwc);

		Page thePage = this;
		//thePage.setBackgroundColor(backgroundColor);
		//thePage.setAllMargins(0);

		thePage.setTitle("idegaWeb Applications");


		//if(embedForm){
		//	initalizeEmbeddedForm();
		//}

		//String requestUri = iwc.getExternalContext().getRequestPathInfo();
		String requestUri = iwc.getRequestURI();
		if(requestUri.endsWith("content")){
			try{
				Class clazz = Class.forName("com.idega.block.article.CMSPage");
				UIComponent comp = (UIComponent) clazz.newInstance();
				add(comp);
			}
			catch(Throwable t){
				t.printStackTrace();
			}
		}
		ViewNode node = ViewManager.getInstance(iwc.getIWMainApplication()).getViewNodeForContext(iwc);
		if(node instanceof FramedApplicationViewNode){
			FramedApplicationViewNode frameNode = (FramedApplicationViewNode)node;
			WFFrame frame = new WFFrame(node.getName(),frameNode.getFrameUrl());
			//WFBlock frame = new WFBlock("test");
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
		if(embedForm){
			return getForm().getChildren();
		}
		else{
			return super.getChildren();
		}
		
	}
	
	
	public void add(UIComponent comp){
		this.getForm().getChildren().add(comp);
	}
	
	/**
	 * This sets the page to embed a UIForm. This does not currently handle restoring state.
	 * @param doEmbed
	 */
	public void setToEmbedForm(boolean doEmbed){
		//TODO: implement handling of this fully
		this.embedForm=doEmbed;
	}
	
	private void initalizeEmbeddedForm(){
		HtmlForm form = new HtmlForm();
		//form.setId(this.getId()+"-form");
		form.setParent(this);
		//specialList = new SpecialChildList(this,form);
		
		//add(form);
		setForm(form);
	}
	
	private void setForm(UIForm form){
		//String formId = this.getId()+"-form";
		//getFacets().put(formId,form); 
		this.form=form;
	}
	
	private UIForm getForm(){
		//String formId = this.getId()+"-form";
		//return (UIForm)getFacets().get(formId);
		if(form==null){
			form = findSubForm();
			if(form==null){
				throw new RuntimeException("WorkspacePage: No form found in page, it must be explicitly added inside page");
			}
		}
		return this.form;
	}
	
	private UIForm findSubForm(){
		Collection children = getChildren();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			UIComponent child = (UIComponent) iter.next();
			if(child instanceof UIForm){
				return (UIForm)child;
			}
		}
		return null;
	}
	
	public void encodeBegin(FacesContext context) throws IOException{
		if(!isInitalized){
			this.initializeContent(context);
			isInitalized=true;
		}
		super.encodeBegin(context);
	}
	
	public void encodeChildren(FacesContext context) throws IOException{
		
		UIForm form = getForm();
		if(this.embedForm){
			form.encodeBegin(context);
		}
		super.encodeChildren(context);
		
		if(this.embedForm){
			form.encodeChildren(context);
			form.encodeEnd(context);
		}
	}
	
	public void encodeEnd(FacesContext context) throws IOException{
		super.encodeEnd(context);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		Boolean bIsInitalized = (Boolean) values[1];
		this.isInitalized=bIsInitalized.booleanValue();
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = Boolean.valueOf(this.isInitalized);
		return values;
	}
	
	

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processRestoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void processRestoreState(FacesContext fc, Object arg1) {
		// TODO Auto-generated method stub
		super.processRestoreState(fc, arg1);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processSaveState(javax.faces.context.FacesContext)
	 */
	public Object processSaveState(FacesContext arg0) {
		// TODO Auto-generated method stub
		return super.processSaveState(arg0);
	}
	/**
	 * 
	 *  Last modified: $Date: 2004/11/01 15:00:48 $ by $Author: tryggvil $
	 * 
	 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
	 * @version $Revision: 1.3 $
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