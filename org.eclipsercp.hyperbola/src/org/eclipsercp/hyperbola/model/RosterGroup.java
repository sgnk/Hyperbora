package org.eclipsercp.hyperbola.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

public class RosterGroup extends Roster {
	private List entries;

	private RosterGroup parent;

	private String name;

	private ListenerList listeners;

	public RosterGroup(RosterGroup parent, String name) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public RosterGroup getParent() {
		return parent;
	}

	public void rename(String newName) {
		this.name = newName;
		fireContactsChanged(null);
	}

	public void addEntry(Roster entry) {
		if (entries == null)
			entries = new ArrayList(5);
		entries.add(entry);
		fireContactsChanged(null);
	}

	public void removeEntry(Roster entry) {
		if (entries != null) {
			entries.remove(entry);
			if (entries.isEmpty())
				entries = null;
		}
		fireContactsChanged(null);
	}

	public Roster[] getEntries() {
		if (entries != null)
			return (Roster[]) entries.toArray(new Roster[entries.size()]);
		return new Roster[0];
	}

	public void addContactsListener(RosterListener listener) {
		if (parent != null)
			parent.addContactsListener(listener);
		else {
			if (listeners == null)
				listeners = new ListenerList();
			listeners.add(listener);
		}
	}

	public void removeContactsListener(RosterListener listener) {
		if (parent != null)
			parent.removeContactsListener(listener);
		else {
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty())
					listeners = null;
			}
		}
	}

	protected void fireContactsChanged(RosterEntry entry) {
		if (parent != null)
			parent.fireContactsChanged(entry);
		else {
			if (listeners == null)
				return;
			Object[] rls = listeners.getListeners();
			for (int i = 0; i < rls.length; i++) {
				RosterListener listener = (RosterListener) rls[i];
				listener.contactsChanged(this, entry);
			}
		}
	}
}
