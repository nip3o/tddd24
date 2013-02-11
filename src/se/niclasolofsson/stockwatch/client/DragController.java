package se.niclasolofsson.stockwatch.client;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DragController extends PickupDragController {

	//private Widget parent;

	public DragController(AbsolutePanel boundaryPanel,
			boolean allowDroppingOnBoundaryPanel) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
		
		//this.parent = parent;
	}
	
	@Override
	public void dragStart() {
		// For some reason GWT-DND thinks you should override this method...
		super.dragStart();
	}
	
	@Override
	public Widget newDragProxy(DragContext context) {
		return new HTML("Wieeee");
	}

}
