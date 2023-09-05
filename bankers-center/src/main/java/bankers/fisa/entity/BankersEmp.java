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
@Entity(name = "bankers_emp")
public class BankersEmp {
	@Id
	private int bemp_number;
	
	private String bemp_phone_number;
	private String bemp_name;
	private String bemp_id;
	private String bemp_pw;
	private String bemp_pos;
}
