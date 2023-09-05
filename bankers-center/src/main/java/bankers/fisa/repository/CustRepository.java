package bankers.fisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.Cust;
import bankers.fisa.entity.CustEmp;

@Repository
public interface CustRepository extends JpaRepository<Cust, Long> {
	@Query(value = "SELECT MAX(cust_number) FROM cust;", nativeQuery = true)
	int getMAXNumber();
	
	@Query(value = "SELECT MAX(cust_number) FROM cust;", nativeQuery = true)
	Cust findByCustDisplayName(String custDisplayName);
	
	@Query(value = "SELECT MAX(cust_number) FROM cust;", nativeQuery = true)
	Cust findByCust_number(int custNumber);
	
	@Query(value = "SELECT cust_display_name FROM cust WHERE cust_display_name = :custDisplayName", nativeQuery = true)
	String findCustDisplayName(@Param("custDisplayName") String custDisplayName);
	
	@Query(value = "SELECT * FROM cust WHERE cust_display_name = :custDisplayName", nativeQuery = true)
	String findAllCust(@Param("custDisplayName") String custDisplayName);
	
	@Query(value = "SELECT cust_number FROM cust WHERE cust_display_name = :custDisplayName", nativeQuery = true)
	int findCustNumberByCustDisplayName(@Param("custDisplayName") String custDisplayName);
	
	@Query(value = "SELECT * FROM cust WHERE cust_display_name = :custDisplayName", nativeQuery = true)
    List<Cust> findByCustAll(@Param("custDisplayName") String custDisplayName);
	

}
