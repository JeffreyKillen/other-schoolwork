/*
 * FileName: Thing.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 12/1/19
 * Purpose: This class is the parent/grandparent class for all data classes in
 *  the SeaPort project. This class establishes the basic data that all classes
 *  will inherit (index, name, parent). Additionally, this class defines the 
 *  bases toString() and compareTo() methods that will be used by all child
 *  classes.
 */
package seaportprogram;

import java.util.*;

public class Thing implements Comparable<Thing> {
    
/////////////////////////////////////////////////////////////////////////////////    
    
    //Variables
    
    Integer index;
    String name;
    Integer parent;
    
    SeaPort mySeaPort;
    
/////////////////////////////////////////////////////////////////////////////////    
    
    //Constructors
    
    //Default
    public Thing() {
        
        //Do not use
        
    } // end ofpublic Thing() {
    
    //Scanner
    public Thing(Scanner sc) {
        
        this.name = sc.next();
        this.index = sc.nextInt();
        this.parent = sc.nextInt();
                
    } // end of public Thing(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods    
    public String getName() {
        
        //getter
        
        return this.name;
        
    } // end of public String getName() {
    
    public Integer getIndex() {
        
        //getter
        
        return this.index;
        
    } // end of public String getIndex() {
    
    public Integer getParent() {
        
        //getter
        
        return this.parent;
        
    } // end of public String getParent() {
    
    public SeaPort getSeaPort() {
        
        //getter
        
        return this.mySeaPort;
        
    } // end of public SeaPort getSeaPort() {
    
    public void setSeaPort(SeaPort sp) {
        
        //setter
        
        this.mySeaPort = sp;
        
    } // end of public void setSeaPort(SeaPort sp) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //Display this object to the user
        
        return this.getName();
        
    } // end of public String toString();
    
    @Override
    public int compareTo(Thing t) {
        
        //This method compares the name field of itself and another Thing
        //using the String.compareTo() method and returns the result.
        
        //This can be used to sort a Collection<Thing> alphabetically by name
        
        
        return this.getName().compareTo(t.getName());
        
    } // end of public int compareTo(Thing t) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    public boolean compareTo(String searchParameter) {
        
        //Custom compareTo() - OR Gate to compare multiple values
        //Children and on another OR gate when they @override
        
        return searchParameter.equals(this.getName());
        
    } // end of public STring compareTo(String searchParameter) {  
    
    public void clearIndex() {
        
        //set index to null
        
        this.index = null;
        
    } // end of public void clearIndex() {
    
/////////////////////////////////////////////////////////////////////////////////    
    
} // end of public class Thing implements Comparable<Thing> {
