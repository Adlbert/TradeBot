package com.trade.bot.entity.currencies;

import java.util.ResourceBundle;

public class Euro extends Currency {
    @Override
    public String getName() {
        return ResourceBundle.getBundle("currency").getString("Euro");
    }
}
