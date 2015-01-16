package MoosePackage.commands;

import MoosePackage.Moose;
import MoosePackage.RobotMap;
import edu.wpi.first.wpilibj.Timer;


public class PickUpTeleOp extends CommandBase {

    private static class pickUpMotorInButton {

        public pickUpMotorInButton() {
        }
    }
    Timer timer;
    public PickUpTeleOp() {
        requires(pickup);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer = new Timer();
        timer.stop();
        timer.reset();
        timer.start();
        pickup.setDownState(false);
    }

    //Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //naming is wrong from oi ("in" is actually "out")
        if(oi.isPickUpMotorIn())
        {
           pickup.setMotor(Moose.pref.getDouble("IntakeOutSpeed", RobotMap.pickupOutSpeed));
        }
        else if(oi.isPickUpMotorOut())
        {
           pickup.setMotor(Moose.pref.getDouble("IntakeInSpeed", RobotMap.pickupInSpeed));
        }
        else 
        {
             pickup.setMotor(0);
                      
        }
        
        if(oi.isPickUpPositionUp() && !shooter.isShooting()){
            pickup.Up();
            timer.reset();
            pickup.setDownState(false);
        }
        else 
        {
            pickup.Down();
            if(timer.get()>RobotMap.downWaitTime)
            {
                pickup.setDownState(true);
            }
            else{
                pickup.setDownState(false);
            }
        }
   }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
