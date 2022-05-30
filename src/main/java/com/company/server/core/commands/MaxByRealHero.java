package com.company.server.core.commands;

import com.company.server.data.HumanBeing;
import com.company.server.core.Collection;

public class MaxByRealHero {
   public String maxByRealHero(Collection collection) {
       if (collection.getCollection().size() > 0) {
           return collection.getCollection().stream().filter(HumanBeing::getRealHero).findFirst().map(HumanBeing::toString).orElse("В коллекции нет нужных объектов.");
       }
       return "В коллекции нет объектов.";
   }
}
