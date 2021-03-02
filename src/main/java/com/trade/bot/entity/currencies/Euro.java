package com.trade.bot.entity.currencies;

import java.util.ResourceBundle;

public class Euro extends Currency {
    public Euro() {
        super(ResourceBundle.getBundle("currency").getString("Euro"));
    }
}
