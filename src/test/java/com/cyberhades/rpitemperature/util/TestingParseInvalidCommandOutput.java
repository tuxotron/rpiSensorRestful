package com.cyberhades.rpitemperature.util;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

import com.cyberhades.rpitemperature.model.SensorData;

@RunWith(value = Parameterized.class)
public class TestingParseInvalidCommandOutput {


	private String output;


	public TestingParseInvalidCommandOutput(String output) {
		this.output = output;
	}


	@Parameters
	public static Collection<Object[]> data() {

		Object[][] data = new Object[][] { 
				{"success=false,temperature:24.4,humidity:66.0"}, 
				{"success:true,temperature:28.4"}, 
				{"success:true,message:error message,humidity:66.0"}, 
				{"success:false"}, 
				{"success:true,message:ignored message,humidity:25.0"}, 
				{"success:true,message:ignored message,temperature:25.0"} 
				};

		return Arrays.asList(data);
	}

	@Test(expected = SensorDataException.class)
	public void shouldntComplaint() throws SensorDataException {

		SensorData sensorData = Helper.parseCommandOutput(output);
	}
	
}
