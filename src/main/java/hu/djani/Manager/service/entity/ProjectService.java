package hu.djani.Manager.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.dao.ProjectDao;

@Service
public class ProjectService {

	@Autowired
	ProjectDao dao;

	public List<Project> getList() {
		return this.dao.findAll();
	}

	public Project getById(Integer id) {
		return this.dao.getOne(id);
	}

	public void save(Project group) {
		this.dao.save(group);
	}
}
