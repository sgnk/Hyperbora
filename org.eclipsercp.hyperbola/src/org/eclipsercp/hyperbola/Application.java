package org.eclipsercp.hyperbola;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipsercp.hyperbola.model.ConnectionDetails;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.XMPPException;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	public static final String PLUGIN_ID = "org.eclipsercp.hyperbola.product";

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			final Session session = Session.getInstance();
			if (!login(session)) {
				return IApplication.EXIT_OK;
			}
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART)
				return IApplication.EXIT_RESTART;
			else
				return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
		
	}

	private boolean login(final Session session) {
		ConnectionDetails d = new ConnectionDetails("reader", "localhost", "secret");
		session.setConnectionDetails(d);
		try {
			session.connectAndLogin(new NullProgressMonitor());
		} catch (XMPPException e) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
