package org.eclipsercp.hyperbola.model;

import org.eclipse.core.runtime.PlatformObject;

public abstract class Roster extends PlatformObject {
	public abstract String getName();

	public abstract RosterGroup getParent();
}
