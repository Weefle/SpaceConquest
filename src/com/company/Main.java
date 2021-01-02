package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    private static ArrayList<Port> ports = new ArrayList<>();
    private static ArrayList<Float> distances = new ArrayList<>();
    public static void main(String[] args) {

        for(int i = 0 ; i<new Random().nextInt(10);i++) {
            ports.add(new Port(new Random().nextInt(10), new Random().nextInt(10)));
        }
        for(Port pp : ports){
            System.out.println(pp.x + " : " + pp.y);
        }

        Bateau bateau = new Bateau();
        for (Port p : ports) {

            /*if (bateau.accoster(p)) {

            }*/
            bateau.arrivee.ajouterBateau();
            bateau.depart.retourneX();
            bateau.depart.retourneY();
            //bateau.accoster();
            distances.add(bateau.distance());

        }
       // chooseBoat();
        for(float nb : distances){
            System.out.println(nb);
        }

       // System.out.println(distances[0]);

    }

    static void chooseBoat(float[] tab)
    {
        int taille = tab.length;
        float tmp = 0;
        for(int i=0; i < taille; i++)
        {
            for(int j=1; j < (taille-i); j++)
            {
                if(tab[j-1] > tab[j])
                {
                    //echanges des elements
                    tmp = tab[j-1];
                    tab[j-1] = tab[j];
                    tab[j] = tmp;
                }

            }
        }
    }

}