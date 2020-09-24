package jaist.tanlab.echonetIoTAgent;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareEntity;

public class test {

	public static void main(String[] args) throws IOException {
		String kk = "{\"entities\":[{\"id\":\"urn:ngsi-ld-reg-GeneralLight-192.168.0.104-029001\",\"type\":\"GeneralLight\"}],\"attrs\":[\"OperationStatus\",\"InstallationLocation\"]}";
		
		String json = "{entities:[{id:urn-ngsi-ld-reg-GeneralLight-192.168.0.104-029001, type:GeneralLight, OperationStatus:{type:Boolean, value:false, metadata:{}}}], actionType:update}";
		String json2 = "{\"entities\":[{\"id\":\"urn:ngsi-ld-reg-GeneralLight-192.168.0.104-029001\",\"type\":\"GeneralLight\",\"OperationStatus\":{\"type\":\"Boolean\",\"value\":false,\"metadata\":{}}}],\"actionType\":\"update\"}";
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		JsonNode node = mapper.readTree(json2);
		System.out.println("Node " + node.get("entities").get("id"));
		
		List<FiwareEntity> entities = mapper.readValue(node.get("entities").traverse(), new TypeReference<List<FiwareEntity>>(){});
		for(FiwareEntity e: entities) {
			System.out.println(e.getId());
			e.getAttributes().forEach((k, v) -> {
				System.out.println("Key: " + k + ", Value: " + v.getValue());
			});
		}
	}

}
