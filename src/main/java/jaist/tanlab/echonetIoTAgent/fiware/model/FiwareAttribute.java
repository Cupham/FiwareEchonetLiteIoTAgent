package jaist.tanlab.echonetIoTAgent.fiware.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class FiwareAttribute {
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
    private Optional<String> type;
	
	
	private Object value;


    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Map<String, FiwareMetadata> metadata;

    public FiwareAttribute() {
    }

    public FiwareAttribute(Object value) {
        this.setValue(value);
    }
    public FiwareAttribute(Object value, Optional<String> type) {
        this.setValue(value);
        this.setType(type);
    }

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public Optional<String> getType() {
        return type;
    }

    public void setType(Optional<String> type) {
        this.type = type;
    }
	
	public Map<String, FiwareMetadata> getMetadata() {
        if (metadata == null) {
            return Collections.emptyMap();
        }
        return metadata;
    }

    public void setMetadata(Map<String, FiwareMetadata> metadata) {
        this.metadata = metadata;
    }

    @JsonIgnore
    public void addMetadata(String key, FiwareMetadata metadata) {
        if (this.metadata == null) {
            this.metadata = new HashMap<String, FiwareMetadata>();
        }
        this.metadata.put(key, metadata);
    }

}
