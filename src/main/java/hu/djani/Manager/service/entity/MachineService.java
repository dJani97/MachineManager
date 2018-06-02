package hu.djani.Manager.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.dao.MachineDao;

@Service
public class MachineService {

	@Autowired
	MachineDao dao;

	public List<Machine> getList() {
		return this.dao.findAll();
	}

	public Machine getById(Integer id) {
		return this.dao.getOne(id);
	}

	public void save(Machine machine) {
		this.dao.save(machine);
	}

	public Long count() {
		return this.dao.count();
	}
}
