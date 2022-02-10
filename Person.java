/*
 * FileName: Person.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 11/17/19
 * Purpose: This class is a representation of a person in the seaport program.a
 * In addition to the inherited fields of Thing, this class has a representation
 * of a skill that the Person has. Overriden compareTo() and toString() methods
 * make this class searchable and displayable to the user respectively.
 */
package seaportprogram;

import java.util.*;

public class Person extends Thing {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private String skill;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public Person(Scanner sc) {
        
        super(sc);
        this.skill = sc.next();
        
    } // end of public Person(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    public String getSkill() {
        
        //getter
        
        return this.skill;
        
    } // end of public String getSkill() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //This method allows the person's skill to be displayed in addition
        //to the inherited fields.
        
        String st = "Person: " + super.toString() + " " + this.getSkill();
        
        return st;
        
    } // end of public String toString() {
    
    @Override
    public boolean compareTo(String searchParameter) {
        
        //Additional OR Gate for the value of getSkill()
        
        if(super.compareTo(searchParameter))
            return true;
        
        if(searchParameter.equals(this.getSkill()))
            return true;
        
        return false;
        
    } // end of public String comparTo(String searchParameter) {
    
} // end of public class Person extends Thing {
