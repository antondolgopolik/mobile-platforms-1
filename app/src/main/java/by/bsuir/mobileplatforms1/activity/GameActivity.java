package by.bsuir.mobileplatforms1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import by.bsuir.mobileplatforms1.ApplicationState;
import by.bsuir.mobileplatforms1.R;
import by.bsuir.mobileplatforms1.service.ResultService;

public class GameActivity extends AppCompatActivity {
    private Button[] buttons;

    private int points;

    private final Random random = new Random();
    private int selectedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Кнопки
        buttons = new Button[]{
                findViewById(R.id.button_1), findViewById(R.id.button_2),
                findViewById(R.id.button_3), findViewById(R.id.button_4)
        };
        // Начальный выбор кнопки
        selectButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ResultService resultService = new ResultService(this);
        resultService.saveResultForUser(ApplicationState.getUsername(), points);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareResult();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareResult() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "I've reached " + points + " points in lab №1!"
        );
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void button1ClickHandler(View view) {
        handleClick(0);
    }

    public void button2ClickHandler(View view) {
        handleClick(1);
    }

    public void button3ClickHandler(View view) {
        handleClick(2);
    }

    public void button4ClickHandler(View view) {
        handleClick(3);
    }

    private void handleClick(int n) {
        if (selectedNumber == n) {
            updatePoints();
            if ((points % 15) == 0) {
                runAnimation();
            }
        }
        selectButton();
    }

    private void updatePoints() {
        points++;
        ((TextView) findViewById(R.id.textView_points)).setText(String.valueOf(points));
    }

    private void runAnimation() {
        findViewById(R.id.imageview_cat).animate().rotationYBy(360).setDuration(1000).start();
    }

    private void selectButton() {
        buttons[selectedNumber].setScaleY(1);
        selectedNumber = random.nextInt(4);
        buttons[selectedNumber].setScaleY(1.2f);
    }
}