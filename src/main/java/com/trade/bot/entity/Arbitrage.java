package com.trade.bot.entity;

import com.trade.bot.entity.currencies.Currency;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Arbitrage {
    private Currency currency1;
    private Currency currency2;
    private Currency currency3;
    private Pair<Currency, Currency> pair1;
    private Pair<Currency, Currency> pair2;
    private Pair<Currency, Currency> pair3;
    private Pair<Pair,Double> rate1;
    private Pair<Pair,Double> rate2;
    private Pair<Pair,Double> rate3;
    private double lots;

    public Arbitrage(Currency currency1, Currency currency2, Currency currency3, double lots){
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.currency3 = currency3;
        this.pair1 = new Pair<>(currency1, currency2);
        this.pair2 = new Pair<>(currency2, currency3);
        this.pair3 = new Pair<>(currency3, currency1);
        this.rate1 = new Pair<>(pair1, 0.0);
        this.rate2 = new Pair<>(pair2, 0.0);
        this.rate3 = new Pair<>(pair3, 0.0);
        this.lots = lots;
    }

    public Arbitrage(Currency currency1, Currency currency2, Currency currency3, double rate1, double rate2, double rate3, double lots){
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.currency3 = currency3;
        this.pair1 = new Pair<>(currency1, currency2);
        this.pair2 = new Pair<>(currency2, currency3);
        this.pair3 = new Pair<>(currency3, currency1);
        this.rate1 = new Pair<>(pair1, rate1);
        this.rate2 = new Pair<>(pair2, rate2);
        this.rate3 = new Pair<>(pair3, rate3);
        this.lots = lots;
    }

    public double getLots(){
        return this.lots;
    }

    public Pair<Currency, Currency> getPair1() {
        return pair1;
    }

    public Pair<Currency, Currency> getPair2() {
        return pair2;
    }

    public Pair<Currency, Currency> getPair3() {
        return pair3;
    }

    public double getRate1() {
        return rate1.getValue1();
    }

    public void setRate1(double rate) {
        this.rate1 = this.rate1.setAt1(rate);
    }

    public double getRate2() {
        return rate2.getValue1();
    }

    public void setRate2(double rate) {
        this.rate2 = this.rate2.setAt1(rate);
    }

    public double getRate3() {
        return rate3.getValue1();
    }

    public void setRate3(double rate) {
        this.rate3 = this.rate3.setAt1(rate);
    }

    public List<Pair<Pair,Double>> getOrderedCurrencyList(){
        List<Pair<Pair,Double>> list = new ArrayList<>();
        list.add(rate1);
        list.add(rate2);
        list.add(rate3);
        //Dont know if ordering makes sense
        /*Collections.sort(list, (r1, r2) -> {
            if(r2.getValue1() > r1.getValue1())
                return 1;
            else if(r2.getValue1() < r1.getValue1())
                return -1;
            else
                return 0;
        });*/
        return list;
    }
}
