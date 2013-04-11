package com.cyberhades.rpitemperature;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class TemperatureApplication extends Application {

	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Temperature.class);
		return classes;
	}
	
}
