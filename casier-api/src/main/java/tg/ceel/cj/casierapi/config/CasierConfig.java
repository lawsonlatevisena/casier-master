package tg.ceel.cj.casierapi.config;


import java.util.HashMap;
import java.util.Map;



public class CasierConfig {

    private static Map<String, String> environment = new HashMap<>();

    static {
        initDafaultEnvironment();
        initContenerizedEnvironment();
    }

    private static void initDafaultEnvironment() {
        initFNCDefaults();
        initCasierWebDefaults();
        initTresorDefaults();
        initLaPosteDefaults();
        initMailDeliveryDefaults();
        initKifemaDefaults();
    }

    private static void initContenerizedEnvironment() {
        Map<String, String> output = new HashMap<>();
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            String paramName  = entry.getKey();
            String paramValue = entry.getValue();
            if (System.getenv(paramName) != null) {
                output.put(paramName, System.getenv(paramName));
            } else {
                output.put(paramName, paramValue);
            }
        }
        environment = output;
    }
    
    private static void initFNCDefaults() {
        environment.put("FNC_PROTOCOL", "http");
        environment.put("FNC_SERVER", "localhost");
//        environment.put("FNC_SERVER", "192.168.6.5");
        environment.put("FNC_SERVER_PORT", "8080");
        environment.put("FNC_WS_PATH", "casier-ws-interne/rs/casier/");
    }
    
    private static void initCasierWebDefaults() {
        environment.put("CASIER_WEB_PROTOCOL", "http");
        environment.put("CASIER_WEB_SERVER", "localhost");
//        environment.put("CASIER_WEB_SERVER", "192.168.6.5");
        environment.put("CASIER_WEB_PORT", "8080");
        environment.put("CASIER_WEB_WS_PATH", "casier-web/rs/");
    }
    
    private static void initTresorDefaults() {
        environment.put("TRESOR_WEB_PROTOCOL", "http");
        environment.put("TRESOR_WEB_SERVER", "app.mjl.tg");
        environment.put("TRESOR_WEB_PORT", "8080");
        environment.put("TRESOR_WEB_WS_PATH", "/");
    }
    
    private static void initLaPosteDefaults() {
        environment.put("LAPOSTE_WEB_PROTOCOL", "https");
        environment.put("LAPOSTE_API_SUBDOMAIN", "api");
        environment.put("LAPOSTE_WEB_SERVER", "trak.codes");
        environment.put("LAPOSTE_API_KEY", "skw7juH2tFCjkNzPWnwkbAw6SPZxVC7mncDHJniWJ91GrGVe5xXdXxx0Ft2IgORU");
    }
    
    private static void initMailDeliveryDefaults() {
        environment.put("MAILER_HOST", "mail.gouv.tg");
        environment.put("MAILER_PORT", "465");
        environment.put("MAILER_ENABLE_SSL", "true");
        environment.put("MAILER_AUTH", "true");
        environment.put("MAILER_USERNAME", "cjn@justice.gouv.tg");
        environment.put("MAILER_PASSWORD", "B(4>@q@Jth:uu~Nw");
        environment.put("MAILER_EMAIL_SENDER", "cjn@justice.gouv.tg");
    }
    
    private static void initKifemaDefaults() {
        environment.put("API_KEY", "A4JA-Nscq-FOH3-4ItM-As2p");
        environment.put("SECRET_KEY", "M9fNLIYF5tq4j8hdA0gXKWmP5oUIy7cTUUgbObZmEZ5rDhGUoxGXc2LctZKrGJlnMgdB9xS8eZ54oKgOqFGymzuBVOJOpCGPWZlWGm7CHqDsi34C7yuGfU6DdH9t6DkdXVcSPtOCro2txDZz83s");
        environment.put("SIGNED_ATTRIBUTE_NAMES", "transactionUUID,devise,total,apiKey");
        environment.put("DEVISE", "XOF");
    }
    
    public static String getConfigValue(String key) {
        if (environment.containsKey(key))
            return environment.get(key);
        return null;
    }

    public static String fncWsUrl() {
        return String.format("%s://%s:%s/%s", 
            environment.get("FNC_PROTOCOL"), 
            environment.get("FNC_SERVER"),
            environment.get("FNC_SERVER_PORT"),
            environment.get("FNC_WS_PATH")
        );
    }

    public static String casierAppInterneWsUrl() {
        return String.format("%s://%s:%s/%s", 
            environment.get("CASIER_WEB_PROTOCOL"),
            environment.get("CASIER_WEB_SERVER"),
            environment.get("CASIER_WEB_PORT"),
            environment.get("CASIER_WEB_WS_PATH")
        );
    }

    public static String tresorAppUrl() {
        return String.format("%s://%s:%s/", 
            environment.get("TRESOR_WEB_PROTOCOL"),
            environment.get("TRESOR_WEB_SERVER"),
            environment.get("TRESOR_WEB_PORT")
        );
    }

    public static String laposteApiUrl() {
        return String.format("%s://%s", 
            environment.get("LAPOSTE_WEB_PROTOCOL"), 
            environment.get("LAPOSTE_WEB_SERVER")
        );
    }

    public static String laposteEndPoint() {
        return String.format("%s://%s.%s", 
            environment.get("LAPOSTE_WEB_PROTOCOL"),
            environment.get("LAPOSTE_API_SUBDOMAIN"),
            environment.get("LAPOSTE_WEB_SERVER")
        );
    }
}