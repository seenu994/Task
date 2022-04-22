package com.xyram.ticketingTool.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xyram.ticketingTool.baseData.model.AuditModel;
import com.xyram.ticketingTool.id.generator.IdGenerator;
import com.xyram.ticketingTool.id.generator.IdPrefix;

@Entity
@Table(name = "notes")
public class Notes extends AuditModel {
	
	@Id
	@IdPrefix(value = "NOTE")
	@GeneratedValue(generator = IdGenerator.ID_GENERATOR)
	@GenericGenerator(name = IdGenerator.ID_GENERATOR, strategy = "com.xyram.ticketingTool.id.generator.IdGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "notes_update_date")
	private Date  notesUploadedDate;
	
	@Column(name = "notes", length = 1000)
	private String notes;

	


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getNotesUploadedDate() {
		return notesUploadedDate;
	}

	public void setNotesUploadedDate(Date notesUploadedDate) {
		this.notesUploadedDate = notesUploadedDate;
	}

	
	
	
	

}
