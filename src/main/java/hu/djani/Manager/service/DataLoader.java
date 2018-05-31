package hu.djani.Manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.bean.MachineGroup;
import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.bean.UserRole;
import hu.djani.Manager.service.entity.MachineGroupService;
import hu.djani.Manager.service.entity.MachineService;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserRoleService;
import hu.djani.Manager.service.entity.UserService;

@Service
public class DataLoader {

	MachineService machineService;
	MachineGroupService groupService;
	ProjectService projectService;
	UserService userService;
	UserRoleService roleService;

	// @Autowired
	// UserDao userDao;

	@Autowired
	public DataLoader(MachineService machineService, MachineGroupService machineGroupService,
			ProjectService projectService, UserService userService, UserRoleService roleService)
			throws InterruptedException {
		this.machineService = machineService;
		this.groupService = machineGroupService;
		this.projectService = projectService;
		this.userService = userService;
		this.roleService = roleService;

		Thread.sleep(5000);
	}

	@PostConstruct
	public void checkDatabase() {

		String rootUserName = "root@root.com";

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (this.machineService.getList().isEmpty()) {
			this.loadData();
		}

		UserRole adminRole = this.roleService.getByName(UserRoleService.ADMIN_ROLE);

		if (adminRole == null) {
			adminRole = new UserRole();
			adminRole.setAuthority(UserRoleService.ADMIN_ROLE);
			this.roleService.save(adminRole);
		}

		User adminUser;
		try {
			adminUser = (User) this.userService.loadUserByUsername(rootUserName);
		} catch (UsernameNotFoundException ex) {
			adminUser = new User();
			adminUser.setAccountNonExpired(true);
			adminUser.setAccountNonLocked(true);
			adminUser.setCredentialsNonExpired(true);
			adminUser.setEnabled(true);
			adminUser.setPassword(new BCryptPasswordEncoder().encode("root"));
			adminUser.setUsername(rootUserName);
			adminUser.setFirstname("János");
			adminUser.setLastname("Dobszai");
			this.userService.save(adminUser);
		}

		if (!adminUser.hasRole(adminRole)) {
			adminUser.addRole(adminRole);
			this.userService.save(adminUser);
		}

		adminUser = (User) this.userService.loadUserByUsername(rootUserName);

		adminRole = this.roleService.getByName(UserRoleService.ADMIN_ROLE);

		System.out.println("admin: " + adminUser);
		System.out.println("role: " + adminRole);

	}

	public void loadData() {
		// @formatter:off


		/*
		 * Creating Projects
		 */

		Project projectNoe = new Project("NOÉ");
		Project projectHuedu = new Project("HUEDU");
		Project projectTeszt = new Project("Teszt");

		/*
		 * Saving Projects
		 */

		this.projectService.save(projectNoe);
		this.projectService.save(projectHuedu);
		this.projectService.save(projectTeszt);

		/*
		 * Creating MachineGroups
		 */

		MachineGroup groupOes = new MachineGroup("OES", projectNoe);
		MachineGroup groupIPrint = new MachineGroup("iPrint", projectNoe);

		MachineGroup groupOpenLab = new MachineGroup("OpenLAB szerverek", projectHuedu);
		MachineGroup groupOpenLabKliens = new MachineGroup("OpenLAB Windows kliensek", projectHuedu);

		MachineGroup groupInternet = new MachineGroup("Internet", projectTeszt);
		MachineGroup groupLocal = new MachineGroup("Lokális gépek", projectTeszt);
		MachineGroup groupTest = new MachineGroup("Teszt", projectTeszt);
		MachineGroup groupTest2 = new MachineGroup("Teszt2", projectTeszt);

		/*
		 * Saving MachineGroups
		 */

		this.groupService.save(groupOes);
		this.groupService.save(groupIPrint);

		this.groupService.save(groupOpenLab);
		this.groupService.save(groupOpenLabKliens);

		this.groupService.save(groupInternet);
		this.groupService.save(groupLocal);
		this.groupService.save(groupTest);
		this.groupService.save(groupTest2);

		/*
		 * Creating Machines
		 */

		Machine machine40 = new Machine("OES kiszolgáló 1", "Fő OES kiszolgáló", "10.11.22.50", new Date(), groupOes);
		Machine machine41 = new Machine("OES kiszolgáló 2", "al-OES kiszolgáló", "10.11.22.51", new Date(), groupOes);

		Machine machine42 = new Machine("iPrint kiszolgáló", "Gergő iPrint szervere", "10.11.16.161", new Date(), groupIPrint);



		Machine machine30 = new Machine("OpenLAB régi", "OpenLAB huedu szerver", "10.11.16.190", new Date(), groupOpenLab);
		Machine machine31 = new Machine("OpenLAB régi (debug)", "A Peti által továbbított ügy kivizsgálására létrehozott OpenLAB szerver", "10.11.22.69", new Date(), groupOpenLab);
		Machine machine32 = new Machine("OpenLAB új", "OpenLAB huedu szerver", "10.11.16.191", new Date(), groupOpenLab);

		Machine machine33 = new Machine("Windows 7", "OpenLAB kliens", "10.11.16.198", new Date(), groupOpenLabKliens);
		Machine machine34 = new Machine("Windows 10", "OpenLAB kliens", "10.11.16.199", new Date(), groupOpenLabKliens);



		Machine machine10 = new Machine("Google DNS", "Google DNS szerver", "8.8.8.8", new Date(), groupInternet);
		Machine machine11 = new Machine("Google Search", "a Google kereső", "www.google.com", new Date(), groupInternet);
		Machine machine12 = new Machine("Facebook", "Facebook közösségi oldal", "www.facebook.com", new Date(), groupInternet);

		Machine machine00 = new Machine("Localhost", "Saját gép", "127.0.0.1", new Date(), groupLocal);
		Machine machine01 = new Machine("build gép", "build szerver", "10.11.16.150", new Date(), groupLocal);

		Machine machine20 = new Machine("hamis cím", "teszt leiras", "10.11.16.161", new Date(), groupTest);
		Machine machine21 = new Machine("Eszméletlenül hosszú cím ami már valószínűleg ki fog lógni elég rendesen",
				"Eszméletlenül hosszú leírás ami már valószínűleg ki fog lógni elég rendesen, de ez még nem volt elég neki ezért még sokkal hosszabbra kell írni hogy egészen biztosan elérjen ennek a sornak a végéig. És akkor most megoldom hogy túllógjon.",
				"10.11.16.189", new Date(), groupTest);


		/*
		 * Saving Machines
		 */

		this.machineService.save(machine00);
		this.machineService.save(machine01);
		this.machineService.save(machine30);
		this.machineService.save(machine10);
		this.machineService.save(machine11);
		this.machineService.save(machine12);
		this.machineService.save(machine20);
		this.machineService.save(machine21);

		this.machineService.save(machine31);
		this.machineService.save(machine32);
		this.machineService.save(machine33);
		this.machineService.save(machine34);

		this.machineService.save(machine40);
		this.machineService.save(machine41);
		this.machineService.save(machine42);

	}

}
