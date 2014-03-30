package org.eclipsercp.hyperbola;

import java.util.Collection;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;

public class ContactsView extends ViewPart {

	public static final String ID = "org.eclipsercp.hyperbola.views.contacts";

	private TreeViewer treeViewer;

	public ContactsView() {
		super();
	}

	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		treeViewer.setLabelProvider(new HyperbolaLabelProvider());
		treeViewer.setContentProvider(new HyperbolaContentProvider());
		Roster roster = Session.getInstance().getConnection().getRoster();
		treeViewer.setInput(roster);
		if(roster != null) {
		roster.addRosterListener(new RosterListener() {
			@Override
			public void entriesAdded(Collection<String> arg0) {
				refresh();
			}
			@Override
			public void entriesDeleted(Collection<String> arg0) {
				refresh();
			}
			@Override
			public void entriesUpdated(Collection<String> arg0) {
				refresh();
			}
			@Override
			public void presenceChanged(org.jivesoftware.smack.packet.Presence arg0) {
				refresh();
			}
		});
		}
	}
	
	private void refresh() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
	}

	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
}
