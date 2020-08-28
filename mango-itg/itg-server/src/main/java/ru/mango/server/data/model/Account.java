package ru.mango.server.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mango.server.data.AccountState;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    private String vpbxApiKey;
    private String vpbxApiSalt;

    private Integer adminUserId;
    private String adminFirstName;
    private String adminUserName;
    private Long adminChatId;

    private Long groupCharId;
    private String groupChatName;

    private AccountState accountState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVpbxApiKey() {
        return vpbxApiKey;
    }

    public void setVpbxApiKey(String vpbxApiKey) {
        this.vpbxApiKey = vpbxApiKey;
    }

    public String getVpbxApiSalt() {
        return vpbxApiSalt;
    }

    public void setVpbxApiSalt(String vpbxApiSalt) {
        this.vpbxApiSalt = vpbxApiSalt;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public Long getAdminChatId() {
        return adminChatId;
    }

    public void setAdminChatId(Long adminChatId) {
        this.adminChatId = adminChatId;
    }

    public Long getGroupCharId() {
        return groupCharId;
    }

    public void setGroupCharId(Long groupCharId) {
        this.groupCharId = groupCharId;
    }

    public String getGroupChatName() {
        return groupChatName;
    }

    public void setGroupChatName(String groupChatName) {
        this.groupChatName = groupChatName;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public void setAccountState(AccountState accountState) {
        this.accountState = accountState;
    }

}
