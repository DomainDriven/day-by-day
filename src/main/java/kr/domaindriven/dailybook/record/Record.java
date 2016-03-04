package kr.domaindriven.dailybook.record;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * <p>
 * 후원금 관리 기준이 되는 입출금 이력과 그에 따른 잔액을 표현하는 객체이며 JPA {@link Entity} 역할을 겸함
 * </p>
 *
 * @author Younghoe Ahn
 * @author Jerry Ahn
 */

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime date;

    // TODO -/+ 로 간편하게 만들지 고민해보기
    @Enumerated(value = EnumType.STRING)
    private RecordType revenueOrExpense;

    private Won amount;

    private Won balance;

    private String summary;

    @Enumerated(value = EnumType.STRING)
    private RecordCategory category;

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setRevenueOrExpense(RecordType revenueOrExpense) {
        this.revenueOrExpense = revenueOrExpense;
    }

    public void setAmount(Won amount) {
        this.amount = amount;
    }

    public void setBalance(Won balance) {
        this.balance = balance;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCategory(RecordCategory category) {
        this.category = category;
    }

    public Record(LocalDateTime date, boolean isRevenue, Won amount, Won balance, String summary, RecordCategory category) {
        this.date = date;
        if (isRevenue) {
            revenueOrExpense = RecordType.수입;
        } else {

            revenueOrExpense = RecordType.지출;
        }
        this.amount = amount;
        this.balance = balance;
        this.summary = summary;
        this.category = category;
    }

    public Record() {

    }


    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Won getAmount() {
        return amount;
    }

    public Won getBalance() {
        return balance;
    }

    public String getSummary() {
        return summary;
    }

    public RecordType getRevenueOrExpense() {
        return revenueOrExpense;
    }

    public RecordCategory getCategory() {
        return category;
    }
}
