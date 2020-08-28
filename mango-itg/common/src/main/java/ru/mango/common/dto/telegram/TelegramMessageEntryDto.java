package ru.mango.common.dto.telegram;

public class TelegramMessageEntryDto {

    private Integer userId;
    private String firstName;
    private String userName;
    private Long chatId;
    private String text;
    private String chatType;
    private boolean leftChat;
    private boolean addToChat;
    private boolean isCallback;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isLeftChat() {
        return leftChat;
    }

    public void setLeftChat(boolean leftChat) {
        this.leftChat = leftChat;
    }

    public boolean isAddToChat() {
        return addToChat;
    }

    public void setAddToChat(boolean addToChat) {
        this.addToChat = addToChat;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public boolean isCallback() {
        return isCallback;
    }

    public void setCallback(boolean callback) {
        isCallback = callback;
    }

    @Override
    public String toString() {
        return "TelegramMessageEntryDto{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", userName='" + userName + '\'' +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", chatType='" + chatType + '\'' +
                ", leftChat=" + leftChat +
                ", addToChat=" + addToChat +
                '}';
    }
}
