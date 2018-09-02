package com.bmtdsl.votemeal;

/**
 * Created by matthew on 14/06/17.
 */

public enum Party {
    CONSERVATIVES("Conservatives"), LABOUR ("Labour"), LIBDEMS("Liberal Democrats"), GREEN("Green Party"), UKIP("UKIP");

    private String name;

    Party(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
