package diary_models;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String... args) {
        Diary diary = diaryObject();

        System.out.println("Diary is locked!");
        unlockDiary(diary);
    }

    private static void menu(Diary diary) {
        System.out.println("""
                1. Write contents
                2. View your contents
                3. Delete contents
                4. Exit
                """);

        switch (Integer.parseInt(input.next())) {
            case 1 -> writeIntoDiary(diary);
            case 2 -> viewContents(diary);
            case 3 -> deleteContents(diary);
            case 4 -> System.exit(0);
        }
    }

    private static void deleteContents(Diary diary) {
        System.out.println("""
                1. Delete a content
                2. Delete All contents
                """);

        switch (Integer.parseInt(input.next())) {
            case 1 -> delete(diary);
            case 2 -> deleteAll(diary);
            case 3 -> menu(diary);
        }

    }

    private static void deleteAll(Diary diary) {
        diary.deleteAll();
        System.out.println("All contents deleted successfully");
        deleteContents(diary);
    }

    private static void delete(Diary diary) {
        System.out.println("Enter the ID or Topic of the content to delete");
        diary.delete(input.nextLine());
        deleteContents(diary);
    }

    private static void viewContents(Diary diary) {
        System.out.println("Enter either the content ID or Topic");
        System.out.println(diary.getEntry(input.nextLine()));

        System.out.println("""
                1. View more
                2. Back
                """);
        switch (Integer.parseInt(input.next())) {
            case 1 -> viewContents(diary);
            case 2 -> menu(diary);
        }
    }

    private static Diary diaryObject() {
        System.out.println("Welcome to my DEAR DIARY");
        System.out.println("Enter your name to register");
        String name = input.nextLine();

        System.out.println("\nEnter password to register");
        String password = input.nextLine();

        if (name == null || password == null) {
            throw new NullPointerException("Name and Password must be entered");
        } else {
            Diary diary = new Diary(name, password);
            System.out.printf("""
                    Congratulations %s!
                    Your diary was created successfully!
                    """, name);
            return diary;
        }
    }

    private static void unlockDiary(Diary diary) {
        System.out.println("\nEnter password to unlock diary");
        diary.unlocked(input.next());
        if (!diary.isLocked()) {
            System.out.println("Diary opened successfully\n");
            menu(diary);
        } else {
            System.out.println("input correct password\n");
            unlockDiary(diary);
        }
    }

    private static void writeIntoDiary(Diary diary) {
        DTO dto = new DTO();
        input.nextLine();
        System.out.println("Enter topic of your entry");
        dto.setTopic(input.nextLine());

        System.out.println("Enter body of your entry");
        dto.setBody(input.nextLine());

        diary.write(dto.getTopic(), dto.getBody());
        System.out.println("Entry entered successfully\n");

        System.out.println("""
                1. Update content topic
                2. Update content body
                3. Back
                """);
        switch (Integer.parseInt(input.next())) {
            case 1 -> updateTopic(diary);
            case 2 -> updateBody(diary);
            case 3 -> menu(diary);
        }
    }

    private static void updateBody(Diary diary) {
        System.out.println("Enter your content topic");
        input.nextLine();
        String topic = input.nextLine();

        System.out.println("Enter new content");
        String body = input.nextLine();
        if (topic == null || body == null) {
            throw new NullPointerException("Topic or contents cannot be empty");
        }
        diary.updateEntry(topic, body);
        System.out.println("Contents updated successfully");

        writeIntoDiary(diary);
    }

    private static void updateTopic(Diary diary) {
        System.out.println("Enter your old topic");
        input.nextLine();
        String oldTopic = input.nextLine();

        System.out.println("Enter new Topic");
        String newTopic = input.nextLine();
        if (oldTopic == null || newTopic == null) {
            throw new NullPointerException("Old Topic or New Topic cannot be empty");
        }
        diary.updateTopic(oldTopic, newTopic);
        System.out.println("Topic updated successfully");

        writeIntoDiary(diary);
    }
}
