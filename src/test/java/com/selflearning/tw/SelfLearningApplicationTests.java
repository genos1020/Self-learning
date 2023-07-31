package com.selflearning.tw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearning.tw.model.Member;
import com.selflearning.tw.repo.MemberRepo;
import com.selflearning.tw.vo.Album;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@SpringBootTest
@DisplayName("When running Member Test")
class SelfLearningApplicationTests {

    @Autowired
    private MemberRepo memberRepo;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("add member")
    void addMember() throws JsonProcessingException {
//		String dateString = "1999-06-14T10:20:09";
//		ZonedDateTime HBD = ZonedDateTime.parse(dateString + "+09:00");
        ZonedDateTime HBD = ZonedDateTime.now();
        Date birthdayWithoutTimezone = Date.from(HBD.toInstant());

//		LocalDateTime HBD_L = LocalDateTime.parse(dateString);
        LocalDateTime HBD_L = LocalDateTime.now();

        // LocalDateTime -> ZonedDateTime -> Date
        // 其實 HBD_L.atZone(ZoneId.systemDefault()) 就等於 ZonedDateTime.now()
        Date birthday_L = Date.from(HBD_L.atZone(ZoneId.systemDefault()).toInstant());
//		Date birthday_L = Date.from(HBD_L.toInstant(ZoneOffset.of("+8")));

        System.out.println(HBD + " / " + birthdayWithoutTimezone);
        System.out.println(HBD_L + " / " + birthday_L);

        Member member = new Member();
        member.setMember_order(22);
        member.setName("T22");
        member.setBirthday(birthday_L);
        member.setBirthday_withtimezone(birthday_L);
        System.out.println(member);

        String memberJson = objectMapper.writeValueAsString(member);
        System.out.println(memberJson);
        memberRepo.save(member);
    }

    @Test
    @DisplayName("delete member")
    void deleteMember() {
        memberRepo.deleteById(9);
    }

    @Test
    @DisplayName("query member by id")
    void testMember() throws JsonProcessingException {

        for (int i = 21; i <= 21; i++) {
            Optional<Member> memberOptional = memberRepo.findById(i);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                String name = member.getName();
                Date birthday = member.getBirthday();
                Date birthday_withtimezone = member.getBirthday_withtimezone();
                String formatString = String.format("%s - %s / %s", name, birthday, birthday_withtimezone);
                System.out.println(formatString);

                String memberJson = objectMapper.writeValueAsString(member);
                System.out.println(memberJson);
            } else {
                System.out.println(String.format("Order %s not found in Member table.", i));
            }
        }

    }

    @Test
    void contextLoads() {
        // A5 02 [33 32 31 30] 0D => e.g. 0123個車位
        Integer member_car = 789; // 789 => 39 38 37 30
        Integer temp_car = 9; // 6789 => 39 38 37 36
        Integer temp_moto = 23; // 234 => 34 33 32 30
        char[] member_car_char = member_car.toString().toCharArray(); // [1,2,3] / [1,2,3,4]
        char[] temp_car_char = temp_car.toString().toCharArray();
        char[] temp_moto_char = temp_moto.toString().toCharArray();

        String member_car_888 = transform(member_car);
        String temp_car_888 = transform(temp_car);
        String temp_moto_888 = transform(temp_moto);
        System.out.println("月租汽車剩餘車位數 = " + member_car + " => " + member_car_888);
        System.out.println("臨停汽車剩餘車位數 = " + temp_car + " => " + temp_car_888);
        System.out.println("臨停機車剩餘車位數 = " + temp_moto + " => " + temp_moto_888);
    }


    private String transform(Integer remainNum) {
        String result = new StringBuilder(String.valueOf(remainNum)).reverse().toString();
        switch (result.length()) {
            case 3:
                result += "0";
                break;
            case 2:
                result += "00";
                break;
            case 1:
                result += "000";
                break;
        }

        return result;
    }

    @Test
    void stringLength(){
        Album a = null;
        System.out.println("#1: " + a);
        a = new Album();
        System.out.println("#2: " + a);
        System.out.println("#3: " + a.getNumber());

        Map<String, Album> map = new HashMap<>();
        System.out.println("#4: " + map);
        System.out.println("#5: " + map.get("1"));
        map.put("k", new Album());
        System.out.println("#6: " + map);
        System.out.println("#7: " + map.get("k"));

        Map<String, List<String>> ml = new HashMap<>();
        System.out.println(ml);
        System.out.println(ml.get("m"));

        Queue<Map<Integer, SseEmitter>> asyncs = new ConcurrentLinkedQueue<>();
        Map<String, Queue<Map<Integer, SseEmitter>>> mq = new HashMap<>();
        mq.put("tt", asyncs);

        Queue<Map<Integer, SseEmitter>> queue
                = (Queue<Map<Integer, SseEmitter>>) mq.get("tt");
        System.out.println(queue);
    }

}
