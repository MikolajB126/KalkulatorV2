package com.example.kalkulatorv2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayTextView;

    private String currentInput = "0";
    private double accumulator = 0;
    private Operation currentOperation = Operation.NONE;
    private boolean resetInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = findViewById(R.id.screen);
        displayTextView.setText(currentInput);
    }

    public void CalcButton(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();

        if (isDigitOrDot(key)) {
            if (resetInput) {
                currentInput = "";
                resetInput = false;
            }
            if (currentInput.equals("0") && !key.equals(".")) {
                currentInput = key;
            } else {
                currentInput += key;
            }
            displayTextView.setText(currentInput);

        } else if (key.equals("=")) {
            executeOperation();
            displayTextView.setText(String.valueOf(accumulator));
            currentInput = String.valueOf(accumulator);
            currentOperation = Operation.NONE;
            resetInput = true;

        } else {
            executeOperation();
            currentOperation = Operation.operationFromKey(key);
            resetInput = true;
        }
    }

    private void executeOperation() {
        double value = Double.parseDouble(currentInput);
        switch (currentOperation) {
            case NONE:
                accumulator = value;
                break;
            case ADD:
                accumulator += value;
                break;
            case SUBSTRACT:
                accumulator -= value;
                break;
            case MULTIPLY:
                accumulator *= value;
                break;
            case DIVIDE:
                if (value != 0) {
                    accumulator /= value;
                } else {
                    accumulator = 0;
                }
                break;
        }
    }

    private boolean isDigitOrDot(String key) {
        return key.matches("[0-9\\.]");
    }
}
