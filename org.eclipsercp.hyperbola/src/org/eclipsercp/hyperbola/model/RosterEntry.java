package org.eclipsercp.hyperbola.model;

public class RosterEntry extends Roster {

	private final String name;

	private final String nickname;

	private final String server;

	private Presence presence;

	private final RosterGroup group;

	public RosterEntry(RosterGroup group, String name, String nickname,
			String server) {
		this.group = group;
		this.name = name;
		this.nickname = nickname;
		this.server = server;
		this.presence = Presence.INVISIBLE;
	}

	public Presence getPresence() {
		return presence;
	}

	public void setPresence(Presence presence) {
		this.presence = presence;
		group.fireContactsChanged(this);
	}

	public String getName() {
		return name;
	}

	public String getNickname() {
		return nickname;
	}

	public String getServer() {
		return server;
	}

	public RosterGroup getParent() {
		return group;
	}
}
