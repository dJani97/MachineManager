package hu.djani.Manager.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.MachineGroup;
import hu.djani.Manager.dao.MachineGroupDao;

@Service
public class MachineGroupService {

	@Autowired
	MachineGroupDao dao;

	public List<MachineGroup> getList() {
		return this.dao.findAll();
	}

	public MachineGroup getById(Integer id) {
		return this.dao.getOne(id);
	}

	public void save(MachineGroup group) {
		this.dao.save(group);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
}
