/*
 * $Id: WFWizard.java,v 1.1 2005/02/02 03:01:09 tryggvil Exp $
 * Created on 10.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.wizard;

import com.idega.webface.WFContainer;


/**
 *  <p>
 *  Class to display a "wizard" type interface with a step-through of actions.
 *  </p>
 *  Last modified: $Date: 2005/02/02 03:01:09 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class WFWizard extends WFContainer{
	
	private WFContainer header;
	private WFContainer mainArea;
	private WFContainer footer;
	
	private int currentStep;
	private int totalSteps;
	
}