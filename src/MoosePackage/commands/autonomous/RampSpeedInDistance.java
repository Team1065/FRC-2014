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
public class RampSpeedInDistance extends CommandBase {
    
    double setSpeed, distance, speedChange, crntSpeed;
    
    public RampSpeedInDistance(double speed, double distance) {
        requires(drive);
        this.setSpeed = speed;
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drive.startEncoders();
	drive.resetEncoders();
        speedChange = (setSpeed - drive.autoSpeed)/distance;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        crntSpeed = drive.autoSpeed + (speedChange * Math.abs(drive.avgEncoderCount()));
        
        if(setSpeed > RobotMap.driveMinSpeed && crntSpeed < RobotMap.driveMinSpeed)
        {
            crntSpeed = RobotMap.driveMinSpeed;
        }
        else if(setSpeed < -RobotMap.driveMinSpeed && crntSpeed > -RobotMap.driveMinSpeed)
        {
            crntSpeed = -RobotMap.driveMinSpeed;
        }
        
        drive.driveStraight(crntSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        drive.autoSpeed = crntSpeed;
        return Math.abs(drive.avgEncoderCount()) >= distance;
        //return this.isTimedOut() || use distance;
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.stopEncoders();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
