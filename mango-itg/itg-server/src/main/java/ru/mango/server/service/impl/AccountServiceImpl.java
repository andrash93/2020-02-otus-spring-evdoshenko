package ru.mango.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mango.server.data.model.Account;
import ru.mango.server.repository.AccountRepository;
import ru.mango.server.service.AccountService;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccountByUserId(Integer userId) {
        return accountRepository.findByAdminUserId(userId);
    }

    @Override
    public Optional<Account> findAccountByGroupChatId(Long chatId) {
        return accountRepository.findByGroupCharId(chatId);
    }

    @Override
    public Optional<Account> findAccountByVpbxApiKey(String vbxApiKey) {
        return accountRepository.findByVpbxApiKey(vbxApiKey);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
