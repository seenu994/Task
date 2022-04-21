package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, String> {

	@Query("Select n from Notes n where date(n.notesUploadedDate) =date(:paramDate) AND n.createdBy=:scopeId")
	Notes getNotes(Date paramDate, String scopeId);

	@Query("Select n from Notes n where n.id =:id")
	Notes getNotesByID(String id);

	@Query("Select n from Notes n where n.createdBy=:scopeId")
	Notes getNotesByScopeId(String scopeId);

	@Query("Select new map(n.id as id, n.notes as notes,e.firstName as name,n.notesUploadedDate as date)"
			+ " from Notes n left join Employee e on n.createdBy=e.id  where "
			+ "(:fromDate is null OR Date(n.notesUploadedDate) >= STR_TO_DATE(:fromDate, '%Y-%m-%d')) AND "
			+ "(:toDate is null OR Date(n.notesUploadedDate) <= STR_TO_DATE(:toDate, '%Y-%m-%d')) AND "
			+ "(:searchString is null OR n.notes LIKE %:searchString%) AND "
			+ " n.createdBy=:scopeId")
	Page<Map> getAllNotes(String searchString, String fromDate, String toDate, Pageable pageable, String scopeId);

	@Modifying
	@Query("Delete from Notes n where date(n.notesUploadedDate) =date(:paramDate) AND n.createdBy=:scopeId ")
	void deleteNotes(Date paramDate, String scopeId);

}