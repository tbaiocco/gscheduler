package com.nuovonet.gscheduler.jobs;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerIni implements Serializable {

	@Autowired
	private Scheduler scheduler;

	private static final long serialVersionUID = 8982949394486924526L;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		System.out.println("##### INICIOU #####");
	}
}