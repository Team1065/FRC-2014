/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import MoosePackage.RobotMap;
import MoosePackage.commands.CommandBase;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;

/**
 *
 * @author Team1065
 */
public class ShootOnce extends CommandBase {
    
    boolean setpointReached, shootingDone;
    double setPosition, setSpeed;
    
    public ShootOnce(double position, double speed) {
        requires(shooter);
        setPosition = position;
        setSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.stopEncoder();
        shooter.resetEncoder();
        shooter.startEncoder();
        shooter.setMotor(-0.041);
        setpointReached = false;
        shootingDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double speedDiff, motorValue;
        if(!setpointReached && shooter.getEncoderCount() < setPosition/3)// && shooter.getPot() < RobotMap.potHighLimit)
        {
            speedDiff = (setSpeed - shooter.getEncoderRate())*shooter.getProportional();
            motorValue = shooter.getMotor()+ speedDiff;
            if(motorValue <0)
                motorValue = -0.041;
            shooter.setMotor(motorValue);
        }
        else if(!setpointReached && shooter.getEncoderCount() < setPosition)// && shooter.getPot() < RobotMap.potHighLimit)
        {
            speedDiff = (setSpeed - shooter.getEncoderRate())*shooter.getProportional();
            motorValue = shooter.getMotor()+ speedDiff;
            if(motorValue <0)
                motorValue = -0.041;
            shooter.setMotor(motorValue);
        }
        else{
            setpointReached = true;
            if(shooter.getEncoderCount()<5)// || shooter.getPot() < RobotMap.potLowLimit)
            {
                shooter.setMotor(-0.041);
                shooter.resetEncoder();
                setpointReached = false;
                shootingDone = true;
            }
            else
            {
                shooter.setMotor(RobotMap.shooterReverseSpeed);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shootingDone;
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.setMotor(-0.041);
        shooter.resetEncoder();
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
