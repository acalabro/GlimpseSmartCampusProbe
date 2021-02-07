 /*
  * GLIMPSE: A generic and flexible monitoring infrastructure.
  * For further information: http://labsewiki.isti.cnr.it/labse/tools/glimpse/public/main
  * 
  * Copyright (C) 2011  Software Engineering Laboratory - ISTI CNR - Pisa - Italy
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
package it.cnr.isti.labsedc.glimpse.utils;

import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventMachineInformation;

/**
 * This class provides print function for debug
 * 
 * @author Antonello Calabr&ograve;
 *
 */
public class DebugMessages {

	public static int lastMessageLength = 0;
	public static Calendar calendarConverter = Calendar.getInstance();
	public static Long lastMessageTime = 0L;
	
	/**
	 * Print the string "className : message " without break line.
	 * Can be used with method {@link #ok()}
	 * 
	 * @param callerClass the name of the class that is calling method
	 * @param messageToPrint the message to print
	 */
	
	public static void print(Long now, String callerClass, String messageToPrint)
	{
		calendarConverter.setTimeInMillis(now);
		String message =  calendarConverter.getTime().toString() + " - " +  callerClass + ": " + messageToPrint;
		System.err.print(message);
		lastMessageLength = message.length();
		DebugMessages.lastMessageTime = now;
	}

	/**
	 * Print the string "className : message " with break line.
	 * Can be used with method {@link #ok()}
	 * 
	 * @param callerClass the name of the class that is calling method
	 * @param messageToPrint the message to print
	 */
	public static void println(Long now, String callerClass, String messageToPrint)
	{		
		calendarConverter.setTimeInMillis(now);
		String message =  calendarConverter.getTime().toString() + " - " +  callerClass + ": " + messageToPrint;
		System.err.println(message);
		DebugMessages.lastMessageTime = now;		
	}
	
	public static void fail()
	{
		int tab = 10 - (lastMessageLength / 8);
		String add="";
		for(int i = 0; i< tab;i++) {
			add +="\t"; 
		}
		System.out.println(add + "[FAIL]");
	}
	
	/**
	 * Print the OK text
	 */
	public static void ok()
	{
		int tab = 15 - (lastMessageLength / 8);
		String add="";
		for(int i = 0; i< tab;i++) {
			add +="\t"; 
		}
		System.err.println(add + "[ OK ]");
	}
	/**
	 * 
	 * Print a line <br />
	 */
	public static void line() {
		System.err.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * Print asterisks
	 */
	public static void asterisks() {
		System.err.println("******************************************************************************************************************************");
	}
	public static void error(long currentTimeMillis, String callerClass, String messageToPrint) {

		System.err.println("******************************************************************************************************************************");
		System.err.println("------------------------------------------------------------------------------------------------------------------------------");
		calendarConverter.setTimeInMillis(currentTimeMillis);
		String message =  "[ERROR]" + calendarConverter.getTime().toString() + " - " +  callerClass + ": " + messageToPrint;
		System.err.print(message);
		lastMessageLength = message.length();
		DebugMessages.lastMessageTime = currentTimeMillis;
		System.err.println("------------------------------------------------------------------------------------------------------------------------------");
		System.err.println("******************************************************************************************************************************");
	}
	public static void printlnMachineInformationInJSONformat(GlimpseBaseEventMachineInformation<String> theObjectToPrint) {
		//jsonTest
		ObjectMapper TEMPjsonObjectToPrint = new ObjectMapper();
		TEMPjsonObjectToPrint.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		try {
			DebugMessages.println(System.currentTimeMillis(),  DebugMessages.class.getCanonicalName(), 
					TEMPjsonObjectToPrint.writeValueAsString(theObjectToPrint));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//end-JsonTest		
	}
}