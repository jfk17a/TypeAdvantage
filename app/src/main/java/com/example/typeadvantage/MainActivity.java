package com.example.typeadvantage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView result = findViewById(R.id.resultTextView);
        Button compareButton = findViewById(R.id.typeCompareButton);
        final Spinner attackingSpinner = findViewById(R.id.AttackSpinner);
        final Spinner defendingSpinner1 = findViewById(R.id.DefendSpinner1);
        final Spinner defendingSpinner2 = findViewById(R.id.DefendSpinner2);

        ArrayAdapter<String> typesArray = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Types));
        typesArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackingSpinner.setAdapter(typesArray);
        defendingSpinner1.setAdapter(typesArray);
        defendingSpinner2.setAdapter(typesArray);

        compareButton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String attackVal = attackingSpinner.getSelectedItem().toString();
                String defendVal1 = defendingSpinner1.getSelectedItem().toString();
                String defendVal2 = defendingSpinner2.getSelectedItem().toString();

                if(!(attackVal.equals("None") || defendVal1.equals("None"))){
                    TypeAdvantage(attackVal, defendVal1, defendVal2, result);
                } else if (attackVal.equals("None") && !(defendVal1.equals("None"))){
                    result.setText("Please select an attacking type.");
                } else if (!(attackVal.equals("None")) && defendVal1.equals("None")){
                    result.setText("Please select a defending type.");
                } else if (attackVal.equals("None") && defendVal1.equals("None")){
                    result.setText("Please select attacking and defending types.");
                }
            }
        });



    }

    public void TypeAdvantage(String attackVal, String defendVal1, String defendVal2, TextView result) {

        // 0 = immune, 1 = normal damage, 2 = double damage, 3 = half of the damage, 4 = quadruple damage, and 5 = a forth of the damage

        int damageMod1 = TypeCompare(attackVal, defendVal1);
        int damageMod2 = TypeCompare(attackVal, defendVal2);

        int trueDamageMod = TrueValue(damageMod1, damageMod2);

        if (trueDamageMod == 0 && defendVal2.equals("None")){
            result.setText(attackVal + " would deal no damage to " + defendVal1 + " types.");
        } else if (trueDamageMod == 1 && defendVal2.equals("None")){
            result.setText(attackVal + " would deal a normal amount of damage to " + defendVal1 + " types.");
        } else if (trueDamageMod == 2 && defendVal2.equals("None")){
            result.setText(attackVal + " would deal double the damage to " + defendVal1 + " types.");
        } else if (trueDamageMod == 3 && defendVal2.equals("None")) {
            result.setText(attackVal + " would deal half the damage to " + defendVal1 + " types.");
        } else if (trueDamageMod == 0){
            result.setText(attackVal + " would deal no damage to " + defendVal1 + "/" + defendVal2 + " types.");
        } else if (trueDamageMod == 1){
            result.setText(attackVal + " would deal normal amount of damage to " + defendVal1 + "/" + defendVal2 + " types.");
        } else if (trueDamageMod == 2){
            result.setText(attackVal + " would deal double the damage to " + defendVal1 + "/" + defendVal2 + " types.");
        } else if (trueDamageMod == 3){
            result.setText(attackVal + " would deal half the damage to " + defendVal1 + "/" + defendVal2 + " types.");
        } else if (trueDamageMod == 4){
            result.setText(attackVal + " would deal four times the damage to " + defendVal1 + "/" + defendVal2 + " types.");
        } else if (trueDamageMod == 5){
            result.setText(attackVal + " would only deal a fourth of the damage to " + defendVal1 + "/" + defendVal2 + " types.");
        }
    }

    public int TrueValue(int damageMod1, int damageMod2) {
        if(damageMod1 == 0){
            return 0;
        }else if(damageMod1 == 1){
            switch (damageMod2){
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
            }
        }else if(damageMod1 == 2){
            switch (damageMod2){
                case 0:
                    return 0;
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 1;
            }
        }else if(damageMod1 == 3){
            switch (damageMod2){
                case 0:
                    return 0;
                case 1:
                    return 3;
                case 2:
                    return 1;
                case 3:
                    return 5;
            }
        }
        return 0;
    }

    public int TypeCompare(String attackVal, String defendVal) {
        if(attackVal.equals("Normal")){
            switch (defendVal) {
                case "Rock":
                case "Steel":
                    return 3;
                case "Ghost":
                    return 0;
            }
        } else if (attackVal.equals("Fighting")){
            switch (defendVal){
                case "Normal":
                case "Dark":
                case "Ice":
                case "Steel":
                case "Rock":
                    return 2;
                case "Flying":
                case "Fairy":
                case "Psychic":
                case "Bug":
                case "Poison":
                    return 3;
                case "Ghost":
                    return 0;
            }
        } else if (attackVal.equals("Flying")){
            switch (defendVal){
                case "Fighting":
                case "Grass":
                case "Bug":
                    return 2;
                case "Rock":
                case "Electric":
                case "Steel":
                    return 3;
            }
        } else if (attackVal.equals("Poison")){
            switch(defendVal) {
                case "Poison":
                case "Ghost":
                case "Rock":
                case "Ground":
                    return 3;
                case "Steel":
                    return 0;
                case "Grass":
                case "Fairy":
                    return 2;
            }
        }else if (attackVal.equals("Ground")){
            switch (defendVal){
                case "Flying":
                    return 0;
                case "Poison":
                case "Electric":
                case "Fire":
                case "Steel":
                case "Rock":
                    return 2;
                case "Bug":
                case "Grass":
                    return 3;
            }
        }else if (attackVal.equals("Rock")){
            switch (defendVal){
                case "Fighting":
                case "Steel":
                case "Ground":
                    return 3;
                case "Flying":
                case "Ice":
                case "Fire":
                case "Bug":
                    return 2;
            }
        }else if (attackVal.equals("Bug")){
            switch (defendVal){
                case "Fighting":
                case "Fairy":
                case "Fire":
                case "Steel":
                case "Ghost":
                case "Poison":
                case "Flying":
                    return 3;
                case "Grass":
                case "Dark":
                case "Psychic":
                    return 2;
            }
        }else if (attackVal.equals("Ghost")){
            switch (defendVal){
                case "Normal":
                    return 0;
                case "Ghost":
                case "Psychic":
                    return 2;
                case "Dark":
                    return 3;
            }
        }else if (attackVal.equals("Steel")){
            switch (defendVal){
                case "Rock":
                case "Fairy":
                case "Ice":
                    return 2;
                case "Steel":
                case "Electric":
                case "Water":
                case "Fire":
                    return 3;
            }
        }else if (attackVal.equals("Fire")){
            switch (defendVal){
                case "Rock":
                case "Dragon":
                case "Water":
                case "Fire":
                    return 3;
                case "Bug":
                case "Ice":
                case "Grass":
                case "Steel":
                    return 2;
            }
        }else if (attackVal.equals("Water")){
            switch (defendVal){
                case "Ground":
                case "Fire":
                case "Rock":
                    return 2;
                case "Water":
                case "Dragon":
                case "Grass":
                    return 3;
            }
        }else if (attackVal.equals("Grass")){
            switch (defendVal){
                case "Flying":
                case "Dragon":
                case "Grass":
                case "Fire":
                case "Steel":
                case "Bug":
                case "Poison":
                    return 3;
                case "Ground":
                case "Water":
                case "Rock":
                    return 2;
            }
        }else if (attackVal.equals("Electric")){
            switch (defendVal){
                case "Flying":
                case "Water":
                    return 2;
                case "Ground":
                    return 0;
                case "Grass":
                case "Dragon":
                case "Electric":
                    return 3;
            }
        }else if (attackVal.equals("Psychic")){
            switch (defendVal){
                case "Fighting":
                case "Poison":
                    return 2;
                case "Steel":
                case "Psychic":
                    return 3;
                case "Dark":
                    return 0;
            }
        }else if (attackVal.equals("Ice")){
            switch (defendVal){
                case "Flying":
                case "Dragon":
                case "Grass":
                case "Ground":
                    return 2;
                case "Steel":
                case "Ice":
                case "Water":
                case "Fire":
                    return 3;
            }
        }else if (attackVal.equals("Dragon")){
            switch (defendVal){
                case "Steel":
                    return 3;
                case "Dragon":
                    return 2;
                case "Fairy":
                    return 0;
            }
        }else if (attackVal.equals("Dark")){
            switch (defendVal){
                case "Fighting":
                case "Fairy":
                case "Dark":
                    return 3;
                case "Ghost":
                case "Psychic":
                    return 2;
            }
        }else if (attackVal.equals("Fairy")){
            switch (defendVal){
                case "Fighting":
                case "Dark":
                case "Dragon":
                    return 2;
                case "Poison":
                case "Fire":
                case "Steel":
                    return 3;
            }
        }
        return 1;
    }
}
