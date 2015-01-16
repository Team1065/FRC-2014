
package MoosePackage.commands;

import MoosePackage.RobotMap;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;


public class ShooterTeleop extends CommandBase {
    
    boolean setpointReached;
    
    public ShooterTeleop() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.stopEncoder();
        shooter.resetEncoder();
        shooter.startEncoder();
        shooter.setMotor(-0.041);
        setpointReached = false;
    }

    protected void execute() {
        double setPosition, setSpeed, speedDiff, motorValue;
        if(oi.getShootButton() && !shooter.isShooting()){
            shooter.setShootingState(true);
            shooter.resetEncoder();
        }
        if(shooter.isShooting()){
            if(pickup.isDown()){
                try {
                    setPosition = oi.getShooterSetPosition();
                    
                    /*if(!setpointReached && shooter.getEncoderCount() < setPosition/3 && shooter.getPot() < RobotMap.potHighLimit)
                    {
                        setSpeed = oi.getShooterSetSpeed();
                        speedDiff = (setSpeed - shooter.getEncoderRate())*shooter.getProportional();
                        motorValue = shooter.getMotor()+ speedDiff;
                        if(motorValue <0)
                            motorValue = -0.041;
                        shooter.setMotor(motorValue);
                    } else if*/
                    if(!setpointReached && shooter.getEncoderCount() < setPosition)// && shooter.getPot() < RobotMap.potHighLimit)
                    {
                        setSpeed = oi.getShooterSetSpeed();
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
                            shooter.setShootingState(false);
                            shooter.resetEncoder();
                            setpointReached = false;
                        }
                        else
                        {
                            shooter.setMotor(RobotMap.shooterReverseSpeed);
                        }
                    }
                    
                } catch (EnhancedIOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }


    protected void end() {
        shooter.setMotor(0);
    }

    protected void interrupted() {
        end();
    }
}
