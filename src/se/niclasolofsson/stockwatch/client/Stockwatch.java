package se.niclasolofsson.stockwatch.client;

import java.util.ArrayList;
import java.util.Date;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.Timer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Stockwatch implements EntryPoint {
	private static final int REFRESH_INTERVAL = 5000; // ms

	private HorizontalPanel mainPanel = new HorizontalPanel();
	private VerticalPanel watcherPanel = new VerticalPanel();
	private FlexTable populationFlexTable = new FlexTable();
	private TabPanel dropTabPanel = new TabPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newCountryTextBox = new TextBox();
	private Button addCountryButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	private ArrayList<String> countries = new ArrayList<String>();
    private PopulationServiceAsync populationSvc = GWT.create(PopulationService.class);
    private Label errorMsgLabel = new Label();
    private DragController dragController;
    private DropController dropController;
    
    private Country test;

	  private void refreshWatchList() {
		    // Initialize the service proxy.
		    if (populationSvc == null) {
		      populationSvc = GWT.create(PopulationService.class);
		    }

		    // Set up the callback object.
		    AsyncCallback<Country[]> callback = new AsyncCallback<Country[]>() {
		      public void onFailure(Throwable caught) {
		          // If the country name is not found, display an error message.
		          String details = caught.getMessage();
		          if (caught instanceof DelistedException) {
		            details = "Country '" + ((DelistedException)caught).getSymbol() + "' was not found";
		          }

		          errorMsgLabel.setText("Error: " + details);
		          errorMsgLabel.setVisible(true);
		      }

		      public void onSuccess(Country[] result) {
		        updateTable(result);
		      }
		    };

		    // Make the call to the stock price service.
		    populationSvc.getPopulations(countries.toArray(new String[0]), callback);
		  }

	private void updateTable(Country[] prices) {
		for (Country price : prices) {
			updateTable(price);
		}

		// Display timestamp showing last refresh.
		lastUpdatedLabel.setText("Last updated: "
				+ DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_FULL)
							    .format(new Date()));
	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	};

	private void updateTable(Country country) {
		int row = countries.indexOf(country.getName()) + 1;

		// Format the data in the population field
		String populationText = NumberFormat.getFormat("#,##0.00").format(country.getPopulation());

		// Populate the Price and Change fields with new data.
		populationFlexTable.setText(row, 1, populationText);
	}

	/**
	 * Create the view
	 */
	public void onModuleLoad() {
		// Create table for population data.
		populationFlexTable.setText(0, 0, "Country");
		populationFlexTable.setText(0, 1, "Population");
		populationFlexTable.setText(0, 2, "Remove");
		
		populationFlexTable.setCellPadding(6);
		
		populationFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		populationFlexTable.addStyleName("watchList");
	    populationFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    populationFlexTable.getCellFormatter().addStyleName(0, 2, "watchListRemoveColumn");

		addPanel.add(newCountryTextBox);
		addPanel.add(addCountryButton);
		
		// Assemble Main panel.
	    errorMsgLabel.setStyleName("errorMessage");
	    errorMsgLabel.setVisible(false);
	    
	    dropTabPanel.setStyleName("dropTable");

	    watcherPanel.add(errorMsgLabel);
		watcherPanel.add(populationFlexTable);
		watcherPanel.add(addPanel);
		watcherPanel.add(lastUpdatedLabel);
		
		mainPanel.add(watcherPanel);
		mainPanel.add(new Label("Drag stuff --->"));
		mainPanel.add(dropTabPanel);
		
		// Associate the Main panel with the HTML host page.
		RootPanel.get("stockList").add(mainPanel);
		
		dragController = new DragController(RootPanel.get(), false);
		dropController = new DropController(dropTabPanel);
		
		dragController.setBehaviorDragProxy(true);
		dragController.registerDropController(dropController);
		// dragController.makeDraggable(addCountryButton);
		
		newCountryTextBox.setFocus(true);

		// Setup timer to refresh list automatically.
		Timer refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshWatchList();
			}
		};

		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

		// Add listener to add-button
		addCountryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addCountry();
			}
		});

		// Listen for keyboard events in the input box.
		// Using KeyDownHandler since the handler given in the tutorial does not work,
		// (see http://code.google.com/p/google-web-toolkit/issues/detail?id=5558)
		newCountryTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {				
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addCountry();
				}
			}
		});
	}

	private void addCountry() {
		// Make the first letter uppercase, the rest lowercase
		String lower = newCountryTextBox.getText().toLowerCase().trim();
		final String name = Character.toUpperCase(lower.charAt(0)) + lower.substring(1);

		if (!name.matches("^[a-zA-Z]{1,15}$")) {
			Window.alert("'" + name + "' is not a valid country name.");
			newCountryTextBox.selectAll();
			return;
		}

		newCountryTextBox.setText("");

		if (countries.contains(name)) {
			return;
		}

		int row = populationFlexTable.getRowCount();
		countries.add(name);
		
		test = new Country(name);
		CountryDraggable nameLabel = new CountryDraggable(name, test);
		
		test.setPopulation(125);
		HorizontalPanel nameParent = new HorizontalPanel();
		nameParent.add(nameLabel);
		populationFlexTable.setWidget(row, 0, nameParent);
		
		dragController.makeDraggable(nameLabel);
		
		populationFlexTable.setWidget(row, 2, new Label());
	    populationFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
	    populationFlexTable.getCellFormatter().addStyleName(row, 2, "watchListRemoveColumn");

		Button removeCountryButton = new Button("x");
		
		removeCountryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int removedIndex = countries.indexOf(name);
				countries.remove(removedIndex);
				populationFlexTable.removeRow(removedIndex + 1);
			}
		});
		populationFlexTable.setWidget(row, 2, removeCountryButton);
		refreshWatchList();
	}
}