package com.trade.bot.entity.currencies;

import java.util.ResourceBundle;

public class Ethereum extends Currency {

    public Ethereum() {
        super(ResourceBundle.getBundle("currency").getString("Ethereum"));
    }
}
