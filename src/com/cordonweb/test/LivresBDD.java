package com.cordonweb.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LivresBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "eleves.db";
 
	private static final String TABLE_LIVRES = "table_livres";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_ISBN = "ISBN";
	private static final int NUM_COL_ISBN = 1;
	private static final String COL_TITRE = "Titre";
	private static final int NUM_COL_TITRE = 2;
 
	private SQLiteDatabase bdd;
 
	private MaBaseSQLite maBaseSQLite;
 
	public LivresBDD(Context context){
		//On cr�er la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertLivre(Livre livre){
		//Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_ISBN, livre.getIsbn());
		values.put(COL_TITRE, livre.getTitre());
		//on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_LIVRES, null, values);
	}
 
	public int updateLivre(int id, Livre livre){
		//La mise � jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simplement pr�ciser quel livre on doit mettre � jour gr�ce � l'ID
		ContentValues values = new ContentValues();
		values.put(COL_ISBN, livre.getIsbn());
		values.put(COL_TITRE, livre.getTitre());
		return bdd.update(TABLE_LIVRES, values, COL_ID + " = " +id, null);
	}
 
	public int removeLivreWithID(int id){
		//Suppression d'un livre de la BDD gr�ce � l'ID
		return bdd.delete(TABLE_LIVRES, COL_ID + " = " +id, null);
	}
 
	public Livre getLivreWithTitre(String titre){
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE}, COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
		return cursorToLivre(c);
	}
 
	//Cette m�thode permet de convertir un cursor en un livre
	private Livre cursorToLivre(Cursor c){
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier �l�ment
		c.moveToFirst();
		//On cr�� un livre
		Livre livre = new Livre();
		//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
		livre.setId(c.getInt(NUM_COL_ID));
		livre.setIsbn(c.getString(NUM_COL_ISBN));
		livre.setTitre(c.getString(NUM_COL_TITRE));
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return livre;
	}
	
	public String getPath(){
		return bdd.getPath();
	}
}
