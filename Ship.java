/*
 * FileName: Ship.java
 * Author: Jeffrey
 * Date Created: 10/25/19
 * Last Modified: 12/15/19
 * Purpose: This class serves as the parent to PassengerShip and CargoShip, and
 * defines the fields and methods common to these two classes. These include
 * extending Thing to contain Doubles for weight, length, width, and draft. 
 * The compareTo() and toString() methods are overriden to account for the
 * additional fields added by this class.
 */
package seaportprogram;

import java.util.*;

public class Ship extends Thing {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private Double weight;
    private Double length;
    private Double width;
    private Double draft;
    private ArrayList<Job> jobs = new ArrayList<>();
    
    private Boolean hasJobs = false;
    private Boolean isDocked = false;
    private Integer jobsCount = 0;
    private Dock myDock;
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public Ship(Scanner sc) {
        
        super(sc);
        this.weight = sc.nextDouble();
        this.length = sc.nextDouble();
        this.width = sc.nextDouble();
        this.draft = sc.nextDouble();        
        
    } // end of public Ship(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    public Double getWeightAsDouble() {
        
        //getter
        
        return this.weight;
        
    } // end of public Double getWeightAsDouble() {
    
    public Double getLengthAsDouble() {
        
        //getter
        
        return this.length;
        
    } // end of public Double getLengthAsDouble() {
    
    public Double getWidthAsDouble() {
        
        //getter
        
        return this.width;
        
    } // end of public Double getWidthAsDouble() {
    
    public Double getDraftAsDouble() {
        
        //getter
        
        return this.draft;
        
    } // end of public Double getDraftAsDouble() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    public String getWeight() {
        
        //getter
        
        return this.weight.toString();
        
    } // end of public String getWeight() {
    
    public String getLength() {
        
        //getter
        
        return this.length.toString();
        
    } // end of public String getLength() {
    
    public String getWidth() {
        
        //getter
        
        return this.width.toString();
        
    } // end of public String getWidth() {
    
    public String getDraft() {
        
        //getter
        
        return this.draft.toString();
        
    } // end of public String getDraft() {
    
    public ArrayList<Job> getJobs() {
        
        //getter
        
        return this.jobs;
        
    } // end of public ArrayList<Job> getJobs() {
    
    public Integer getJobsCount() {
        
        //getter
        
        return jobsCount;
        
    } // end of public Integer getJobsCount() {
    
    public Boolean getIsDocked() {
        
        //getter
        
        return this.isDocked;
        
    } // end of public Boolean getIsDocked() {
    
    public Dock getDock() {
        
        //getter
        
        return this.myDock;
        
    } // end of public Dock getDock() {
        
    public void addJob(Job jb) {
        
        //setter
        
        if(this.hasJobs == false)
            this.hasJobs = true;
        
        this.jobs.add(jb);
        
        jb.setShip(this);

    } // end of public void addJob(Job jb) {
    
    public Boolean getHasJobs() {
        
        //getter
        
        return this.hasJobs;
        
    } // end of public Boolean getHasJobs() {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        String st;
        
        if(this.hasJobs==false) {
            
            st = super.toString() + "\t" + this.getWeight() + "\t" 
                + this.getLength() + "\t" + this.getWidth() + "\t"
                + this.getDraft();
            
        } else {
        
            st = super.toString() + "\t" + this.getWeight() + "\t" 
                    + this.getLength() + "\t" + this.getWidth() + "\t"
                    + this.getDraft() + "\t" + this.getJobs();
        
        } // end of if(this.hasJobs==false) { ... else {
        
        return st;
        
    } // end of public String toString() {
    
    @Override
    public boolean compareTo(String searchParameter) {
        
        //Overriden method to compare this classes fields in addition to
        //its inherited fields.
        
        if(super.compareTo(searchParameter))
            return true;
        
        if((searchParameter.equals(this.getWeight())) || (searchParameter.equals(this.getLength()))
                || (searchParameter.equals(this.getWidth())) || (searchParameter.equals(this.getDraft())))
            return true;
        
        return false;
        
    } // end of public String comparTo(String searchParameter) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    public void chooseParentByType(Thing candidateParent) {
        
        //Instance method for all Ships to determine if the parent Thing
        //is the Que or a Dock
        //Added to parent
        
        if (candidateParent instanceof Dock) {
                        
            Dock dk = (Dock) candidateParent;
            this.setSeaPort(dk.getSeaPort());
            dock(dk);

        } else {

            mySeaPort = (SeaPort) candidateParent;
            mySeaPort.getQue().add(this);

        } // end of if (mapOfThings.get(sh.getParent()) instanceof Dock) { ... else
        
    } // end of public void chooseParentByType(Thing candidateParent, Ship sh) {
    
    public synchronized void dock(Dock dk) {
        
        //This method sets the flags for a ship docking
        //and assigns the objects to each other for reference
        
        //Assign this as my dock
        this.myDock = dk;
        
        //Set my ship with the dock
        this.myDock.setShip(this);
        
        //Set the flag on the dock
        this.myDock.toggleHasShip();
        
        //Set my flag to Docked 
        this.isDocked = true;
        
    } // end of public void dock(Dock dk) {
    
    public synchronized void unDock() {
        
        //This method sets the flags for a ship leaving a dock
            
        //toggle flag on dock
        this.myDock.toggleHasShip();

        //set my flag to undocked
        this.isDocked = false;

        //put the dock back in the blocking que
        if(this.getSeaPort().dockDispenser.offer(myDock))
            myDock = null;       
        
    } // end of public synchronized void unDock() {
        
} // end of public class Ship extends Thing {
