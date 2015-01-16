/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import MoosePackage.RobotMap;
import MoosePackage.commands.CommandBase;

/**
 *
 * @author atenc_000
 */
public class RampSpeedInTime extends CommandBase {
    
    double setSpeed, time, speedChange, crntSpeed;
    
    public RampSpeedInTime(double speed, double time) {
        requires(drive);
        this.setTimeout(time);
        this.setSpeed = speed;
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        speedChange = (setSpeed - drive.autoSpeed)/time;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        crntSpeed = drive.autoSpeed + (speedChange * this.timeSinceInitialized());
        
        if(setSpeed > RobotMap.driveMinSpeed && crntSpeed < RobotMap.driveMinSpeed)
        {
            crntSpeed = RobotMap.driveMinSpeed;
        }
        else if(setSpeed < -RobotMap.driveMinSpeed && crntSpeed > -RobotMap.driveMinSpeed)
        {
            crntSpeed = -RobotMap.driveMinSpeed;
        }
        
        drive.tankDrive(crntSpeed, crntSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        drive.autoSpeed = crntSpeed;
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
