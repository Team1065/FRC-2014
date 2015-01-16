
package MoosePackage.subsystems;

import MoosePackage.RobotMap;
import MoosePackage.commands.ShooterTeleop;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterSystem extends Subsystem {
    Talon motor, motor2;
    Encoder encoder;
    AnalogPotentiometer pot;
    private double proportional;
    private boolean shooting;
    
    public ShooterSystem()
    {
        motor = new Talon(RobotMap.shooterMotor);
        motor2 = new Talon(RobotMap.shooterMotor2);
        pot = new AnalogPotentiometer(RobotMap.pot);
        encoder =  new Encoder(RobotMap.shooterEncoderA, RobotMap.shooterEncoderB,false,CounterBase.EncodingType.k1X);
        proportional = (1.00/2000.00);
        shooting = false;
    }

    public void initDefaultCommand() 
    {
        //setDefaultCommand(new ShooterTeleop());
    }
    
    public double getProportional(){
        return proportional;
    }
    
    public double getMotor(){
        return motor.get();
    }
    
    public void setMotor(double speed){
        motor.set(-speed);
        motor2.set(-speed);
    }
    
    public boolean isShooting(){
        return shooting;
    }
    
    public void setShootingState(boolean state){
        shooting = state;
    }
    
    public void startEncoder()
    {
        encoder.start();
    }
    
    public void stopEncoder()
    {
        encoder.stop();
    }
    
    public void resetEncoder()
    {
        encoder.reset();
    }
    
    public double getPot()
    {
        return 5.0 - pot.get();
    }
    
    public double getEncoderCount()
    {
        return encoder.get();
    }
    
    public double getEncoderRate()
    {
        return encoder.getRate();
    }
    
    public void setEncoderProportional(double proportional)
    {
        this.proportional = proportional;
    }
    
    public void updateStatus()
    {
        SmartDashboard.putNumber("Shooter Encoder Rate",encoder.getRate());
        SmartDashboard.putNumber("Shooter Encoder Count",encoder.get());
        SmartDashboard.putNumber("Pot Value",getPot());
        SmartDashboard.putBoolean("Shooting State", isShooting());
    }
}
