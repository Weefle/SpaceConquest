package com.company;

public class Port {

  /* 2 La classe Port
Les ports vont être définis par la classe Port.
2.1 Déclaration et Attributs
La classe Port est définie par :
– des attributs x et y de type réel correspondant à sa position sur une carte (en théorie, il s’agit de
longitude et de latitude, mais nous simplifions pour faciliter le calcul des distances) ;
– d’un attribut quais de type Quais correspondant au gestionnaire de quais du port.
2.2 Constructeurs
La classe Port dispose :
– d’un constructeur qui prend deux paramètres réels x1 et y1 et qui crée un port situé en (x1,y1)
avec un gestionnaire de quais par défaut ;
– d’un constructeur qui prend deux paramètres réels x1 et y1 et un paramètre entier nbQuais
correspondant au nombre de quais du port.
2.3 Méthodes
2.3.1 Accesseurs
La classe Port dispose de deux accesseurs retourneX() et retourneY() qui retournent l’abscisse et
l’ordonnée du port.
2.3.2 Ajout et retrait de bateau
La classe Port possède deux méthodes pour réserver ou libérer des quais pour les bateaux : public
void retirerBateau()et public boolean ajouterBateau(). */

float x, y;
Quais quais;

    public Port(float x, float y) {
        this.x = x;
        this.y = y;
        this.quais = new Quais();
    }

    public Port(float x, float y, int nbQuais) {
        this.x = x;
        this.y = y;
        this.quais = new Quais(nbQuais);
    }

    public float retourneX() {
        return x;
    }

    public float retourneY() {
        return y;
    }

    public boolean ajouterBateau(){

        if(this.quais.quaisOcc < this.quais.nbQuais) {
            this.quais.nbQuais++;
            return true;
        }

        return false;

    }

    public
    void retirerBateau(){

        if(this.quais.quaisOcc > 0){
            this.quais.nbQuais--;
        }

    }


}
