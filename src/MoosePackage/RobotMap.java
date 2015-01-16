package MoosePackage;

import edu.wpi.first.wpilibj.Preferences;


public class RobotMap {
    //Shooter Speeds
    public static final double 
            shooterSpeed0 = 1000,
            shooterSpeed1 = 100000,
            shooterSpeed2 = 100000,
            shooterSpeed3 = 100000,
            shooterSpeed4 = 100000,
            shooterSpeed5 = 100000;
    
    //Shooter Positions
    public static final double 
            shooterPosition0 = 300,//TODO change once tuned
            shooterPosition1 = 210,//TODO change once tuned
            shooterPosition2 = 220,//TODO change once tuned
            shooterPosition3 = 230,//TODO change once tuned
            shooterPosition4 = 240,//TODO change once tuned
            shooterPosition5 = 250;//TODO change once tuned
    
    //Shooter Knob values
    public static final double 
            shooterVDposition0 = 0.3,
            shooterVDposition1 = 0.5,
            shooterVDposition2 = 1.2,
            shooterVDposition3 = 1.8,
            shooterVDposition4 = 2.4,
            shooterVDposition5 = 3.1;
    
    //Auto Knob values
    public static final double 
            autoVDposition1 = 0.5,
            autoVDposition2 = 1.2,
            autoVDposition3 = 1.9,// 2, old value
            autoVDposition4 = 2.7; // 3; old value
    
    //Constants
    public static final double
           downWaitTime   = 1.0,
           potLowLimit    = 1.0,
           potHighLimit   = 2.9,
           encoderCountPerRev = 360.0,
           driveWheelsDiameter = 6.0,
           shooterReverseSpeed = -0.25,
           driveMinSpeed = 0.2,
           driveStraightThresholdPercentage = .30,//TODO change once tuned
           driveStraightPTerm = 0.005,//TODO change once tuned
           pickupOutSpeed = .9,//TODO change once tuned
           pickupInSpeed = -.8;//TODO change once tuned
    
    //Relays
    public static final int
           compressor   = 1;
    
    //Motors PMW input on Robot
    public static final int 
            frontLeftMotor  = 1,  
            frontRightMotor = 2,  
            backLeftMotor   = 3,  
            backRightMotor  = 4,
            pickUpMotor     = 5,
            shooterMotor    = 6,
            shooterMotor2    = 7;
    
    //Analog Inputs
    public static final int 
            pot  = 1;
    
     //Digital Inputs
    public static final int
           preassureSwitch   = 1,
           encoderLeftA      = 2,
           encoderLeftB      = 3,
           encoderRightA     = 4,
           encoderRightB     = 5,
           lineSensor1       = 6,
           lineSensor2       = 7,
           lineSensor3       = 8,
           shooterEncoderA   = 9,
           shooterEncoderB   = 10;
    
    //Solenoids
     public static final int
           traction         = 1,
           pickUp           = 2,
           whiteLight       = 3,
           blueLight        = 4,
           redLight         = 5;
    
    //OI
     public static final int 
            leftJoyPort       = 1,
            rightJoyPort      = 2,
            extraJoyPort      = 3,
            triggerPort       = 1,  // shoot the balls
            mecanumButtonPort = 2;  // turn on mecanum
     
     //Enhanced IO
    public static final int 
            shooterFeedbackEnablePort  = 8,
            compressorEnablePort       = 6,
            //disckLockPort              = 8, 
            pickUpPositionSwitch       = 2,
            pickUpInSwitch             = 5,
            pickUpOutSwitch            =4;

}
