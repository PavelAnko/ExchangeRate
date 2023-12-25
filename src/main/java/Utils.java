import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static String url = "https://bank.gov.ua/NBU_Exchange/exchange_site?";
    private static String startDate = "start=";
    private static String finishDate = "&end=";
    static String currencyCode = "&valcode=usd";
    private static String sortAndOrdersParams = "&sort=exchangedate&order=desc&json";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
    private static EntityManager em = emf.createEntityManager();
    private static Scanner scanner = new Scanner(System.in);
    private static CurrencyUnitDao cud;
    public static void printQueryResult(double result, String outputParam){
        String formattedString = String.format(outputParam + "%.2f", result);
        System.out.println(formattedString);
    }

    private static List<CurrencyUnit> getCurrencyStatistic(){
        Type itemsListType = new TypeToken<List<CurrencyUnit>>() {
        }.getType();
        String response = null;
        try {
            response = getStringFromResponse(getUrlWithParams());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        List<CurrencyUnit> currencyUnits = gson.fromJson(response, itemsListType);
        return currencyUnits;
    }
    private static  void saveStatisticToBd(List<CurrencyUnit> list){
        cud = new CurrencyUnitDao(em);
        for (CurrencyUnit currencyUnit : list) {
            cud.create(currencyUnit);
        }
    }
    private static String getStringFromResponse(URL url) throws IOException {
        String strBuf;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        try {
            byte[] buf = Utils.responseBodyToArray(is);
            strBuf = new String(buf, StandardCharsets.UTF_8);

        } finally {
            is.close();
        }
        return strBuf;
    }
    private static URL getUrlWithParams() throws MalformedURLException {
        url += (startDate + finishDate + currencyCode + sortAndOrdersParams);
        return new URL(url);
    }
    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;
        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);
        return bos.toByteArray();
    }
    public static void loadStatistic() {
        setStartDate();
        setFinishDate();
        saveStatisticToBd(getCurrencyStatistic());
    }
    public static List<CurrencyUnit> getCurrencyList(){
        setStartDate();
        setFinishDate();
        return getCurrencyStatistic();
    }
    private static void setStartDate() {
        System.out.println("Введіть дату початку періоду (yyyyMMdd):");
        startDate += scanner.nextLine();
    }
    private static void setFinishDate() {
        System.out.println("Введіть дату кінця періоду (yyyyMMdd):");
        finishDate += scanner.nextLine();
    }
    public static CurrencyUnitDao getCurrencyUnitDao(){
        return cud;
    }
}
