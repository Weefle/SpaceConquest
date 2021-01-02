package com.company;

import java.util.Random;

public class Bateau {

   /* 3 La classe Bateau
    Les bateaux sont représentés par la classe Bateau.
            3.1 Attributs
    Un bateau est caractérisé par :
            – un attribut depart de type Port correspondant au port d’où le bateau est parti ;
– un attribut arrivee de type Port correspondant au port où le bateau a accosté (si le bateau est en
            mer cet attribut doit valoir null) ;
– un attribut enMer de type boolean précisant si le bateau est en mer (true) ou s’il est accosté sur un
    port (false).
            3.2 Constructeur
    La classe Bateau dispose :
            – d’un constructeur vide qui crée un bateau en mer, sans départ et sans arrivée (null) ;
– d’un constructeur avec pour paramètre un Port, qui réserve un quai et qui crée un bateau,
    positionné sur le port (arrivée est égal à ce port) si cela est possible ou sinon en mer.
            3.3 Méthode accoster
    La classe Bateau dispose de la méthode public void accoster(Port a). Cette méthode consiste à
    réserver un quai au port a et à y accoster le bateau si cela est possible. Dans ce cas, a deviendra le
    port d’arrivée du bateau.
3.4 Méthode quitter
    La classe Bateau dispose d’une méthode public void quitter(). Cette méthode consiste à libérer le
    quai occupé par le bateau (s’il est sur un quai) et à faire partir le bateau (n’oubliez pas de mettre à
    jour les attributs depart et arrivee).
            3.5 Méthode distance
    Afin d’évaluer la distance d’une escale, la classe Bateau dispose de la méthode public float distance().
    Cette méthode retourne la distance euclidienne (la racine de la somme des carrés des abscisses et
            des ordonnées) entre le port de départ et le port d’arrivée.
    Bien entendu, l’appel à cette méthode n’a de sens qui si le bateau est accosté sur un port et que le
    port de départ et d’arrivée sont connus. La méthode doit retourner -1 dans les cas contraires. Pour
    calculer une racine carrée en JAVA, utiliser l’instruction float racine = (float) Math.sqrt(a) qui
    retourne la racine carré de a*/

    Port depart, arrivee;
    boolean enMer;

    public Bateau() {
        this.depart = null;
        this.arrivee = null;
        this.enMer = true;
    }

    public Bateau(Port arrivee) {

        if(accoster(arrivee)){
            this.depart = null; //TODO par quoi remplacer le port de depart ?
        }else{
            this.depart = null;
            this.arrivee = null;
            this.enMer = true;
        }

    }

    public boolean accoster(Port a){


        if(a.ajouterBateau()){

            this.arrivee = a;
            this.enMer = false;
            return true;

        }
return false;
    }

    public void quitter(){



    }

    public float distance(){

       float racine = (float) Math.sqrt(Math.pow(arrivee.x - depart.x, 2) + Math.pow(depart.y - arrivee.y, 2));

        System.out.println(racine);
        return racine;
    }

}
