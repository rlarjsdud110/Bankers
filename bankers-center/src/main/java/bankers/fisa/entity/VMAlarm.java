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
@Entity(name = "vm_alarm")
public class VMAlarm {
    @Id
    private int alarm_number;

    private int vm_number;
    private int cpu1;
    private int cpu2;
    private int ram1;
    private int ram2;
    private int storage1;
    private int storage2;
    
	@Override
	public String toString() {
		return alarm_number + "_" + vm_number + "_" + cpu1 + "_" + cpu2 + "_" + ram1 + "_" + ram2 + "_" + storage1 + "_" + storage2;
	}
}
