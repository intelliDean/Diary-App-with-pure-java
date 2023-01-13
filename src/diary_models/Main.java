package diary_models;

import javax.swing.*;

public class Main {
    public static void main(String... args) {
        Diary diary = diaryObject();
        assert diary != null;
        unlockDiary(diary);
    }

    private static void menu(Diary diary) {
        switch (Integer.parseInt(
                inputIn("""
                        1. Write contents
                        2. View your contents
                        3. Delete contents
                        4. Lock diary
                        5. Exit
                        """))) {
            case 1 -> writeIntoDiary(diary);
            case 2 -> viewContents(diary);
            case 3 -> deleteContents(diary);
            case 4 -> {
                diary.locked();
                unlockDiary(diary);
            }
            case 5 -> exit();
        }
    }

    private static void deleteContents(Diary diary) {

        switch (Integer.parseInt(
                inputIn("""
                        1. Delete a content
                        2. Delete All contents
                        3. Back
                        """))) {
            case 1 -> delete(diary);
            case 2 -> deleteAll(diary);
            case 3 -> menu(diary);
        }
    }

    private static void deleteAll(Diary diary) {
        if (diary != null) {
            diary.deleteAll();
            print("All contents deleted successfully");

            deleteContents(diary);
        } else {
            print("No content found");
            main();
        }
    }

    private static void delete(Diary diary) {

        String reply = inputIn("Enter the ID or Topic of the content to delete");

        if (Integer.parseInt(reply) <= diary.size()) {
            diary.delete(reply);
            print("Entry deleted successfully");
            deleteContents(diary);
        } else {
            print("No content found");
        }
    }

    private static void viewContents(Diary diary) {

        switch (Integer.parseInt(
                inputIn("""
                        1. View a content
                        2. View All contents
                        3. View total number of entries
                        4. Back
                        """))) {
            case 1 -> viewOneContent(diary);
            case 2 -> viewAllContents(diary);
            case 3 -> numberOfEntries(diary);
            case 4 -> menu(diary);
        }
    }

    private static void numberOfEntries(Diary diary) {
        if (diary != null) {
            print("You have "+String.valueOf(diary.size())+" entries");
            viewContents(diary);
        }
    }

    private static void viewAllContents(Diary diary) {
        if (diary != null) {
            print(diary.viewAll().toString());
            viewContents(diary);
        }
    }

    private static void viewOneContent(Diary diary) {
        String reply = inputIn("Enter the content ID or Topic");
        if (diary.getEntry(reply) != null) {
            Entry returnedDiary = diary.getEntry(reply);
            print(returnedDiary.toString());
            viewContents(diary);
        } else {
            print("No content found");
            viewContents(diary);
        }
    }

    private static Diary diaryObject() {

        String name = inputIn("""
                Welcome to my DEAR DIARY
                
                Enter your name to register
                """);

        String password = inputIn("""
                       ENTER PASSWORD
                Password must not be less than
                8 characters and must contain,
                numbers, lowercase and uppercase
                letters, and special characters""");

        if (Diary.isValidPassword(password) && name.length() != 0){
            Diary diary = new Diary(name, password);
            print("Congratulations " + name + "!\nYour diary was created successfully");
            return diary;
        } else {
            print("Name and Password is compulsory for registration");
            diaryObject();
        }

//        if (name.length() == 0 || password.length() == 0) {
//            print("Name and Password is compulsory for registration");
//            diaryObject();
//        } else {
//            Diary diary = new Diary(name, password);
//            print("Congratulations " + name + "!\nYour diary was created successfully");
//            return diary;
//        }
        return null;
    }

    private static void unlockDiary(Diary diary) {
        String reply = inputIn("""
                Diary is locked!
                Enter password to unlock diary
                
                Password must not be less than
                8 characters and must contain,
                numbers, lowercase and uppercase
                letters, and special characters
                """);
        if (reply.equals(diary.getPassword())) {
            diary.unlocked(reply);

            print("Diary opened successfully");

            menu(diary);
        } else {
            print("incorrect password\n\nEnter correct password");
            switch (Integer.parseInt(inputIn("""
                    1. Enter correct password
                    2. Exit App
                    """))){
                case 1 -> unlockDiary(diary);
                case 2 -> exit();
            }


        }
    }

    private static void exit() {
        print("Thank You for using our App!");
        System.exit(0);
    }

    private static void writeIntoDiary(Diary diary) {
        DTO dto = new DTO();
        dto.setTopic(inputIn("Enter topic of your entry"));
        dto.setBody(inputIn("Enter body of your entry"));

        if (dto.getTopic().length() != 0) {
            diary.write(dto.getTopic(), dto.getBody());
            print("Entry entered successfully");

            switch (Integer.parseInt(
                    inputIn("""
                            1. Write another content
                            2. View contents
                            3. Update content topic
                            4. Update content body
                            5. Back
                            """))) {
                case 1 -> writeIntoDiary(diary);
                case 2 -> viewContents(diary);
                case 3 -> updateTopic(diary);
                case 4 -> updateBody(diary);
                case 5 -> menu(diary);
            }
        } else {
            print("Topic cannot be empty");
            writeIntoDiary(diary);
        }
    }

    private static void updateBody(Diary diary) {
        String topic = inputIn("Enter content topic");
        String body = inputIn("Enter new content");

        if (topic.length() == 0) {
            print("Topic is compulsory for content update");
            updateBody(diary);
        }
        diary.updateEntry(topic, body);
        print("Contents updated successfully");

        writeIntoDiary(diary);
    }

    private static void updateTopic(Diary diary) {
        String oldTopic = inputIn("Enter your old topic");
        String newTopic = inputIn("Enter new Topic");

        if (oldTopic.length() == 0 || newTopic.length() == 0) {
            print("Topics are very compulsory for update");
        } else {
            diary.updateTopic(oldTopic, newTopic);
            print("Topic updated successfully");

            writeIntoDiary(diary);
        }
    }

    private static void print(String prompt) {
        JOptionPane.showMessageDialog(null, prompt);
    }

    private static String inputIn(String prompt) {
        return JOptionPane.showInputDialog(null, prompt);
    }
}