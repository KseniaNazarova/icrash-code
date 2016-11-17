package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.MultiSelectionModel;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.MediaBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class MediaListView extends VerticalLayout implements View, Serializable{

	private static final long serialVersionUID = 3405649647975786985L;
	transient Logger log = Log4JUtils.getInstance().getLogger();

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
		
	private Label welcomeText;
	private Grid mediaGrid;
	BeanItemContainer<MediaBean> container;
	
	public MediaListView(){
		
		welcomeText = new Label("List Of Media");
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
		mediaGrid.setEditorEnabled(true);
		
		mediaGrid.removeColumn("id");
		
		HorizontalButtonGroup buttons = new HorizontalButtonGroup();
		Button addMediaButton = new Button("Add");
		
		
		addMediaButton.addClickListener(e -> {
			container.addBean(new MediaBean(null, "", "", ""));
		});
		
		addMediaButton.setIcon(FontAwesome.PLUS);
		addMediaButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				
		MultiSelectionModel selection = (MultiSelectionModel) mediaGrid.getSelectionModel();		
		Button removeMediaButton = new Button("Remove", e -> {
		    // Delete all selected data items
		    for (Object itemId: selection.getSelectedRows())
		    	mediaGrid.getContainerDataSource().removeItem(itemId);
		    mediaGrid.getSelectionModel().reset();
		    e.getButton().setEnabled(false);
		});
		removeMediaButton.setEnabled(mediaGrid.getSelectedRows().size() > 0);
		mediaGrid.addSelectionListener(selectionEvent -> { // Java 8
		    Notification.show(selectionEvent.getAdded().size() +
		                      " items added, " +
		                      selectionEvent.getRemoved().size() +
		                      " removed.");

		    // Allow deleting selected only if there's any selected
		    removeMediaButton.setEnabled(mediaGrid.getSelectedRows().size() > 0);
		});
		
		removeMediaButton.setIcon(FontAwesome.MINUS);
		removeMediaButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		
		
		addMediaButton.setImmediate(true);
		removeMediaButton.setImmediate(true);
		
		buttons.addComponents(addMediaButton, removeMediaButton);
		buttons.setSizeUndefined();
		buttons.setWidthUndefined();
				
		setSizeFull();
		addComponents(welcomeText, mediaGrid, buttons);		
		setComponentAlignment(welcomeText, Alignment.BOTTOM_CENTER);
		setComponentAlignment(mediaGrid, Alignment.MIDDLE_CENTER);
		setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
		
		setMargin(true);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {}

}
