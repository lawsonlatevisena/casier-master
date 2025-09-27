/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.poste;

import okhttp3.*;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

public class TrustAllHostsSSLClient {
    private TrustAllHostsSSLClient() {
        
    };
    public static Interceptor getHeaderInterceptor(String name, String value) {
        return (Chain chain) -> {
            Request orig = chain.request();
            Request newRequest = orig.newBuilder().addHeader(name, value).build();
            return chain.proceed(newRequest);
        };
    }

    public static Interceptor basicAuth(String user, String password) {
        return (Chain chain) -> {
            Request orig = chain.request();
            String credential = Credentials.basic(user, password);
            Request newRequest = orig.newBuilder().addHeader("Authorization", credential).build();
            return chain.proceed(newRequest);
        };
    }

    // {{start:client}}
    private static final OkHttpClient client;
    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(15);

        client = new Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .dispatcher(dispatcher)
            .build();
    }

    ;

    /*
     * Global client that can be shared for common HTTP tasks.
     */
    public static OkHttpClient globalClient() {
        return client;
    }
    // {{end:client}}
    
    public static RuntimeException unknownException(Response response) throws IOException {
        return new RuntimeException(String.format("code: %s, body: %s", response.code(), response.body().string()));
    }

    // {{start:trustManager}}
    /*
     * This is very bad practice and should NOT be used in production.
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[]{};
            }
        }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();
    // {{end:trustManager}}

    // {{start:trustAllSslClient}}
    /*
     * This should not be used in production unless you really don't care
     * about the security. Use at your own risk.
     */
    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
          @Override
          public boolean verify(String hostname, SSLSession session) {
            return true;
          }
        });
        return builder.build();
    }
    // {{end:trustAllSslClient}}
}
