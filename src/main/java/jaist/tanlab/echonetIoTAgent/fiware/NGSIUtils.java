package jaist.tanlab.echonetIoTAgent.fiware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.apache.catalina.util.IOTools;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.tanlab.echonetIoTAgent.EchonetIoTAgentApplication;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eConverter;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareEntity;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.DataProvided;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.FiwareRegistration;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.ProviderMethod;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.RegistrationEntity;
import jaist.tanlab.echonetIoTAgent.fiware.model.registration.RegistrationProvider;

public class NGSIUtils {
	private static final Logger logger = Logger.getLogger(NGSIUtils.class.getName());
	
	public static String toJSON(String key, Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		ObjectNode attributeNode = mapper.valueToTree(object);
		ObjectNode jsonNode = mapper.createObjectNode();
		jsonNode.set(key, attributeNode);
		String rs = "";
		try {
			rs = mapper.writeValueAsString(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.toString());
		}
		return rs;
	}
	public static String toJSON(Object entity) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String rs = "";
		try {
			rs = mapper.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.toString());
		}
		return rs;
	}
	public static void parseReponse(Response response) {
		System.out.println("status: " + response.getStatus());
		System.out.println("headers: " + response.getHeaders());
		System.out.println("body:" + response.readEntity(String.class));
		
	}
	
	public static FiwareEntity toEntity(Node node, EOJ eoj, eDeviceType type) {
		FiwareEntity rs = new FiwareEntity();
		rs.setType(type.getName());
		rs.setId(eConverter.toNgsiID(node.getNodeInfo().toString(), eoj, type));
		return rs;
	}
	
	public static FiwareEntity toEntity(Node node, EOJ eoj, eDeviceType type, Map<String,FiwareAttribute> attributes) {
		FiwareEntity rs = new FiwareEntity();
		rs.setType(type.getName());
		rs.setId(eConverter.toNgsiID(node.getNodeInfo().toString(), eoj, type));
		rs.setAttributes(attributes);
		return rs;
	}
	
	
	public static List<FiwareEntity> toEntities(String JSON) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		JsonNode node = null;
		try {
			node = mapper.readTree(JSON);
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<FiwareEntity> entities = null;
		try {
			entities = mapper.readValue(node.get("entities").traverse(), new TypeReference<List<FiwareEntity>>(){});
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return entities;
	}
	public static void createEntity(FiwareEntity e) {
		try {
			EchonetIoTAgentApplication.client.createEntity(e);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void addAttributeToEntity(Node node, EOJ eoj, eDeviceType type, 
			String attributeKey, FiwareAttribute attribute) {
		String entityID = eConverter.toNgsiID(node.getNodeInfo().toString(), eoj, type);
		try {
			EchonetIoTAgentApplication.client.addAttributeToEntity(entityID, type.getName(), attributeKey, attribute);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addAttributeToEntity(String entityID, String entityType, 
			String attributeKey, FiwareAttribute attribute)  {
		try {
			EchonetIoTAgentApplication.client.addAttributeToEntity(entityID, entityType, attributeKey, attribute);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void registerService(Node node, EOJ eoj, eDeviceType type, String description, 
									   List<String> supportAttributes) {
		FiwareRegistration registration = new FiwareRegistration();
		
		String entityID = eConverter.toNgsiRegistrationID(node.getNodeInfo().toString(), eoj, type);
		RegistrationEntity entity = new RegistrationEntity(entityID, type.getName());
		List<RegistrationEntity> entities = new ArrayList<RegistrationEntity>();
		entities.add(entity);
		DataProvided data= new DataProvided(entities, supportAttributes);
	
		ProviderMethod providerMethod = new ProviderMethod(FiwareConstants.CONTEXT_PROVIDER_URL);
		RegistrationProvider registrationProvider = new RegistrationProvider(providerMethod);
		
		registration.setDescription(description);
		registration.setDataProvided(data);
		registration.setProvider(registrationProvider);
		try {
			EchonetIoTAgentApplication.client.createRegistration(registration);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String reply() {
		FiwareEntity e = new FiwareEntity();
		e.setId("urn:ngsi-ld-reg-GeneralLight-192.168.0.104-029001");
		e.setType("GeneralLight");
		Map<String, FiwareAttribute> attr = new HashMap<String, FiwareAttribute>();
		attr.put("OperationStatus", new FiwareAttribute(false));
		e.setAttributes(attr);
		return NGSIUtils.toJSON(e);
	}
}
