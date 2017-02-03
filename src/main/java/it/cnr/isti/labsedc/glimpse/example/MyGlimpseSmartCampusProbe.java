 /*
  * GLIMPSE: A generic and flexible monitoring infrastructure.

  * For further information: http://labsewiki.isti.cnr.it/labse/tools/glimpse/public/main
  * 
  * Copyright (C) 2015  Software Engineering Laboratory - ISTI CNR - Pisa - Italy
  * 
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * 
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * 
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * 
*/

package it.cnr.isti.labsedc.glimpse.example;

import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Random;

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventFaceRecognition;
import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventSB;
import it.cnr.isti.labsedc.glimpse.probe.GlimpseAbstractProbe;
import it.cnr.isti.labsedc.glimpse.utils.DebugMessages;
import it.cnr.isti.labsedc.glimpse.utils.Manager;
import it.cnr.isti.labsedc.glimpse.utils.SensorType;

public class MyGlimpseSmartCampusProbe extends GlimpseAbstractProbe {

	/**
	 * This class provides an example of how to send messages (events) to Glimpse CEP.
	 * @author Antonello Calabr&ograve;
	 * @version 3.3.2
	 *
	 */
	
	public static int sendingInterval = 10000;
	
	public static String parameterName;
	public static float parameterValue;
	public static String roomID;
	public static String sensorName;
	public static Random rand = new Random();
	
	public MyGlimpseSmartCampusProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException {

		//creating a probe
		DebugMessages.line();
		MyGlimpseSmartCampusProbe aGenericProbe = new MyGlimpseSmartCampusProbe(
				Manager.createProbeSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
								"tcp://ubuntu@n037.smart-applications.area.pi.cnr.it:61616","system", "manager","TopicCF", "jms.probeTopic",false,"probeName", "probeTopic"));
		
		//sending events
		try {
				aGenericProbe.generateAndSendExample_GlimpseBaseEvents_SmartBuildingPayload();
				aGenericProbe.generateAndSendExample_GlimpseBaseEvents_FaceRecognitionPayload();
		} catch (IndexOutOfBoundsException e) {}		
	}

	@Override
	public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {		
	}
	
	private void generateAndSendExample_GlimpseBaseEvents_SmartBuildingPayload() {
		
		DebugMessages.ok();
		DebugMessages.print(MyGlimpseSmartCampusProbe.class.getSimpleName(),"Creating GlimpseBaseEventSB message");
		GlimpseBaseEventSB<Float> message;
		DebugMessages.ok();
		DebugMessages.line();
		
		message = new GlimpseBaseEventSB<Float>(64f, "HumiditySensorName",
						System.currentTimeMillis(),	"Humidity",	false, "C-70", SensorType.HUMIDITY);
				
			try {
				this.sendEventMessage(message, false);
				DebugMessages.println(
					MyGlimpseSmartCampusProbe.class.getSimpleName(),
					"GlimpseBaseEventSB message sent: {\n"
							+ "sensorName: " + message.getProbeID() + "\n"
							+ "parameterName: " + message.getEventName() + "\n"
							+ "parameterValue: " + message.getEventData() + "\n"
							+ "timestamp: " + message.getTimeStamp() + "\n"
							+ "roomID: " + message.getExtraDataField() + "\n"
							+ "sensorType: " + message.getSensorType().toString() + "\n"
							+"}");
				DebugMessages.line();
			} catch (JMSException | NamingException e) {
				e.printStackTrace();
			}
		}
	
	private void generateAndSendExample_GlimpseBaseEvents_FaceRecognitionPayload() {
		DebugMessages.print(MyGlimpseSmartCampusProbe.class.getSimpleName(),"Creating GlimpseBaseEventFaceRecognition message");
		GlimpseBaseEventFaceRecognition<Boolean> message;
		DebugMessages.ok();
		DebugMessages.line();
		
		message = new GlimpseBaseEventFaceRecognition<Boolean>(
				false, "CameraName", System.currentTimeMillis(),
				"StringParameterAvailable", false, "C-70");
				
			try {
				this.sendEventMessage(message, false);
				DebugMessages.println(
					MyGlimpseSmartCampusProbe.class.getSimpleName(),
					"GlimpseBaseEventFaceRecognition message sent: {\n"
							+ "cameraName: " + message.getProbeID() + "\n"
							+ "parameterName: " + message.getEventName() + "\n"
							+ "recognitionValue: " + message.getEventData() + "\n"
							+ "timestamp: " + message.getTimeStamp() + "\n"
							+ "roomID: " + message.getExtraDataField() + "\n"
							+ "}");
				DebugMessages.line();
			} catch (JMSException | NamingException e) {
				e.printStackTrace();
			}
		}
}