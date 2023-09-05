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
@Entity(name = "credit_alarm")
public class CreditAlarm {
	@Id
	private int alarm_number;
	
	private int cust_number;
	private int credit1;
	private int credit2;
	private int credit3;

}
