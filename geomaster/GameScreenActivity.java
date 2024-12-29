package com.application.geomaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GameScreenActivity extends AppCompatActivity {
    int currentQuestionIndex;
    int totalCorrect;
    int totalQuestions;

    int life =3;

    Button a;
    Button b;
    Button c;
    Button d;
    TextView q;
    RadioGroup options;
    Button submit ;

    TextView score;

    RatingBar lives;
    FrameLayout pauseOverlay;
    int diff;
    ArrayList<Question> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_screen);
        a = findViewById(R.id.radioButtonA);
        b = findViewById(R.id.radioButtonB);
        c = findViewById(R.id.radioButtonC);
        d = findViewById(R.id.radioButtonD);
        q = findViewById(R.id.questionText);
        lives = findViewById(R.id.ratingBar);
        options = findViewById(R.id.radioGroup);
        score = findViewById(R.id.scoreBoard);

        Button btn = findViewById(R.id.pauseButton);
        pauseOverlay = findViewById(R.id.pauseOverlay);

        btn.setOnClickListener(v -> showPauseMenu());

        // Handle button clicks inside the pause menu
        pauseOverlay.findViewById(R.id.continueButton).setOnClickListener(v -> resumeGame());
        pauseOverlay.findViewById(R.id.newGameButton).setOnClickListener(v -> newGame());
        pauseOverlay.findViewById(R.id.homeButton).setOnClickListener(v -> goHome());

        startNewGame();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_game_screen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void startNewGame(){
        diff = getIntent().getIntExtra("diff", 0);
        loadQuestionsFromRaw();
        totalCorrect = 0;
        currentQuestionIndex = -1;
        totalQuestions = questions.size();
        continueGame();
    }

    public void continueGame() {
        if(questions.isEmpty()){
            printResult("There is no question left!!!");
            return;
        }
        else{
            currentQuestionIndex = generateRandomNumber(questions.size());

        }
        Question currentQuestion = getCurrentQuestion();
        a.setText(currentQuestion.answer0);
        b.setText(currentQuestion.answer1);
        c.setText(currentQuestion.answer2);
        d.setText(currentQuestion.answer3);
        q.setText(currentQuestion.questionText);
        score.setText("Score: " + totalCorrect);
        lives.setRating(life);
        submit = findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = options.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    // No radio button selected, show warning
                    findViewById(R.id.warningText).setVisibility(View.VISIBLE);

                    // Hide the warning after 1 second (1000 milliseconds)
                    new Handler(Looper.getMainLooper()).postDelayed(() -> findViewById(R.id.warningText).setVisibility(View.GONE), 1000);
                } else {
                    int playerChoice = 0;
                    if (selectedId == R.id.radioButtonA) {
                        playerChoice = 0;
                    } else if (selectedId == R.id.radioButtonB) {
                        playerChoice = 1;
                    } else if (selectedId == R.id.radioButtonC) {
                        playerChoice = 2;
                    } else if (selectedId == R.id.radioButtonD) {
                        playerChoice = 3;
                    }
                    currentQuestion.playerAnswer = playerChoice;
                    if (currentQuestion.isCorrect()) {
                        totalCorrect += 1;
                    } else {
                        life -= 1;
                        for (int i = 0; i < options.getChildCount(); i++) {
                            Button radioButton = (Button) options.getChildAt(i);
                            if (radioButton.getId() == selectedId) {
                                // Reset the text color of the unselected buttons (optional)
                                radioButton.setBackgroundColor(Color.parseColor("#FF0000"));
                                break;
                            }
                        }
                        if (life == 0) {
                            printResult("You have no life left!!!");
                            return;
                        }

                    }
                    int correctId;
                    if (currentQuestion.correctAnswer == 0) {
                        correctId = R.id.radioButtonA;
                    } else if (currentQuestion.correctAnswer == 1) {
                        correctId = R.id.radioButtonB;
                    } else if (currentQuestion.correctAnswer == 2) {
                        correctId = R.id.radioButtonC;
                    } else if (currentQuestion.correctAnswer == 3) {
                        correctId = R.id.radioButtonD;
                    } else {
                        correctId = 0;
                    }
                    for (int i = 0; i < options.getChildCount(); i++) {
                        Button radioButton = (Button) options.getChildAt(i);
                        if (radioButton.getId() == correctId) {
                            // Reset the text color of the unselected buttons (optional)
                            radioButton.setBackgroundColor(Color.parseColor("#00FF00"));
                            break;
                        }
                    }options.clearCheck();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    for (int i = 0; i < options.getChildCount(); i++) {
                        Button radioButton = (Button) options.getChildAt(i);
                        if (radioButton.getId() == selectedId) {
                            // Reset the text color of the unselected buttons (optional)
                            radioButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            break;
                        }
                    }
                    for (int i = 0; i < options.getChildCount(); i++) {
                        Button radioButton = (Button) options.getChildAt(i);
                        if (radioButton.getId() == correctId) {
                            // Reset the text color of the unselected buttons (optional)
                            radioButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            break;
                        }
                    }
                    questions.remove(currentQuestion);
                    continueGame();
                    }, 1000);
                }
            }
    });

    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }
    public int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }
    public void printResult(String output){
        Intent intent = new Intent(GameScreenActivity.this, EndGameActivity.class);
        int highScore = readFirstLineAsInt(this);
        if (totalCorrect>highScore){
            highScore = totalCorrect;
            writeToFile(this,totalCorrect);
        }
        intent.putExtra("SCORE", totalCorrect); // Replace `currentScore` with the actual score
        intent.putExtra("OUTPUT",output);
        intent.putExtra("HIGH_SCORE", highScore); // Replace `highScore` with the stored high score
        startActivity(intent);
        finish();
    }
    public void loadQuestionsFromRaw() {
        String fileName;
        if (diff == 2){
            fileName = "hardQuestions.csv";
        }
        else if(diff == 1){
            fileName = "mediumQuestions.csv";
        }
        else{
            fileName = "easyQuestions.csv";
        }
        try (InputStream inputStream = getAssets().open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            // Skip the header row
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String question = data[0];
                String option1 = data[1];
                String option2 = data[2];
                String option3 = data[3];
                String option4 = data[4];
                int correctAnswerIndex = Integer.parseInt(data[5]);
                questions.add(new Question(question, option1, option2, option3, option4, correctAnswerIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int readFirstLineAsInt(Context context) {
        int number = 0;  // Default value if no number is found
        String fileName;
        if (diff == 0){
            fileName = "easyHighscore.txt";
        }
        else if(diff == 1){
            fileName = "mediumHighscore.txt";
        }
        else{
            fileName = "hardHighscore.txt";
        }
        File directory = context.getExternalFilesDir(null); // null means default directory
        if (directory != null) {
            File file = new File(directory, fileName);

            try (FileInputStream fis = new FileInputStream(file);
                 DataInputStream dis = new DataInputStream(fis)) {
                // Read the integer from the file
                number = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return number;

    }
    public void writeToFile(Context context, int data) {
        String filename;
        if (diff == 0){
            filename = "easyHighscore.txt";
        }
        else if(diff == 1){
            filename = "mediumHighscore.txt";
        }
        else{
            filename = "hardHighscore.txt";
        }
        File directory = context.getExternalFilesDir(null);  // null means default directory
        if (directory != null) {
            File file = new File(directory, filename);

            try (FileOutputStream fos = new FileOutputStream(file);
                 DataOutputStream dos = new DataOutputStream(fos)) {
                // Write the high score integer to the file
                dos.writeInt(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showPauseMenu() {
        pauseOverlay.setVisibility(View.VISIBLE); // Show the overlay
    }

    private void resumeGame() {
        pauseOverlay.setVisibility(View.GONE); // Hide the overlay and resume the game
    }

    private void newGame() {
        // Start a new game (replace with actual logic)
        pauseOverlay.setVisibility(View.GONE);
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
        finish();
    }

    private void goHome() {
        // Navigate to the home screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}