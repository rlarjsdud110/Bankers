package bankers.fisa.entity;

import java.sql.Date;

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
@Entity(name = "cust")
public class Cust {
	@Id
	private int cust_number;
	
	private String cust_name; 
	private String cust_display_name;
	private String cust_address;
	private String cust_email;
	private String cust_ceo_phone_number;
	private String cust_phone_number;
	
	private int cust_max_credit;
	private int cust_credit;
	private int cust_total_credit;
	
	private String submit_date;

	@Override
	public String toString() {
		return cust_number + "_" + cust_name + "_"
				+ cust_display_name + "_" + cust_address + "_" + cust_email
				+ "_" + cust_ceo_phone_number + "_" + cust_phone_number
				+ "_" + cust_max_credit + "_" + cust_credit + "_"
				+ cust_total_credit + "_" + submit_date;
	}
}
