package ru.mango.server.events.call.impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.mango.common.model.vpbx.Call;
import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.common.model.vpbx.CallEntryResult;
import ru.mango.server.data.model.Account;
import ru.mango.server.events.call.CallEventHandler;
import ru.mango.server.service.AccountService;
import ru.mango.server.service.CallService;
import ru.mango.server.service.TelegramService;

import java.util.Optional;


@Component
public class CallEventHandlerImpl implements CallEventHandler {

    private final Logger logger = LoggerFactory.getLogger(CallEventHandlerImpl.class);

    private final CallService callService;
    private final TelegramService telegramService;
    private final AccountService accountService;

    public CallEventHandlerImpl(CallService callService,
                                TelegramService telegramService,
                                AccountService accountService) {
        this.callService = callService;
        this.telegramService = telegramService;
        this.accountService = accountService;
    }

    @Override
    public void handle(Call call) {
        logger.info("invoke handle call {}", call);
        callService.saveCall(call);
        if (call.getCallDirection() == CallDirection.inbound && call.getEntryResult() == CallEntryResult.MISSED) {
            notifyMissedCall(call);
        }
    }

    private void notifyMissedCall(Call call) {
        Account accountSettings = getAccountSettings(call.getVpbxApiKey());
        String text = getMissedCallText(call);
        telegramService.sendMessage(accountSettings.getGroupCharId(), text);
    }

    private String getMissedCallText(Call call) {
        String text = "Пропущенный звонок от " + call.getPhoneFrom();
        if (!StringUtils.isEmpty(call.getExt())) {
            text += " : " + call.getExt();
        } else {
            text += " : IVR";
        }
        return text;
    }


    private Account getAccountSettings(String vpbxApiKey) {
        return accountService.findAccountByVpbxApiKey(vpbxApiKey).orElseThrow(RuntimeException::new);
    }
}
