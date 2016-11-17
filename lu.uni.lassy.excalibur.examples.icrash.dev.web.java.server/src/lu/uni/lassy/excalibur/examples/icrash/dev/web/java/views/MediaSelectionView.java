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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.MediaBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class MediaSelectionView extends VerticalLayout implements View, Serializable {

	private static final long serialVersionUID = -8729200335724706056L;
	transient Logger log = Log4JUtils.getInstance().getLogger();

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
		
	private Label welcomeText;
	private Grid mediaGrid;
	private BeanItemContainer<MediaBean> container;
	
	public MediaSelectionView(){
		welcomeText = new Label("Select media to share information");
		welcomeText.setSizeUndefined();
				
		MediaBean media1 = new MediaBean(1, "media1", "email1", "category_1");
		MediaBean media2 = new MediaBean(2, "media2", "email2", "category_2");
		MediaBean media3 = new MediaBean(3, "media3", "email3", "category_3");
		MediaBean media4 = new MediaBean(4, "media4", "email4", "category_1");
		MediaBean media5 = new MediaBean(5, "media5", "email5", "category_2");
		MediaBean media6 = new MediaBean(6, "media6", "email6", "category_3");
		MediaBean media7 = new MediaBean(7, "media7", "email7", "category_1");
		
		List<MediaBean> mediaList = new ArrayList<>(Arrays.asList(media1, media2, media3, media4, media5, media6, media7));
		container = new BeanItemContainer<>(MediaBean.class, mediaList);
		mediaGrid = new Grid(container);
		mediaGrid.setColumnOrder("id", "name", "email", "category");
		mediaGrid.setSelectionMode(SelectionMode.MULTI);	
		mediaGrid.setSizeUndefined();
		mediaGrid.setResponsive(true);
		mediaGrid.setImmediate(true);
		
		mediaGrid.removeColumn("id");
		mediaGrid.removeColumn("email");

		filterColumn("category");
		
		Button sendButton = new Button("Send", e -> {			
			Notification.show("Sending...", Notification.Type.TRAY_NOTIFICATION);
		});
		
		
		setSizeFull();
		addComponents(welcomeText, mediaGrid, sendButton);		
		setComponentAlignment(welcomeText, Alignment.BOTTOM_CENTER);
		setComponentAlignment(mediaGrid, Alignment.MIDDLE_CENTER);
		setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);
		
		setMargin(true);
		
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
