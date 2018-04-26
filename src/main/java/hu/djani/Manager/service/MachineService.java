package hu.djani.Manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.dao.MachineDao;

@Service
public class MachineService {

	@Autowired
	MachineDao dao;

	@Autowired
	public MachineService(MachineDao machineDao) {
		this.dao = machineDao;
	}

	public List<Machine> getList() {
		return this.dao.findAll();
	}

	public Machine getById(Integer id) {
		return this.dao.getOne(id);
	}

	public void save(Machine machine) {
		this.dao.save(machine);
	}
}