/*
 * Created on 3.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface.event;

import javax.faces.event.FacesListener;

/**
 * WFToolbarButtonPressedListener //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public interface WFToolbarButtonPressedListener extends FacesListener {
		void buttonPressed(WFToolbarButtonPressedEvent e);
}
