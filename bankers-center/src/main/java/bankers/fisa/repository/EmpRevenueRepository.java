package bankers.fisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bankers.fisa.entity.EmpRevenue;

@Repository
public interface EmpRevenueRepository extends JpaRepository<EmpRevenue, Long> {

}
