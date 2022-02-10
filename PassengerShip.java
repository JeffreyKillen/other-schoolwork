/*
 * FileName: PassengerShip.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 11/17/19
 * Purpose: This class is a representation of a passengership for the
 * Seaport program. Addtional fields are defined for passengers, rooms, and
 * occupancy. Additional methods are defined to get the new fields. A compareTo()
 * and toString() method are overriden to account for these additional fields
 * in search, and to display this ship to the user.
 */
package seaportprogram;

import java.util.*;

public class PassengerShip extends Ship {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private Integer numberOfOccupiedRooms;
    private Integer numberOfPassengers;
    private Integer numberOfRooms;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public PassengerShip(Scanner sc) {
        
        super(sc);
        numberOfOccupiedRooms = sc.nextInt();
        numberOfPassengers = sc.nextInt();
        numberOfRooms = sc.nextInt();
        
    } // end of public PassengerShip(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    public String getNumberOfOccupiedRooms() {
        
        //getter
        
        return this.numberOfOccupiedRooms.toString();
        
    } // end of public String getNumberOfOccupiedRooms() {
    
    public String getNumberOfPassengers() {
        
        //getter
        
        return this.numberOfPassengers.toString();
        
    } // end of public String getNumberOfPassengers() {
    
    public String getNumberOfRooms() {
        
        //getter
        
        return this.numberOfRooms.toString();
        
    } // end of public String getNumberOfRooms() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //Overridden method to display this ship to the user.
        
        String st = "Passenger ship: " + super.toString();
        
        return st;
        
    } // end of public String toString() {
    
    @Override
    public boolean compareTo(String searchParameter) {
        
        //Overridden method to compare the new fields defined by this class
        //when searching the tree
        
        if(super.compareTo(searchParameter))
            return true;
        
        if((searchParameter.equals(this.getNumberOfOccupiedRooms())) || (searchParameter.equals(this.getNumberOfPassengers()))
                || (searchParameter.equals(this.getNumberOfRooms())))
            return true;
        
        return false;
        
    } // end of public String comparTo(String searchParameter) {
    
} // end of public class PassengerShip extends Ship {
