package diary_models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Diary {
    private final List<Entry> entries;
    private boolean isLocked;
    private String password;
    private int idInitializer = 0;

    public Diary(String name, String password) {
        entries = new ArrayList<>();
        this.isLocked = true;
        this.password = password;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlocked(String password) {
        if (this.password.equals(password)) {
            isLocked = false;
        }
    }

    public void locked() {
        isLocked = true;
    }

    public String write(String topic, String body) {
        if (!isLocked) {
            String updateReport = updateEntry(topic, body);
            return Objects.requireNonNullElseGet(updateReport, ()->addNewEntry(topic, body));
        }
        return "Enter password to unlock diary";
    }

    public String updateEntry(String topic, String body) {
        for (Entry entry : entries) {
            if (entry.getTopic().equals(topic)) {
                entry.setBody(body);
                return "Entry Updated successfully";
            }
        }
        return null;
    }

    private String addNewEntry(String topic, String body) {
        String id = String.valueOf(++idInitializer);
        Entry entry = new Entry(topic, body, id);
        entries.add(entry);
        return "Entry added successfully";
    }

    public String getEntryId(String topic) {
        String id = " ";
        for (Entry entry : entries) {
            if (entry.getTopic().equals(topic)) {
                id = entry.getId();
                break;
            }
        }
        return id;
    }

    public int size() {
        return entries.size();
    }

    public Entry getEntry(String searchWith) {
        for (Entry entry : entries) {
            if (entry.getTopic().equals(searchWith)
                    || entry.getId().equals(searchWith)) {
                return entry;
            }
        }
        return null;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
        }
    }

    public void updateTopic(String oldTopic, String newTopic) {
        for (Entry entry : entries) {
            if (entry.getTopic().equals(oldTopic)) {
                entry.setTopic(newTopic);
                break;
            }
        }
    }

    public void delete(String searchWith) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getTopic().equals(searchWith)
                    || entries.get(i).getId().equals(searchWith)) {
                entries.remove(entries.get(i));
                break;
            }
        }
    }

    public List<Entry> viewAll() {
        return entries;
    }
    public void deleteAll() {
        entries.removeAll(viewAll());
    }
}
