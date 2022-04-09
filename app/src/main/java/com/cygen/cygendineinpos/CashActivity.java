package com.cygen.cygendineinpos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CashActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    EditText calculationEt;
    TextView oneTv, twoTv, threeTv, fourTv, fiveTv, sixTv, sevenTv, eightTv, nineTv, zeroTv, additionTv, minusTv, multiplyTv, divideTv, dotTv, equalsTv, clearTv;

    float mValueOne, mValueTwo;

    boolean crunchifyAddition, mSubtract, crunchifyMultiplication, crunchifyDivision;

    private int[] numericButtons = {R.id.zeroTv, R.id.oneTv, R.id.twoTv, R.id.threeTv, R.id.fourTv, R.id.fiveTv, R.id.sixTV, R.id.sevenTv, R.id.eightTv, R.id.nineTv};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.additionTv, R.id.minusTv, R.id.multiplyTv, R.id.divideTv};
    // TextView used to display the output
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    // Represent that current state is in error or not
    private boolean stateError;
    // If true, do not allow to add another DOT
    private boolean lastDot;

    TextView totalPayingsTv, totalPayableTv, changeReturnTv;
    Double totalPayable, totalPayings = 0.00, changeReturn = 0.00;

    TextView oneDollarTv, twoDollarTv, fiveDollarTv, tenDollarTv, twentyDollarTv, fiftyDollarTv, hundredDollarTv;

    int result;
    int count = 0;
    int newNumber;

    String results = null;

    Spinner paymentTypeSpinner;
    String[] country = { "Cash", "Card", "Paytm", "Finance", "UBER"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        calculationEt = (EditText) findViewById(R.id.calculationEt);
        totalPayingsTv = (TextView) findViewById(R.id.totalPayingsTv);
        totalPayableTv = (TextView) findViewById(R.id.totalPayableTv);
        changeReturnTv = (TextView) findViewById(R.id.changeReturnTv);

        oneDollarTv = (TextView) findViewById(R.id.oneDollarTv);
        twoDollarTv = (TextView) findViewById(R.id.twoDollarTv);
        fiveDollarTv = (TextView) findViewById(R.id.fiveDollarTv);
        tenDollarTv = (TextView) findViewById(R.id.tenDollarTv);
        twentyDollarTv = (TextView) findViewById(R.id.twentyDollarTv);
        fiftyDollarTv = (TextView) findViewById(R.id.fiftyDollarTv);
        hundredDollarTv = (TextView) findViewById(R.id.hundredDollarTv);

        paymentTypeSpinner = (Spinner) findViewById(R.id.paymentTypeSpinner);

        paymentTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        paymentTypeSpinner.setAdapter(aa);

        totalPayable = 6.35;

        totalPayingsTv.setText("Total Payings : " + totalPayings);
        totalPayableTv.setText("Total Payable : " + totalPayable);
        changeReturnTv.setText("Change Return : " + changeReturn);

        calculationEt.setText("0");

        oneDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                results = calculationEt.getText().toString();

                Log.d("resultsss", results);

                int newNumber = Integer.parseInt(results) + 1;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);


                lastNumeric = true;

                onEqual();

            }
        });

        twoDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("oooooo", "rererre");
                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 2;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });

        fiveDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 5;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });

        tenDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 10;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });

        twentyDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 20;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });

        fiftyDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 50;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });

        hundredDollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results = calculationEt.getText().toString();

                int newNumber = Integer.parseInt(results) + 100;

                Log.d("calllll", "" + newNumber);


//                count++;
                calculationEt.setText("" + newNumber);
                lastNumeric = true;

                onEqual();

            }
        });


//        oneTv = (TextView) findViewById(R.id.oneTv);
//        twoTv = (TextView) findViewById(R.id.twoTv);
//        threeTv = (TextView) findViewById(R.id.threeTv);
//        fourTv = (TextView) findViewById(R.id.fourTv);
//        fiveTv = (TextView) findViewById(R.id.fiveTv);
//        sixTv = (TextView) findViewById(R.id.sixTV);
//        sevenTv = (TextView) findViewById(R.id.sevenTv);
//        eightTv = (TextView) findViewById(R.id.eightTv);
//        nineTv = (TextView) findViewById(R.id.nineTv);
//        zeroTv = (TextView) findViewById(R.id.zeroTv);
//        additionTv = (TextView) findViewById(R.id.additionTv);
//        minusTv = (TextView) findViewById(R.id.minusTv);
//        multiplyTv = (TextView) findViewById(R.id.multiplyTv);
//        divideTv = (TextView) findViewById(R.id.divideTv);
//        clearTv = (TextView) findViewById(R.id.clearTv);
//        equalsTv = (TextView) findViewById(R.id.equalsTv);
//        dotTv = (TextView) findViewById(R.id.dotTv);

        setNumericOnClickListener();
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener();

