package com.xyram.ticketingTool.ticket.config;

import org.springframework.stereotype.Component;

@Component
public class PermissionConfig {
	
	
	private Integer PROJ_ADD = 1;
	private Integer PROJ_EDIT = 2;
	private Integer PROJ_VIEW = 3;
	private Integer PROJ_STATUS_EDIT = 4;
	
	private Integer EMP_ADD = 5;
	private Integer EMP_EDIT = 6;
	private Integer EMP_VIEW = 7;
	private Integer EMP_STATUS_EDIT = 8;
	
	private Integer TCKT_ADD = 9;
	private Integer TCKT_EDIT = 10;
	private Integer TCKT_VIEW = 11;
	private Integer TCKT_STATUS_EDIT = 12;
	
	private Integer JBOP_ADD = 13;
	private Integer JBOP_EDIT = 14;
	private Integer JBOP_VIEW = 15;
	private Integer JBOP_STATUS_EDIT = 16;
	
	private Integer JBAPLC_ADD = 17;
	private Integer JBAPLC_EDIT = 18;
	private Integer JBAPLC_VIEW = 19;
	private Integer JBAPLC_STATUS_EDIT = 20;
	
	private Integer JBINTV_ADD = 21;
	private Integer JBINTV_EDIT = 22;
	private Integer JBINTV_VIEW = 23;
	private Integer JBINTV_STATUS_EDIT = 24;
	
	private Integer JBOFR_ADD = 25;
	private Integer JBOFR_EDIT = 26;
	private Integer JBOFR_VIEW = 27;
	private Integer JBOFR_STATUS_EDIT = 28;
	
	private Integer JBVND_ADD = 29;
	private Integer JBVND_EDIT = 30;
	private Integer JBVND_VIEW = 31;
	private Integer JBVND_STATUS_EDIT = 32;
	
	private Integer access = 0;
	
	public PermissionConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public PermissionConfig(Integer access) {
		// TODO Auto-generated constructor stub
		this.access = access;
	}
	
	public void addPermissions(Integer permission[]) {
		// TODO Auto-generated constructor stub
		for(int i=0;i <permission.length;i++)
			this.access = this.access | permission[i];
	}
	
	public void removePermissions(Integer permission[]) {
		// TODO Auto-generated constructor stub
		for(int i=0;i <permission.length;i++)
			this.access = this.access ^ permission[i];
	}
	
	public Boolean hasPermission(Integer permission) {
		// TODO Auto-generated constructor stub
		return (this.access & permission) == permission;
	}
	
	public Integer setDefaultPermissions(String role) {
		
		Integer access = 0;
		switch(role) {
			case "SUPER_ADMIN":
				access =  
				(
					   PROJ_ADD | PROJ_EDIT | PROJ_VIEW | PROJ_STATUS_EDIT
					 | EMP_ADD | EMP_EDIT | EMP_VIEW | EMP_STATUS_EDIT
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_ADD | JBOP_EDIT | JBOP_VIEW | JBOP_STATUS_EDIT
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_ADD | JBINTV_EDIT | JBINTV_VIEW | JBINTV_STATUS_EDIT 
					 | JBOFR_ADD | JBOFR_EDIT | JBOFR_VIEW | JBOFR_STATUS_EDIT 
					 | JBVND_ADD | JBVND_EDIT | JBVND_VIEW | JBVND_STATUS_EDIT   
				);
			break;
			case "TICKETINGTOOL_ADMIN":
				access =  
				(
					   PROJ_ADD | PROJ_EDIT | PROJ_VIEW | PROJ_STATUS_EDIT
					 | EMP_ADD | EMP_EDIT | EMP_VIEW | EMP_STATUS_EDIT
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_VIEW 
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_VIEW 
				);
			break;
			case "INFRA":
				access =  
				(
					   PROJ_VIEW
					 | EMP_VIEW
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_VIEW
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_VIEW
				);
			break;
			case "HR_ADMIN":
				access =  
				(
					   PROJ_VIEW
					 | EMP_VIEW
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_ADD | JBOP_EDIT | JBOP_VIEW | JBOP_STATUS_EDIT
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_ADD | JBINTV_EDIT | JBINTV_VIEW | JBINTV_STATUS_EDIT 
					 | JBOFR_ADD | JBOFR_EDIT | JBOFR_VIEW | JBOFR_STATUS_EDIT 
					 | JBVND_ADD | JBVND_EDIT | JBVND_VIEW | JBVND_STATUS_EDIT
				);
			break;
			case "HR":
				access =  
				(
					   PROJ_VIEW
					 | EMP_VIEW
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_VIEW
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_VIEW | JBINTV_EDIT | JBINTV_VIEW 
				);
			break;
			case "DEVELOPER":
				access =  
				(
					   PROJ_VIEW
					 | EMP_VIEW
					 | TCKT_ADD | TCKT_EDIT | TCKT_VIEW | TCKT_STATUS_EDIT 
					 | JBOP_VIEW
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW | JBAPLC_STATUS_EDIT
					 | JBINTV_VIEW | JBINTV_STATUS_EDIT   
				);
			break;
			case "JOB_VENDOR":
				access =  
				(
					   JBOP_VIEW
					 | JBAPLC_ADD | JBAPLC_EDIT | JBAPLC_VIEW
					 | JBINTV_VIEW
					 | JBOFR_VIEW
				);
			break;
		}
		
		return access;
	}
	
	

}
