package com.tenco.web.utis;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static String timestampFormat(Timestamp startDate) {
        // board 엔티티에 선언된 Timestamp를 date 객체로 변환
        // getTime() 메서드를 호출해서 밀리초 단위로 시간을 받아 --> Date 객체 생성
        Date currentDate = new Date(startDate.getTime());

        // 아파치 commons 라이브러리
        return DateFormatUtils.format(currentDate, "yyyy년 MM월 dd일 HH시 mm분");
    }

    public static String dateTimeFormat(Timestamp endDate) {
        if (endDate == null) {
            return "";
        }

        LocalDateTime localDateTime = endDate.toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
