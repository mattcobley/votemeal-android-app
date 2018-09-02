package com.bmtdsl.votemeal;

import java.util.ArrayList;


/**
 * Created by matthew on 14/06/17.
 */

public class Results {

    public int getMaxValue(){
        ArrayList<Integer> voteList = new ArrayList<>();
        voteList.add(getConservatives());
        voteList.add(getLabour());
        voteList.add(getLibdems());
        voteList.add(getGreens());
        voteList.add(getUkip());
        int max = (int)voteList.get(0);
        for (int i = 1; i < voteList.size(); i++){
            int val = (int)voteList.get(i);
            if(val > max){
                max = val;
            }
        }
        return max;
    }

    public int getTotal(){
        return getConservatives() + getLabour() + getLibdems() + getGreens() + getUkip();
    }

    private int conservatives;
    private int labour;
    private int libdems;
    private int greens;
    private int ukip;

    public int getConservatives() {
        return conservatives;
    }

    public void setConservatives(int conservatives) {
        this.conservatives = conservatives;
    }

    public int getLabour() {
        return labour;
    }

    public void setLabour(int labour) {
        this.labour = labour;
    }

    public int getLibdems() {
        return libdems;
    }

    public void setLibdems(int libdems) {
        this.libdems = libdems;
    }

    public int getGreens() {
        return greens;
    }

    public void setGreens(int greens) {
        this.greens = greens;
    }

    public int getUkip() {
        return ukip;
    }

    public void setUkip(int ukip) {
        this.ukip = ukip;
    }
}
