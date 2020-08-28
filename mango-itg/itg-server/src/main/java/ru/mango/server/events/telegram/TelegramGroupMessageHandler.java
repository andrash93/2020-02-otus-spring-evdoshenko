package ru.mango.server.events.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.util.StringUtils;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;
import ru.mango.common.model.vpbx.Call;
import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.common.model.vpbx.CallLocation;
import ru.mango.server.data.model.Account;
import ru.mango.server.service.AccountService;
import ru.mango.server.service.TelegramService;
import ru.mango.server.service.VpbxService;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@MessageEndpoint
public class TelegramGroupMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramGroupMessageHandler.class);

    private final AccountService accountService;
    private final TelegramService telegramService;
    private final VpbxService vpbxService;

    public TelegramGroupMessageHandler(AccountService accountService,
                                       TelegramService telegramService,
                                       VpbxService vpbxService) {
        this.accountService = accountService;
        this.telegramService = telegramService;
        this.vpbxService = vpbxService;
    }

    @ServiceActivator(inputChannel = "groupMessageRouterChannel")
    public void handleMessage(TelegramMessageEntryDto message) {
        logger.info("invoke handleMessage {}", message);

        if (message.getText() == null) {
            handleActionCommand(message);
            return;
        }

        Account account = getAccountByChatId(message);
        if (StringUtils.isEmpty(account.getVpbxApiKey())) {
            logger.warn("account {} error. vpbxApiKey is empty", account.getId());
            return;
        }
        switch (message.getText().substring(0, message.getText().indexOf("@"))) {
            case "/current_call":
                handleCurrentCallCommand(account);
                break;
            case "/callback":
                handleCallbackCommand();
                break;
            case "/help":
                handleHelpCommand(account);
                break;
        }
    }

    private void handleCurrentCallCommand(Account account) {
        List<Call> currentCalls = vpbxService.getCurrentCalls(account.getVpbxApiKey());
        String text = currentCallsMessageText(currentCalls);
        telegramService.sendMessage(account.getGroupCharId(), text);
    }

    private void handleCallbackCommand() {
    }

    private void handleHelpCommand(Account account) {
        String text = "/current_call - посмотреть текущие звонки \n" +
                "При пропущенном звонке, появится уведомление и кнопка обратного вызова(перезвона)";
        telegramService.sendMessage(account.getGroupCharId(), text);
    }

    private Account getAccountByChatId(TelegramMessageEntryDto message) {
        Optional<Account> account = accountService.findAccountByGroupChatId(message.getChatId());
        return account.orElseThrow(RuntimeException::new);
    }


    private void handleActionCommand(TelegramMessageEntryDto message) {
        if (message.isAddToChat()) {
            handleAddToChatCommand(message);
        }
        if (message.isLeftChat()) {
            handleLeftChatCommand();
        }
    }

    private void handleAddToChatCommand(TelegramMessageEntryDto message) {
        Optional<Account> accountOptional = accountService.findAccountByUserId(message.getUserId());
        if (!accountOptional.isPresent()) {
            return; //todo подумать че надо делать в такой ситуации
        }

        Account account = accountOptional.get();
        account.setGroupCharId(message.getChatId());
        accountService.saveAccount(account);

        if (!isAccountSetUp(account)) {
            sendErrorSetUpMessageToAdmin(account);
        } else {
            sendStartChatMessage(account);
        }

    }

    private void handleLeftChatCommand() {

    }

    private boolean isAccountSetUp(Account account) {
        if (StringUtils.isEmpty(account.getVpbxApiKey()) || StringUtils.isEmpty(account.getVpbxApiSalt())) {
            return false;
        }
        return true;
    }

    private void sendErrorSetUpMessageToAdmin(Account account) {
        String text = "Внимание! Вы добавили бота Манго телеком в чат \n" +
                "В данный момент бот не настроен \n" +
                "Для работы бота, необходимо сохранить ApiKey из Личного кабинета Манго Телеком(/edit_api_key),\n" +
                "сохранить ApiSalt(/edit_api_salt),\n" +
                "наcтроить сопоставление пользователей ВАТС и пользователей Telegram(/add_user), \n" +
                "далее добавить бота в рабочий чат группы \n" +
                "При возникновении вопросов, обратиться в техническую поддержку Манго телеком";
        telegramService.sendMessage(account.getAdminChatId(), text);
    }

    private void sendStartChatMessage(Account account) {
        String text = "Вы добавили бота Манго телеком в чат \n" +
                "Подробнее о работе бота /help";
        telegramService.sendMessage(account.getGroupCharId(), text);
    }

    private String currentCallsMessageText(List<Call> currentCalls) {
        StringBuilder text = new StringBuilder("Текущие звонки:\n");
        if (currentCalls.isEmpty()) {
            text.append("Текущих звонков нет");
        }
        for (Call call : currentCalls) {
            text.append("-----------------------\n").append(currentCallMessageText(call)).append("\n");
        }
        return text.toString();
    }

    private String currentCallMessageText(Call call) {
        if (call.getCallDirection() == CallDirection.inbound) {
            return getInboundCallMessageText(call);
        } else if (call.getCallDirection() == CallDirection.outbound) {
            return getOutboundCallMessageText(call);
        }
        return "";
    }

    private String getInboundCallMessageText(Call call) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(call.getStartTime() * 1000);

        String text = "Входящий звонок: " + c.getTime() + " | " + call.getPhoneFrom() + "  ->  " + call.getPhoneTo() + " | ";
        if (call.getLocation() == CallLocation.ivr) {
            text += " IVR | ";
        } else if (call.getLocation() == CallLocation.abonent) {
            text += call.getExt() + " | ";
        }
        if (call.getConnectedTime() != null && call.getConnectedTime() != 0) {
            text += "идет разговор: " + (System.currentTimeMillis() / 1000 - call.getStartTime()) / 60 + ":" +
                    (System.currentTimeMillis() / 1000 - call.getStartTime()) % 60 + " | ";
        } else {
            text += "ожидание " + (System.currentTimeMillis() / 1000 - call.getStartTime()) / 60 + ":" +
                    (System.currentTimeMillis() / 1000 - call.getStartTime()) % 60 + " | ";
        }
        return text;
    }

    private String getOutboundCallMessageText(Call call) {
        String text = "Исходящий звонок: " + call.getPhoneFrom() + " : " + call.getExt() + "  ->  " + call.getPhoneTo() + " | ";
        if (call.getConnectedTime() != null && call.getConnectedTime() != 0) {
            text += "идет разговор: " + (System.currentTimeMillis() / 1000 - call.getStartTime()) / 60 + ":" +
                    (System.currentTimeMillis() / 1000 - call.getStartTime()) % 60 + " | ";
        } else {
            text += "ожидание " + (System.currentTimeMillis() / 1000 - call.getStartTime()) / 60 + ":" +
                    (System.currentTimeMillis() / 1000 - call.getStartTime()) % 60 + " | ";
        }
        return text;
    }


}
