package com.bmtdsl.votemeal;

/**
 * Created by matthew on 15/06/17.
 */

public class Predictions {
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public String getPredictedPartyString() {
        return predictedPartyString;
    }

    public void setPredictedPartyString(String predictedPartyString) {
        this.predictedPartyString = predictedPartyString;
        this.setPredictedParty();
    }

    private String predictedPartyString;

    public Party getPredictedParty() {
        return predictedParty;
    }

    public void setPredictedParty() {
        this.predictedParty = Party.valueOf(this.getPredictedPartyString());
    }

    private Party predictedParty;

    public int getConservativesPercentage() {
        return conservativesPercentage;
    }

    public void setConservativesPercentage(int conservativesPercentage) {
        this.conservativesPercentage = conservativesPercentage;
    }

    private int conservativesPercentage;

    public int getLabourPercentage() {
        return labourPercentage;
    }

    public void setLabourPercentage(int labourPercentage) {
        this.labourPercentage = labourPercentage;
    }

    private int labourPercentage;

    public int getLibdemsPercentage() {
        return libdemsPercentage;
    }

    public void setLibdemsPercentage(int libdemsPercentage) {
        this.libdemsPercentage = libdemsPercentage;
    }

    private int libdemsPercentage;

    public int getGreensPercentage() {
        return greensPercentage;
    }

    public void setGreensPercentage(int greensPercentage) {
        this.greensPercentage = greensPercentage;
    }

    private int greensPercentage;

    public int getUkipPercentage() {
        return ukipPercentage;
    }

    public void setUkipPercentage(int ukipPercentage) {
        this.ukipPercentage = ukipPercentage;
    }

    private int ukipPercentage;
}
