package com.cordonweb.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText editText;
	private Button button;
	private String prenom;
	
	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //r�cup�ration de l'EditText gr�ce � son ID
        editText = (EditText) findViewById(R.id.EditTextPrenom);
        
        //r�cup�ration du bouton gr�ce � son ID
        button = (Button) findViewById(R.id.ButtonEnvoyer);
        
        //on applique un �couteur d'�v�nement au clique sur le bouton
        button.setOnClickListener(
        	new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttonEnvoyerPressed();
				}
			}
        );
        
      //r�cup�ration du bouton gr�ce � son ID
        button = (Button) findViewById(R.id.ButtonException);
        
        //on applique un �couteur d'�v�nement au clique sur le bouton
        button.setOnClickListener(
        	new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttonExceptionPressed();
				}
			}
        );
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void buttonEnvoyerPressed() {
    	//on r�cup�re le texte �crit dans l'EditText
		prenom = editText.getText().toString();

	        //on affiche "Hello votrePrenom ! " dans une petite pop-up qui s'affiche quelques secondes en bas d'�cran
	        Toast.makeText(MainActivity.this, "Hello " + prenom + " !", Toast.LENGTH_LONG).show();

	        /*on affiche "Hello votrePrenom !" dans un textView que l'on a cr�� tout � l'heure
	         * et dont on avait pas pr�cis� la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
	         * De toute fa�on gr�ce � l'ID vous devrez facilement le trouver dans le fichier main.xml
	         */
	        ((TextView)findViewById(R.id.TextViewHello)).setText("Hello " + prenom + " !");
    }
    
    protected void buttonExceptionPressed() {
    	//un petit System.out.println pour voir s'il s'affiche bien
        System.out.println("Nous faisons expr�s de faire apparaitre une exception !");
 
        //on cr�e un String
        String s = "Exception";
 
        //et on essaye de convertir ce String en entier (ce qui ne marchera pas �videmment)
        int i = Integer.parseInt(s);
        Toast.makeText(MainActivity.this, "valeur de i: " + i, Toast.LENGTH_LONG).show();
	}
    
}
