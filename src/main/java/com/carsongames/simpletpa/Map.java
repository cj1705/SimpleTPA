package com.carsongames.simpletpa;

import java.util.HashMap;
import java.util.UUID;


/*
    This could just be defined in SimpleTPA.java, without a nested class as just
    playermap = new Hashmap<>(); However I won't change it in fear of breaking
    stuff
 */
public class Map {
    public  static HashMap<String,String> playermap= new HashMap<>();
    //sent->init
}
