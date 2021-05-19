package main.java.bsu.frct.java.lab8.entity;

public class ChatMessage {

    private String message;
    private ChatUser author;
    private long timestamp;
    private String privateMessage;

    public ChatMessage(String message, ChatUser author,
                       long timestamp, String privateMessage) {
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
        this.privateMessage = privateMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatUser getAuthor() {
        return author;
    }

    public void setAuthor(ChatUser author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(String privateMessage) {
        this.privateMessage = privateMessage;
    }
}
