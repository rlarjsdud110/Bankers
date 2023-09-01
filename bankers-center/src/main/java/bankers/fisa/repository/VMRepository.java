package bankers.fisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.VM;
import bankers.fisa.entity.ckey.VMckey;

@Repository
public interface VMRepository extends JpaRepository<VM, VMckey>{
	
	@Query(value = "SELECT t.* FROM vm AS t JOIN(SELECT vm_number, MAX(vm_create_date) AS date FROM vm GROUP BY vm_number) AS temp ON t.vm_number=temp.vm_number AND t.vm_create_date = temp.date", nativeQuery = true)
	List<VM> findAllLatestVM();
	
	@Query(value = "SELECT t.* FROM vm AS t JOIN(SELECT vm_number, MAX(vm_create_date) AS date FROM vm GROUP BY vm_number) AS temp ON t.vm_number=temp.vm_number AND t.vm_create_date = temp.date WHERE t.vm_number = ?", nativeQuery = true)
	VM findLatestVM(String vm_number);
	
	@Query(value = "SELECT MAX(vm_number) FROM vm", nativeQuery = true)
	int getMAXNumber();
}
