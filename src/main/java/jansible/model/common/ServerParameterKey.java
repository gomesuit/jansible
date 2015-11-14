package jansible.model.common;

public class ServerParameterKey extends ServerKey{
	private String parameterName;
	
	public ServerParameterKey(){}

	public ServerParameterKey(ServerKey serverKey){
		super(serverKey, serverKey.getServerName());
	}

	public ServerParameterKey(ServerKey serverKey, String parameterName) {
		this(serverKey);
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}
