package com.zapps.dailymoney;

import io.realm.Realm;

/**
 * Created by Zimincom on 2017. 9. 4..
 */

public class Wallet {

    private int remains;
    private int budget;

    private Realm realm;


    public int getRemains() {
        return remains;
    }

    public void setRemains(int remains) {
        this.remains = remains;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
