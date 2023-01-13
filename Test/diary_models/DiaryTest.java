package diary_models;

import diary_models.Diary;
import diary_models.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiaryTest {
    Diary diary;

    @BeforeEach
    void setUP() {
        diary = new Diary("Dean", "password");
    }

    @Test
    void diaryIsLocked() {
        assertTrue(diary.isLocked());
    }

    @Test
    void diaryIsUnlocked() {
        diary.unlocked("password");
        assertFalse(diary.isLocked());
    }

    @Test
    void lockDiary() {
        diary.locked();
        assertTrue(diary.isLocked());
    }

    @Test
    void unlockLockAndUnlockDiary() {
        assertTrue(diary.isLocked());

        diary.unlocked("password");
        assertFalse(diary.isLocked());

        diary.locked();
        assertTrue(diary.isLocked());
    }

    @Test
    void addEntryToDiary() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        assertEquals(1, diary.size());
        assertEquals("Entry added successfully", report);
    }

    @Test
    void entryNotAddedMessage() {
        //If diary is locked, you cannot write in it. enter password to unlock
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        assertEquals(0, diary.size());
        assertEquals("Enter password to unlock diary", report);
    }

    @Test
    void cannotHaveTwoEntriesWithTheSameTopic() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        diary.write(topic, body);

        String topic1 = "my topic";
        String body1 = "this is my updated body completely different from the old body";
        diary.write(topic1, body1);

        assertEquals(1, diary.size());
    }

    @Test
    void getAnEntry() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        String topic1 = "new topic";
        String body1 = "this is my new body completely different from the old body";
        String report1 = diary.write(topic1, body1);
        
        assertEquals(2, diary.size());

        Entry entry = diary.getEntry("2");
        assertEquals("this is my new body completely different from the old body", entry.getBody());

        assertEquals("1", diary.getEntryId("my topic"));

        Entry entry1= diary.getEntry("my topic");
        assertEquals("this is my entry body", entry1.getBody());

        assertNull(diary.getEntry("sandwich"));
    }
    @Test
    void changePassword() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        diary.changePassword("password", "Password");
        diary.locked();
        diary.unlocked("Password");

        String topic1 = "new topic";
        String body1 = "this is my new body completely different from the old body";
        String report1 = diary.write(topic1, body1);

        assertEquals(2, diary.size());
    }
    @Test
    void updateTopic() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        diary.updateTopic("my topic", "new topic");
        Entry entry = diary.getEntry("new topic");

        assertEquals("this is my entry body", entry.getBody());
    }
    @Test
    void updateAnEntryBody() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        String topic1 = "new topic";
        String body1 = "this is my entry body";
        String report1 = diary.write(topic1, body1);

        diary.updateEntry("my topic", "this is a new body");

        Entry entry = diary.getEntry("1");

        assertEquals(2, diary.size());
        assertEquals("this is a new body", entry.getBody());
    }

    @Test
    void deleteEntry() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        String topic1 = "new topic1";
        String body1 = "this is my entry body1";
        String report1 = diary.write(topic1, body1);

        String topic2 = "my topic2";
        String body2 = "this is my entry body2";
        String report2 = diary.write(topic2, body2);

        String topic3 = "new topic3";
        String body3 = "this is my entry body3";
        String report3 = diary.write(topic3, body3);

        assertEquals(4, diary.size());

        diary.delete("my topic2");


        assertEquals(3, diary.size());
        assertNull(diary.getEntry("my topic2"));
    }

    @Test
    void removeAllEntries() {
        diary.unlocked("password");
        String topic = "my topic";
        String body = "this is my entry body";
        String report = diary.write(topic, body);

        String topic1 = "new topic1";
        String body1 = "this is my entry body1";
        String report1 = diary.write(topic1, body1);

        String topic2 = "my topic2";
        String body2 = "this is my entry body2";
        String report2 = diary.write(topic2, body2);

        String topic3 = "new topic3";
        String body3 = "this is my entry body3";
        String report3 = diary.write(topic3, body3);

        assertEquals(4, diary.size());

        diary.delete("my topic2");


        assertEquals(3, diary.size());
        assertNull(diary.getEntry("my topic2"));

        diary.deleteAll();

        assertEquals(0, diary.size());
        assertNull(diary.getEntry("new topic1"));
    }


}