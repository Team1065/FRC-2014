
package MoosePackage;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class OI {
    DriverStationEnhancedIO ds;
    
    Joystick leftStick = new Joystick(RobotMap.leftJoyPort);
    Joystick rightStick = new Joystick(RobotMap.rightJoyPort);
    
    Button tractionButton = new JoystickButton(leftStick, RobotMap.triggerPort);
    Button shootButton = new JoystickButton(rightStick, RobotMap.triggerPort);
    Button mecanumButton = new JoystickButton(rightStick, RobotMap.mecanumButtonPort);
    Button slowButton = new JoystickButton(leftStick, RobotMap.mecanumButtonPort);
    
    
    Button shooterFeedbackEnableButton = new DigitalIOButton(RobotMap.shooterFeedbackEnablePort);
    Button pickUpPositionButton = new DigitalIOButton(RobotMap.pickUpPositionSwitch);
    Button pickUpMotorOutButton = new DigitalIOButton(RobotMap.pickUpOutSwitch);
    Button pickUpMotorInButton = new DigitalIOButton(RobotMap.pickUpInSwitch);
    
    private double position, speed;
    
    public OI(){
         ds =DriverStation.getInstance().getEnhancedIO();
         //shooterFeedbackEnableButton.whileHeld(new ShootingWithFeedback());
     }
    
    public double getLeftY() {
        return -leftStick.getY();
    }
    
    public double getLeftX() {
        return leftStick.getX();
    }
 
    public double getRightY() {
        return -rightStick.getY();
    }
    
    public double getRightX() {
        return rightStick.getX();
    }
    
    public double getYAverage(){
        return ((-leftStick.getY()) + (-rightStick.getY()))/2.0;
    }
    
    public boolean getTracktionButton(){
        return tractionButton.get();
    }
    
    public boolean getMecanumButton(){
        return mecanumButton.get();
    }
    
    public boolean getSlowButton(){
        return slowButton.get();
    }
    
    public boolean getShootButton(){
        return shootButton.get();
    }
    
    public boolean isPickUpPositionUp(){
        return !pickUpPositionButton.get();
    }
    public boolean isPickUpPositionDown(){
        return pickUpPositionButton.get();
    }
    
    public boolean isPickUpMotorOut(){
        return !pickUpMotorOutButton.get();
    }
    
    public boolean isPickUpMotorIn(){
        return !pickUpMotorInButton.get();
    }
    
    public boolean isPickUpMotorOff(){
        return !(!pickUpMotorInButton.get() && !pickUpMotorInButton.get());
    }
    
    public boolean getCompressorSwitch() throws EnhancedIOException{
        return ds.getDigital(RobotMap.compressorEnablePort);
    }
    public double getShooterSetSpeed() throws DriverStationEnhancedIO.EnhancedIOException{
        
        if(ds.getAnalogIn(1)<RobotMap.shooterVDposition0){
            speed = RobotMap.shooterSpeed0;//front assist
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition1 && ds.getAnalogIn(1)<RobotMap.shooterVDposition1+.3){
            speed = RobotMap.shooterSpeed1;
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition2 && ds.getAnalogIn(1)<RobotMap.shooterVDposition2+.4){
            speed = RobotMap.shooterSpeed2;
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition3&& ds.getAnalogIn(1)<RobotMap.shooterVDposition3+.4){
            speed = RobotMap.shooterSpeed3;
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition4&& ds.getAnalogIn(1)<RobotMap.shooterVDposition4+.4){
            speed = RobotMap.shooterSpeed4;
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition5){
            speed = RobotMap.shooterSpeed5;//5000 x 300 works well for high goal at 17 feet 
         }
         return speed;
    }
    
    public double getShooterSetPosition() throws DriverStationEnhancedIO.EnhancedIOException{
        
        if(ds.getAnalogIn(1)<RobotMap.shooterVDposition0){
            position = Moose.pref.getDouble("shooterPosition0", RobotMap.shooterPosition0);
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition1 && ds.getAnalogIn(1)<RobotMap.shooterVDposition1+.3){
            position = Moose.pref.getDouble("shooterPosition1", RobotMap.shooterPosition1);//150 with 4000 for front
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition2 && ds.getAnalogIn(1)<RobotMap.shooterVDposition2+.4){
            position = Moose.pref.getDouble("shooterPosition2", RobotMap.shooterPosition2); // 3000 x 250 works well for high goal at 3 feet
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition3&& ds.getAnalogIn(1)<RobotMap.shooterVDposition3+.4){
            position = Moose.pref.getDouble("shooterPosition3", RobotMap.shooterPosition3);
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition4&& ds.getAnalogIn(1)<RobotMap.shooterVDposition4+.4){
            position = Moose.pref.getDouble("shooterPosition4", RobotMap.shooterPosition4);
        }
        //If Station Knob is at 
        else if(ds.getAnalogIn(1)>=RobotMap.shooterVDposition5){
            position = Moose.pref.getDouble("shooterPosition5", RobotMap.shooterPosition5);// 5000 x 300 works well for high goal at 17 feet
         }
         return position;
    }
    
     public int getAuto() throws DriverStationEnhancedIO.EnhancedIOException{
        int auto = 1;
         if(ds.getAnalogIn(2)<RobotMap.autoVDposition1){
            auto = 1;
        }
        else if(ds.getAnalogIn(2)<RobotMap.autoVDposition2){
            auto = 2;
        }
        else if(ds.getAnalogIn(2)<RobotMap.autoVDposition3){
            auto = 3;
        }
        else{
            auto = 4;
        }
        return auto;
    }
    
    public void updateStatus(){
        SmartDashboard.putNumber("Left Joystick Y",getLeftY());
        SmartDashboard.putNumber("left Joystick X",getLeftX());
        SmartDashboard.putNumber("Right Joystick Y",getRightY());
        SmartDashboard.putNumber("Right Joystick X",getRightX());
        SmartDashboard.putBoolean("Tracktion Button",getTracktionButton());
        SmartDashboard.putBoolean("Mecanum Button",getMecanumButton());
    }
    
}

