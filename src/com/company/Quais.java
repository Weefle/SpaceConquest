package com.company;

public class Quais {

 /*   Le gestionnaire de quais
1 Le gestionnaire de Quais va être défini par la classe Quais.
1.1 Déclaration et attributs
    La classe Quais est caractérisée par :
            – un attribut entier nbQuais correspondant au nombre de quais du port ;
– un attribut entier quaisOcc correspondant au nombre de quais déjà occupés dans le port.
            1.2 Constructeur
    La classe Quais dispose :
            – d’un constructeur vide créant un objet correspondant `a un port initialement vide, avec 3 quais ;
– d’un constructeur prenant un paramètre entier (à savoir, le nombre de quais) et créant un objet
    correspondant à un port initialement vide avec le nombre de quais correspondant (attention, ce
            paramètre doit être positif). Si le paramètre en question est négatif, la construction se fera comme le
    cas précédent.
            1.3 Méthodes
1.3.1 Ajouter bateau
    La classe Quais dispose de la méthode public boolean ajouterBateau() permettant de réserver un
    quai. Il faut noter que le booléen de retour permet de savoir si la réservation a été correctement
    effectuée. Il vaut true si, et uniquement si, la place a été réservée.
    Cette méthode réserve un quai si cela est possible et retourne le booléen correspondant.
    Si la place est réservée, il faut la considérer comme occupée (à vous donc de mettre à jour les
            attributs correspondants).
            1.3.2 Retirer un bateau
    La classe Quais dispose de la méthode public void retraitBateau() permettant de libérer un quai
    lorsqu’un bateau s’en va.*/


    int nbQuais;
    int quaisOcc;

public Quais(){
    this.nbQuais=3;
    this.quaisOcc=0;

}

    public Quais(int nbQuais){

        if(nbQuais>0){
        this.nbQuais = nbQuais;
    }else{
        this.nbQuais=3;
    }

        this.quaisOcc = 0;

    }

}
