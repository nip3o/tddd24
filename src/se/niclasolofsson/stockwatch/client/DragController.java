package se.niclasolofsson.stockwatch.client;

import java.util.ArrayList;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DragController extends PickupDragController {
	public DragController(AbsolutePanel boundaryPanel,
			boolean allowDroppingOnBoundaryPanel) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
	}
	
	@Override
	public Widget newDragProxy(DragContext context) {
		return new HTML("Wieeee");
	}
}
