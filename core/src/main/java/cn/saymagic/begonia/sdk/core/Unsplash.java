package cn.saymagic.begonia.sdk.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.saymagic.begonia.sdk.core.api.UnsplashService;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Unsplash {

    private static Unsplash instance = new Unsplash();

    private static UnsplashService service;

    private static final String API_HOST = "https://api.unsplash.com/";

    private static String clientId;

    private static OkHttpClient okHttpClient;

    public synchronized static void init(String id) {
        init(id, null);
    }

    public synchronized static void init(String id, OkHttpClient client) {
        check(id);
        clientId = id;
        okHttpClient = client;
        buildService();
    }

    public UnsplashService getService() {
        return service;
    }

    public static Unsplash getInstance() {
        return instance;
    }

    private static void buildService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = okHttpClient == null ? new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging) : okHttpClient.newBuilder();

        okHttpClient = builder.connectTimeout(10, TimeUnit.SECONDS).addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Headers headers = request.headers().newBuilder()
                        .add("Authorization", "Client-ID " + clientId).build();
                return chain.proceed(request.newBuilder().headers(headers).build());
            }
        }).build();

        service = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(UnsplashService.class);
    }

    private static void check(String id) {
        if (clientId != null) {
            throw new IllegalArgumentException("already init with id: " + clientId);
        }
        if (id == null) {
            throw new IllegalArgumentException("id can't be null");
        }
    }

    private Unsplash() {

    }

}
