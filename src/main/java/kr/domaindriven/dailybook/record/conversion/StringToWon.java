package kr.domaindriven.dailybook.record.conversion;

import kr.domaindriven.dailybook.record.Won;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * <p>
 * 문자열을 대한민국 화폐로 변환시키기 위한 클래스
 * {@link Converter} 구현하여 작성
 * </p>
 *
 * @author Jerry Ahn
 */
public class StringToWon implements Converter<String, Won> {
    @Override
    public Won convert(String source) {
        String cha = source.replaceAll("[^-]", "");
        String num = source.replaceAll("[^0-9]", "");
        BigDecimal amonut = new BigDecimal(num);
        return new Won(amonut);
    }
}
