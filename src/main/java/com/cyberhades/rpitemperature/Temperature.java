package com.cyberhades.rpitemperature;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cyberhades.rpitemperature.model.SensorData;
import com.cyberhades.rpitemperature.util.Helper;

@Path("/temperature")
public class Temperature {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SensorData sensorData() {

    	return Helper.getSensorData();
    }


}