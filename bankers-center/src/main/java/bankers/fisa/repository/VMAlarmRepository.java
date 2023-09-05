package bankers.fisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.VMAlarm;

@Repository
public interface VMAlarmRepository extends JpaRepository <VMAlarm, Long> {
	
	@Query(value = "SELECT * FROM vm_alarm WHERE vm_number = ?", nativeQuery = true)
	VMAlarm findVMNumber(String vm_number);
	
	@Query(value = "SELECT MAX(alarm_number) FROM vm_alarm;", nativeQuery = true)
	int getMAXNumber();
}
