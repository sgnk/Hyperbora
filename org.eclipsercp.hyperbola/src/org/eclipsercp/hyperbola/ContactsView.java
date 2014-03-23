package org.eclipsercp.hyperbola;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.eclipsercp.hyperbola.model.Roster;
import org.eclipsercp.hyperbola.model.RosterEntry;
import org.eclipsercp.hyperbola.model.RosterGroup;
import org.eclipsercp.hyperbola.model.RosterListener;
import org.eclipsercp.hyperbola.model.Session;

public class ContactsView extends ViewPart {

	public static final String ID = "org.eclipsercp.hyperbola.views.contacts";

	private TreeViewer treeViewer;

	private Session session;

	private IAdapterFactory adapterFactory = new HyperbolaAdapterFactory();

	public ContactsView() {
		super();
	}

	public void createPartControl(Composite parent) {
		initializeSession(); // temporary tweak to build a fake model
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Roster.class); // ch 5.4.2
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(session.getRoot());
		session.getRoot().addContactsListener(new RosterListener() {
			public void contactsChanged(RosterGroup contacts,
					RosterEntry entry) {
				treeViewer.refresh();
			}
		});
	}
	
	public void dispose() {
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		super.dispose();
	}

	private void initializeSession() {
		session = new Session();
		RosterGroup root = session.getRoot();
		RosterGroup friendsGroup = new RosterGroup(root, "Friends");
		root.addEntry(friendsGroup);
		friendsGroup.addEntry(new RosterEntry(friendsGroup, "Alize", "aliz", "localhost"));
		friendsGroup.addEntry(new RosterEntry(friendsGroup, "Sydney", "syd", "localhost"));
		RosterGroup otherGroup = new RosterGroup(root, "Other");
		root.addEntry(otherGroup);
		otherGroup.addEntry(new RosterEntry(otherGroup, "Nadine", "nad", "localhost"));
	}

	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
}
