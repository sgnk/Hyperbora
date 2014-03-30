package org.eclipsercp.hyperbola;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPException;

public class AddContactAction extends Action implements
		ISelectionListener, IWorkbenchAction {
	
	private final IWorkbenchWindow window;
	public final static String ID = "org.eclipsercp.hyperbola.addContact";
	private IStructuredSelection selection;

	public AddContactAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("&Add Contact...");
		setToolTipText("Add a contact to your contacts list.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ADD_CONTACT));
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		// Selection containing elements
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			setEnabled(selection.size() == 1
					&& selection.getFirstElement() instanceof RosterGroup);
		} else {
			// Other selections, for example containing text or of other kinds.
			setEnabled(false);
		}
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}
	
	public void run() {
		Object item = selection.getFirstElement();
		if (item instanceof RosterGroup) {
			RosterGroup group = (RosterGroup) item;
			AddContactDialog d = new AddContactDialog(window.getShell());
			int code = d.open();
			if (code == Window.OK) {
				try {
				Roster list = Session.getInstance().getConnection().getRoster();
				String user = d.getUserId() + "@" + d.getServer();
				String[] groups = new String[] { group.getName() };
				list.createEntry(user, d.getNickname(), groups);
				} catch (XMPPException e) {
					// Handle
				}
			}
		}
	}

}
