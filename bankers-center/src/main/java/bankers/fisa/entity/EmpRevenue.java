package bankers.fisa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "emp_revenue")
public class EmpRevenue {
	@Id
	private int erev_number;
	
	private int bemp_number;
	private int cust_number;

}
