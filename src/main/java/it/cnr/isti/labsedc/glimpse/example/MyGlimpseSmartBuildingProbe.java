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

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventSB;
import it.cnr.isti.labsedc.glimpse.probe.GlimpseAbstractProbe;
import it.cnr.isti.labsedc.glimpse.utils.DebugMessages;
import it.cnr.isti.labsedc.glimpse.utils.Manager;

public class MyGlimpseSmartBuildingProbe extends GlimpseAbstractProbe {

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
	
	public MyGlimpseSmartBuildingProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException {

		DebugMessages.line();
		DebugMessages.println(MyGlimpseSmartBuildingProbe.class.getName(),
				"\nUSAGE: java -jar MyGlimpseSmartBuildingProbe.jar [sendingInterval] [roomID] [sensorName]");
		DebugMessages.line();
		try {
			if (args.length > 0 && Integer.parseInt(args[0])>0) {
				sendingInterval = Integer.parseInt(args[0]);
				roomID = args[1];
				sensorName = args[2];
			}	
		} catch (IndexOutOfBoundsException e) {
		}
		
		MyGlimpseSmartBuildingProbe aGenericProbe = new
				MyGlimpseSmartBuildingProbe(Manager.createProbeSettingsPropertiesObject(
								"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
								"tcp://atlantis.isti.cnr.it:61616",
								"system", "manager",
								"TopicCF", "jms.probeTopic",
								false,
								"probeName", "probeTopic"));
		
		DebugMessages.println(
				MyGlimpseSmartBuildingProbe.class.getName(),
				"Starting loop");
		
		aGenericProbe.generateAndSendExample_GlimpseBaseEvents_FloatPayload(
				parameterName, parameterValue, sendingInterval);
	}
	
	@Override
	public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {		
	}
	
	private void generateAndSendExample_GlimpseBaseEvents_FloatPayload(
			String parameterName, Float parameterValue, int sendingInterval) throws UnknownHostException {
		
		DebugMessages.ok();
		DebugMessages.print(MyGlimpseSmartBuildingProbe.class.getName(),"Creating GlimpseBaseEventSB message");
		GlimpseBaseEventSB<Float> message;
		DebugMessages.ok();
		DebugMessages.line();
		try {
			
			for (int i = 0; i<1000;i++) {
								
				message = new GlimpseBaseEventSB<Float>(
						parameterValue,
						MyGlimpseSmartBuildingProbe.sensorName,
						System.currentTimeMillis(),
						parameterName,
						false,
						MyGlimpseSmartBuildingProbe.roomID
						);
				
				this.sendEventMessage(message, false);
				DebugMessages.println(
						MyGlimpseSmartBuildingProbe.class.getName(),
						"GlimpseBaseEventSB message sent: {\n"
								+ "sensorName: " + message.getProbeID() + "\n"
								+ "parameterName: " + message.getEventName() + "\n"
								+ "parameterValue: " + message.getEventData() + "\n"
								+ "timestamp: " + message.getTimeStamp() + "\n"
								+ "roomID: " + message.getExtraDataField() + "\n"
								+"}");
				DebugMessages.line();
				Thread.sleep(sendingInterval);
			}
		} catch (JMSException e1) {
			e1.printStackTrace();
		} catch (NamingException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
