package cn.cjf;

import lombok.Data;

/**
 * POJO
 */
@Data
public class PaymentInformation {

    private final UserAccount user;
    private final String creditCardNumber;
    private final int expirationMonth;
    private final int expirationYear;

    public PaymentInformation(UserAccount user, String creditCardNumber, int expirationMonth, int expirationYear) {
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }
}