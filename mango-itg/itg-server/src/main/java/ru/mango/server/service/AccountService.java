package ru.mango.server.service;

import ru.mango.server.data.model.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findAccountByUserId(Integer userId);

    Optional<Account> findAccountByGroupChatId(Long chatId);

    Optional<Account> findAccountByVpbxApiKey(String vbxApiKey);

    void saveAccount(Account account);

}
