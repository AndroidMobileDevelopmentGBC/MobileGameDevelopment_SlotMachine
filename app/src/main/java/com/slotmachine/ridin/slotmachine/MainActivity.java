package com.slotmachine.ridin.slotmachine;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.JarURLConnection;
import java.util.Random;

public class MainActivity extends Activity {

    //Stats
    private int playerMoney=0;
    private int winnings=0;
    private int jackpot=0;
    private int turn=0;
    private int playerBet=0;
    private int winNumber=0;
    private int lossNumber=0;
    private String[] spinResult = {" ", " ", " "};
    private int[] spinInt = {0,0,0};
    private int winRatio=0;
    private int lemon=0;
    private int watermelon=0;
    private int heart=0;
    private int cherries=0;
    private int bars=0;
    private int bells=0;
    private int sevens=0;
    private int blanks =0;


    ///Slot Sprites

    private ImageView slot1;
    private ImageView slot2;
    private ImageView slot3;

    private int ImageID;

    private int random1, random2,random3;

    //Buttons Initialized
    private Button ReelButton;
    private Button ResetButton;
    private Button Bet10Button;
    private Button Bet50Button;
    private Button Bet100Button;
    private Button ResetBetButton;
    private Button FinishButton;


    TextView CurrentBet;
    TextView CurrentMoney;
    TextView WinLoseText;
    TextView Jackpot;


    Random rand;

    int[] imageArray = {
            R.drawable.horseshoe,
            R.drawable.lemon,
            R.drawable.watermelon,
            R.drawable.heart,
            R.drawable.cherry,
            R.drawable.bar,
            R.drawable.bell,
            R.drawable.seven
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        rand = new Random();
        slot1 = findViewById(R.id.slot1Image);
        slot2 = findViewById(R.id.slot2Image);
        slot3 = findViewById(R.id.slot3Image);

        CurrentBet = findViewById(R.id.BetID);
        CurrentMoney = findViewById(R.id.currentMoneyID);
        WinLoseText = findViewById(R.id.WinLoseID);
        Jackpot = findViewById(R.id.JackpotID);

        //Reel Button
        ReelButton = findViewById(R.id.reelButton);
        ReelButtonListener reelButtonListener = new ReelButtonListener();
        ReelButton.setOnClickListener(reelButtonListener);

        //ResetButton
        ResetButton = findViewById(R.id.resetButton);
        ResetButtonListener resetButtonListener = new ResetButtonListener();
        ResetButton.setOnClickListener(resetButtonListener);

        Bet10Button = findViewById(R.id.bet10Button);
        Bet10ButtonListener bet10ButtonListener = new Bet10ButtonListener();
        Bet10Button.setOnClickListener(bet10ButtonListener);

        Bet50Button = findViewById(R.id.bet50Button);
        Bet50ButtonListener bet50ButtonListener = new Bet50ButtonListener();
        Bet50Button.setOnClickListener(bet50ButtonListener);

        Bet100Button = findViewById(R.id.bet100Button);
        Bet100ButtonListener bet100ButtonListener = new Bet100ButtonListener();
        Bet100Button.setOnClickListener(bet100ButtonListener);

        FinishButton = findViewById(R.id.QuitButtom);
        QuitButtonListener quitButtonListener = new QuitButtonListener();
        FinishButton.setOnClickListener(quitButtonListener);

        ResetBetButton = findViewById(R.id.ResetBet);
        ResetBetButtonListener resetBetButtonListener = new ResetBetButtonListener();
        ResetBetButton.setOnClickListener(resetBetButtonListener);



        resetFruitTally();
        resetAll();

        updatePlayerStats();


    }

    void updatePlayerStats(){
        if (playerMoney < playerBet || playerBet == 0){
            ReelButton.setEnabled(false);

        }
        else {
            ReelButton.setEnabled(true);
        }
        CurrentBet.setText(""+playerBet);
        CurrentMoney.setText(""+playerMoney);
        Jackpot.setText("" + jackpot);

    }



    void resetAll() {
        playerMoney = 1000;
        winnings = 0;
        jackpot = 5000;
        turn = 0;
        playerBet = 0;
        winNumber = 0;
        lossNumber = 0;
        winRatio = 0;
    }

