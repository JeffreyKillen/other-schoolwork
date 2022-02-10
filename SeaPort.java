/*
 * FileName: SeaPort.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 12/15/19
 * Purpose: This class defines the Seaport objects that will be used in the
 *  SeaPort program. This class contains for ArrayList data structures for 
 *  holding Dock objects, Ship objects, and Person objects. An overriden
 *  toSting() method allows all stored objects toString methods to be traversed,
 *  which results in the entirety tree to be displayed.
 */
package seaportprogram;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.*;

public class SeaPort extends Thing {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private ArrayList<Dock> docks = new ArrayList<>();
    private ArrayList<Ship> que = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Person> persons = new ArrayList<>();
    
    public ConcurrentHashMap personsBySkill;
    public ArrayBlockingQueue<Dock> dockDispenser = new ArrayBlockingQueue<>(16, false);
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public SeaPort(Scanner sc) {
        
        //Different from super(sc)
        this.name = sc.next();
        this.index = sc.nextInt();
        this.parent = null;
        
    } // end of public SeaPort(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    //Methods
    public ArrayList<Dock> getDocks() {
        
        //getter
        
        return this.docks;
        
    } // end of public ArrayList<Dock> getDocks() {
    
    public ArrayList<Ship> getShips() {
        
        //getter
        
        return this.ships;
        
    } // end of public ArrayList<Ship> getShips() {
    
    public ArrayList<Ship> getQue() {
        
        //getter
        
        return this.que;
        
    } // end of public ArrayList<Ship> getQue() {
    
    public ArrayList<Person> getPersons() {
        
        //getter
        
        return this.persons;
        
    } // end of public ArrayList<Person> getPersons() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //This method starts a traversal of the Seaport's data structures
        //It is used to display the contents of the tree to the user
        
        String st = "\n\nSeaPort: " + super.toString() + "\n";
        for(Dock md: docks) st += "\n" + md + "\n";
        st += "\n\n --- List of all ships in que:";
        for(Ship ms: que) st += "\n >" + ms;
        st += "\n\n --- List of all ships:";
        for(Ship ms: ships) st += "\n >" + ms;
        st += "\n\n --- List of all persons:";
        for(Person mp: persons) st += "\n >" + mp;
        
        return st;
        
    } // end of public String toSTring() {
    
/////////////////////////////////////////////////////////////////////////////////
    
} // end of public class SeaPort extends Thing {
