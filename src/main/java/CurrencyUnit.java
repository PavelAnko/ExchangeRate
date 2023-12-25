import javax.persistence.*;
import java.util.Date;
@Entity
@NamedQuery(name = "getAverageCurrencyFromPeriod", query = "SELECT AVG (e.rate) FROM CurrencyUnit e")
@NamedQuery(name = "getMaxCurrencyFromPeriod", query = "SELECT MAX (e.rate) FROM CurrencyUnit e")
@NamedQuery(name = "getCurrencyFromDate", query = "SELECT e.rate FROM CurrencyUnit e WHERE e.exchangedate=:target_date")
public class CurrencyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date exchangedate;

    private String txt1;

    private String txt2;
    private double rate;
    public CurrencyUnit(){}

    public CurrencyUnit(String txt1, String txt2, double rate) {
        this.txt1 = txt1;
        this.txt2 = txt2;
        this.rate = rate;
    }

    public String getTxt1() { return txt1; }
    public String getTxt2() {
        return txt2;
    }
    public double getRate() {
        return rate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    @Override
    public String toString() {
        return "dao.dao.CurrencyUnitDao.entity.CurrencyUnit{" +
                "exchangedate='" + exchangedate + '\'' +
                ", txt1 = '" + txt1 + '\'' +
                ", txt2 = '" + txt2 + '\'' +
                ", rate = " + rate +
                '}';
    }
}