//        oneTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "1");
//            }
//        });
//
//        twoTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "2");
//            }
//        });
//
//        threeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "3");
//            }
//        });
//
//        fourTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "4");
//            }
//        });
//
//        fiveTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "5");
//            }
//        });
//
//        sixTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "6");
//            }
//        });
//
//
//        sevenTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "7");
//            }
//        });
//
//        eightTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "8");
//            }
//        });
//
//        nineTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "9");
//            }
//        });
//
//        zeroTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + "0");
//            }
//        });
//
//        additionTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (calculationEt == null) {
//                    calculationEt.setText("+");
//                } else {
//                    mValueOne = Float.parseFloat(calculationEt.getText() + "");
//                    crunchifyAddition = true;
//                    calculationEt.setText(null);
//                }
//            }
//        });
//
//        minusTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mValueOne = Float.parseFloat(calculationEt.getText() + "");
//                mSubtract = true;
//                calculationEt.setText(null);
//            }
//        });
//
//        multiplyTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mValueOne = Float.parseFloat(calculationEt.getText() + "");
//                crunchifyMultiplication = true;
//                calculationEt.setText(null);
//            }
//        });
//
//        divideTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mValueOne = Float.parseFloat(calculationEt.getText() + "");
//                crunchifyDivision = true;
//                calculationEt.setText(null);
//            }
//        });
//
//        equalsTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mValueTwo = Float.parseFloat(calculationEt.getText() + "");
//
//                if (crunchifyAddition == true) {
//                    calculationEt.setText(mValueOne + mValueTwo + "");
//                    crunchifyAddition = false;
//                }
//
//                if (mSubtract == true) {
//                    calculationEt.setText(mValueOne - mValueTwo + "");
//                    mSubtract = false;
//                }
//
//                if (crunchifyMultiplication == true) {
//                    calculationEt.setText(mValueOne * mValueTwo + "");
//                    crunchifyMultiplication = false;
//                }
//
//                if (crunchifyDivision == true) {
//                    calculationEt.setText(mValueOne / mValueTwo + "");
//                    crunchifyDivision = false;
//                }
//            }
//        });
//
//        clearTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText("");
//            }
//        });
//
//        dotTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculationEt.setText(calculationEt.getText() + ".");
//            }
//        });

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    /**
     * Find and set OnClickListener to numeric buttons.
     */
    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just append/set the text of clicked button
                TextView button = (TextView) v;
                if (stateError) {
                    // If current state is Error, replace the error message
                    calculationEt.setText(button.getText());
                    stateError = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    calculationEt.append(button.getText());
                }
                // Set the flag
                lastNumeric = true;
            }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.
     */
    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if (lastNumeric && !stateError) {
                    TextView button = (TextView) v;
                    calculationEt.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        // Decimal point
        findViewById(R.id.dotTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    calculationEt.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
        // Clear button
        findViewById(R.id.clearTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculationEt.setText("");  // Clear the screen
                // Reset all the states and flags
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });
        // Equal button
        findViewById(R.id.equalsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    /**
     * Logic to calculate the solution.
     */
    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        Log.d("dfdfdff", "" + "sddssddsd");

        Log.d("dsssddssdd", "" + lastNumeric);
        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = calculationEt.getText().toString();
            // Create an Expression (A class from exp4j library)
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                totalPayingsTv.setText(calculationEt.getText().toString());
                result = (int) expression.evaluate();
                calculationEt.setText("" + result);

                Log.d("dfdfdff", "" + result);

                totalPayingsTv.setText("Total Payings : " + result);

                changeReturn = result - totalPayable;
                changeReturnTv.setText("Change Return : " + changeReturn);
                lastDot = true; // Result contains a dot
            } catch (ArithmeticException ex) {
                // Display an error message
                calculationEt.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }


}
