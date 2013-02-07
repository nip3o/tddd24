package se.niclasolofsson.stockwatch.client;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class DropController extends SimpleDropController {
	private TabPanel target;
	
	public DropController(TabPanel dropTarget) {
		super(dropTarget);
		this.target = dropTarget;
	}

	  @Override
	  public void onDrop(DragContext context) {
	    for (Widget widget : context.selectedWidgets) {
	      this.target.add(widget, "Mhm");
	    }
	    super.onDrop(context);
	  }

}
