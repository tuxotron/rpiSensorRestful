package com.cyberhades.rpitemperature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cyberhades.rpitemperature.model.SensorData;

public class Helper {
	
//	private final static String COMMAND = "less /home/tuxotron/data.txt";
	private final static String COMMAND = "dht22 4";

	/*
	 * Returns a SensorData object with the information retrieved 
	 * from the sensor
	 * @return SensorData
	 */
	public static SensorData getSensorData() {
		
		String output = runCommand(COMMAND);
		
		SensorData sensorData;
		try {
			sensorData = parseCommandOutput(output);
		} catch (SensorDataException e) {
			sensorData = new SensorData();
			sensorData.setSuccess(false);
			sensorData.setMessage(e.getMessage());
		}
		
		return sensorData;
	}
	
	/*
	 * Actually runs a system command
	 * @param command String with the command to run
	 * @return returns a String with the result
	 */
	public static String runCommand(String command) {
    	StringBuilder salida = new StringBuilder();
    	
		try {
			
			Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null, previous = null;
			while ((line = br.readLine()) != null) {
				if (!line.equals(previous)) {
					previous = line;
					salida.append(line);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return salida.toString();
	}
	
	/*
	 * it parses the output returned by the command 
	 * @param output This is the String returned by the command. See @runCommand
	 * @return SensorData object with the information received by the output
	 * @throws a SensorDataException if the received output is not in the
	 * expected format
	 */
	public static SensorData parseCommandOutput(String output) throws SensorDataException {
		
		if (output == null || output.trim().length() == 0) {
			throw new SensorDataException("Empty data string");
		}
		
		Map<String, String> fields = new HashMap<String, String>();
		
		String[] tokens = output.split(",");
		for (String pair : tokens) {
			String[] nameValue = pair.split(":");
			if (nameValue.length == 2) {
				fields.put(nameValue[0], nameValue[1]);
			} else {
				throw new SensorDataException("Unknown format");
			}
		}
		
		if ("true".equalsIgnoreCase(fields.get("success"))) {
			if (fields.get("humidity") == null || fields.get("temperature") == null) {
				throw new SensorDataException("Invalid format");
			}
		} else {
			if (fields.get("message") == null) {
				throw new SensorDataException("Invalid format");
			}
		}
		
		return dumpMap(fields);
		
	}
	
	/*
	 * Copies a Map into a SensorData
	 * @param values Map<String, String>
	 * @return SensorData
	 */
	public static SensorData dumpMap(Map<String, String> values) {
	
		SensorData sensorData = new SensorData();
		sensorData.setSuccess(values.get("success").equalsIgnoreCase("true"));
		sensorData.setMessage(values.get("message"));
		sensorData.setTemperature(values.get("temperature"));
		sensorData.setHumidity(values.get("humidity"));
		
		return sensorData;
	}
	
}
