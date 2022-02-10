/*
 * FileName: PortTime.java
 * Author: Jeffrey Killen
 * Date Created: 10/25/19
 * Last Modified: 11/17/19
 * Purpose: This class serves as a getter/setter wrapper for an int. This is
 * meant to be a representation of time within the program.
 */
package seaportprogram;

public class PortTime {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private int time;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructor
    public PortTime(int time) {
        
        this.time = time;
        
    } // end of public PortTime(int time) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    public int getTime() {
        
        //getter
        
        return this.time;
        
    } // end of public int getTime()
    
    public void setTime(int time) {
        
        //setter
        
        this.time = time;
        
    } // end of public void setTime(int time) {
    
} // end of public class PortTime {
