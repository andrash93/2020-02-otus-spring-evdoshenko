package ru.mango.server.events.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;
import ru.mango.server.data.AccountState;
import ru.mango.server.data.model.Account;
import ru.mango.server.service.AccountService;
import ru.mango.server.service.TelegramService;

import java.time.LocalDateTime;
import java.util.Optional;

@MessageEndpoint
public class TelegramPrivateMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramPrivateMessageHandler.class);

    private final AccountService accountService;
    private final TelegramService telegramService;

    public TelegramPrivateMessageHandler(AccountService accountService, TelegramService telegramService) {
        this.accountService = accountService;
        this.telegramService = telegramService;
    }

    @ServiceActivator(inputChannel = "privateMessageRouterChannel")
    public void handleMessage(TelegramMessageEntryDto message) {
        logger.info("invoke handleInboundMessage {}", message);

        Account account = getAccountByUserId(message);
        switch (message.getText()) {
            case "/start":
                handleStartCommand(account);
                break;
            case "/edit_api_key":
                handleEditApiKeyCommand(account);
                break;
            case "/edit_api_salt":
                handleEditApiSaltCommand(account);
                break;
            case "/add_user":
                handleAddUserCommand(account);
                break;
            case "/info":
                handleInfoCommand(account);
                break;
            case "/help":
                handleHelpCommand(account);
                break;
            default:
                handleOnStateCommand(account, message);
                break;
        }
    }

    private void handleStartCommand(Account account) {
        String text = "Добро пожаловать! \n" +
                "Для работы бота, необходимо сохранить ApiKey из Личного кабинета Манго Телеком(/edit_api_key),\n" +
                "сохранить ApiSalt(/edit_api_salt),\n" +
                "наcтроить сопоставление пользователей ВАТС и пользователей Telegram(/add_user), \n" +
                "далее добавить бота в рабочий чат группы";
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.NONE);
        accountService.saveAccount(account);
    }

    private void handleEditApiKeyCommand(Account account) {
        String text = "Введите ApiKey из Личного кабинета Манго Телеком";
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.EDIT_VPBX_API_KEY);
        accountService.saveAccount(account);
    }

    private void handleEditApiSaltCommand(Account account) {
        String text = "Введите ApiSalt из Личного кабинета Манго Телеком";
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.EDIT_VPBX_API_SALT);
        accountService.saveAccount(account);
    }

    private void handleAddUserCommand(Account account) {
        String text = "Введите пользователя в формате 3500:@TelegramUserName " + LocalDateTime.now();
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.ADD_USER);
        accountService.saveAccount(account);
    }

    private void handleInfoCommand(Account account) {
        String text = "Информация: \n" +
                "vpbxApiKey : " + account.getVpbxApiKey() + " \n" +
                "vpbxApiSalt : " + account.getVpbxApiSalt() + " \n" +
                "Пользователи : ";
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.NONE);
        accountService.saveAccount(account);
    }

    private void handleHelpCommand(Account account) {
        String text = "Для работы бота, необходимо сохранить ApiKey из Личного кабинета Манго Телеком(/edit_api_key),\n" +
                "сохранить ApiSalt(/edit_api_salt),\n" +
                "наcтроить сопоставление пользователей ВАТС и пользователей Telegram(/add_user), \n" +
                "далее добавить бота в рабочий чат группы \n" +
                "При возникновении вопросов, обратиться в техническую поддержку Манго телеком";
        telegramService.sendMessage(account.getAdminChatId(), text);
        account.setAccountState(AccountState.NONE);
        accountService.saveAccount(account);
    }

    private void handleOnStateCommand(Account account, TelegramMessageEntryDto message) {
        switch (account.getAccountState()) {
            case EDIT_VPBX_API_KEY:
                saveVpbxApiKey(account, message.getText());
                break;
            case EDIT_VPBX_API_SALT:
                saveVpbxApiSalt(account, message.getText());
                break;
            case ADD_USER:
                saveUser(account, message.getText());
                break;
        }
    }

    private void saveVpbxApiKey(Account account, String vpbxApiKey) {
        account.setAccountState(AccountState.NONE);
        account.setVpbxApiKey(vpbxApiKey);
        accountService.saveAccount(account);
    }

    private void saveVpbxApiSalt(Account account, String vpbxApiSalt) {
        account.setAccountState(AccountState.NONE);
        account.setVpbxApiSalt(vpbxApiSalt);
        accountService.saveAccount(account);
    }

    private void saveUser(Account account, String userString) {

    }

    private Account getAccountByUserId(TelegramMessageEntryDto message) {
        Optional<Account> account = accountService.findAccountByUserId(message.getUserId());
        return account.orElseGet(() -> createAccount(message));
    }

    private Account createAccount(TelegramMessageEntryDto message) {
        Account account = new Account();
        account.setAdminUserId(message.getUserId());
        account.setAdminChatId(message.getChatId());
        account.setAdminFirstName(message.getFirstName());
        account.setAdminUserName(message.getUserName());
        accountService.saveAccount(account);
        return account;
    }

}
