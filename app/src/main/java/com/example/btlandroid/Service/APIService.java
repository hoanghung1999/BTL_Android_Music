package com.example.btlandroid.Service;

public class APIService {
//    private static String base_url = "https://608984af8c8043001757ef9d.mockapi.io/api/";
    private static String base_url = "http://192.168.1.44:8080/";

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
