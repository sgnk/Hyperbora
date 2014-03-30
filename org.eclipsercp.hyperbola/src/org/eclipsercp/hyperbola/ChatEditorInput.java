package org.eclipsercp.hyperbola;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.jivesoftware.smack.util.StringUtils;

/**
 * Input for a chat editor.
 */
public class ChatEditorInput implements IEditorInput {

	private String participant;
	
	/**
	 * Creates a chat editor input on the given session and participant.
	 */
	public ChatEditorInput(String participant) {
		Assert.isNotNull(participant);
		this.participant = StringUtils.parseBareAddress(participant);
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return getParticipant();
	}

	@Override
	public IPersistableElement getPersistable() {
		// Not persistable between sessions
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}

	@Override
	public Object getAdapter(Class adapter) {
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

	public String getParticipant() {
		return participant;
	}
}
