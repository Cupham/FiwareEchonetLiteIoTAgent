package jaist.tanlab.echonetIoTAgent.fiware.model;

public class FiwareMetadata {
    private String type;
	private Object value;

    public FiwareMetadata() {
    }

    public FiwareMetadata(String type, Object value) {
        this.setType(type);
        this.setValue(value);
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
