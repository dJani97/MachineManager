package hu.djani.Manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.Project;

public interface ProjectDao extends JpaRepository<Project, Integer> {

}
