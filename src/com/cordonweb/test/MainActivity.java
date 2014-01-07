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
        
        //récupération de l'EditText grâce à son ID
        editText = (EditText) findViewById(R.id.EditTextPrenom);
        
        //récupération du bouton grâce à son ID
        button = (Button) findViewById(R.id.ButtonEnvoyer);
        
        //on applique un écouteur d'évènement au clique sur le bouton
        button.setOnClickListener(
        	new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttonEnvoyerPressed();
				}
			}
        );
        
      //récupération du bouton grâce à son ID
        button = (Button) findViewById(R.id.ButtonException);
        
        //on applique un écouteur d'évènement au clique sur le bouton
        button.setOnClickListener(
        	new OnClickListener() {
				@Override
				public void onClick(View v) {
					buttonExceptionPressed();
				}
			}
        );
        
      //récupération du bouton grâce à son ID
        button = (Button) findViewById(R.id.ButtonInitBDD);
      //on applique un écouteur d'évènement au clique sur le bouton
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
    	//on récupère le texte écrit dans l'EditText
		prenom = editText.getText().toString();

	        //on affiche "Hello votrePrenom ! " dans une petite pop-up qui s'affiche quelques secondes en bas d'écran
	        Toast.makeText(MainActivity.this, "Hello " + prenom + " !", Toast.LENGTH_LONG).show();

	        /*on affiche "Hello votrePrenom !" dans un textView que l'on a créé tout à l'heure
	         * et dont on avait pas précisé la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
	         * De toute façon grâce à l'ID vous devrez facilement le trouver dans le fichier main.xml
	         */
	        ((TextView)findViewById(R.id.TextViewHello)).setText("Hello " + prenom + " !");
    }
    
    protected void buttonExceptionPressed() {
    	//un petit System.out.println pour voir s'il s'affiche bien
        System.out.println("Nous faisons exprès de faire apparaitre une exception !");
 
        //on crée un String
        String s = "exception";
 
        //et on essaye de convertir ce String en entier (ce qui ne marchera pas évidemment)
        int i = Integer.parseInt(s);
        Toast.makeText(MainActivity.this, "valeur de i: " + i, Toast.LENGTH_LONG).show();
	}
    
    protected void buttonInitBDDPressed() {
    	//Création d'une instance de ma classe LivresBDD
        LivresBDD livreBdd = new LivresBDD(this);
        
        //Création d'un livre
        Livre livre = new Livre("123456789", "Programmez pour Android");
 
        //On ouvre la base de données pour écrire dedans
        livreBdd.open();
        
        Toast.makeText(this, livreBdd.getPath(), Toast.LENGTH_LONG).show();
        
        //On insère le livre que l'on vient de créer
        livreBdd.insertLivre(livre);
 
        //Pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());
        //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
        if(livreFromBdd != null){
        	//On affiche les infos du livre dans un Toast
        	Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
        	//On modifie le titre du livre
        	livreFromBdd.setTitre("J'ai modifié le titre du livre");
        	//Puis on met à jour la BDD
            livreBdd.updateLivre(livreFromBdd.getId(), livreFromBdd);
        }
 
        //On extrait le livre de la BDD grâce au nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //S'il existe un livre possédant ce titre dans la BDD
        if(livreFromBdd != null){
	        //On affiche les nouvelle info du livre pour vérifié que le titre du livre a bien été mis à jour
	        Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
	        //on supprime le livre de la BDD grâce à son ID
	    	livreBdd.removeLivreWithID(livreFromBdd.getId());
        }
 
        //On essaie d'extraire de nouveau le livre de la BDD toujours grâce à son nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //Si aucun livre n'est retourné
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