    /* When this function is called it determines the betLine results.
e.g. Bar - Orange - Banana */
    void Reels() {
        int[] outCome = {0, 0, 0};

        for (int spin = 0; spin < 3; spin++) {
            outCome[spin] = (int)Math.floor((Math.random() * 65) + 1);
           if(checkRange(outCome[spin], 1, 27)){// 41.5% probability
                //Horseshoe
               ImageID = 0;
                blanks++;
            }
            else if (checkRange(outCome[spin], 28, 37)){  // 15.4% probability
               //Lemon
               ImageID = 1;
               lemon++;
           }
           else if (checkRange(outCome[spin], 38, 46)){  // 13.8% probability
                    //Watermelon
                    ImageID = 2;
                    watermelon++;
           }
           else if (checkRange(outCome[spin], 47, 54)){ // 12.3% probability
                   //"Heart";
                    ImageID = 3;
                    heart++;
            }
           else if (checkRange(outCome[spin], 55, 59)){ //  7.7% probability
                   //"Cherry";
                    ImageID = 4;
                    cherries++;
           }
           else if (checkRange(outCome[spin], 60, 62)) {//  4.6% probability
                    //"Bar";
                    ImageID = 5;
                    bars++;
           }
           else if (checkRange(outCome[spin], 63, 64)){ //  3.1% probability
                    // "Bell";
                    ImageID = 6;
                    bells++;
           }
           else if (checkRange(outCome[spin], 65, 65)){ //  1.5% probability
                    //"Seven";
                   ImageID = 7;
                   sevens++;
           }
           spinInt[spin] = ImageID;
        }

        slot1.setImageResource(imageArray[spinInt[0]]);
        slot2.setImageResource(imageArray[spinInt[1]]);
        slot3.setImageResource(imageArray[spinInt[2]]);

    }

    boolean checkRange(double value, int lowerBounds,int  upperBounds) {
        if (value >= lowerBounds && value <= upperBounds)
        {
            return true;
        }
        else{
            return false;
        }
    }

    void determineWinnings() {
        if (blanks == 0) {
            if (lemon == 3) {
                winnings = playerBet * 10;
            } else if (watermelon == 3) {
                winnings = playerBet * 20;
            } else if (heart == 3) {
                winnings = playerBet * 30;
            } else if (cherries == 3) {
                winnings = playerBet * 40;
            } else if (bars == 3) {
                winnings = playerBet * 50;
            } else if (bells == 3) {
                winnings = playerBet * 75;
            } else if (sevens == 3) {
                winnings = playerBet * 100;
            } else if (lemon == 2) {
                winnings = playerBet * 2;
            } else if (watermelon == 2) {
                winnings = playerBet * 2;
            } else if (heart == 2) {
                winnings = playerBet * 3;
            } else if (cherries == 2) {
                winnings = playerBet * 4;
            } else if (bars == 2) {
                winnings = playerBet * 5;
            } else if (bells == 2) {
                winnings = playerBet * 10;
            } else if (sevens == 2) {
                winnings = playerBet * 20;
            } else if (sevens == 1) {
                winnings = playerBet * 5;
            } else {
                winnings = playerBet * 1;
            }
            showWinMessage();
            winNumber++;
        } else {
            showLossMessage();
            lossNumber++;
        }
    }

    void showWinMessage() {
        playerMoney += winnings;
        resetFruitTally();
        checkJackPot();
        WinLoseText.setText("YOU WIN!");
        WinLoseText.setTextColor(Color.WHITE);
    }

    void showLossMessage() {
        playerMoney -= playerBet;
        resetFruitTally();
        WinLoseText.setText("YOU LOSE!");
        WinLoseText.setTextColor(Color.WHITE);
    }

    void checkJackPot() {
    /* compare two random values */
        int jackPotTry = (int)Math.floor(Math.random() * 51 + 1);
        int jackPotWin = (int)Math.floor(Math.random() * 51 + 1);
        if (jackPotTry == jackPotWin) {
            playerMoney += jackpot;
            jackpot = 1000;
        }
    }
    void resetFruitTally() {
        lemon = 0;
        watermelon = 0;
        heart = 0;
        cherries = 0;
        bars = 0;
        bells = 0;
        sevens = 0;
        blanks = 0;
    }

    void resetBet(){
        playerBet = 0;
        updatePlayerStats();
    }

    private class ReelButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){

                Reels();
                determineWinnings();
                updatePlayerStats();
        }
    }

    private class ResetButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            resetAll();
            WinLoseText.setTextColor(Color.BLACK);
            updatePlayerStats();
        }
    }

    private class Bet10ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            playerBet +=10;
            WinLoseText.setTextColor(Color.BLACK);
            updatePlayerStats();
        }
    }

    private class Bet50ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            playerBet +=50;
            WinLoseText.setTextColor(Color.BLACK);
            updatePlayerStats();
        }
    }

    private class Bet100ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            playerBet +=100;
            WinLoseText.setTextColor(Color.BLACK);
            updatePlayerStats();
        }
    }

    private class ResetBetButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            resetBet();
            WinLoseText.setTextColor(Color.BLACK);
        }
    }

    private class QuitButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
           finish();
        }
    }
}

