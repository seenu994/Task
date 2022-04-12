package com.xyram.ticketingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xyram.ticketingTool.entity.TicketAttachment;

public interface ticketAttachmentRepository extends JpaRepository<TicketAttachment, String> {

	//TicketAttachment delete(Integer ticketId);

 @Modifying
	@Query("Delete  From TicketAttachment  WHERE id = :ticketId")
	
	 void  deletByTicket(String ticketId);

	//void deleteImage(MultipartFile file, Ticket ticketId);

}
