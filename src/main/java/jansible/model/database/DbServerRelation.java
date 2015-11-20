package jansible.model.database;

import jansible.model.common.ServerRelationKey;

public class DbServerRelation extends ServerRelationKey{
	
	public DbServerRelation(){}
	
	public DbServerRelation(ServerRelationKey serverRelationKey) {
		super(serverRelationKey, serverRelationKey.getServerName());
	}
}
