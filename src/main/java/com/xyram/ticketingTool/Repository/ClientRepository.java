package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Brand;
import com.xyram.ticketingTool.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	@Query("Select new map(c.Id as id,c.clientName as clientName,c.status as status) from Client c ORDER BY clientName")
	Page<Map> getAllClientList(Pageable pageable);

	@Query("Select c from Client c where Id=:clientId")
	Client getClientById(String clientId);

	/*@Query("Select new map(c.Id as id,c.clientName as clientName,c.status as status) from Client c "
			+ "(:searchString  is null "
			+ "c.clientName LIKE %:searchString% OR "
			+ "status is null OR c.status=:status LIKE %:searchString% )")*/
	
	@Query("select distinct new map(c.Id as id,c.clientName as clientName, c.status as status) from Client c where "
			+ "lower(c.clientName) LIKE %:searchString% ")
	List<Map> serchClient(String searchString);

	@Query("Select c from Client c where c.clientName =:client")
	Client getClient(String client);
	
	@Query("Select c.Id from Client c where c.clientName =:clientName")
	String getClientName(String clientName);
	
	@Query("Select c from Client c where c.clientName =:clientName")
	Client getClientsName(String clientName);
}
