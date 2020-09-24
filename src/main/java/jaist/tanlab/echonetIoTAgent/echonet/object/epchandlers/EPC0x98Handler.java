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
import java.util.Date;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.tanlab.echonetIoTAgent.echonet.object.eSuperObject;
import jaist.tanlab.echonetIoTAgent.echonet.object.eTrigger;
import jaist.tanlab.echonetIoTAgent.echonet.object.utils.eDeviceType;
import jaist.tanlab.echonetIoTAgent.fiware.NGSIUtils;
import jaist.tanlab.echonetIoTAgent.fiware.model.FiwareAttribute;

public class EPC0x98Handler {
	private static final Logger logger = Logger.getLogger(EPC0x98Handler.class.getName());
	public static final String description = "Service to set current date setting of a device";
	public static void refreshCurrentDateSetting(Object obj, Date newVal) {

		if(obj instanceof eSuperObject) {
			eSuperObject dev = (eSuperObject) obj;
				Node node = ((eSuperObject) obj).getNode();
				EOJ eoj = ((eSuperObject) obj).getEoj();
				eDeviceType type = ((eSuperObject) obj).getType();
				if(type!= eDeviceType.Profile) {
			if(dev.getCurrentDateSetting() != newVal) {
				if(!((eSuperObject) obj).setCurrentDateServiceRegistered) {
					NGSIUtils.registerService(node, eoj, type, description, Arrays.asList("CurrentDate"));
					((eSuperObject) obj).setCurrentDateServiceRegistered = true;
				}
				dev.setCurrentDateSetting(newVal);
				NGSIUtils.addAttributeToEntity(node, eoj, type, "CurrentDate", 
						new FiwareAttribute(newVal.toInstant()));
				eTrigger.currentDateSettingChanged(dev);
			}
				}
		}else {
			logger.severe(String.format("=============INVALID OBJECT DETECTED=============\n "
					+ "					 =============%s =============", obj.toString()));
		}
	}

}
