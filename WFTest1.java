/*
 * Created on 4.3.2004 by  tryggvil in project smile
 */
package net.sourceforge.smile.examples.cbp;

import javax.faces.component.UIComponent;

import com.idega.webface.WFBlock;

/**
 * WFTest1 //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFTest1 extends AbstractDemo{



	public UIComponent createContent() {
		WFBlock block = new WFBlock("Test block");
		
		return block;
	}
	
}
