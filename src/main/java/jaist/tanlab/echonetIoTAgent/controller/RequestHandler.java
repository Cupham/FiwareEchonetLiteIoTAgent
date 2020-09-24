package jaist.tanlab.echonetIoTAgent.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jaist.tanlab.echonetIoTAgent.EchonetIoTAgentApplication;
import jaist.tanlab.echonetIoTAgent.fiware.NGSIUtils;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareEntity;

@RestController
public class RequestHandler {
	
	@RequestMapping("/registration/op/query")
	public String onGETRequestReceived(@RequestBody Object request) throws JsonMappingException, JsonProcessingException {
		return NGSIUtils.reply();
	}
	@RequestMapping("/registration/op/update")
	public String onSETRequestReceived(@RequestBody Object request) {
		Long startTime = System.currentTimeMillis();
		String jsonRequest = NGSIUtils.toJSON(request);
		System.out.println("requeste received!!!\n" + jsonRequest);
		List<FiwareEntity> entities= NGSIUtils.toEntities(jsonRequest);
		
		for(FiwareEntity entity : entities) {
			boolean rs = EchonetIoTAgentApplication.serviceExecutor.executeRequest(entity);
			if (rs) {
				//return NGSIUtils.reply();
			} else {
				//return NGSIUtils.reply();
			}
		}
		Long handleRequest = System.currentTimeMillis() - startTime;
		System.out.println("handleRequestTime: " + handleRequest);
		return null;
	}

}
