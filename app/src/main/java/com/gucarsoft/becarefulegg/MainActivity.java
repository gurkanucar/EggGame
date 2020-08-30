package com.gucarsoft.becarefulegg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button playBtn;
    Button increaseBtn;
    Button decreaseBtn;
    Button halfMinute;
    Button minute;
    Button infinite;
    TextView playerCounts;
    int countDown=100;
    int minPlayerCounts=2;
    int currentPlayerCounts=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn=findViewById(R.id.play);
        increaseBtn=findViewById(R.id.increase);
        decreaseBtn=findViewById(R.id.decrease);
        playerCounts=findViewById(R.id.playerCounts);
        halfMinute=findViewById(R.id.halfMinute);
        minute=findViewById(R.id.minute);
        infinite=findViewById(R.id.infinite);

        infinite.setBackgroundResource(R.drawable.button_background_selected);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerCounts+=1;
                playerCounts.setText(currentPlayerCounts+"");
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayerCounts>minPlayerCounts){
                    currentPlayerCounts-=1;
                    playerCounts.setText(currentPlayerCounts+"");
                }else{
                    Toast.makeText(MainActivity.this,"Min player count must be 2",Toast.LENGTH_LONG);
                }
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Game.class);
                intent.putExtra("playerCounts", playerCounts.getText().toString());
                String timer = countDown+"";
                intent.putExtra("countDown", timer);
                startActivity(intent);
            }
        });

        halfMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllDeselect();
                countDown=30000;
                halfMinute.setBackgroundResource(R.drawable.button_background_selected);
            }
        });
        minute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllDeselect();
                countDown=60000;
                minute.setBackgroundResource(R.drawable.button_background_selected);
            }
        });
        infinite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllDeselect();
                countDown=100;
                infinite.setBackgroundResource(R.drawable.button_background_selected);
            }
        });

    }
    public void setAllDeselect()
    {
        halfMinute.setBackgroundResource(R.drawable.button_background);
        minute.setBackgroundResource(R.drawable.button_background);
        infinite.setBackgroundResource(R.drawable.button_background);
    }
}
