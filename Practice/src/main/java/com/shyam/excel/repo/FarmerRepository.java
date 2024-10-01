package com.shyam.excel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.excel.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer,Long> {

}
