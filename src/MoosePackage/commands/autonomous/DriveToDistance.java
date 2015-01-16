package MoosePackage.commands.autonomous;

import MoosePackage.RobotMap;
import MoosePackage.commands.CommandBase;

/**
 *
 * @author atenc_000
 */

//set speed to a negative value [-1,0) to go backwards. distance should allways be positive
public class DriveToDistance extends CommandBase {
    private double setSpeed, distance;
    
    public DriveToDistance(double speed, double distance) {
        requires(drive);
	this.setSpeed = speed;
	this.distance = distance;
    }

    protected void initialize() {
        drive.startEncoders();
	drive.resetEncoders();
    }

    protected void execute() {
        
        if(setSpeed > 0 && setSpeed < RobotMap.driveMinSpeed)
        {
            setSpeed = RobotMap.driveMinSpeed;
        }
        else if(setSpeed < 0 && setSpeed > -RobotMap.driveMinSpeed)
        {
            setSpeed = -RobotMap.driveMinSpeed;
        }
        
        drive.driveStraight(setSpeed);
    }

    protected boolean isFinished() {
        return Math.abs(drive.avgEncoderCount()) >= distance;
    }

    protected void end() {
        drive.stopEncoders();
    }


    protected void interrupted() {
        end();
    }
}
