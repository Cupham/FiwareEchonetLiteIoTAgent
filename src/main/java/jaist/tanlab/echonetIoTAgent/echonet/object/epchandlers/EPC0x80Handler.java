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
package jaist.tanlab.echonetIoTAgent.echonet.object.epchandlers;

import java.util.Arrays;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.tanlab.echonetIoTAgent.echonet.object.eSuperObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.eTrigger;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.fiware.NGSIUtils;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;

public class EPC0x80Handler {
	private static final Logger logger = Logger.getLogger(EPC0x80Handler.class.getName());
	public static final String description = "Service to turn on/ off a device";
	public static void refreshOperationStatus(Object obj, boolean newVal) {
		
		if(obj instanceof eSuperObject) {
			eSuperObject dev = (eSuperObject) obj;
			Node node = ((eSuperObject) obj).getNode();
			EOJ eoj = ((eSuperObject) obj).getEoj();
			eDeviceType type = ((eSuperObject) obj).getType();
			// Ignore profile object
			if(type!= eDeviceType.Profile) {
				if(dev.getOperationStatus() != newVal) {
					if(!((eSuperObject) obj).setOperationStatusServiceRegistered) {
						NGSIUtils.registerService(node, eoj, type, description, Arrays.asList("OperationStatus"));
						((eSuperObject) obj).setOperationStatusServiceRegistered = true;
					}
					dev.setOperationStatus(newVal);
					NGSIUtils.addAttributeToEntity(node, eoj, type, "OperationStatus", new FiwareAttribute(newVal));
					eTrigger.operationStatusChanged(dev);
				}
			} else {
				dev.setOperationStatus(newVal);
			}
		}else {
			logger.severe(String.format("=============INVALID OBJECT DETECTED=============\n "
					+ "					 =============%s =============", obj.toString()));
		}
	}
}
