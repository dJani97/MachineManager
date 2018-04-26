package hu.djani.Manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.Machine;

public interface MachineDao extends JpaRepository<Machine, Integer> {

}
