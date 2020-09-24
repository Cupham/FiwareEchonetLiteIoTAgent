package jaist.tanlab.echonetIoTAgent.fiware.model.subscription;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareMetadata;


public class FiwareSubscription {
	public enum Status {
	    active, expired, inactive;
	}
	String id;
	
	String description;
	
	SubscriptionSubject subject;
	
	Notification notification;
	
	Instant expires;
	
	Status status;
	
	int throttling;
	

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, FiwareMetadata> metadata;
	

}
