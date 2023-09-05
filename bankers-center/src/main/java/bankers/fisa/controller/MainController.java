package bankers.fisa.controller;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import bankers.fisa.entity.BankersEmp;
import bankers.fisa.entity.Cust;
import bankers.fisa.entity.CustEmp;
import bankers.fisa.entity.VM;
import bankers.fisa.entity.VMAlarm;
import bankers.fisa.entity.ckey.VMckey;
import bankers.fisa.repository.BankersEmpRepository;
import bankers.fisa.repository.CustEmpRepository;
import bankers.fisa.repository.CustRepository;
import bankers.fisa.repository.VMAlarmRepository;
import bankers.fisa.repository.VMRepository;

@RestController
@RequestMapping("/center")
public class MainController {

	@Autowired
	private final CustEmpRepository custEmpRepository = null;
	@Autowired
	private final CustRepository custRepository = null;
	@Autowired
	private final BankersEmpRepository bankersEmpRepository = null;
	@Autowired
	private final VMRepository vmRepository = null;
	@Autowired
	private final VMAlarmRepository vmAlarmRepository = null;

	@PostMapping("/vmmonitoringlist")
	public String vmmonitoringlist(@RequestParam("id") String id) {

		List<VM> vmlist = vmRepository.findAllLatestVM();
		String result = new String();

		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8867").path("/getvmaddress").encode().build()
				.toUri();

		for (VM vm : vmlist) {
			if (vm.getVm_state().equals("ON")) {

				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
				parameters.add("vmId", "" + vm.getVmckey().getVm_number());

				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, parameters, String.class);

				String cpuload = null;
				String ramtotal = null;
				String ramfree = null;
				String storagetotal = null;
				String storagefree = null;

				String address = responseEntity.getBody().toString();
				if (address.equals("preparing")) {
					result += vm.getVm_name() + "_PREPARING,";
					continue;
				}
				cpuload = getvmspec(address, "process.cpu.load");
				if (cpuload.equals("monitoring tool off")) {
					result += vm.getVm_name() + "_TOOL_OFF,";
					continue;
				}
				ramtotal = getvmspec(address, "process.ram.total");
				ramfree = getvmspec(address, "process.ram.free");
				storagetotal = getvmspec(address, "process.storage.total");
				storagefree = getvmspec(address, "process.storage.free");

				result += vm.getVm_name() + "_" + vm.getVmckey().getVm_number() + "_" + cpuload + "_" + ramtotal + "_"
						+ ramfree + "_" + storagetotal + "_" + storagefree + ",";
			} else if (vm.getVm_state().equals("OFF")) {
				result += vm.getVm_name() + "_OFF_OFF_OFF,";
			}
		}
		return result;
	}

	public String getvmspec(String address, String request) {
		URI uri = UriComponentsBuilder.fromUriString("http://" + address + ":6606").path("/actuator/metrics/" + request)
				.encode().build().toUri();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.getForEntity(uri, String.class);
		} catch (Exception e) {
			return "monitoring tool off";
		}

		String load = responseEntity.getBody().toString().split(",")[4].split(":")[1];
		return load.substring(0, load.length() - 2);
	}

	@PostMapping("/vmdelete")
	public void vmdelete(@RequestParam("vmnumber") String vmnumber) {
		VM vm = new VM(vmRepository.findLatestVM(vmnumber));

		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());
		VMckey vmckey = new VMckey(Integer.parseInt(vmnumber), date);

		vm.setVmckey(vmckey);
		vm.setVm_state("DEL");

		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8867").path("/vmdelete").encode().build()
				.toUri();

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("vmId", "" + vm.getVmckey().getVm_number());

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(uri, parameters, String.class);

		vmRepository.save(vm);
	}

	@PostMapping("/getvmalarm")
	public String getvmalarm(@RequestParam("vmnumber") String vmnumber) {
		return vmAlarmRepository.findVMNumber(vmnumber).toString();
	}

	@PostMapping("/getvm")
	public String getvm(@RequestParam("vmnumber") String vmnumber) {
		return vmRepository.findLatestVM(vmnumber).toString();
	}

	@PostMapping("/vmupdate")
	public void vmupdate(@RequestParam("vmnumber") String vmnumber, @RequestParam("vmname") String vmname,
			@RequestParam("catalType") String catalType, @RequestParam("state") String state,
			@RequestParam("cpu1") String cpu1, @RequestParam("cpu2") String cpu2, @RequestParam("ram1") String ram1,
			@RequestParam("ram2") String ram2, @RequestParam("storage1") String storage1,
			@RequestParam("storage2") String storage2) {

		if (catalType.equals("C") || catalType.equals("D") || catalType.equals("E")) {
			return;
		}

		try {
			VM vm = new VM(vmRepository.findLatestVM(vmnumber));

			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String date = dateFormat.format(new Date());
			VMckey vmckey = new VMckey(Integer.parseInt(vmnumber), date);

			vm.setVmckey(vmckey);

			if (!vm.getVm_name().equals(vmname) || !vm.getVm_catal_type().equals(catalType) || !state.equals("NONE")) {
				vm.setVm_name(vmname);
				vm.setVm_catal_type(catalType);
				if (!state.equals("NONE")) {
					vm.setVm_state(state);
				}

				URI uri = UriComponentsBuilder.fromUriString("http://localhost:8867").path("/vmpower").encode().build()
						.toUri();

				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
				parameters.add("vmId", "" + vm.getVmckey().getVm_number());
				parameters.add("state", state);

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.postForEntity(uri, parameters, String.class);

				vmRepository.save(vm);
			}

			VMAlarm vmalarm = vmAlarmRepository.findVMNumber(vmnumber);

			vmalarm.setCpu1(Integer.parseInt(cpu1));
			vmalarm.setCpu2(Integer.parseInt(cpu2));
			vmalarm.setRam1(Integer.parseInt(ram1));
			vmalarm.setRam2(Integer.parseInt(ram2));
			vmalarm.setStorage1(Integer.parseInt(storage1));
			vmalarm.setStorage2(Integer.parseInt(storage2));

			vmAlarmRepository.save(vmalarm);
		} catch (Exception e) {

		}
	}

	@PostMapping("/vmcreate")
	public String vmcreate(@RequestParam("vmname") String vmname, @RequestParam("catalType") String catalType,
			@RequestParam("creater") String creater) {

		if (catalType.equals("C") || catalType.equals("D") || catalType.equals("E")) {
			return "false";
		}

		try {
			int custEmpNumber = custEmpRepository.findById(creater).get().getCust_emp_number();

			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String date = dateFormat.format(new Date());

			URI uri = UriComponentsBuilder.fromUriString("http://localhost:8867").path("/createVM").encode().build()
					.toUri();

			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
			parameters.add("vmname", vmname + ":" + ((int) (Math.random() * 1000)));
			parameters.add("catalType", catalType);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, parameters, String.class);

			String responseNumber = responseEntity.getBody().toString().split("-")[1];
			int vmnumber = Integer.parseInt(responseNumber.substring(0, responseNumber.length() - 1));

			VMckey vmckey = new VMckey(vmnumber, date);
			VM newVM = new VM(vmckey, vmname, catalType, "preparing", "OFF", custEmpNumber);

			vmRepository.save(newVM);
			vmAlarmRepository.save(new VMAlarm(vmAlarmRepository.getMAXNumber() + 1, vmnumber, 50, 90, 50, 90, 50, 90));

			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	@PostMapping("/custemp")
	public String custEmp(@RequestParam("custEmpNumber") int custEmpNumber) {
		try {
			return custEmpRepository.findbyCustEmpNumber(custEmpNumber).toString();
		} catch (Exception e) {
			return "false";
		}
	}

	@PostMapping("/bankersemp")
	public String bankersEmp() {
		System.out.println("-------센터 접속");

		String result = new String();

		for (BankersEmp bankersEmp : bankersEmpRepository.findAll()) {
			result += bankersEmp.getBemp_number() + "_";
			result += bankersEmp.getBemp_name() + "_";
			result += bankersEmp.getBemp_phone_number() + "_";
			result += bankersEmp.getBemp_id() + "_";
			result += bankersEmp.getBemp_pw() + "_";
			result += bankersEmp.getBemp_pos() + "/";
		}

		return result;
	}

	@PostMapping("/vmlist")
	public String vmlist(@RequestParam("id") String id) {
		List<VM> vmlist = vmRepository.findAllLatestVM();
		String result = new String();
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8867").path("/getvmaddress").encode().build()
				.toUri();

		for (VM vm : vmlist) {
			if (vm.getVm_state().equals("ON") || vm.getVm_state().equals("OFF")) {
				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
				parameters.add("vmId", "" + vm.getVmckey().getVm_number());

				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, parameters, String.class);
				if (!(vm.getVm_address()).equals(responseEntity.getBody().toString())) {
					vm.setVm_address(responseEntity.getBody().toString());
					vmRepository.save(vm);
				}
				result += vm.toString() + ",";
			}
		}
		return result;
	}

	@PostMapping("/login")
	public String loginPage(@RequestParam("loginID") String id, @RequestParam("loginPW") String pw) {
		if (login(id, pw)) {
			return "true";
		} else {
			return "false";
		}
	}

	@PostMapping("/salesLogin")
	public String salesLogin(@RequestParam("loginID") String id, @RequestParam("loginPW") String pw) {
		if (salesLoginPage(id, pw)) {
			return "true";
		} else {
			return "false";
		}
	}

	private boolean login(String id, String pw) {
		try {
			CustEmp player = custEmpRepository.findById(id).get();
			if (pw.equals(player.getCust_pw())) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean salesLoginPage(String id, String pw) {
		try {
			BankersEmp player = bankersEmpRepository.findbyBankersBempId(id);
			if (pw.equals(player.getBemp_pw())) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/createCust")
	private boolean createCust(String companyName, String companyAddress, String companyNumber, String phoneNumber,
			String company, String credits, String companyEmail) {

		int credit = Integer.parseInt(credits);
		try {

			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
			String date = dateFormat.format(new Date());

			Cust cust = new Cust(custRepository.getMAXNumber() + 1, companyName, company, companyAddress, companyEmail,
					phoneNumber, companyNumber, credit, credit, credit, date);
			custRepository.save(cust);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/getcustmaxnumber")
	private String getCustMaxNumber() {
		int maxNumber = custEmpRepository.getMAXNumber();
		String maxEmpNumberStr = String.valueOf(maxNumber);
		return maxEmpNumberStr;

	}

	@PostMapping("/tableCust")
	private boolean tableCust(String tableJob, String tableID, String tablePW, String tableEmail,
			String maxEmpNumberStr) {
		int maxEmpNumberInt = Integer.parseInt(maxEmpNumberStr);
		int custEmpNum = custEmpRepository.getMaxCustEmpNum(); // 현재 db에서 가장 큰 custEmpNum 값
		try {
			CustEmp custEmp = new CustEmp();
			custEmp.setCust_email(tableEmail);
			custEmp.setCust_id(tableID);
			custEmp.setCust_pw(tablePW);
			custEmp.setCust_pos(tableJob);
			custEmp.setCust_number(maxEmpNumberInt);
			custEmp.setCust_emp_number(custEmpNum + 1); // 1씩 더해줘서 db에 반영
			custEmpRepository.save(custEmp);
			System.out.println(custEmp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/retaincustomer")
	private String retaincustomer(@RequestParam("companyName") String companyName) {
		System.out.println("dasdassdasds");
		if (custRepository.findCustDisplayName(companyName).equals(companyName)) {
			int custnum = custRepository.findCustNumberByCustDisplayName(companyName);
			List<CustEmp> custlist = custEmpRepository.findCustEmpsByCustNumber(custnum);
			String result = new String();

			for (CustEmp custemp : custlist) {
				result += custemp.toString() + ",";
			}

			return result;
		} else {
			return null;
		}
	}

	@PostMapping("/custInfo")
	private String custInfo(@RequestParam("companyName") String companyName) {
		System.out.println("custInfo 접속완료");
		if (custRepository.findCustDisplayName(companyName).equals(companyName)) {
			List<Cust> custList = custRepository.findByCustAll(companyName);

			return custList.get(0).toString();
		}
		return null;
	}
}
