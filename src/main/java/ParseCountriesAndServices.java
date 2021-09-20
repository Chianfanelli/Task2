import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ParseCountriesAndServices {

    public static String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void parseCountriesJson(String resultJson) {
        try {
            JSONObject countriesJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);
            Map<String, Map<String, String>> countriesAndServices = new HashMap<>();
            JSONObject countries = (JSONObject) countriesJsonObject.get("countries");
            JSONObject servicesByCountry = (JSONObject) countriesJsonObject.get("list");
            JSONObject countriesNamesOnRu = (JSONObject) countriesJsonObject.get("text");
            for (Object countriesElement : countries.keySet()) {
                Map<String, String> servicesMap = new HashMap<>();
                JSONObject ServicesElements = (JSONObject) servicesByCountry.get(countriesElement);
                for (Object ServiceElement : ServicesElements.keySet()) {
                    servicesMap.put(ServiceElement.toString(), ServicesElements.get(ServiceElement).toString());
                }
                for (Object countryCode : countriesNamesOnRu.keySet()) {
                    if (countryCode.toString().contains(countriesElement.toString())) {
                        countriesAndServices.put(countriesNamesOnRu.get(countryCode).toString(), servicesMap);
                    }
                }
            }
            output(countriesAndServices);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void output(Map<String, Map<String, String>> countriesAndServices) {
        for (String country : countriesAndServices.keySet()) {
            System.out.println(country);
            for (String serviceName : countriesAndServices.get(country).keySet()) {
                System.out.println(serviceName + " " + countriesAndServices.get(country).get(serviceName));
            }
        }
    }

    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
