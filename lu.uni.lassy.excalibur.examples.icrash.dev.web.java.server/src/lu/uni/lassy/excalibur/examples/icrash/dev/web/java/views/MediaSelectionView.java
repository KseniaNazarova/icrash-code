package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.MediaBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtMediaID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class MediaSelectionView extends VerticalLayout implements View, Serializable {

	private static final long serialVersionUID = -8729200335724706056L;
	transient Logger log = Log4JUtils.getInstance().getLogger();

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	

	private ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
		
	private Label welcomeText;
	private Grid mediaGrid;
	private BeanItemContainer<MediaBean> container;
	Window subWindow;
	
	public MediaSelectionView(){
		welcomeText = new Label("Select media to share information");
		welcomeText.setSizeUndefined();

		actAdmin.oeGetMediaSet();
		container = actAdmin.getMediaContainer();
		mediaGrid = new Grid(container);
		mediaGrid.setColumnOrder("id", "name", "email", "category");
		mediaGrid.setSelectionMode(SelectionMode.MULTI);	
		mediaGrid.setSizeUndefined();
		mediaGrid.setResponsive(true);
		mediaGrid.setImmediate(true);
		
		filterColumn("category");
		
		Button sendButton = new Button("Send", e -> {			
			Notification.show("Sending...", Notification.Type.TRAY_NOTIFICATION);
			
			for (Object item: mediaGrid.getSelectedRows()){		    	
		    	MediaBean mediaBean = (MediaBean)item;
		    	DtMediaID mediaID = new DtMediaID(new PtString(String.valueOf(mediaBean.getId())));
		    }
			UI.getCurrent().addWindow(subWindow);
		});
		
		
		setSizeFull();
		addComponents(welcomeText, mediaGrid, sendButton);		
		setComponentAlignment(welcomeText, Alignment.BOTTOM_CENTER);
		setComponentAlignment(mediaGrid, Alignment.MIDDLE_CENTER);
		setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);
		
		setMargin(true);
		
		subWindow = new Window("Sending..");
	    VerticalLayout subContent = new VerticalLayout();
	    subContent.setMargin(true);
	    subWindow.setContent(subContent);
	    subWindow.setSizeUndefined();

	    // Put some components in it
	    subContent.addComponent(new Label("Sending is successful"));
	    subContent.addComponent(new Button("OK", e ->{
	    	subWindow.close();
	    }));

	    // Center it in the browser window
	    subWindow.center();
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void filterColumn(Object pid){
		HeaderRow filterRow = mediaGrid.appendHeaderRow();
		HeaderCell cell = filterRow.getCell(pid);

		// Have an input field to use for filter
		TextField filterField = new TextField();
		filterField.setColumns(8);
		filterField.setInputPrompt("Filter");
		filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);

		// Update filter When the filter input is changed
		filterField.addTextChangeListener(change -> {
			// Can't modify filters so need to replace
			container.removeContainerFilters(pid);

			// (Re)create the filter if necessary
			if (! change.getText().isEmpty())
				container.addContainerFilter(new SimpleStringFilter(pid, change.getText(), true, false));
		});
		cell.setComponent(filterField);
	}

}
