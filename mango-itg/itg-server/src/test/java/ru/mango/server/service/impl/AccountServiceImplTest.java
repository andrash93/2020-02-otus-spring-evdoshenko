package ru.mango.server.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mango.server.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void findAccountByUserIdTest() {
        final Integer id = 123;
        accountService.findAccountByUserId(id);
        verify(accountRepository, times(1)).findByAdminUserId(id);
    }

    @Test
    public void findAccountByGroupChatIdTest() {
        final Long id = 123L;
        accountService.findAccountByGroupChatId(id);
        verify(accountRepository, times(1)).findByGroupCharId(id);
    }

    @Test
    public void findAccountByVpbxApiKeyTest() {
        final String key = "123gfhfgh";
        accountService.findAccountByVpbxApiKey(key);
        verify(accountRepository, times(1)).findByVpbxApiKey(key);
    }


}