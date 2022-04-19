package it.cnr.isti.labsedc.concern.example;

import java.net.UnknownHostException;
import java.util.Properties;
import javax.jms.JMSException;
import javax.naming.NamingException;
import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernBaseEvent;
import it.cnr.isti.labsedc.concern.probe.ConcernAbstractProbe;
import it.cnr.isti.labsedc.concern.utils.ConnectionManager;
import it.cnr.isti.labsedc.concern.utils.DebugMessages;

public class SUAProbe extends ConcernAbstractProbe {

	public SUAProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		//creating a probe
		SUAProbe aGenericProbe = new SUAProbe(
				ConnectionManager.createProbeSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						"tcp://localhost:61616","system", "manager",
						"TopicCF","DROOLS-InstanceOne", false, "SUA_probe",	
						"it.cnr.isti.labsedc.concern,java.lang,javax.security,java.util",
						"vera", "griselda"));
		//sending events
		try {
			DebugMessages.line();
			DebugMessages.println(System.currentTimeMillis(), SUAProbe.class.getSimpleName(),"Sending SUA messages");

			sendConnectionEventMessage(aGenericProbe);
			
			
//			Thread.sleep(5000);
//			sendDisconnectionEventMessage(aGenericProbe);
//			Thread.sleep(1000);
//			sendConnectionEventMessage(aGenericProbe);
//			Thread.sleep(5000);
//			sendDisconnectionEventMessage(aGenericProbe);
//			Thread.sleep(1000);
//			sendConnectionEventMessage(aGenericProbe);
//			Thread.sleep(1000);
//			sendConnectionEventMessage(aGenericProbe);
//		  Thread.sleep(100);
//			sendConnectionEventMessage(aGenericProbe);
//			
			
			sendVelocityMessage(aGenericProbe, "0.1");
			Thread.sleep(100);
			sendVelocityMessage(aGenericProbe, "0.1");
			Thread.sleep(100);
			sendScoreMessage(aGenericProbe, "0.1f");
			Thread.sleep(100);
			sendVelocityMessage(aGenericProbe, "0.1");

//			
//			  Thread.sleep(100);
//				sendConnectionEventMessage(aGenericProbe);
//				
//				
				sendVelocityMessage(aGenericProbe, "0.1");
				Thread.sleep(300);
				sendVelocityMessage(aGenericProbe, "0.1");
				Thread.sleep(300);
				sendScoreMessage(aGenericProbe, "0.1f");
				Thread.sleep(300);
				sendVelocityMessage(aGenericProbe, "0.1");
				
//				  Thread.sleep(300);
//					sendConnectionEventMessage(aGenericProbe);
//					
					
					sendVelocityMessage(aGenericProbe, "0.1");
					Thread.sleep(1000);
					sendVelocityMessage(aGenericProbe, "0.1");
					Thread.sleep(300);
					sendScoreMessage(aGenericProbe, "0.1f");
					Thread.sleep(500);
					sendVelocityMessage(aGenericProbe, "0.1");
			
		} catch (IndexOutOfBoundsException | NamingException e) {} catch (JMSException e) {
			e.printStackTrace();
		} 
	}

	
	protected static void sendVelocityMessage(SUAProbe aGenericProbe, String speed) throws JMSException, NamingException {
		aGenericProbe.sendEventMessage(new ConcernBaseEvent<String>(
				System.currentTimeMillis(),
				"SUA_Probe",
				"Monitoring",
				"sessionID",
				"noChecksum",
				"Velocity", speed,
				CepType.DROOLS, false, "anotherPropertyToFill"), false);	
	}

	protected static void sendScoreMessage(SUAProbe aGenericProbe, String score) throws JMSException, NamingException {
		aGenericProbe.sendEventMessage(new ConcernBaseEvent<String>(System.currentTimeMillis(),
				"SUA_Probe","Monitoring","sessionID","noChecksum","Score", score,
				CepType.DROOLS, false, "anotherPropertyToFill"), false);	
	}

	protected static void sendConnectionEventMessage(SUAProbe aGenericProbe) throws JMSException, NamingException {
		aGenericProbe.sendEventMessage(new ConcernBaseEvent<String>(System.currentTimeMillis(),
				"SUA_Probe",
				"Monitoring",
				"sessionID",
				"noChecksum",
				"Connection", "established",
				CepType.DROOLS, false, "anotherPropertyToFill"), false);	
	}
	
	protected static void sendDisconnectionEventMessage(SUAProbe aGenericProbe) throws JMSException, NamingException {
		aGenericProbe.sendEventMessage(new ConcernBaseEvent<String>(System.currentTimeMillis(),
				"SUA_Probe","Monitoring","sessionID","noChecksum","Connection", "closed",
				CepType.DROOLS, false, "anotherPropertyToFill"), false);	
	}
	
	@Override
	public void sendMessage(ConcernBaseEvent<?> event, boolean debug) {
	}	
}