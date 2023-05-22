/**
 * File: MockUserDataProvider.java
 * Author: Waruna
 * Created: 5/21/2023
 * Description: Generates mock user data.
 */

package dataproviders;

import java.util.Random;

public class MockUserDataProvider {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "~!@#$%^&*()_+|-=.,<>{}[]:?";
    private static final String INVALID_CHARACTERS = "` £€§±ªº«»¿ç";
    private static final Random RANDOM = new Random();

    public static String generateValidUsername() {
        return generateRandomString(9, LETTERS + NUMBERS);
    }

    public static String generateValidPassword(String username) {
        String password;
        do {
            password = generateRandomString(1, LETTERS) +
                    generateRandomString(1, NUMBERS) +
                    generateRandomString(6, LETTERS + NUMBERS + SPECIAL_CHARACTERS);
        } while (password.equals(username));
        return password;
    }


    public static String generateInvalidUsername() {
        return generateRandomString(1, NUMBERS);
    }

    public static String generateValidEmail() {
        return generateRandomString(5, LETTERS + NUMBERS) + "@" + generateRandomString(5, LETTERS + NUMBERS) + ".com";
    }
    public static String generateShortPassword() {
        return generateRandomString(7, LETTERS + NUMBERS + SPECIAL_CHARACTERS);
    }

    public static String generatePasswordThatMatchesUsername(String username) {
        return username;
    }


    public static String generateMismatchingPassword(String currentPassword) {
        return currentPassword + "mismatch";
    }


    public static String generateInvalidEmail() {
        return generateRandomString(5, LETTERS + NUMBERS) + generateRandomString(5, LETTERS + NUMBERS);
    }

    private static String generateRandomString(int length, String characters) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
