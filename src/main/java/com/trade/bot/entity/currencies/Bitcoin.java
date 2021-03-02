package com.trade.bot.entity.currencies;

import java.util.ResourceBundle;

public class Bitcoin extends Currency {

    public Bitcoin() {
        super(ResourceBundle.getBundle("currency").getString("Bitcoin"));
    }
}
