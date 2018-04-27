package hu.djani.Manager.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;

@Service
public class DataLoader {

	MachineService machineService;

	@Autowired
	public DataLoader(MachineService machineService) {
		this.machineService = machineService;
	}

	@PostConstruct
	public void loadData() {
		this.machineService.save(new Machine("Localhost", "Saját gép", "127.0.0.1", new Date()));
		this.machineService.save(new Machine("build gép", "build szerver", "10.11.16.150", new Date()));
		this.machineService.save(new Machine("hamis cím", "teszt leiras", "10.11.16.161", new Date()));

		this.machineService.save(new Machine("OpenLAB", "OpenLAB huedu szerver", "10.11.16.190", new Date()));

		this.machineService.save(new Machine("Google DNS", "Google DNS szerver", "8.8.8.8", new Date()));
		this.machineService.save(new Machine("Google Search", "a Google kereső", "www.google.com", new Date()));
		this.machineService.save(new Machine("Facebook", "Facebook közösségi oldal", "www.facebook.com", new Date()));
	}

}
