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
package jaist.tanlab.echonetIoTAgent.echonet.object.groupCode03;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.tanlab.echonetIoTAgent.echonet.object.eDataObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eConverter;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.SampleConstants;

public class eRefrigerator extends eDataObject{
	private static final Logger logger = Logger.getLogger(eRefrigerator.class.getName());
	private Timer timer;
	private boolean doorOpenCloseStatus;
	private boolean doorOpenWarningStatus;
	
	

	public eRefrigerator(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xB7);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.Refrigerator);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshDoorOpenCloseStatus(boolean val) {
		if(val != isDoorOpenCloseStatus()) {
			logger.info(String.format("DoorOpenCloseStatus changed from %s to %s",isDoorOpenCloseStatus(),val));
			setDoorOpenCloseStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDoorOpenWarningStatus(boolean val) {
		if(val != isDoorOpenWarningStatus()) {
			logger.info(String.format("DoorOpenWarningStatus changed from %s to %s",isDoorOpenWarningStatus(),val));
			setDoorOpenWarningStatus(val);
			//TODO: More task can be added here
		}
	}
	
	
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		if(observeEnabled) {
			observeSuperData(service, node, eoj);
			observeSpecificData(service);
		}
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	
	
 	private void getSpecificData(Service service){
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xB0);
		epcs.add(EPC.xB1);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB0:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshDoorOpenCloseStatus(true);
							} else {
								refreshDoorOpenCloseStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={DoorOpenCloseStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDoorOpenCloseStatus()));
						break;
						case xB1:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshDoorOpenWarningStatus(true);
							} else {
								refreshDoorOpenWarningStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={DoorOpenWarningStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDoorOpenWarningStatus()));
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
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xB1);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB1:
						if(eConverter.dataToInteger(resultData) == 65) {
							refreshDoorOpenWarningStatus(true);
						} else {
							refreshDoorOpenWarningStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={DoorOpenWarningStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDoorOpenWarningStatus()));
					break;
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
	}
	public boolean isDoorOpenCloseStatus() {
		return doorOpenCloseStatus;
	}

	public void setDoorOpenCloseStatus(boolean doorOpenCloseStatus) {
		this.doorOpenCloseStatus = doorOpenCloseStatus;
	}

	public boolean isDoorOpenWarningStatus() {
		return doorOpenWarningStatus;
	}

	public void setDoorOpenWarningStatus(boolean doorOpenWarningStatus) {
		this.doorOpenWarningStatus = doorOpenWarningStatus;
	}

}
