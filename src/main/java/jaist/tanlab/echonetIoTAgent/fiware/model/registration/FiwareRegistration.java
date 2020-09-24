package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareMetadata;



public class FiwareRegistration {
	public enum Status {
	    active, expired, inactive;
	}
	String id;
	
	String description;
	
	RegistrationProvider provider;
	
	DataProvided dataProvided;
	
	Status status;
	
	Instant expires;
	
	RegistrationForwardingInformation forwardingInformation;
	

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, FiwareMetadata> metadata;


    public FiwareRegistration() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Map<String, FiwareMetadata> getMetadata() {
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public RegistrationProvider getProvider() {
		return provider;
	}


	public void setProvider(RegistrationProvider provider) {
		this.provider = provider;
	}


	public DataProvided getDataProvided() {
		return dataProvided;
	}


	public void setDataProvided(DataProvided dataProvided) {
		this.dataProvided = dataProvided;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Instant getExpires() {
		return expires;
	}


	public void setExpires(Instant expires) {
		this.expires = expires;
	}


	public RegistrationForwardingInformation getForwardingInformation() {
		return forwardingInformation;
	}


	public void setForwardingInformation(RegistrationForwardingInformation forwardingInformation) {
		this.forwardingInformation = forwardingInformation;
	}
    

}
