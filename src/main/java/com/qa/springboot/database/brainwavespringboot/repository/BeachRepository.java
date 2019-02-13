package com.qa.springboot.database.brainwavespringboot.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.springboot.database.brainwavespringboot.model.Beach;

@Repository
public interface BeachRepository extends JpaRepository<Beach,Long> {
	
	Collection<Beach> findByNameContaining(String name);

}
