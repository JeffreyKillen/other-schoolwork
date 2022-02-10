/*
 * FileName: Job.java
 * Author: Jeffrey Killen
 * Date Created: 11/28/19
 * Last Modified: 12/15/19
 * Purpose: This method creates a thread using the Runnable() interface in 
 * order simulate performing jobs while docked.
 */
package seaportprogram;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.concurrent.*;

public class Job extends Thing implements Runnable {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Variables
    private Double duration;
    private ArrayList<String> requirements = new ArrayList<>();
    private Runnable job;
    private Ship sp;
    private Integer workerAttempts = 0;
    private Boolean kill = false;
    private Boolean go = true;
    
    private static ExecutorService cachedTP = Executors.newFixedThreadPool(4);
    private ArrayList<Person> workers = new ArrayList<>();
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Components
    public static JPanel jobPanel = new JPanel(new GridLayout(0,2));
    private JProgressBar jpb;
    private JLabel jobLabel;
    private JLabel shipLabel;
    private JLabel statusLabel;
    
    private JButton suspendButton = new JButton("Suspend");
    private JButton killButton = new JButton("Kill");
    
    enum Status {RUNNING, SUSPENDED, WAITING, DONE, INITIALIZED, DOCKED, INTERRUPTED, INSUFF};
    
    private Status status = Status.SUSPENDED;

/////////////////////////////////////////////////////////////////////////////////
    
    //Constructors
    public Job(Scanner sc) {
        
        super(sc);
        this.duration = sc.nextDouble();
        
        //Populate the requirments
        String[] req = sc.nextLine().split(" ");
        
        //Skip the first index in req, add all the other to requirements
        for(int i = 1; i < req.length; i++) {

            requirements.add(req[i]);
            
        } // end of for(String rq: req) {
        
        //Build the Gui for this job
        jpb = new JProgressBar(0, duration.intValue());
        jpb.setStringPainted(true);
        jobLabel = new JLabel(this.getName());
        
        shipLabel = new JLabel();
        statusLabel = new JLabel("In Que");
        
        jobPanel.add(jobLabel);
        jobPanel.add(jpb);
        jobPanel.add(shipLabel);
        jobPanel.add(statusLabel);
        
        suspendButton.addActionListener(e -> toggleGoFlag());
        killButton.addActionListener(e -> setKillFlag());
        
        jobPanel.add(suspendButton);
        jobPanel.add(killButton);
        
        //Add this job to the gui
        SeaPortProgram.getGui().addJob(jobPanel);
        
        //Start the thread
        //job = new Thread(this, this.getName()); 
        //job.start();
        
        job = this;
        cachedTP.execute(job);
        
    } // end of public Job(Scanner sc) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    //Methods
    private void toggleGoFlag() {
        
        this.go = !this.go;
        
    } // end of private void toggleGoFlag() {
    
    private void setKillFlag() {
        
        this.kill = true;
        
    } // end of private void setKillFlag() {
    
    private void showStatus(Status st) {
        
        //Method to update the gui to show state of threadd
        
        status = st;
        
        switch(status) {
            
            case RUNNING: //this.jobPanel.setBackground(Color.green);
                this.statusLabel.setText("Running");
                break;
                
            case SUSPENDED: //this.jobPanel.setBackground(Color.yellow);
                this.statusLabel.setText("Suspended");
                break;
                
            case WAITING: //this.jobPanel.setBackground(Color.orange);
                this.statusLabel.setText("In Que");
                break;
                
            case DONE: //this.jobPanel.setBackground(Color.red);
                this.statusLabel.setText("Done");
                break;
                
            case INITIALIZED: 
                this.statusLabel.setText("Initialized");
                break;
                
            case DOCKED:
                this.statusLabel.setText("Docked");
                break;
                
            case INTERRUPTED:
                this.statusLabel.setText("Waiting for a dock...");
                break;
                
            case INSUFF:
                this.statusLabel.setText("Not enough workers");
                break;
                
            default: this.statusLabel.setText("Unknown");
                break;
            
        } // end of switch
        
    } // end of showStatus() {
    
    public String getDuration() {
        
        //getter
        
        return this.duration.toString();
        
    } // end of public String getDuration() {
    
    public void setDuration(Double dur) {
        
        //setter
        
        this.duration = dur;
        
    } // end of public void setDuration(Double dur) {    
    
    public ArrayList<String> getRequirements() {
        
        //getter
        
        return this.requirements;
        
    } // end of public ArrayList<String> getRequirements() {
    
    public void addRequirement(String requirement) {
        
        //setter
        
        requirements.add(requirement);
        
    } // end of public void addRequirement(String requirement) {
    
    public void setShip(Ship sp) {
        
        //setter
        
        this.sp = sp;
        
    } // end of public void setShip(Ship sp) {
    
    private void putWorkersBack() {
        
        for(Person worker: workers) {
            
            this.getSeaPort().personsBySkill.put(worker.getSkill(), worker);
            
        } // end of for(Person worker: workers) {        
        
    } // end of private void putWorkersBack() {
    
    private void jobFailed(String jobName, String reason) {
        
        SeaPortProgram.displayOutput("\nJob " + jobName + " failed due to: " + reason);
        
    } // end of private void jobFailed(String jobName, String reason) {
    
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
        
        //This method allows the person's skill to be displayed in addition
        //to the inherited fields.
        
        String st = "Job: " + super.toString() + " " + this.getDuration() 
                + " " + this.getRequirements();
        
