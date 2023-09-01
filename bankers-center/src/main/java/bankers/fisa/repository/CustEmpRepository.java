package bankers.fisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.CustEmp;

@Repository
public interface CustEmpRepository extends JpaRepository<CustEmp, String> {
	@Query(value = "SELECT * FROM cust_emp WHERE cust_emp_number=?", nativeQuery = true)
	CustEmp findbyCustEmpNumber(int cust_emp_number);
	
	@Query(value = "SELECT MAX(cust_number) FROM cust_emp;", nativeQuery = true)
	int getMAXNumber();
	
	@Query(value = "SELECT MAX(cust_emp_number) FROM cust_emp;", nativeQuery = true)
	int getMaxCustEmpNum();
	
	@Query(value = "SELECT * FROM cust_emp WHERE cust_number=?", nativeQuery = true)
	List<CustEmp> findCustEmpsByCustNumber(@Param("cust_number") int custNumber);
}
