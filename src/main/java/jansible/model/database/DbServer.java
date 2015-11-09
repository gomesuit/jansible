package jansible.model.database;

import jansible.model.common.ServerKey;

public class DbServer extends ServerKey{
	public DbServer(){}

	public DbServer(ServerKey serverKey) {
		super(serverKey, serverKey.getServerName());
	}
}
