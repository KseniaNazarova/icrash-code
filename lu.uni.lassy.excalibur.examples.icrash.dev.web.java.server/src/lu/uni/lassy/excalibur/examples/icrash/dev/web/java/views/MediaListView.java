package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Not;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.CrisisBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.MediaBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtMedia;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtEmail;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtMediaID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtMediaName;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtMediaCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class MediaListView extends VerticalLayout implements View, Serializable{

	private static final long serialVersionUID = 3405649647975786985L;
	transient Logger log = Log4JUtils.getInstance().getLogger();

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
	CtAdministrator ctAdmin =  (CtAdministrator) sys.getCtAuthenticated(actAdmin);
		
	private Label welcomeText;
	private Grid mediaGrid;
	BeanItemContainer<MediaBean> container;
	
	public MediaListView(){
		
		
		setSizeFull();		
		welcomeText = new Label("List Of Media");
		welcomeText.setSizeUndefined();
		
		actAdmin.oeGetMediaSet();
		container = actAdmin.getMediaContainer();
		
		mediaGrid = new Grid(container);
		mediaGrid.setColumnOrder("id", "name", "email", "category");
		mediaGrid.setSelectionMode(SelectionMode.MULTI);	
		mediaGrid.setSizeUndefined();
		mediaGrid.setResponsive(true);
		mediaGrid.setImmediate(true);
		mediaGrid.setEditorEnabled(true);	
		
		
		mediaGrid.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {

			private static final long serialVersionUID = -135627807328414159L;

			@Override
	        public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
	        }

	        @Override
	        public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
	        	MediaBean mediaBean = (MediaBean) mediaGrid.getEditedItemId();
	        	CtMedia media = new CtMedia();
	        	

    			String theCategory = mediaBean.getCategory();
				EtMediaCategory aCategory = null;

				if (theCategory.equals(EtMediaCategory.category_1.name()))
					aCategory = EtMediaCategory.category_1;
				if (theCategory.equals(EtMediaCategory.category_2.name()))
					aCategory = EtMediaCategory.category_2;
				if (theCategory.equals(EtMediaCategory.category_3.name()))
					aCategory = EtMediaCategory.category_3;
				
	        	media.init(new DtMediaID(new PtString(String.valueOf(mediaBean.getId()))), 
	        			new DtMediaName(new PtString(mediaBean.getName())),  
	        			new DtEmail(new PtString(mediaBean.getEmail())),				
						aCategory);
	        	actAdmin.oeAddMedia(media);
				Notification.show(mediaBean.getId() + " edited");
				
	        }
		});
		
				
		HorizontalButtonGroup buttons = new HorizontalButtonGroup();
		buttons.setSizeFull();
			
		Button addMediaButton = new Button("Add");
		addMediaButton.addClickListener(e -> {
			container.addBean(new MediaBean(null, "", "", ""));
		});
		
		addMediaButton.setIcon(FontAwesome.PLUS);
		addMediaButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				
		MultiSelectionModel selection = (MultiSelectionModel) mediaGrid.getSelectionModel();		
		Button removeMediaButton = new Button("Remove", e -> {
		    for (Object item: selection.getSelectedRows()){		    	
		    	MediaBean mediaBean = (MediaBean)item;
		    	mediaGrid.getContainerDataSource().removeItem(item);
				PtBoolean res = actAdmin.oeRemoveMedia(new DtMediaID(new PtString(String.valueOf(mediaBean.getId()))));
		    }

		    mediaGrid.getSelectionModel().reset();
		    e.getButton().setEnabled(false);
		    
		});
		removeMediaButton.setEnabled(mediaGrid.getSelectedRows().size() > 0);
		mediaGrid.addSelectionListener(selectionEvent -> {
		    Notification.show(selectionEvent.getAdded().size() +
		                      " items added, " +
		                      selectionEvent.getRemoved().size() +
		                      " removed.");
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
