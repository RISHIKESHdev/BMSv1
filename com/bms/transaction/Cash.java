package com.bms.transaction;

import java.time.LocalDateTime;
import java.util.List;

public class Cash implements PaymentMode{
    public enum Denomination{TEN(0),TWENTY(0),FIFTY(0),HUNDRED(0),TWO_HUNDRED(0),FIVE_HUNDRED(0),TWO_THOUSAND(0);
        private int count;
        Denomination(int count){
            this.count=count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private List<Denomination> denominationList;

    public List<Denomination> getDenominationList() {
        return denominationList;
    }
    public void addDenominationList(Denomination denomination) {
        this.denominationList.add(denomination);
    }
}
