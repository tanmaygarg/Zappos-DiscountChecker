package com.example.zappos;

import java.util.ArrayList;

public abstract class GenericSearch<T> {
    protected static final String BASE_URL = "http://api.zappos.com";    
    protected static final String API_KEY = "12c3302e49b9b40ab8a222d7cf79a69ad11ffd78";
    protected static final int LIMIT = 12;
	protected static int PAGE_NUM;

    protected HttpReceiver httpReceiver = new HttpReceiver(); 
    protected JsonParser jsonParser = new JsonParser();
    public abstract ArrayList<T> find(String query);
    public abstract Product find(String query, String query1);

    protected String constructSearchUrl(String query) {
        StringBuffer sb = new StringBuffer();
        sb.append(BASE_URL);
        sb.append("/Search/term/");
        sb.append(query);
        sb.append("?limit=");
        sb.append(LIMIT);
        sb.append("&page=");
        sb.append(PAGE_NUM);
        sb.append("&key=");
        sb.append(API_KEY);
        return sb.toString();
    }
}
