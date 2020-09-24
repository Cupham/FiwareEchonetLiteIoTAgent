package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

public class RegistrationProvider {
	public enum ForwardingMode {
		none, query, update, all;
	}
	public RegistrationProvider(ProviderMethod httpServer) {
		setHttp(httpServer);
	}
	private ProviderMethod http;
	private ForwardingMode supportedForwardingMode;
	public ProviderMethod getHttp() {
		return http;
	}
	public void setHttp(ProviderMethod http) {
		this.http = http;
	}
	public ForwardingMode getSupportedForwardingMode() {
		return supportedForwardingMode;
	}
	public void setSupportedForwardingMode(ForwardingMode supportedForwardingMode) {
		this.supportedForwardingMode = supportedForwardingMode;
	}

}
