/*
 *  $Id: WorkspacePage.java,v 1.6 2004/12/12 14:56:53 eiki Exp $
 *
 *  Created on 13.7.2004 by Tryggvi Larusson
 *
 *  Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 *  This software is the proprietary information of Idega hf.
 *  Use is subject to license terms.
 *
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
import com.idega.core.view.FramedApplicationViewNode;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.presentation.IWContext;
import com.idega.presentation.Page;
import com.idega.webface.WFContainer;
import com.idega.webface.WFFrame;

/**
 * A base page for using in the Workspace environment.<br>
 * This page should be around all UI components in the environment.<br>
 * 
 * <br>
 * Last modified: $Date: 2004/12/12 14:56:53 $ by $Author: eiki $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.6 $
 */
public class WorkspacePage extends Page {


	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";

	//private List specialList;
	private boolean embedForm=false;
	private boolean isInitalized=false;
	private transient UIForm form;
	private String WF_PAGE_CLASS="wf_body";
	
	private static String FACET_HEAD="ws_head";
	private static String FACET_FUNCTIONMENU="ws_functionmenu";
	private static String FACET_MAIN="ws_main";
	
	public WorkspacePage() {
		setTransient(false);
		//IWContext iwc = IWContext.getInstance();
		//init(iwc);
		//initalizeEmbeddedForm();
		//if(embedForm){
		//	initalizeEmbeddedForm();
		//}
		this.setStyleClass(WF_PAGE_CLASS);
	}
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	public void initializeContent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);	
//		IWBundle iwb = this.getBundle(iwc);
//		IWResourceBundle iwrb = this.getResourceBundle(iwc);
		
		//Initialize the areas:
		this.getMainArea();
		this.getHead();
		this.getFunctionMenu();

		Page thePage = this;
		//thePage.setBackgroundColor(backgroundColor);
		//thePage.setAllMargins(0);

		thePage.setTitle("idegaWeb Applications");

		String requestUri = iwc.getRequestURI();
		//TODO: Change this, this is a hack for the function menu:
		ViewNode node = ViewManager.getInstance(iwc.getIWMainApplication()).getViewNodeForContext(iwc);
		if(requestUri.indexOf("content")!=-1){
		//if(node.getChildren().size()>0){
			try{

				WorkspaceFunctionMenu menu = new WorkspaceFunctionMenu();
				menu.setApplication("content");
				
				add(FACET_FUNCTIONMENU,menu);		
				
			}
			catch(Throwable t){
				t.printStackTrace();
			}
		}
		if(node instanceof FramedApplicationViewNode){
			FramedApplicationViewNode frameNode = (FramedApplicationViewNode)node;
			WFFrame frame = new WFFrame(node.getName(),frameNode.getFrameUrl());
			//WFBlock frame = new WFBlock("test");
			add(FACET_MAIN,frame);
		}
		
		//UISaveState savestate = new UISaveState();
		//form.getChildren().add(savestate);
		
		WorkspaceBar bar = new WorkspaceBar();
		//form.getChildren().add(bar);
		add(FACET_HEAD,bar);
		
		boolean isLoggedOn = false;
		try {
			isLoggedOn = iwc.isLoggedOn();
		}
		catch (Exception e) {
			isLoggedOn = false;
		}

		if (isLoggedOn) {
		}
		else {

		}
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
	
	public void add(String key,UIComponent comp){
		UIComponent setComp = getForm().getFacet(key);
		if(setComp==null){
			WFContainer container = new WFContainer();
			container.setStyleClass(key);
			container.add(comp);
			this.getForm().getFacets().put(key,container);
		}
		else{
			setComp.getChildren().add(comp);
		}
	}
	
	
	public UIComponent getMainArea(){
		UIComponent area = getForm().getFacet(FACET_MAIN);
		if(area==null){
			WFContainer container = new WFContainer();
			container.setStyleClass(FACET_MAIN);
			getForm().getFacets().put(FACET_MAIN,container);
			area=container;
		}
		return area;
	}
	
	public UIComponent getHead(){
		UIComponent head = getForm().getFacet(FACET_HEAD);
		if(head==null){
			WFContainer container = new WFContainer();
			container.setStyleClass(FACET_HEAD);
			getForm().getFacets().put(FACET_HEAD,container);
			head=container;
		}
		return head;
	}
	
	public UIComponent getFunctionMenu(){
		UIComponent menu = getForm().getFacet(FACET_FUNCTIONMENU);
		if(FACET_HEAD==null){
			WFContainer container = new WFContainer();
			container.setStyleClass(FACET_FUNCTIONMENU);
			getForm().getFacets().put(FACET_FUNCTIONMENU,container);
			menu=container;
		}
		return menu;
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
		//if(this.embedForm){
			form.encodeBegin(context);
		//}
		//super.encodeChildren(context);
		UIComponent bar = getHead();
		this.renderChild(context,bar);
		UIComponent fMenu = getFunctionMenu();
		this.renderChild(context,fMenu);
		UIComponent mainArea = getMainArea();
		if(mainArea==null){
			mainArea = new WFContainer();
			((WFContainer)mainArea).setStyleClass(FACET_MAIN);
		}
		
		mainArea.encodeBegin(context);
		
		if(mainArea.getRendersChildren()){
			mainArea.encodeChildren(context);
		}
		//super.encodeChildren(context);
		
		//form.encodeChildren(context);
		for (Iterator iter = form.getChildren().iterator(); iter.hasNext();) {
			UIComponent child = (UIComponent) iter.next();
			renderChild(context,child);
		}
		
		mainArea.encodeEnd(context);
		
		
		//if(this.embedForm){
			//form.encodeChildren(context);
			form.encodeEnd(context);
		//}
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
		super.processRestoreState(fc, arg1);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processSaveState(javax.faces.context.FacesContext)
	 */
	public Object processSaveState(FacesContext arg0) {
		return super.processSaveState(arg0);
	}
	/**
	 * 
	 *  Last modified: $Date: 2004/12/12 14:56:53 $ by $Author: eiki $
	 * 
	 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
	 * @version $Revision: 1.6 $
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