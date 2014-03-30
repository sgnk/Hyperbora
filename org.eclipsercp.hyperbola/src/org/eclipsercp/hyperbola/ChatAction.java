package org.eclipsercp.hyperbola;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jivesoftware.smack.RosterEntry;

public class ChatAction extends Action implements ISelectionListener, IWorkbenchAction {

	private final IWorkbenchWindow window;
	private static final String ID = "org.eclipsercp.hyperbola.chat";
	private IStructuredSelection selection;
	
	public ChatAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("&Chat");
		setToolTipText("Chat with the selected contact.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.CHAT));
		window.getSelectionService().addSelectionListener(this);
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof RosterEntry);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void run() {
		Object item = selection.getFirstElement();
		RosterEntry entry = (RosterEntry) item;
		IWorkbenchPage page = window.getActivePage();
		ChatEditorInput input = new ChatEditorInput(entry.getUser());
		try {
			page.openEditor(input, ChatEditor.ID);
		} catch (PartInitException e) {
			// Handle error.
		}
	}
}
