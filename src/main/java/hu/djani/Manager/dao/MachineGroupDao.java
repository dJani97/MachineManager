package hu.djani.Manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.MachineGroup;

public interface MachineGroupDao extends JpaRepository<MachineGroup, Integer> {

}
