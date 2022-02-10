/*
 * FileName: CargoShip.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 11/17/19
 * Purpose: This class defines the CargoShip datatype for use in the Seaport
 *  program. This class defines field for the value, volume, and weight of the
 *  cargo. Additionally, this class overrides the inherited compareTo() method
 *  to allow these additional fields to be searchable.
 */
package seaportprogram;

import java.util.*;

public class CargoShip extends Ship {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private Double cargoValue;
    private Double cargoVolume;
    private Double cargoWeight;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public CargoShip(Scanner sc) {
        
        super(sc);
        this.cargoValue = sc.nextDouble();
        this.cargoVolume = sc.nextDouble();
        this.cargoWeight = sc.nextDouble();
        
    } // end of public CargoShip(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    public String getCargoValue() {
        
        //getter
        
        return this.cargoValue.toString();
        
    } // end of public String getCargoValue() {
    
    public String getCargoVolume() {
        
        //getter
        
        return this.cargoVolume.toString();
        
    } // end of public String getCargoVolume() {
    
    public String getCargoWeight() {
        
        //getter
        
        return this.cargoWeight.toString();
        
    } // end of public String getCargoWeight() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //Overridden method to display this ship object to the user.
        
        String st = "Cargo ship: " + super.toString();
        
        return st;
        
    } // end of public String toString() {
    
    @Override
    public boolean compareTo(String searchParameter) {
        
        //Additional OR Gates - compare the input String this.nValue
        
        if(super.compareTo(searchParameter))
            return true;
        
        if((searchParameter.equals(this.getCargoValue())) || (searchParameter.equals(this.getCargoVolume()))
                || (searchParameter.equals(this.getCargoWeight())))
            return true;
        
        return false;
        
    } // end of public String comparTo(String searchParameter) {
    
} // end of public class CargoShip extends Ship {
