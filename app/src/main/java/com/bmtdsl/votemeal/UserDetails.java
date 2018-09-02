package com.bmtdsl.votemeal;

/**
 * Created by matthew on 13/06/17.
 */

public class UserDetails {
    private int userId;
    private String name;
    private boolean successfulMatch;

    public int getUserId(){
        return this.userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean isSuccessfulMatch() {
        return successfulMatch;
    }

    public void setSuccessfulMatch(boolean successfulMatch) {
        this.successfulMatch = successfulMatch;
    }
}
