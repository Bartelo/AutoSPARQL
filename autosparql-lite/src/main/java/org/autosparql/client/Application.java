package org.autosparql.client;

import java.util.Arrays;
import java.util.logging.Logger;
import org.autosparql.client.controller.ApplicationController;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/** Entry point.*/
public class Application implements EntryPoint
{
	private static final Logger log = Logger.getLogger(Application.class.toString()); {log.fine("client logger works");}

	/** This is the entry point method. */
	public void onModuleLoad()
	{
		log.info("Starting AutoSPARQL TBSL Client Application");
		Window.setTitle("AutoSPARQL TBSL");
		AsyncCallback<long[]> callback = new AsyncCallback<long[]>
		() // Eclipse auto intendation needs () there for good intendation...
		{
			@Override public void onFailure(Throwable caught)
			{log.severe("Couldn't get the number of running clients from the server. "+Arrays.toString(caught.getStackTrace()));}
			@Override public void onSuccess(long[] hitsAndRunningClients)
			{Window.setTitle("AutoSPARQL TBSL - "+hitsAndRunningClients[1]+" running clients, "+hitsAndRunningClients[0]+" hits.");}
		};
		AutoSPARQLServiceAsync service = AutoSPARQLService.Util.getInstance();
		service.hitsAndNumberOfRunningClients(callback);
		//service.getExamplesByQTL(new ArrayList<String>(positives), new ArrayList<String>(negatives), callback);
		GXT.setDefaultTheme(Theme.BLUE, true);		
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new ApplicationController());
		Dispatcher.forwardEvent(AppEvents.Init);		
		GXT.hideLoadingPanel("loading");
	}
}