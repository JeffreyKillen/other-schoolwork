/*
 * FileName: World.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 12/15/19
 * Purpose: This class serves as the controlling structure for a multi-tree
 * data structure. Taking in a Scanner object to build a tree of <Thing> objects,
 * this class contains the methods to build a tree and search that tree. This
 * class also contains the static methods that a Thing can use to place itself
 * on the tree. There are methods to return a Thing after a search by index.
 * An overriden toString() starts a cascade through the tree structure, returning
 * the entire structure for display to the user.

 * This class can sort the items in the tree. All items can be sorted by name.
 * Ships can be sorted by weight, length, width, and draft.
 */
package seaportprogram;

import java.util.*;
import java.util.concurrent.*;

public class World extends Thing {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private static ArrayList<SeaPort> ports = new ArrayList<SeaPort>();
    private static PortTime time;
    
    //List of all sort variations
    private static String[] sortCriteria = {"All Ships By Name", "All Ships By Weight", "All Ships By Length",
        "All Ships By Width", "All Ships By Draft", "Qued Ships By Name", "Qued Ships By Weight",
        "Qued Ships By Length", "Qued Ships By Width", "Qued Ships By Draft", "Seaports By Name", "Persons By Name",
        "Docks By Name"};
    
    //Whether or not a tree has been built
    private static boolean hasTree = false; //
    
    //StringBuilder to store the results of the search as they hit
    private static StringBuilder sb;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public World(Scanner sc) throws Exception {
        
        //Different than super(sc)
        this.name = "Galactic Sector ZZ9 Plural Z Alpha";
        this.index = 42;
        this.parent = null;
        
        //Build a new tree
        buildTree(sc);
        this.hasTree = true;
        
    } // end of public World(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    //Methods
    private static ArrayList<Ship> getQue() {
        
        //Getter method for seaport.que
        //Can be modified to loop through multiple Seaports
        
        return ports.get(0).getQue();
        
    } // end of public static ArrayList<Ship> getQue() {
    
    private static ArrayList<Ship> getShips() {
        
        //Getter method for seaport.ships
        //Can be modified to loop through multiple Seaports
        
        return ports.get(0).getShips();
        
    } // end of public static ArrayList<Ship> getShips() {
    
    private static ArrayList<Person> getPersons() {
        
        //Getter method for seaport.persons
        
        return ports.get(0).getPersons();
        
    } // end of private static ArrayList<Person> getPersons() {
    
    private static ArrayList<Dock> getDocks() {
        
        //getter method for seaport.docks
        
        return ports.get(0).getDocks();
        
    } // end of private static ArrayList<Dock> getDocks() {
    
    public ArrayList<SeaPort> getPorts() {
        
        //getter
        
        return this.ports;
        
    } // end of public ArrayList<SeaPort> getPorts() {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    public int getTime() {
        
        //getter
        
