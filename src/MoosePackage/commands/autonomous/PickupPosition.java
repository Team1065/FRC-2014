/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import MoosePackage.commands.CommandBase;

/**
 *
 * @author Team1065
 */
public class PickupPosition extends CommandBase {
    
    boolean down;
    
    public PickupPosition(boolean down, double t) {
        requires(pickup);
        this.setTimeout(t);
        this.down = down;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(down)
        {
            pickup.Down();
        }
        else
        {
            pickup.Up();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
