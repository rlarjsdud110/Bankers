package bankers.fisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.BankersEmp;

@Repository
public interface BankersEmpRepository extends JpaRepository<BankersEmp, Long> {
	@Query(value = "SELECT * FROM bankers_emp WHERE bemp_id=?", nativeQuery = true)
	BankersEmp findbyBankersBempId(String bemp_id);
}
