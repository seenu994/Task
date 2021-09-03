package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}