/**
 * File: SecurityQuestionProvider.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Provides random security questions.
 */
package dataproviders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import utils.JsonDataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecurityQuestionProvider {
    private final List<String> questions;
    private final List<String> backupQuestions;
    private int rounds;

    private static final String FILE_PATH = "/testdata/securityQuestions.json";
    private static SecurityQuestionProvider instance;

    private SecurityQuestionProvider() {
        questions = new ArrayList<>();
        backupQuestions = new ArrayList<>();
        JsonDataReader jsonDataReader = new JsonDataReader();
        rounds = 0;

        JsonElement jsonElement = jsonDataReader.readJsonFile(FILE_PATH);

        if (jsonElement != null) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray questionArray = jsonObject.getAsJsonArray("questions");
            if (questionArray != null) {
                for (JsonElement questionElement : questionArray) {
                    questions.add(questionElement.getAsString());
                    backupQuestions.add(questionElement.getAsString());
                }
            }
        }
    }

    public static synchronized SecurityQuestionProvider getInstance() {
        if (instance == null) {
            instance = new SecurityQuestionProvider();
        }
        return instance;
    }

    public String getRandomQuestion() {
        if (questions.isEmpty()) {
            if (rounds >= backupQuestions.size()) {
                rounds = 0;
            }
            questions.addAll(backupQuestions);
            rounds++;
        }
        int randomIndex = new Random().nextInt(questions.size());
        return questions.remove(randomIndex);
    }

    public static String getQuestion() {
        return getInstance().getRandomQuestion();
    }
}
