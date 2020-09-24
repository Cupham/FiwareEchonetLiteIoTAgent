package jaist.tanlab.echonetIoTAgent;



import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import echowand.common.EOJ;
import echowand.logic.TooManyObjectsException;
import echowand.monitor.Monitor;
import echowand.monitor.MonitorListener;
import echowand.net.Inet4Subnet;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.EchonetObjectException;
import echowand.service.Core;
import echowand.service.Service;
import jaist.tanlab.echonetIoTAgent.echonet.object.eDataObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.eNode;
import jaist.tanlab.echonetIoTAgent.echonet.object.eNodeProfile;
import jaist.tanlab.echonetIoTAgent.echonet.service.ServiceExecutor;
import jaist.tanlab.echonetIoTAgent.fiware.NGSIV2Client;

@SpringBootApplication
public class EchonetIoTAgentApplication {
	public static String baseURL = "http://localhost:1026";
	private static String networkInterface = "en0";
	public static Core echonetCore;
	public static Service echonetService;
	public static Map<String,eDataObject> echonetLiteNodes;
	public static NGSIV2Client client;
	private static final Logger logger = Logger.getLogger(EchonetIoTAgentApplication.class.getName());
	public static  ServiceExecutor serviceExecutor;
	public static void main(String[] args) {
		SpringApplication.run(EchonetIoTAgentApplication.class, args);
		echonetLiteNodes = new HashMap<String,eDataObject>();
		client = new NGSIV2Client(baseURL);	
		networkMonitor(networkInterface);
	}
	public static boolean networkMonitor(String networkInterface) {
		boolean isSuccessed = false;
		if(echonetService == null) {

			NetworkInterface nif = null;
			try {
				nif = NetworkInterface.getByName(networkInterface);
			} catch (SocketException e1) {
				logger.log(Level.SEVERE, e1.toString());
			}
			try {
				echonetCore = new Core(Inet4Subnet.startSubnet(nif));
			} catch (SubnetException e1) {
				logger.log(Level.SEVERE, e1.toString());
			}
			try {
				echonetCore.startService();
			} catch (TooManyObjectsException e1) {
				logger.log(Level.SEVERE, e1.toString());
			} catch (SubnetException e1) {
				logger.log(Level.SEVERE, e1.toString());
			}
			echonetService = new Service(echonetCore);
			serviceExecutor = new ServiceExecutor(echonetService);
			Monitor monitor = new Monitor(echonetCore);
			monitor.addMonitorListener(new MonitorListener() {
	            @Override
				public void detectEOJsJoined(Monitor monitor, Node node, List<EOJ> eojs) {
	            	logger.log(Level.INFO, "initialEchonetInterface: detectEOJsJoined: " + node + " " + eojs);
	                eNode eDevice = new eNode(node);
	                eNodeProfile profile = null;
	                for(EOJ eoj :  eojs) {
	            	    if(eoj.isProfileObject()) {
	                		profile = new eNodeProfile(node, eoj);
	                		profile.profileObjectFromEPC(echonetService);
	                		eDevice.setProfileObj(profile);
	                	} else if(eoj.isDeviceObject()) {
	                		try {
								eDevice.parseDataObject(eoj,node,echonetService);
							} catch (EchonetObjectException e) {
								logger.log(Level.SEVERE, e.toString());
							}
	                	}
	                }
	            }

	            @Override
				public void detectEOJsExpired(Monitor monitor, Node node, List<EOJ> eojs) {
	            	logger.info("initialEchonetInterface: detectEOJsExpired: " + node + " " + eojs);
	            }
			});
			try {
				monitor.start();
			} catch (SubnetException e) {
				logger.log(Level.SEVERE,"CAN NOT START MONITORING INTERFACE!!!!!! " + e.toString());
				e.printStackTrace();
			}
			isSuccessed = true;
		}
		if(isSuccessed) {
			logger.info("Initilized ECHONET API successfully!");
		}
		return isSuccessed;
	}




}
