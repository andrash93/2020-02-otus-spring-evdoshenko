package ru.mango.common.dto.telegram;

public class TelegramMessageDto {

    private Long chatId;
    private String text;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TelegramMessageDto{" +
                "chatId=" + chatId +
                ", text='" + text + '\'' +
                '}';
    }
}
