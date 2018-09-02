package com.bmtdsl.votemeal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matthew on 13/06/17.
 */

public class Util {
    public static UserDetails getLoginResultFromNetworkResponse(String response) throws JSONException{
        JSONObject json = new JSONObject(response);
        UserDetails userDetails = new UserDetails();
        if(json.has("id")){
            userDetails.setUserId(json.getInt("id"));
        }
        if(json.has("name")){
            userDetails.setName(json.getString("name"));
        }
        if(json.has("success")){
            userDetails.setSuccessfulMatch(Boolean.parseBoolean(json.getString("success")));
        }
        return userDetails;
    }

    public static Response getResponseResultFromNetworkResponse(String responseString, String responseFor) throws JSONException{
        JSONObject json = new JSONObject(responseString);
        Response response = new Response();
        response.responseFor = responseFor;
        response.results = new Results();
        response.predictions = new Predictions();
        if(json.has("result")){
            JSONObject results = json.getJSONObject("result");
            if(results.has("conservatives")) {
                response.results.setConservatives(results.getInt("conservatives"));
            }
            if(results.has("labour")) {
                response.results.setLabour(results.getInt("labour"));
            }
            if(results.has("libdems")) {
                response.results.setLibdems(results.getInt("libdems"));
            }
            if(results.has("green")) {
                response.results.setGreens(results.getInt("green"));
            }
            if(results.has("ukip")) {
                response.results.setUkip(results.getInt("ukip"));
            }
        }
        if(json.has("predictions")){
            JSONObject predictions = json.getJSONObject("predictions");
            if(predictions.has("age")) {
                response.predictions.setAge(predictions.getInt("age"));
            }
            if(predictions.has("gender")) {
                response.predictions.setGender(predictions.getString("gender"));
            }
            if(predictions.has("conservativesPercentage")) {
                response.predictions.setConservativesPercentage(predictions.getInt("conservativesPercentage"));
            }
            if(predictions.has("labourPercentage")) {
                response.predictions.setLabourPercentage(predictions.getInt("labourPercentage"));
            }
            if(predictions.has("libdemsPercentage")) {
                response.predictions.setLibdemsPercentage(predictions.getInt("libdemsPercentage"));
            }
            if(predictions.has("greensPercentage")) {
                response.predictions.setGreensPercentage(predictions.getInt("greensPercentage"));
            }
            if(predictions.has("ukipPercentage")) {
                response.predictions.setUkipPercentage(predictions.getInt("ukipPercentage"));
            }
            if(predictions.has("predictedPartyString")){
                response.predictions.setPredictedPartyString(predictions.getString("predictedPartyString"));
            }
        }
        return response;
    }
}
