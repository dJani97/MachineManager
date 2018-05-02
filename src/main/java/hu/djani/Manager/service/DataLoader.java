package hu.djani.Manager.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.bean.MachineGroup;

@Service
public class DataLoader {

	MachineService machineService;
	MachineGroupService groupService;

	@Autowired
	public DataLoader(MachineService machineService, MachineGroupService machineGroupService) {
		this.machineService = machineService;
		this.groupService = machineGroupService;
	}

	@PostConstruct
	public void loadData() {

		/*
		 * Creating MachineGroups
		 */

		MachineGroup groupNovell = new MachineGroup("Novell");
		MachineGroup groupInternet = new MachineGroup("Internet");
		MachineGroup groupTest = new MachineGroup("Teszt");

		/*
		 * Saving MachineGroups
		 */

		this.groupService.save(groupNovell);
		this.groupService.save(groupInternet);
		this.groupService.save(groupTest);

		/*
		 * Creating Machines
		 */

		Machine machine00 = new Machine("Localhost", "Saját gép", "127.0.0.1", new Date(), groupNovell);
		Machine machine01 = new Machine("build gép", "build szerver", "10.11.16.150", new Date(), groupNovell);
		Machine machine02 = new Machine("OpenLAB", "OpenLAB huedu szerver", "10.11.16.190", new Date(), groupNovell);

		Machine machine10 = new Machine("Google DNS", "Google DNS szerver", "8.8.8.8", new Date(), groupInternet);
		Machine machine11 = new Machine("Google Search", "a Google kereső", "www.google.com", new Date(), groupInternet);
		Machine machine12 = new Machine("Facebook", "Facebook közösségi oldal", "www.facebook.com", new Date(), groupInternet);

		Machine machine20 = new Machine("hamis cím", "teszt leiras", "10.11.16.161", new Date(), groupTest);
		Machine machine21 = new Machine("Eszméletlenül hosszú cím ami már valószínűleg ki fog lógni elég rendesen", "Eszméletlenül hosszú leírás ami már valószínűleg ki fog lógni elég rendesen, de ez még nem volt elég neki ezért még sokkal hosszabbra kell írni hogy egészen biztosan elérjen ennek a sornak a végéig. És akkor most megoldom hogy túllógjon.", "10.11.16.189", new Date(), groupTest);

		/*
		 * Saving Machines
		 */

		this.machineService.save(machine00);
		this.machineService.save(machine01);
		this.machineService.save(machine02);
		this.machineService.save(machine10);
		this.machineService.save(machine11);
		this.machineService.save(machine12);
		this.machineService.save(machine20);
		this.machineService.save(machine21);

	}

}
