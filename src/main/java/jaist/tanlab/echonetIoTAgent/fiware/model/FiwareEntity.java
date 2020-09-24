package jaist.tanlab.echonetIoTAgent.fiware.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class FiwareEntity {
	 private String id;

	    private String type;
	    @JsonInclude(JsonInclude.Include.ALWAYS)
	    private Map<String, FiwareAttribute> attributes;
	    
	    public FiwareEntity() {
	    }

	    public FiwareEntity(String id, String type) {
	        this.setId(id);
	        this.setType(type);
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		@JsonAnyGetter
	    public Map<String, FiwareAttribute> getAttributes() {
	        return attributes;
	    }

	    @JsonAnySetter
	    public void setAttributes(String key, FiwareAttribute attribute) {
	        if (attributes == null) {
	            attributes = new HashMap<String, FiwareAttribute>();
	        }
	        attributes.put(key, attribute);
	    }

	    @JsonIgnore
	    public void setAttributes(Map<String, FiwareAttribute> attributes) {
	        this.attributes = attributes;
	    }
}
