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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cust_emp")
public class CustEmp {
	private int cust_emp_number;
	private int cust_number;
	private String cust_email;
	
	@Id
	private String cust_id;
	private String cust_pw;
	private String cust_pos;
	
	@Override
	public String toString() {
		return cust_emp_number + "_" + cust_number + "_"
				+ cust_email + "_" + cust_id + "_" + cust_pw + "_" + cust_pos;
	}
}
