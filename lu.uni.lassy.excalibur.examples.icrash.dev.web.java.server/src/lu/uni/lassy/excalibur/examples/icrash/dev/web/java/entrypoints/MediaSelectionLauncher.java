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
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.MediaSelectionView;

@PreserveOnRefresh
@Theme("valo")
@Title("iCrash Select Media to share")
public class MediaSelectionLauncher extends UI{

	private static final long serialVersionUID = 8126996280228975547L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	public final static String mediaSelectionName = "mediaselection";
	
	@WebServlet(value = "/"+mediaSelectionName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MediaSelectionLauncher.class,
			widgetset = "lu.uni.lassy.excalibur.examples.icrash.dev.web.java.widgetset.IcrashWidgetset")
	public static class MediaSelectionServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4438294793355053888L;
	}

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView("", new MediaSelectionView());
	}	
}