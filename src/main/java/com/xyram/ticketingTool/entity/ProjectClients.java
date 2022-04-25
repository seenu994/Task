package com.xyram.ticketingTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.enumType.ClientStatus;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "project_clients")
public class ProjectClients 
{
    @Id
    @IdPrefix(value = "AI")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name ="client_name")
    private String clientName;
    
    //@Enumerated(EnumType.STRING)
   // @Column(name = "client_status")
    //private ProjectClientStatus ClientsStatus = ClientStatus.ACTIVE;
    
}
