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
        
      //r�cup�ration du bouton gr�ce � son ID
        button = (Button) findViewById(R.id.ButtonInitBDD);
      //on applique un �couteur d'�v�nement au clique sur le bouton
        button.setOnClickListener(
        	new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttonInitBDDPressed();
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
        String s = "exception";
 
        //et on essaye de convertir ce String en entier (ce qui ne marchera pas �videmment)
        int i = Integer.parseInt(s);
        Toast.makeText(MainActivity.this, "valeur de i: " + i, Toast.LENGTH_LONG).show();
	}
    
    protected void buttonInitBDDPressed() {
    	//Cr�ation d'une instance de ma classe LivresBDD
        LivresBDD livreBdd = new LivresBDD(this);
        
        //Cr�ation d'un livre
        Livre livre = new Livre("123456789", "Programmez pour Android");
 
        //On ouvre la base de donn�es pour �crire dedans
        livreBdd.open();
        
        Toast.makeText(this, livreBdd.getPath(), Toast.LENGTH_LONG).show();
        
        //On ins�re le livre que l'on vient de cr�er
        livreBdd.insertLivre(livre);
 
        //Pour v�rifier que l'on a bien cr�� notre livre dans la BDD
        //on extrait le livre de la BDD gr�ce au titre du livre que l'on a cr�� pr�c�demment
        Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());
        //Si un livre est retourn� (donc si le livre � bien �t� ajout� � la BDD)
        if(livreFromBdd != null){
        	//On affiche les infos du livre dans un Toast
        	Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
        	//On modifie le titre du livre
        	livreFromBdd.setTitre("J'ai modifi� le titre du livre");
        	//Puis on met � jour la BDD
            livreBdd.updateLivre(livreFromBdd.getId(), livreFromBdd);
        }
 
        //On extrait le livre de la BDD gr�ce au nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifi� le titre du livre");
        //S'il existe un livre poss�dant ce titre dans la BDD
        if(livreFromBdd != null){
	        //On affiche les nouvelle info du livre pour v�rifi� que le titre du livre a bien �t� mis � jour
	        Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
	        //on supprime le livre de la BDD gr�ce � son ID
	    	livreBdd.removeLivreWithID(livreFromBdd.getId());
        }
 
        //On essaie d'extraire de nouveau le livre de la BDD toujours gr�ce � son nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifi� le titre du livre");
        //Si aucun livre n'est retourn�
        if(livreFromBdd == null){
        	//On affiche un message indiquant que le livre n'existe pas dans la BDD
        	Toast.makeText(this, "Ce livre n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //Si le livre existe (mais normalement il ne devrait pas)
        else{
        	//on affiche un message indiquant que le livre existe dans la BDD
        	Toast.makeText(this, "Ce livre existe dans la BDD", Toast.LENGTH_LONG).show();
        }
 
        livreBdd.close();
	}
}
