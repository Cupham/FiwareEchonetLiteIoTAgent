package jaist.tanlab.echonetIoTAgent.fiware;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.orange.ngsi2.model.Attribute;
import com.orange.ngsi2.model.Entity;

import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareEntity;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.FiwareRegistration;
public class NGSIV2Client {
	private HttpClientBuilder clientbuilder;
	private String baseURL;
	public NGSIV2Client() {
	}
	public NGSIV2Client(String url) {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(200);
		connManager.setDefaultMaxPerRoute(100);
		this.setClientbuilder(HttpClients.custom().setConnectionManager(connManager));
		this.baseURL = url;
	}
	public CloseableHttpResponse createEntity(FiwareEntity entity) throws ClientProtocolException, IOException {
		Long startTime = System.currentTimeMillis();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities");
		String uri = builder.toUriString();
		HttpPost postRequest = new HttpPost(uri);
		CloseableHttpClient client = clientbuilder.build();
		postRequest.setEntity(new StringEntity(NGSIUtils.toJSON(entity),ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = client.execute(postRequest);
		response.close();
		Long registrationTime = System.currentTimeMillis() - startTime;
		System.out.println("createEntityTime: " + registrationTime);
		return response;
	}
	public CloseableHttpResponse addAttributeToEntity(String entityID, String entityType, String attributeKey, FiwareAttribute attribute) throws ClientProtocolException, IOException {
		//Build URI
		Long startTime = System.currentTimeMillis();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities/{id}/attrs");
		addParam(builder, "type", entityType);
		String uri = builder.build(entityID).toASCIIString();
		HttpPost postRequest = new HttpPost(uri);
		CloseableHttpClient client = clientbuilder.build();
		postRequest.setEntity(new StringEntity(NGSIUtils.toJSON(attributeKey, attribute),ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = client.execute(postRequest);
		response.close();
		Long createAttributeTime = System.currentTimeMillis() - startTime;
		System.out.println("addAttributeToEntity Time: " + attributeKey + " "+ createAttributeTime);
		return response;
	}
	
	public CloseableHttpResponse updateAttributeData(String entityID, String entityType, String attributeKey, FiwareAttribute attribute) throws ClientProtocolException, IOException {
		//Build URI
		Long startTime = System.currentTimeMillis();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/entities/{id}/attrs/{attrName}");
		addParam(builder, "type", entityType);
		String uri = builder.build(entityID,attributeKey).toASCIIString();
		HttpPut putRequest = new HttpPut(uri);
		CloseableHttpClient client = clientbuilder.build();
		putRequest.setEntity(new StringEntity(NGSIUtils.toJSON(attributeKey, attribute),ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = client.execute(putRequest);
		response.close();
		Long updateAttributeDataTime = System.currentTimeMillis() - startTime;
		System.out.println("updateAttributeDataTime: " + attributeKey+ " "+ updateAttributeDataTime);
		return response;
		
	}
	
	public CloseableHttpResponse createRegistration(FiwareRegistration registration) throws ClientProtocolException, IOException {
		Long startTime = System.currentTimeMillis();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/registrations");
		String uri = builder.toUriString();
		HttpPost postRequest = new HttpPost(uri);
		CloseableHttpClient client = clientbuilder.build();
		postRequest.setEntity(new StringEntity(NGSIUtils.toJSON(registration),ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = client.execute(postRequest);
		response.close();
		Long createRegistration = System.currentTimeMillis() - startTime;
		System.out.println("createRegistration: " + createRegistration);
		return response;
	}
	
	public CloseableHttpResponse deleteRegistration(String id) throws ClientProtocolException, IOException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL());
		builder.path("/v2/registrations/{registrationid}");
		String uri = builder.build(id).toASCIIString();
		HttpDelete  deleteRequest = new HttpDelete(uri);
		CloseableHttpClient client = clientbuilder.build();
		CloseableHttpResponse response = client.execute(deleteRequest);
		response.close();
		return response;
	}

	public ResponseEntity<String> responseSuccess(FiwareEntity entity) {
		return new ResponseEntity<String>(NGSIUtils.toJSON(entity),HttpStatus.OK);
		
	}
	public ResponseEntity responseFailure(FiwareEntity entity) {
		return null;
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
	public HttpClientBuilder getClientbuilder() {
		return clientbuilder;
	}
	public void setClientbuilder(HttpClientBuilder clientbuilder) {
		this.clientbuilder = clientbuilder;
	}

}
