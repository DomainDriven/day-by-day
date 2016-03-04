package kr.domaindriven.dailybook.record.conversion;

import kr.domaindriven.dailybook.record.Won;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * yyyy-mm-dd 형태의 문자열을 LocalDateTime로 변환시키기 위한 클래스
 * {@link Converter} 구현하여 작성
 * </p>
 *
 * @author Jerry Ahn
 */
public class StringToLocalDateTime implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        //소스로 들어온 String(예. 2016-03-04-4:8)을 년 월 일 시간으로 분리.
        String[] date = source.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int dayOfMonth = Integer.parseInt(date[2]);

        // 분리된 시간을 : 를 기준으로 분리.
        String[] HourAndMinute = date[3].split(":");
        int hour = Integer.parseInt(HourAndMinute[0]);
        int minute = Integer.parseInt(HourAndMinute[1]);

        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }
}
