package org.eclipsercp.hyperbola;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ChatEditorInput implements IEditorInput {

	private String participant;
	
	public ChatEditorInput(String participant) {
		super();
		//Assert.isNotNull(participant);
		this.participant = participant;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return participant;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}
	
		public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof ChatEditorInput))
			return false;
		ChatEditorInput other = (ChatEditorInput) obj;
		return this.participant.equals(other.participant);
	}

	public int hashCode() {
		return participant.hashCode();
	}

	@Override
	public String getToolTipText() {
		return participant;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
