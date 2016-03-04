package kr.domaindriven.dailybook;

import kr.domaindriven.dailybook.record.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 후원금 관리 화면 입출력 서버측 접점
 * </p>
 *
 * @author Younghoe Ahn
 * @author Jerry Ahn
 */
@Controller
public class DailyBookPageController {

    /*
     * templates 하위 디렉토리 이름을 Controller별로 한번에 지정하기 위해 사용
     */
    private final String APP_DIR = "dailybook/";

    @Autowired
    private RecordRepository recordRepository;


    /**
     * <p>후원금 관리 목록에 한 줄에 해당하는 데이터를 입력하는 화면 출력</p>
     *
     * @param model
     * @return
     */
    @RequestMapping(value = APP_DIR + "add", method = RequestMethod.GET)
    public String addRecord(Model model) {
        // command 객체
        Record record = new Record();
        record.setAmount(new Won(0));
        record.setCategory(RecordCategory.후원금);
        model.addAttribute("record", record);
        return APP_DIR + "add";
    }


    /**
     * <p>후원금 관리 목록에 한 줄을 추가하는 행위</p>
     *
     * @param model
     * @return
     */
    @RequestMapping(value = APP_DIR + "add", method = RequestMethod.POST)
    public String recordAdded(@ModelAttribute Record record, Model model) {


        // TODO convert hard-cord check to @VALID

        //날짜를 위한 추가부분
        if (record.getDate() == null) {
            model.addAttribute("record", record);
            return APP_DIR + "add";
        } else {
            record.setDate(record.getDate());
        }

        //금액을 위한 추가부분, 음수면 지출 그외에는 수입
        if (record.getAmount() == null) {
            model.addAttribute("record", record);
            return APP_DIR + "add";
        } else if (record.getAmount().toBigDecimal().compareTo(new BigDecimal(0)) == -1) {
            record.setRevenueOrExpense(RecordType.지출);
        } else {
            record.setRevenueOrExpense(RecordType.수입);
        }
        //범주(카테고리)를 위한 부분
        if (record.getCategory() == null) {
            model.addAttribute("record", record);
            return APP_DIR + "add";
        } else if (record.getCategory().equals(RecordCategory.위키유지비)) {
            record.setCategory(RecordCategory.위키유지비);
        } else {
            record.setCategory(RecordCategory.후원금);
        }

        //잔액(balance)를 위한 부분, 마지막 잔액을 확인하여, Amount를 더한다.
        BigDecimal lastBalance = recordRepository.findById(recordRepository.findByLastRecordId()).getBalance().getAmount();
        BigDecimal amount = record.getAmount().toBigDecimal();
        Won balance = new Won(lastBalance.add(amount));
        record.setBalance(balance);

        //적요(Summary)를 위한 부분
        if (record.getSummary() == null) {
            model.addAttribute("record", record);
            return APP_DIR + "add";
        } else {
            record.setSummary(record.getSummary());
        }

        recordRepository.save(record);
        model.addAttribute("record", record);

        return APP_DIR + "result";


     /*   if(result.hasErrors())
            return APP_DIR + "add";
        else
            return APP_DIR + "result";*/
    }


    /**
     * <p>후원금 관리 첫 화면 즉, 목록이 나오는 화면 출력</p>
     *
     * @param model
     * @return
     */
    @RequestMapping(value = APP_DIR + "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Record> recordList = recordRepository.findAll();
        model.addAttribute("list", recordList);
        return APP_DIR + "list";
    }

    // TODO 향후 구현 완료 후에 javadoc 주석 보강

    @RequestMapping(value = "dailybook/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        Record oldRecord = recordRepository.findById(id);
        model.addAttribute("oldRecord", oldRecord);
        System.out.println(oldRecord);
        return APP_DIR + "update";
    }

    @RequestMapping(value = "dailybook/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute Record record, Model model) {
        recordRepository.save(record);
        System.out.println(record);
        return APP_DIR + "result";
    }
}