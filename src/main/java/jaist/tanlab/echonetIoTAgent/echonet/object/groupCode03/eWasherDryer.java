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




import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import echowand.service.Service;
import jaist.tanlab.echonetIoTAgent.echonet.object.eDataObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.SampleConstants;

public class eWasherDryer extends eDataObject{
	private static final Logger logger = Logger.getLogger(eWasherDryer.class.getName());
	private Timer timer;
	
	

	public eWasherDryer(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xD3);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.WasherDryer);
	}
	
	// Provided Services	
	// Device Property Monitoring
	
	
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
	}

	@Override
	public void observeSpecificData(Service service) {
	}

}
