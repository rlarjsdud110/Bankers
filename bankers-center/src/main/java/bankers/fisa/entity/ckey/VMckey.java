package bankers.fisa.entity.ckey;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@SuppressWarnings("serial")
public class VMckey implements Serializable{
	private int vm_number;
	private String vm_create_date;
	@Override
	public String toString() {
		return vm_number + "_" + vm_create_date;
	}
	public VMckey(VMckey vmckey) {
		vm_number = vmckey.getVm_number();
		vm_create_date = vmckey.getVm_create_date();
	}
}
