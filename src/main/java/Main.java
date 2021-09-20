import java.net.URL;

public class Main {
    public static final String Task_URL1 = "https://onlinesim.ru/price-list-data?type=receive";

    public static void main(String[] args)  {

        URL urlTask1 = ParseCountriesAndServices.createUrl(Task_URL1);
        String resultJson1 = ParseCountriesAndServices.parseUrl(urlTask1);
        ParseCountriesAndServices.parseCountriesJson(resultJson1);
    }
}