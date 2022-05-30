package com.company.server.core.comparators;

import com.company.server.data.HumanBeing;

import java.util.Comparator;

public class RealHeroComparator implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        if(o1.getRealHero() && !o2.getRealHero()){
            return 1;
        } else return -1;
    }
}
