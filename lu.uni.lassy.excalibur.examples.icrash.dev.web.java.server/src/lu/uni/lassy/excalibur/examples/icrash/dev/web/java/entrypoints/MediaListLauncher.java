package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints;

import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.MediaListView;


@PreserveOnRefresh
@Theme("valo")
@Title("iCrash Media List")
public class MediaListLauncher extends UI{

	private static final long serialVersionUID = 160444147662948471L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	public final static String mediaListName = "medialist";
	
	@WebServlet(value = "/"+mediaListName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MediaListLauncher.class,
			widgetset = "lu.uni.lassy.excalibur.examples.icrash.dev.web.java.widgetset.IcrashWidgetset")
	public static class MediaListServlet extends VaadinServlet {
		private static final long serialVersionUID = -3732066864556560077L;
	}

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView("", new MediaListView());
	}	
}