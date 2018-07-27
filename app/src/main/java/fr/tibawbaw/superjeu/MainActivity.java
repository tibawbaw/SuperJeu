package fr.tibawbaw.superjeu;

import android.content.DialogInterface;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    // On déclare les éléments graphiques pour pouvoir les manipuler dans le code java
    private EditText txtNumber;
    private Button btValid;
    private TextView lbResult;
    private ProgressBar pbProgress;
    private TextView lbHistory;

    //On déclare d'autres variables
    private int searchedValue;
    private int score;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Après la création de l'activité, on peut lier les éléments graphiques aux variables créées au dessus
        //R signifie "ressource"
        txtNumber = findViewById(R.id.txtNumber);
        btValid = findViewById(R.id.btValid);
        lbResult = findViewById(R.id.lbResult);
        pbProgress = findViewById(R.id.pbProgress);
        lbHistory = findViewById(R.id.lbHistory);

        //On associe le listener au bouton
        btValid.setOnClickListener(btValidListener);

        init();
    }

    //On créée une fonction d'initialisation pour le lancement d'un nouveau jeu
    private void init() {
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i("DEBUG", "Le nombre à trouver est : " + searchedValue);
        txtNumber.setText("");
        lbResult.setText("");
        score = 0;
        pbProgress.setProgress(score);
        lbHistory.setText("");

        txtNumber.requestFocus();
    }

    //On crée une fonction pour quand le résultat est trouvé
    private void numberFound(){
        lbResult.setText(getString(R.string.Bravo) + searchedValue);

        AlertDialog retryAlert = new AlertDialog.Builder(this).create();
        retryAlert.setTitle(R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessage, score));
        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                init();
            }
        });
        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        retryAlert.show();
    }

    //On crée un listener qui va écouter les évènements sur le bouton btValid
    private View.OnClickListener btValidListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // On vérifie s'il y a une valeur dans txtNumber
            if (txtNumber.getText().toString().isEmpty()) return;

            // txtNumber contient bien une valeur
            //On met à jour la barre de progression
            score = score + 1;
            pbProgress.setProgress(score);

            int enteredValue = Integer.parseInt(txtNumber.getText().toString());

            if (enteredValue == searchedValue) {
                numberFound();
            }
                else if (enteredValue > searchedValue) {
                lbResult.setText(getString(R.string.NbInferieur) + enteredValue);
                lbHistory.setText(lbHistory.getText() + getString(R.string.NotThat) + enteredValue + " !\n");
            }
            else {
                lbResult.setText(getString(R.string.Nbsuperieur) + enteredValue);
                lbHistory.setText(lbHistory.getText() + getString(R.string.NotThat) + enteredValue + " !\n" );
            }

            //On réinitialise la zone de saisie
            txtNumber.setText("");
            txtNumber.requestFocus();
        }
    };

}
