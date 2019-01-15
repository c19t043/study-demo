package cn.cjf;

import lombok.Data;

/**
 * Simple POJO to represent a user and their metadata.
 */
@Data
public class UserAccount {

    private final int userId;
    private final String name;
    private final int accountType;
    private final boolean isFeatureXenabled;
    private final boolean isFeatureYenabled;
    private final boolean isFeatureZenabled;

    public UserAccount(int userId, String name, int accountType, boolean x, boolean y, boolean z) {
        this.userId = userId;
        this.name = name;
        this.accountType = accountType;
        this.isFeatureXenabled = x;
        this.isFeatureYenabled = y;
        this.isFeatureZenabled = z;
    }
}