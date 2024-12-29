package com.application.geomaster;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // Get the passed score and high score
        int score = getIntent().getIntExtra("SCORE", 0);
        int highScore = getIntent().getIntExtra("HIGH_SCORE", 0);
        var output = getIntent().getStringExtra("OUTPUT");
        // Update the TextViews
        TextView status = findViewById(R.id.statusText);
        TextView highScoreText = findViewById(R.id.highScoreText);
        TextView playerResultText = findViewById(R.id.playerResultText);


        highScoreText.setText("High Score: " + highScore);
        playerResultText.setText("Your Score: " + score);
        status.setText(output);

        // Retry Button
        Button retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the game (MainActivity in this example)
                Intent intent = new Intent(EndGameActivity.this, Difficulty.class);
                intent.putExtra("isNewGame", false);
                startActivity(intent);
                finish();
            }
        });

        // Home Button
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Home Screen
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}