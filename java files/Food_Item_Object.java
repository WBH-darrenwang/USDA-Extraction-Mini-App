package com.wang.myandroidfirstapplication;

/**
 * Overview:
 *
 * Creates a class object to store the corresponding information to each food item searched
 * Note## Could've used a HashMap to store these values (but there could be more than one two info stored)
 */
public class Food_Item_Object {
    private String name ="", ndbno ="";

    /**
     * Class object initiated with 2 parameters
     * @param name_ name of the food item searched
     * @param ndbno_ the corresponding ndbno number
     */

    public Food_Item_Object(String name_, String ndbno_) {
        name = name_;
        ndbno = ndbno_;
    }

    /**
     * @return the name of the food
     */

    public String getname() {
        return name;
    }

    /**
     * @return the ndbno (food item id –unique to every single food item searched)
     */

    public String getndbno() {
        return ndbno;
    }

    /**
     * @return a String that prints out the name & the corresponding ndbno
     */

    public String toString() {
        return name + ", " + ndbno;
    }
}