        return this.time.getTime();
        
    } // end of public int getTime() {

    public static String[] getSortCriteria() {
        
        //A call to this method returns the String[] of different sorting
        //possiblities of the tree. It is maintained here in World
        //so that only one class need be updated if there is a change in the
        //tree later. 
        
        //This method will be called by a JComboBox constructor
        
        return sortCriteria;
        
    } // end of public String[] getSortCriteria() {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    @Override
    public String toString() {
        
        //Overriden methods starts a cascade down the tree, returning the
        //entire structure to the caller
        
        String st = ">>>>> The world:";
        for(SeaPort sp: ports) st += sp;

        return st += "\n";
        
    } // end of public String toString() {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    public static void buildTree(Scanner sc) throws NullPointerException {
        
        //Variables
        HashMap<Integer, Thing> mapOfThings = new HashMap<>(); //to make building the tree O(1)
        
        //While there are tokens
        //Filter the next token
        ////Create the appropriate Thing
        ////Add it to the map
        ////Ditch the index
        ////Add it to the world
        //Skip line
        
        while(sc.hasNext()) {
            
            String token = sc.next();
            
            switch(token) {
                
                case "port": 
                    
                    //Reference the seaport
                    SeaPort sp = new SeaPort(sc);
                    //Add it to the map
                    mapOfThings.put(sp.getIndex(), sp);
                    //Ditch the index
                    sp.clearIndex();
                    //Add it to the world
                    ports.add(sp);
                    
                    break;
                case "dock": 
                    
                    //Reference the dock
                    Dock dk = new Dock(sc);
                    //Add it to the map
                    mapOfThings.put(dk.getIndex(), dk);
                    //Ditch the index
                    dk.clearIndex();
                    //Reference the seaport
                    sp = (SeaPort) mapOfThings.get(dk.getParent());
                    //Add the dock
                    sp.getDocks().add(dk);
                    dk.setSeaPort(sp);

                    break;
                case "ship": 
                    
                    //Reference the ship
                    Ship sh = new Ship(sc);
                    //Add it to the map
                    mapOfThings.put(sh.getIndex(), sh);
                    //Ditch the index
                    sh.clearIndex();
                    //Que or Dock
                    sh.chooseParentByType(mapOfThings.get(sh.getParent()));
                    //Add to list of all ships
                    sh.getSeaPort().getShips().add(sh);
                        
                    break;
                case "pship": 
                    
                    //Reference Ship
                    PassengerShip ps = new PassengerShip(sc);
                    //Add it to the map
                    mapOfThings.put(ps.getIndex(), ps);
                    //Ditch the index
                    ps.clearIndex();
                    //Que or Dock
                    ps.chooseParentByType(mapOfThings.get(ps.getParent()));
                    //Add to list of all ships
                    ps.getSeaPort().getShips().add(ps);
                    
                    break;
                case "cship": 
                    
                    //Reference Ship
                    CargoShip cs = new CargoShip(sc);
                    //Add it to the map
                    mapOfThings.put(cs.getIndex(), cs);
                    //Ditch the index
                    cs.clearIndex();
                    //Que or Dock
                    cs.chooseParentByType(mapOfThings.get(cs.getParent()));
                    //Add to list of all ships
                    cs.getSeaPort().getShips().add(cs);
                    
                    break;
                case "person": 
                    
                    //Reference the person
                    Person pn = new Person(sc);
                    //Add it to the map
                    mapOfThings.put(pn.getIndex(), pn);
                    //Ditch the index
                    pn.clearIndex();
                    //Reference the SeaPort
                    sp = (SeaPort) mapOfThings.get(pn.getParent());
                    //Add it to the list of persons
                    sp.getPersons().add(pn);
                    
                    break;
                case "job":
                    
                    //Reference the job
                    Job jb = new Job(sc);
                    //Add it to the map
                    mapOfThings.put(jb.getIndex(), jb);
                    //Ditch the index
                    jb.clearIndex();
                    //Reference its ship
                    sh = (Ship) mapOfThings.get(jb.getParent());
                    //Add it to its ship
                    sh.addJob(jb);
                    //Reference its SeaPort
                    jb.setSeaPort(sh.getSeaPort());
                    
                    break;
                default: sc.nextLine();
                    //continue;
                
            } // end of switch(token) {            
            
        } // end of while(sc.hasNext()) {
        
        //Data structure is built, prepare it for concurrency
        wakeWorkers();
        
    } // end of public static void makeNextThing(Scanner sc) {
        
    public static String search(String searchParameter) {
        
        /*
         * O(n) depth-first
         * Searches using compareTo OR Gates
         * String s(n).equals(String st) || s(n+1).equals(String st)
         * where s(n) is some stored value in <Thing>
        
         * children - super(st) || s(n).equals(st)
        
         * 
        */
        
        //Buffer for the output
        sb = new StringBuilder();
        
        //Search Seaports
        
        //This first one is tricky, we cannot use super.toString() as that returns the whole tree
        for(SeaPort sp: ports) {
            if(sp.compareTo(searchParameter)) {
                String st = "Seaport: " + sp.getName() + "\n";
                sb.append(st);
            } // end of if(sp.compareTo(searchParameter)) {
            
            //Search Docks, Ships, Persons
            
            //Search Docks
            for(Dock dk: sp.getDocks()) {
                if(dk.compareTo(searchParameter)) {
                    String st = dk.toString() + "\n";
                    sb.append(st);
                } // end of if(dk.compareTo(searchParameter)) {
            } // end of for(Dock dk: sp.docks) {
            
            //Search Ships
            for(Ship sh: sp.getShips()) {
                if(sh.compareTo(searchParameter)) {
                    String st = sh.toString() + "\n";
                    sb.append(st);
                } // end of if(sh.compareTo(searchParameter)) {
            } // end of for(Ship sh: sp.ships) {
        
            //Search Persons
            for(Person ps: sp.getPersons()) {
                if(ps.compareTo(searchParameter)) {
                    String st = ps.toString() + "\n";
                    sb.append(st);
                } // end of if(ps.compareTo(searchParameter)) {
            } // end of for(Person ps: sp.persons) {
            
        } // end of for(SeaPort sp: ports) {
        
        //Negative
        if(sb.length() == 0)
            return "No results were found.";
        
        //Return results
        return "Search Results: \n\n" + sb.toString();
        
    } // end of public static String search(String searchParameter) {
    
    public static String sort(String sortCriteria){
        
        //This method takes in a String representation of a Thing and
        //a value for sorting (e.g. All Ships By Weight)
        //performs the sort and sends the output to user.
        
        //Lambda implementation of Comparator used in sorts
        
        //Buffer for the output
        sb = new StringBuilder();
        
        if(hasTree) {

            switch(sortCriteria) {

                case "All Ships By Weight": 
                    
                    Collections.sort(World.getShips(), (ship1, ship2) -> ship1.getWeightAsDouble().compareTo(ship2.getWeightAsDouble()));
                    
                    for(Ship sh: getShips()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;

                case "Qued Ships By Weight": 
                    
                    System.out.println("Sorting Qued Ships by Weight! \n");

                    Collections.sort(World.getQue(), (ship1, ship2) -> ship1.getWeightAsDouble().compareTo(ship2.getWeightAsDouble()));
                    
                    for(Ship sh: getQue()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "All Ships By Name":
                    
                    Collections.sort(World.getShips());
                    
                    for(Ship sh: getShips()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {
                    
                    break;
                    
                case "All Ships By Length":
                    
                    Collections.sort(World.getShips(), (ship1, ship2) -> ship1.getLengthAsDouble().compareTo(ship2.getLengthAsDouble()));
                    
                    for(Ship sh: getShips()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "All Ships By Width":
                    
                    Collections.sort(World.getShips(), (ship1, ship2) -> ship1.getWidthAsDouble().compareTo(ship2.getWidthAsDouble()));
                    
                    for(Ship sh: getShips()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "All Ships By Draft":
                    
                    Collections.sort(World.getShips(), (ship1, ship2) -> ship1.getDraftAsDouble().compareTo(ship2.getDraftAsDouble()));
                    
                    for(Ship sh: getShips()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "Qued Ships By Name":
                    
                    Collections.sort(World.getQue());
                    
                    for(Ship sh: getQue()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {
                    
                    break;
                    
                case "Qued Ships By Length":
                    
                    Collections.sort(World.getQue(), (ship1, ship2) -> ship1.getLengthAsDouble().compareTo(ship2.getLengthAsDouble()));
                    
                    for(Ship sh: getQue()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "Qued Ships By Width":
                    
                    Collections.sort(World.getQue(), (ship1, ship2) -> ship1.getWidthAsDouble().compareTo(ship2.getWidthAsDouble()));
                    
                    for(Ship sh: getQue()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "Qued Ships By Draft":
                    
                    Collections.sort(World.getQue(), (ship1, ship2) -> ship1.getDraftAsDouble().compareTo(ship2.getDraftAsDouble()));
                    
                    for(Ship sh: getQue()) {
                        
                        sb.append(sh.toString() + "\n");
                        
                    } // end of for(Ship sh: getShips()) {

                    break;
                    
                case "Seaports By Name":
                    
                    Collections.sort(ports);
                    
                    for(SeaPort sp: ports) {
                        
                        sb.append(sp.getName() + "\n");
                        
                    } // end of for(SeaPort sp: ports) {
                    
                    break;
                    
                case "Persons By Name":
                    
                   Collections.sort(World.getPersons());
                    
                    for(Person ps: World.getPersons()) {
                        
                        sb.append(ps.toString() + "\n");
                        
                    } // end of for(Person ps: World.getPersons()) {
                    
                    break;
                    
                case "Docks By Name":
                    
                    Collections.sort(World.getDocks());
                    
                    for(Dock dk: World.getDocks()) {
                        
                        sb.append(dk.toString() + "\n");
                        
                    } // end of for(Person ps: World.getPersons()) {
                    
                    break;
                    
                case "Docks By Occupancy":
                    
                    Collections.sort(World.getDocks(), (dock1, dock2) -> dock1.getHasShip().compareTo(dock2.getHasShip()));
                    
                    for(Dock dk: World.getDocks()) {
                        
                        sb.append(dk.toString() + "\n");
                        
                    } // end of for(Person ps: World.getPersons()) {
                    
                    break;
                    
                default: sb.append("Sorting Error!");
                
                    break;

            } // end of switch(sortCriteria) {
            
            //Return results
            return "Search Results: \n\n" + sb.toString();
            
        } // end of if(hasTree == true) {
        
        return "You must first build a world.\nOpen a file to begin";
        
    } // end of public static String sort(String sortCriteria){

    public static void clear() {
        
        //Resets the world
        
        hasTree = false;
        ports = new ArrayList<SeaPort>();
        
    } // end of public static void clear() {
    
    public static void wakeWorkers() {
        
        //This method prepares the tree for concurrency by organizing the
        //ArrayList<Person> of each SeaPort in a ConcurrentHashMap within
        //that SeaPort, with the Person's skill as a key.
                
        for(SeaPort sp: ports) {
            
            HashMap temp = new HashMap();
            
            for(Person ps: sp.getPersons()) {
                
                temp.put(ps.getSkill(), ps);
                
            } // end of for(Person ps: sp.getPersons()) {
            
            sp.personsBySkill = new ConcurrentHashMap(temp);
            
        } // end of for(SeaPort sp: ports) {
        
        
    } // end of public static void wakeWorkers() {
    
} // end of public class World extends Thing {
