/*
 * $Id: WFEditableListCellWrapper.java,v 1.3 2005/11/29 15:29:19 laddi Exp $
 * Created on 8.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.bean;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import com.idega.presentation.UIComponentWrapper;


public class WFEditableListCellWrapper extends UIComponentWrapper {

	
	public WFEditableListCellWrapper(){
		super();
	}
	
	public WFEditableListCellWrapper(UIComponent component){
		super(component);
	}
	
	/**
	 * @return Returns the selectItemList.
	 */
	public Object getSelectItemList() {
		UIComponent component = getUIComponent();
		if(component != null){
			UISelectItems items = null;
			for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
				Object element = iter.next();
				if(element instanceof UISelectItems){
					return ((UISelectItems)element).getValue();
				}
			}
		}
		return null;
	}
	/**
	 * @param selectItemList The selectItemList to set.
	 */
	public void setSelectItemList(Object selectItemList) {
		if(selectItemList!=null){
			UIComponent component = getUIComponent();
			if(component != null){
				UISelectItems items = null;
				for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
					Object element = iter.next();
					if(element instanceof UISelectItems){
						items = (UISelectItems)element;
					}
				}
				if(items==null) {
					items = new UISelectItems();
					component.getChildren().add(items);
				}
				items.setValue(selectItemList);
			}
		}
	}
	
	public void encodeBegin(FacesContext context) throws IOException{
		ValueBinding vb = getValueBinding("selectItemList");
		setSelectItemList(vb != null ? (Object)vb.getValue(context) : null);
		super.encodeBegin(context);
	}
	
	public void processUpdates(FacesContext context) {
		super.processUpdates(context);
    }
	
	
}