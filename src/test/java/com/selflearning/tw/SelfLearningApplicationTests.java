package com.selflearning.tw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearning.tw.model.Member;
import com.selflearning.tw.repo.MemberRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@DisplayName("When running Member Test")
class SelfLearningApplicationTests {

	@Autowired
	private MemberRepo memberRepo;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("add member")
	void addMember() throws JsonProcessingException {
		String dateString = "1999-06-14T10:20:09";
		ZonedDateTime HBD = ZonedDateTime.parse(dateString + "+09:00");
//		ZonedDateTime HBD = ZonedDateTime.now();
		Date birthdaty = Date.from(HBD.toInstant());

//		LocalDateTime HBD_L = LocalDateTime.parse(dateString);
		LocalDateTime HBD_L = LocalDateTime.now();

		// LocalDateTime -> ZonedDateTime -> Date
		// 其實 HBD_L.atZone(ZoneId.systemDefault()) 就等於 ZonedDateTime.now()
		Date birthdaty_L = Date.from(HBD_L.atZone(ZoneId.systemDefault()).toInstant());
//		Date birthdaty_L = Date.from(HBD_L.toInstant(ZoneOffset.of("+8")));

		System.out.println(HBD + " / " + birthdaty);
		System.out.println(HBD_L + " / " + birthdaty_L);

		Member member = new Member();
		member.setMember_order(9);
		member.setName("Tzuyu");
		member.setBirthday(birthdaty);
		member.setBirthday_withtimezone(birthdaty);
		System.out.println(member);

		String memberJson = objectMapper.writeValueAsString(member);
		System.out.println(memberJson);
		memberRepo.save(member);
	}

	@Test
	@DisplayName("delete member")
	void deleteMember(){
		memberRepo.deleteById(9);
	}

	@Test
	@DisplayName("query member by id")
	void testMember() throws JsonProcessingException {

		for(int i = 8; i <=8; i++){
			Optional<Member> memberOptional = memberRepo.findById(i);
			if(memberOptional.isPresent()){
				Member member = memberOptional.get();
				String name = member.getName();
				Date birthdaty = member.getBirthday();
				Date birthdaty_withtimezone = member.getBirthday_withtimezone();
				String formatString = String.format("%s - %s / %s", name, birthdaty, birthdaty_withtimezone);
				System.out.println(formatString);

				String memberJson = objectMapper.writeValueAsString(member);
				System.out.println(memberJson);
			}else{
				System.out.println(String.format("Order %s not found in Member table.", i));
			}
		}

	}

	@Test
	void contextLoads() {
	}

}
