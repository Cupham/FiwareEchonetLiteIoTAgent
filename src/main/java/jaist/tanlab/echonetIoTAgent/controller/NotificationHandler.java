package jaist.tanlab.echonetIoTAgent.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationHandler {
	@PostMapping("/notification")
	public void onNotificationReceived(@RequestBody Object request) {
		System.out.println(request.toString());
	}

}
