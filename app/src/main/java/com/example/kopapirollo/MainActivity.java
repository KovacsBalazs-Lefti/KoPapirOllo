package com.example.kopapirollo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
public class MainActivity extends AppCompatActivity {

    private ImageView playerChoiceImage, computerChoiceImage;
    private TextView playerChoiceText, computerChoiceText, resultLabel, scoreLabelHuman, scoreLabelMachine;
    private Button rockButton, paperButton, scissorsButton;

    private int playerScore = 0;
    private int computerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerChoiceImage = findViewById(R.id.playerChoiceImage);
        computerChoiceImage = findViewById(R.id.computerChoiceImage);
        playerChoiceText = findViewById(R.id.playerChoiceText);
        computerChoiceText = findViewById(R.id.computerChoiceText);
        resultLabel = findViewById(R.id.resultLabel);
        scoreLabelHuman = findViewById(R.id.ScoreLabelHuman);
        scoreLabelMachine = findViewById(R.id.ScoreLabelMachine);
        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame("rock");
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame("paper");
            }
        });

        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame("scissors");
            }
        });
    }

    private void playGame(String playerChoice) {
        // Játékos választása
        int playerChoiceImageId = getDrawableId(playerChoice);
        playerChoiceImage.setImageResource(playerChoiceImageId);
        playerChoiceText.setText("A te választásod: " + playerChoice);

        // Számítógép választása
        String[] choices = {"rock", "paper", "scissors"};
        int randomIndex = new Random().nextInt(choices.length);
        String computerChoice = choices[randomIndex];
        int computerChoiceImageId = getDrawableId(computerChoice);
        computerChoiceImage.setImageResource(computerChoiceImageId);
        computerChoiceText.setText("A gép választása: " + computerChoice);

        // Eredmény kiszámítása
        String result = calculateResult(playerChoice, computerChoice);
        resultLabel.setText(result);

        // Játékos és számítógép pontszámának frissítése
        updateScore(result);
    }

    private int getDrawableId(String choice) {
        return getResources().getIdentifier(choice, "drawable", getPackageName());
    }

    private String calculateResult(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "Döntetlen!";
        } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "Te nyertél!";
        } else {
            return "A gép nyert!";
        }
    }

    private void updateScore(String result) {
        if (result.equals("Te nyertél!") || result.equals("A gép nyert") ) {
            playerScore++;
        }
        scoreLabelHuman.setText("Ember: " + playerScore);
        scoreLabelMachine.setText("Gép: " + computerScore);

        if (playerScore == 3 || computerScore == 3) {
            showGameOverDialog(result);
        }
    }
    private void showGameOverDialog(String result){
        String title;
        String message;

        if (playerScore == 3){
            title = "Győzelem";
        }else {
            title = "Vereség";
        }
        message = "Szeretne új játékot játszani?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetGame();
            }
        });
        builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    private void resetGame(){
        playerScore = 0;
        computerScore = 0;

        scoreLabelHuman.setText("Ember: 0");
        scoreLabelMachine.setText("Gép: 0");
        resultLabel.setText("");

        playerChoiceImage.setImageResource(R.drawable.rock);
        computerChoiceImage.setImageResource(R.drawable.rock);
        playerChoiceText.setText("A te választásod:");
        computerChoiceText.setText("A gép választása:");
        resetButtons();
    }
    private void resetButtons(){
        rockButton.setEnabled(true);
        paperButton.setEnabled(true);
        scissorsButton.setEnabled(true);
    }

}