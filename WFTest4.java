package net.sourceforge.smile.examples.cbp;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;

import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFList;
import com.idega.webface.WFTaskbar;

public class WFTest4 extends AbstractDemo {

	public UIComponent createContent() {
		WFTaskbar taskbar = new WFTaskbar();
		taskbar.addButton("button1", "CMS", getCMSPerspective());
		taskbar.addButton("button2", "Edit", getEditPerspective());
		
		return taskbar;
	}
	
	private UIComponent getCMSPerspective() {
		WFContainer c = new WFContainer();
		c.setWidth("700px");
		c.add(getArticleBlock());
		c.add(getCaseBlock());
		return c;
	}
	
	private UIComponent getArticleBlock() {
		return null;
	}
	
	private UIComponent getCaseBlock() {
		return null;
	}	
	
	/*
	private UIComponent getArticleBlock() {
		WFBlock b = new WFBlock("Article list");
		b.setWidth("100%");
		b.setHeight("200px");
		WFList l = new WFList();
		l.setId("article");
		l.setMaxDisplayRows(5);
		l.setColumnHeading(1, getHeading("Headline"));
		l.setColumnHeading(2, getHeading("Published"));
		l.setColumnHeading(3, getHeading("Author"));
		l.setColumnHeading(4, getHeading("Status"));

		l.setCell(1, 1, getLink("Electronic Reykjavik built with IdegaWeb eGov"));
		l.setCell(1, 2, getLink("Idega represented in the Baltic"));
		l.setCell(1, 3, getLink("Idega and Agura IT at the Markus Evans eGovernment Europe 2004..."));
		l.setCell(1, 4, getLink("Täby Municipality in Sweden now using IdegaWeb eGOV"));
		l.setCell(1, 5, getLink("Code name : Check & Peng"));
		l.setCell(1, 6, getRedLink("Conference sucess 'Electric Community - Here and now !'"));
		l.setCell(1, 7, getLink("Vinbud.is (idegaWeb) voted best corporate website 2003 in Iceland"));
		l.setCell(1, 8, getLink("The new Landsteinar-Strengur website implemented in IdegaWeb"));
		
		l.setCell(2, 1, getText("4/20/04 3:04?PM"));
		l.setCell(2, 2, getText("4/20/04 3:00?PM"));
		l.setCell(2, 3, getText("4/14/04 2:48?PM"));
		l.setCell(2, 4, getText("4/14/04 2:32?PM"));
		l.setCell(2, 5, getText("4/14/04 12:17?PM"));
		l.setCell(2, 6, getRedText("12/5/03 3:02?PM"));
		l.setCell(2, 7, getText("10/30/03 3:10?PM"));
		l.setCell(2, 8, getText("10/27/03"));
		
		l.setCell(3, 1, getText("Anderson"));
		l.setCell(3, 2, getText("Isildur"));
		l.setCell(3, 3, getText("Rappson"));
		l.setCell(3, 4, getText("Trappson"));
		l.setCell(3, 5, getText("Snap"));
		l.setCell(3, 6, getRedText("Rappson"));
		l.setCell(3, 7, getText("Anderson"));
		l.setCell(3, 8, getText("Trapp"));

		l.setCell(4, 1, getText("Published"));
		l.setCell(4, 2, getText("Published"));
		l.setCell(4, 3, getText("Published"));
		l.setCell(4, 4, getText("Published"));
		l.setCell(4, 5, getText("Published"));
		l.setCell(4, 6, getRedText("Expired"));
		l.setCell(4, 7, getText("Published"));
		l.setCell(4, 8, getText("Published"));
		
		b.add(l);
		return b;
	}
	
	private UIComponent getCaseBlock() {
		WFBlock b = new WFBlock("Case list");
		b.setWidth("100%");
		b.setHeight("200px");
		WFList l = new WFList();
		l.setId("case");
		l.setMaxDisplayRows(5);
		
		l.setColumnHeading(1, getHeading("Description"));
		l.setColumnHeading(2, getHeading("Created"));
		l.setColumnHeading(3, getHeading("Last modified"));
		l.setColumnHeading(4, getHeading("Author"));
		l.setColumnHeading(5, getHeading("Status"));

		l.setCell(1, 1, getLink("Idega represented in the Baltics"));
		l.setCell(1, 2, getLink("The new Hekla Rejser website is up-and running"));
		l.setCell(1, 3, getLink("Idega Multimedia becomes Idega Software"));
		l.setCell(1, 4, getLink("Idega featured in PC World!"));
		l.setCell(1, 5, getLink("The new ÁTVR website is up-and running"));
		l.setCell(1, 6, getLink("Lifidn Pension Fund selects idegaWeb Builder..."));
		l.setCell(1, 7, getLink("Idega Software signs an agreement with the..."));
		l.setCell(1, 8, getLink("Nacka elected the IT municipality 2003 in Sweden"));

		l.setCell(2, 1, getText("20/04/04 15:04 PM"));
		l.setCell(2, 2, getText("19/04/04 9:23 AM"));
		l.setCell(2, 3, getText("07/04/04 8:11 AM"));
		l.setCell(2, 4, getText("26/03/04 8:01 AM"));
		l.setCell(2, 5, getText("04/03/04-9:12 AM"));
		l.setCell(2, 6, getText("12/5/03 3:02?PM"));
		l.setCell(2, 7, getText("10/30/03 3:10?PM"));
		l.setCell(2, 8, getText("10/27/03 1:15 PM"));
		
		l.setCell(3, 1, getText("20/04/04 10:33 AM"));
		l.setCell(3, 2, getText("20/04/04 10:05 AM"));
		l.setCell(3, 3, getText("20/04/04 10:05 AM"));
		l.setCell(3, 4, getText("-"));
		l.setCell(3, 5, getText("-"));
		l.setCell(3, 6, getText("-"));
		l.setCell(3, 7, getText("-"));
		l.setCell(3, 8, getText("-"));

		l.setCell(4, 1, getText("Smith"));
		l.setCell(4, 2, getText("Andersson"));
		l.setCell(4, 3, getText("Rappson"));
		l.setCell(4, 4, getText("Trappson"));
		l.setCell(4, 5, getText("Smith"));
		l.setCell(4, 6, getText("Rappson"));
		l.setCell(4, 7, getText("Anderson"));
		l.setCell(4, 8, getText("Andersson"));

		l.setCell(5, 1, getText("Under review"));
		l.setCell(5, 2, getText("Rewrite"));
		l.setCell(5, 3, getText("Under review"));
		l.setCell(5, 4, getText("Ready for review"));
		l.setCell(5, 5, getText("Ready for review"));
		l.setCell(5, 6, getText("Ready for review"));
		l.setCell(5, 7, getText("Ready for review"));
		l.setCell(5, 8, getText("Ready for review"));

		b.add(l);
		return b;
	}
	*/
	
	private UIComponent getEditPerspective() {
		WFContainer c = new WFContainer();
		c.add(getText("edit"));
		return c;
	}
	
	private UIComponent getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		t.setStyleClass("wf_listtext");
		return t;
	}
	
	private UIComponent getRedText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		t.setStyleClass("wf_listtext wf_color_red");
		return t;
	}
	
	private UIComponent getHeading(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		return t;
	}
	
	private UIComponent getLink(String s) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setValue(s);
		l.setStyleClass("wf_listlink");
		return l;
	}
	
	private UIComponent getRedLink(String s) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setValue(s);
		l.setStyleClass("wf_listlink wf_color_red");
		return l;
	}

}

