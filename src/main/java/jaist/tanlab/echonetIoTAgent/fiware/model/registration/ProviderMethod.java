package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

public class ProviderMethod {
	private String url;
	public ProviderMethod() {
		
	}
	public ProviderMethod(String url) {
		setUrl(url);
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
