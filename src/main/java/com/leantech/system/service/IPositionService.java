package com.leantech.system.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leantech.system.entity.Position;

public interface IPositionService extends JpaRepository<Position, Long>{
	
	public Position findByName(String name);

}
