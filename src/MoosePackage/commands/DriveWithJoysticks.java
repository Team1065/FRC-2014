package MoosePackage.commands;

import MoosePackage.Moose;
import MoosePackage.RobotMap;

public class DriveWithJoysticks extends CommandBase {
    
    public DriveWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drive.startEncoders();
        drive.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //drive.runLights();
        
        double leftY = oi.getLeftY();
        double rightY = oi.getRightY();
        
        double rightX = oi.getRightX();
        
        double averageY = oi.getYAverage();
        
        if(oi.getSlowButton()){
            leftY = leftY / 2.0;
            rightY = rightY / 2.0;
            averageY = averageY /2.0;
        }
        
        if(oi.getTracktionButton()){
            drive.TractionDown();
            drive.tankDrive(leftY, rightY);
            drive.resetEncoders();
        }
        else{
            drive.TractionUp();
            double joystickDiff = Math.abs(leftY-rightY);
            
            if(oi.getMecanumButton())
            {
                if(Math.abs(rightY)<.4 && Math.abs(leftY)<.2)
                {
                    drive.mecanumFeedback(rightX-drive.getEncoderDifference(), rightX+drive.getEncoderDifference()); 
                }
                else
                {
                    drive.mecanumDriveCartesian(rightX, -rightY, leftY,0);
                }
            }
            else
            {
                //Drive Straight Active if:
                //1) Both Joysticks are going in the same direction
                //2) The Difference between the Joysticks is not larger than a percentage of the set speed
                //As the speed is higher the is a greater forgiveness for error
                if((leftY * rightY >= 0) && joystickDiff < (Math.abs(averageY) * Moose.pref.getDouble("DriveStraightPercentage", RobotMap.driveStraightThresholdPercentage)))
                {
                    drive.driveStraight(averageY);
                }
                else
                {
                    drive.tankDrive(leftY, rightY);
                }
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.TractionUp();
        drive.stopEncoders();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
