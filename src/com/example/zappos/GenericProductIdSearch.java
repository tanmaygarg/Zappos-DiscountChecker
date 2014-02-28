package com.example.zappos;

import java.util.HashMap;

public abstract class GenericProductIdSearch<T> {
    protected static final String BASE_URL = "http://api.zappos.com";    
    protected static final String API_KEY = "12c3302e49b9b40ab8a222d7cf79a69ad11ffd78";


    protected HttpReceiver httpReceiver = new HttpReceiver(); 
    protected JsonParser jsonParser = new JsonParser();
    public abstract HashMap<String,String> find(String query);

    protected String constructSearchUrl(String query) {
        StringBuffer sb = new StringBuffer();
        sb.append(BASE_URL);
        sb.append("/Product/");
        sb.append(query);
        sb.append("?key=");
        sb.append(API_KEY);
        return sb.toString();
    }
}
