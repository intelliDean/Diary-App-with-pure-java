package diary_models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private String topic;
    private String body;
    private String id;
    private LocalDateTime timeOfEntry;

    public Entry(String topic, String body, String id) {
        this.topic = topic;
        this.body = body;
        this.id = id;
        timeOfEntry = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        String time = DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy, hh:mm:ss a").format(timeOfEntry);
        return String.format("""
                =======================================
                Entry   %s
                Written on %s
                Title:  %s
                Body:   %s
                =======================================
                """, id, timeOfEntry, topic, body);
    }
}
