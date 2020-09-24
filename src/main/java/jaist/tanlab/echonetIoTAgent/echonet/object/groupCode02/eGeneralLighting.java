/*******************************************************************************
 * Copyright 2019 Cu Pham
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package jaist.tanlab.echonetIoTAgent.echonet.object.groupCode02;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.util.IOTools;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.ObjectData;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.tanlab.echonetIoTAgent.EchonetIoTAgentApplication;
import jaist.tanlab.echonetIoTAgent.echonet.object.eDataObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.fiware.NGSIUtils;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eConverter;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.SampleConstants;

public class eGeneralLighting extends eDataObject{
	private static final Logger logger = Logger.getLogger(eGeneralLighting.class.getName());
	private Timer timer;
	private int illuminanceLevel;
	private boolean setDeviceBrightnessRegistered;

	public eGeneralLighting(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x90);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.GeneralLight);
		setDeviceBrightnessRegistered = false;
		NGSIUtils.createEntity(NGSIUtils.toEntity(node, eoj, getType()));
		EchonetIoTAgentApplication.echonetLiteNodes.put(eConverter.toNgsiRegistrationID(node.getNodeInfo().toString(), eoj, getType()), this);
		this.cmdExecutor = EchonetIoTAgentApplication.serviceExecutor;
	}
	
	// Provided Services	
	public boolean setDeviceBrightness(int brightness) {
		boolean rs = false;
		if(this.getIlluminanceLevel() == brightness) {
			logger.info(String.format("Light brightness is already set to %d ! nothing to do", brightness));
			rs = true;
		} else {
			if(this.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB0, new ObjectData(Integer.valueOf(brightness).byteValue()))) {
				refreshIlluminanceLevel(brightness);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshIlluminanceLevel (int illuminance) {
		if(this.getIlluminanceLevel() != illuminance) {
			this.illuminanceLevel = illuminance;
			NGSIUtils.addAttributeToEntity(getNode(), getEoj(), getType(),
					"IlluminanceLevel", new FiwareAttribute(illuminance));
		}
	}
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	public int getIlluminanceLevel() {
		return illuminanceLevel;
	}
	public void setIlluminanceLevel(int illuminanceLevel) {
		this.illuminanceLevel = illuminanceLevel;
	}
	
	
 	private void getSpecificData(Service service){
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xE0);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							if(!setDeviceBrightnessRegistered) {
								   String description = "A service to control the brightness of the light";
								   NGSIUtils.registerService(getNode(), getEoj(), getType(), description, Arrays.asList("IlluminanceLevel"));
								   setDeviceBrightnessRegistered = true;
							}
							int illuminance = eConverter.dataToInteger(resultData);
							refreshIlluminanceLevel(illuminance);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={IlluminanceLevel:%d}",
									 getNode().getNodeInfo().toString(),resultData.toBytes()[0],resultData.toBytes()[1],getIlluminanceLevel()));	
							break;
							
						default:
							break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE, ex.toString());
		}
	}

	@Override
	public void observeSpecificData(Service service) {
		// TODO Auto-generated method stub
		
	}
}
