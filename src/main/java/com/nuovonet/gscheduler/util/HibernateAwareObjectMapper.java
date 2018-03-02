package com.nuovonet.gscheduler.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;
	public HibernateAwareObjectMapper() {
		Hibernate4Module module = new Hibernate4Module();
//		registerModule(module);
		
		//opcao 1
		SimpleModule simpleModule = new SimpleModule("SimpleModule", new Version(2,0,0,null,null,null));
//		simpleModule.addSerializer(Date.class, new DateSerializer());
//		simpleModule.addDeserializer(Date.class, new DateDeserializer());
//		registerModule(simpleModule);
		//opcao 1.b
		registerModules(module, simpleModule);
		
		setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		//fim opcao 1

		//opcao 2
		getSerializationConfig().with(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		//fim opcao 2
		
		disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
	}
}