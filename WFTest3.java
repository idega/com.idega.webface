package net.sourceforge.smile.examples.cbp;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import com.idega.webface.WFList;
import com.idega.webface.event.WFListNavigationEvent;
import com.idega.webface.event.WFListNavigationListener;

public class WFTest3 extends AbstractDemo implements WFListNavigationListener {

	public UIComponent createContent() {
		WFList list = new WFList();
		/*list.setColumnHeading(1, getText("column 1"));
		list.setColumnHeading(2, getText("column 2"));
		for (int i = 1; i <= 4; i ++) {
			list.setCell(1, i, getText("" + i));
			list.setCell(2, i, getText("a"));
		}
		list.setMaxDisplayRows(10);
		*/
		list.setRows(200);
		list.addListNavigationListener(this);
		
		return list;
	}
	
	private UIComponent getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		return t;
	}
	
	public void updateList(WFListNavigationEvent e) {
		WFList list = e.getList();
		/*int rows = list.getMaxDisplayRows();
		int rowOffset = list.getRow();
		for (int i = 0; i < rows; i++) {
			list.setCell(1, i + rowOffset, getText("" + (i + rowOffset)));
		}*/
	}

}
