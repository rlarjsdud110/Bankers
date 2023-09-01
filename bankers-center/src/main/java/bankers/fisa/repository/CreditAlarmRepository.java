package bankers.fisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.CreditAlarm;

@Repository
public interface CreditAlarmRepository extends JpaRepository<CreditAlarm, Long> {

}
