package jaist.tanlab.echonetIoTAgent.fiware;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.util.UriComponentsBuilder;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareEntity;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.FiwareRegistration;
public class NGSIV2Client2 {
	private Client client;
	private String baseURL;
	public NGSIV2Client2() {
		 this.client = ClientBuilder.newClient();
	}
	public NGSIV2Client2(String url) {
		 this.client = ClientBuilder.newClient();
		 this.baseURL = url;
	}
	public Response createEntity(FiwareEntity entity) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities");
		String uri = builder.toUriString();
		Entity payload = Entity.json(NGSIUtils.toJSON(entity));
		return client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).post(payload);
	}
	public Response addAttributeToEntity(String entityID, String entityType, String attributeKey, FiwareAttribute attribute) {
		//Build URI
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities/{id}/attrs");
		addParam(builder, "type", entityType);
		String uri = builder.build(entityID).toASCIIString();
		Entity  payload = Entity.json(NGSIUtils.toJSON(attributeKey, attribute));
		return client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).post(payload);
	}
	
	public Response updateAttributeData(String entityID, String entityType, String attributeKey, FiwareAttribute attribute) {
		//Build URI
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities/{id}/attrs/{attrName}");
		addParam(builder, "type", entityType);
		String uri = builder.build(entityID,attributeKey).toASCIIString();
		Entity  payload = Entity.json(NGSIUtils.toJSON(attribute));
		return client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).put(payload);
	}
	
	public Response createRegistration(FiwareRegistration registration) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/registrations");
		String uri = builder.toUriString();
		Entity payload = Entity.json(NGSIUtils.toJSON(registration));
		return client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).post(payload);
	}
	
	public Response deleteRegistration(String id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/registrations/{registrationid}");
		String uri = builder.build(id).toASCIIString();
		return client.target(uri).request(MediaType.APPLICATION_JSON).delete();
	}

	
	
	
	
	
	
	
	
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	private void addParam(UriComponentsBuilder builder, String key, String value) {
        if (!nullOrEmpty(value)) {
            builder.queryParam(key, value);
        }
    }

	private void addParam(UriComponentsBuilder builder, String key, Collection<? extends CharSequence> value) {
        if (!nullOrEmpty(value)) {
            builder.queryParam(key, String.join(",", value));
        }
    }
    
    private boolean nullOrEmpty(Collection i) {
        return i == null || i.isEmpty();
    }

    private boolean nullOrEmpty(String i) {
        return i == null || i.isEmpty();
    }
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

}
