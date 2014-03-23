package org.eclipsercp.hyperbola;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.hyperbola.model.Roster;
import org.eclipsercp.hyperbola.model.RosterEntry;
import org.eclipsercp.hyperbola.model.RosterGroup;
import org.eclipsercp.hyperbola.model.Presence;

public class HyperbolaAdapterFactory implements IAdapterFactory {

	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
		 */
		public Object getParent(Object o) {
			return ((RosterGroup) o).getParent();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
		 */
		public String getLabel(Object o) {
			RosterGroup group = ((RosterGroup) o);
			int available = 0;
			Roster[] entries = group.getEntries();
			for (int i = 0; i < entries.length; i++) {
				Roster contact = entries[i];
				if (contact instanceof RosterEntry) {
					if (((RosterEntry) contact).getPresence() != Presence.INVISIBLE)
						available++;
				}
			}
			return group.getName() + " (" + available + "/" + entries.length + ")";
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
		 */
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.GROUP);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object o) {
			return ((RosterGroup) o).getEntries();
		}
	};

	private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
		 */
		public Object getParent(Object o) {
			return ((RosterEntry) o).getParent();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
		 */
		public String getLabel(Object o) {
			RosterEntry entry = ((RosterEntry) o);
			return entry.getNickname() + " (" + entry.getName() + "@" + entry.getServer() + ")";
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
		 */
		public ImageDescriptor getImageDescriptor(Object object) {
			RosterEntry entry = ((RosterEntry) object);
			String key = presenceToKey(entry.getPresence());
			return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, key);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object o) {
			return new Object[0];
		}
	};

	private String presenceToKey(Presence presence) {
		if (presence == Presence.ONLINE)
			return IImageKeys.ONLINE;
		if (presence == Presence.AWAY)
			return IImageKeys.AWAY;
		if (presence == Presence.DO_NOT_DISTURB)
			return IImageKeys.DO_NOT_DISTURB;
		if (presence == Presence.INVISIBLE)
			return IImageKeys.OFFLINE;
		return "";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class && adaptableObject instanceof RosterGroup)
			return groupAdapter;
		if (adapterType == IWorkbenchAdapter.class && adaptableObject instanceof RosterEntry)
			return entryAdapter;
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}
}
