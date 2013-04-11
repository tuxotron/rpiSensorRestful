package com.cyberhades.rpitemperature.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

import com.cyberhades.rpitemperature.model.SensorData;

@RunWith(value = Parameterized.class)
public class TestingParseValidCommandOutput {


	private String output;


	public TestingParseValidCommandOutput(String output) {
		this.output = output;
	}


	@Parameters
	public static Collection<Object[]> data() {

		Object[][] data = new Object[][] { 
				{"success:true,temperature:24.4,humidity:66.0"}, 
				{"success:true,temperature:28.4,humidity:25.0"}, 
				{"success:false,message:error message"}, 
				{"success:false,message:error message,temperature:28.4,humidity:25.0"}, 
				{"success:true,message:ignored message,temperature:28.4,humidity:25.0"} 
				};

		return Arrays.asList(data);
	}

	@Test
	public void shouldntComplaint() {

		try {
			SensorData sensorData = Helper.parseCommandOutput(output);
			assertNotNull(sensorData.isSuccess());
			if (sensorData.isSuccess()) {
				assertNotNull(sensorData.getHumidity());
				assertNotNull(sensorData.getTemperature());
			} else {
				assertNotNull(sensorData.getMessage());
			}

		} catch (SensorDataException e) {
			e.printStackTrace();
			fail();
		}

	}
}
