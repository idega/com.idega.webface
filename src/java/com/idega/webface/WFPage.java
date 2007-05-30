/*
 * $Id: WFPage.java,v 1.13 2007/05/30 15:09:18 gediminas Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.idega.presentation.IWBaseComponent;

/**
 * ...
 * <p>
 * Last modified: $Date: 2007/05/30 15:09:18 $ by $Author: gediminas $
 *
 * @author Anders Lindman
 * @version $Revision: 1.13 $
 */
public class WFPage extends IWBaseComponent {

	/**
	 * Default contructor. 
	 */
	public WFPage() {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (getRendersChildren()) {
			Iterator children = this.getChildren().iterator();
			while (children.hasNext()) {
				UIComponent element = (UIComponent) children.next();
				renderChild(context, element);
			}
		}
		/*
		 * Iterator children = this.getChildren().iterator(); while
		 * (children.hasNext()) { UIComponent element = (UIComponent)
		 * children.next(); renderChild(context,element); }
		 */
		// super.encodeChildren(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
		// return super.getRendersChildren();
	}	

}
