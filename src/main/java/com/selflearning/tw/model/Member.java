package com.selflearning.tw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
//    @Column(name = "member_order", columnDefinition = "int2", nullable = false)
    private Integer member_order;

    @Column(nullable = false)
    private String name;

    //@JsonFormat 輸出到前端轉為JSON時才看得出效果
    /*
    ### 如果是用 ZonedDateTime 存 ###
    timezone 要填的是該資料的時區，而非本地時區。
    e.g.
        在日本(UTC+9) 10點20分按下儲存一筆資料，存到了位在台灣(UTC+8) 的資料庫，資料庫(withOutTimezone)會存為9點20分，
        因此在輸出為json的時候需要標明該筆資料是來自哪個時區的以便計算之後正確顯示。

    ### 如果是用 LocalDateTime 存 ###
    timezone 要填本地時區。

    ##### 如果資料庫中的欄位資料型態是timestamp without time zone 就沒差 #####
    * */
//    @Column(name ="birthday")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9") // 如果是用 ZonedDateTime 存
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 如果是用 LocalDateTime 存
    private Date birthday;

//    @Column(name = "birthday_withtimezone")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9") // 如果是用 ZonedDateTime 存
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 如果是用 LocalDateTime 存
    private Date birthday_withtimezone;

}

/*
*
* Java                  Type	JPA	Hibernate	JDBC Type
java.time.LocalDate	       x	x	            DATE
java.time.LocalTime	       x	x	            TIME
java.time.LocalDateTime	   x	x	            TIMESTAMP
java.time.OffsetTime	   x	x	            TIME_WITH_TIMEZONE
java.time.OffsetDateTime   x	x	            TIMESTAMP_WITH_TIMEZONE
java.time.Duration	       –	x	            BIGINT
java.time.Instant	       –	x	            TIMESTAMP
java.time.ZonedDateTime	   –	x	            TIMESTAMP
*
*
* */