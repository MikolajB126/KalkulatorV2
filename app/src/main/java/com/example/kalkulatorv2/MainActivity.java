package com.example.kalkulatorv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    String currentInput = "";
    double firstNumber = 0;
    Operation currentOperation = Operation.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);

        GridLayout grid = findViewById(R.id.buttonGrid);

        for (int i = 0; i < grid.getChildCount(); i++) {
            View child = grid.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(this::onButtonClick);
            }
        }
    }

    private void onButtonClick(View v) {
        Button btn = (Button) v;
        String text = btn.getText().toString();

        switch (text) {
            case "C":
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    screen.setText(currentInput);
                }
                break;
            case "CE":
                currentInput = "";
                firstNumber = 0;
                currentOperation = Operation.NONE;
                screen.setText("");
                break;
            case "+":
                setOperation(Operation.ADD);
                break;
            case "-":
                setOperation(Operation.SUBTRACT);
                break;
            case "*":
                setOperation(Operation.MULTIPLY);
                break;
            case "/":
                setOperation(Operation.DIVIDE);
                break;
            case "=":
                calculate();
                break;
            default:
                currentInput += text;
                screen.setText(currentInput);
        }
    }

    private void setOperation(Operation op) {
        if (!currentInput.isEmpty()) {
            if (currentOperation != Operation.NONE) {
                calculate();
            } else {
                firstNumber = Double.parseDouble(currentInput);
            }
            currentOperation = op;
            currentInput = "";
        } else if (currentOperation != Operation.NONE) {
            currentOperation = op;
        }
    }

    private void calculate() {
        if (currentInput.isEmpty() || currentOperation == Operation.NONE) return;

        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        switch (currentOperation) {
            case ADD: result = firstNumber + secondNumber; break;
            case SUBTRACT: result = firstNumber - secondNumber; break;
            case MULTIPLY: result = firstNumber * secondNumber; break;
            case DIVIDE:
                if (secondNumber == 0) { screen.setText("Error"); return; }
                result = firstNumber / secondNumber; break;
            default: break;
        }

        if (result == (long) result) {
            currentInput = String.valueOf((long) result);
        } else {
            currentInput = String.valueOf(result);
        }

        screen.setText(currentInput);
        firstNumber = result;
        currentOperation = Operation.NONE;
    }
}
