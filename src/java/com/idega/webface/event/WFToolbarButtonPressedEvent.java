/*
 * Created on 3.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import com.idega.webface.WFToolbarButton;

/**
 * WFToolbarButtonPressedEvent //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFToolbarButtonPressedEvent extends FacesEvent{

		/**
		 * 
		 * @uml.property name="button"
		 * @uml.associationEnd multiplicity="(0 1)"
		 */
		private WFToolbarButton button = null;

		public WFToolbarButtonPressedEvent(UIComponent toolbar) {
			super(toolbar);
		}

		/**
		 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
		 */
		public boolean isAppropriateListener(FacesListener listener) {
			return (listener instanceof WFToolbarButtonPressedListener);
		}

		/**
		 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
		 */
		public void processListener(FacesListener listener) {
//			WFToolbarButtonPressedListener l = (WFToolbarButtonPressedListener) listener;
//			l.buttonPressed(this);
		}

		/**
		 * @return
		 * 
		 * @uml.property name="button"
		 */
		public WFToolbarButton getButton() {
			return button;
		}

		/**
		 * @param i
		 * 
		 * @uml.property name="button"
		 */
		public void setButton(WFToolbarButton button) {
			this.button = button;
		}

	}