        return st;
        
    } // end of public String toString() {
    
    @Override
    public void run() {
        
/////////////////////////////////////////////////////////////////////////////////

/*
    While this task is not suspended, and the user has not killed it
    sync w/ the ship -> this controls which job is running
    get a dock -> this controls whether or not we can get workers
    get workers -> either get the workers or get back in line
    do the work
    put the workers back
    put the dock back
    cleanup and desync w/ the ship

    if anything fails or the job is killed, put back everything you have
    aquired, cleanup and get back in line.

    if a job fails to get a workers a given number of times, the job is
    impossible to complete.
*/
        
/////////////////////////////////////////////////////////////////////////////////
        
        //while i havent been suspended
        while(!go){
            
            showStatus(Status.SUSPENDED);
            
        } // end of while(!go){
        
        //kill if no ship
        if(this.sp == null) {
            
            cachedTP.execute(this);
            return;
            
        } // end of if(this.sp == null) {
        
        //Update gui w/ ship name
        this.shipLabel.setText(this.sp.getName());
        
        //sync w/ ship and get in line for a dock
        synchronized(this.sp) {         
    
            showStatus(Status.INITIALIZED);

            //While I do not have a dock
            while(!this.sp.getIsDocked()) {
                
                //user suspension
                while(!go){
            
                    showStatus(Status.SUSPENDED);
            
                } // end of while(!go){
                
                //user killed task
                if(kill) {

                    //kill the task
                    showStatus(Status.DONE);
                    jobFailed(this.getName(), "You killed me");
                    return;
                    
                } // end of if(kill) {
                
                //Get a dock from the blocking que
                try {

                    showStatus(Status.WAITING);

                    //set my ship's dock to the next available dock in the blocking que
                    this.sp.dock(this.getSeaPort().dockDispenser.remove()); //does not tie up the thread like take()

                } catch (Exception e) {

                    //No dock was available, get back into the thread pool
                    showStatus(Status.INTERRUPTED);
                    cachedTP.execute(this);
                    return;

                } // end of try/catch

            } // end of while(!this.sp.getIsDocked()) {
            
            showStatus(Status.DOCKED);

/////////////////////////////////////////////////////////////////////////////////

            //Get workers

            for(String req: this.getRequirements()) {

                //if i am not suspended
                while(!go){

                    showStatus(Status.SUSPENDED);

                } // end of while(!go){
                
                //user killed task
                if(kill) {

                    //put the dock back and kill the thread
                    this.sp.unDock();
                    showStatus(Status.DONE);
                    return;

                } // end of if(kill) {

                //if we have a null requirement
                //this causes jobs w/ no requirements to begin work
                if(req == null)
                    continue;

                //check that the resource pool exists
                //its possible to get here before that has happened
                if(this.getSeaPort().personsBySkill != null) {

                    //get a person by skill
                    Person ps = (Person) this.getSeaPort().personsBySkill.remove(req); //atomic

                    //if we did not get them
                    if(ps == null) {

                        workerAttempts++;
                        
                        //kill due to lack of workers
                        if(workerAttempts >= 20) { // 20 attempts because why not

                            putWorkersBack();
                            this.sp.unDock();
                            showStatus(Status.INSUFF);
                            jpb.setValue(100);
                            jobFailed(this.getName(), "There is no " + req + " in " + this.getSeaPort().getName());
                            return;

                        } else {
                            
                            //restart task if no worker is found
                            
                            putWorkersBack();
                            this.sp.unDock();
                            showStatus(Status.WAITING);
                            cachedTP.execute(this);
                            return;

                        } // end of if(workerAttempts >= 20) {

                    } else {
                        
                        //save the worker
                        this.workers.add(ps);

                    } // end of if(ps == null) { ... else {

                } else {
                    
                    //No resource pool, get back in the Executor pool
                    this.sp.unDock();
                    showStatus(Status.WAITING);
                    cachedTP.execute(this);
                    return;

                }// end of if(this.getSeaPort().personsBySkill != null) {

            } // end of for(String req: this.getRequirements()) {
            
/////////////////////////////////////////////////////////////////////////////////

            //Do work
            //The following loop increments the JProgressBar's progress every
            //100 milliseconds

            showStatus(Status.RUNNING);

            for(int i = 0; i <= 100; i++) {
                
                //suspended by user
                while(!go){

                    showStatus(Status.SUSPENDED);

                } // end of while(!go){

                //killed by user
                if(kill) {

                    putWorkersBack();
                    workers.clear();
                    this.sp.unDock();
                    showStatus(Status.DONE);
                    jpb.setValue(100);
                    return;

                } // end of if(kill) {

                jpb.setValue(i);
                jpb.validate();

                try {

                    Thread.sleep(10); 

                } catch (InterruptedException e) {
                } // end of try/catch

            } // end of for(int i = 0; i <= 100; i++) {

/////////////////////////////////////////////////////////////////////////////////

            //Cleanup

            putWorkersBack();

            workers.clear();

            //Put the dock back in the que
            this.sp.unDock();
                                    
            showStatus(Status.DONE);
            
            SeaPortProgram.displayOutput("\nJob " + this.getName() + " in port " + this.getSeaPort().getName() + " finished successfully");
        
        } // end of synchronized(this.sp) {
        
/////////////////////////////////////////////////////////////////////////////////

    } // end of public void run() {
    
///////////////////////////////////////////////////////////////////////////////// 

} // end of public class Job extends Thing implements Runnable {
