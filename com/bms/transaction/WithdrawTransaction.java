package com.bms.transaction;

import java.time.LocalDateTime;

public class WithdrawTransaction extends Transaction{
    private String withdrawalIFSCCode;
    private String withdrawalLocation;

    public WithdrawTransaction(LocalDateTime transactionDateTime, double transactionAmount, PaymentMode payModeDetail, String withdrawalIFSCCode, String withdrawalLocation) {
        super(transactionDateTime, transactionAmount, payModeDetail);
        this.withdrawalIFSCCode = withdrawalIFSCCode;
        this.withdrawalLocation = withdrawalLocation;
    }

    public String getWithdrawalIFSCCode() {
        return withdrawalIFSCCode;
    }

    public void setWithdrawalIFSCCode(String withdrawalIFSCCode) {
        this.withdrawalIFSCCode = withdrawalIFSCCode;
    }

    public String getWithdrawalLocation() {
        return withdrawalLocation;
    }

    public void setWithdrawalLocation(String withdrawalLocation) {
        this.withdrawalLocation = withdrawalLocation;
    }
}
