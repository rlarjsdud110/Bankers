package bankers.fisa.message;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail() {
		//수신 대상을 담을 ArrayList 생성
		ArrayList<String> toUserList = new ArrayList<>();
		//수신 대상 추가
		toUserList.add("dongwook1110@naver.com");
		//수신 대상 개수
		int toUserSize = toUserList.size();
		//simpleMailMessage (단순 텍스트 구성 메일메세지 생성할 때 이용한다고 함)
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		//수신자 설정
		simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));
		//메일 제목
		simpleMessage.setSubject("Subject Sample");
		//메일 내용
		simpleMessage.setText("Text Sample");
		//메일 발송
		javaMailSender.send(simpleMessage);
		
	}

}
