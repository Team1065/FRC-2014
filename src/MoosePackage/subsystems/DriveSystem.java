package MoosePackage.subsystems;

import MoosePackage.Moose;
import MoosePackage.RobotMap;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveSystem extends Subsystem 
{
    Jaguar fLeftMotor, fRightMotor, bLeftMotor, bRightMotor;
    Encoder encoderLeft, encoderRight;
    private double proportional;
    private RobotDrive drive;
    private Solenoid traction, whiteLight, blueLight, redLight;
    private DigitalInput lineSensor1, lineSensor2, lineSensor3;
    public double autoSpeed;
    
    public DriveSystem ()
    {
        fRightMotor = new Jaguar(RobotMap.frontRightMotor);
        fLeftMotor = new Jaguar (RobotMap.frontLeftMotor);
        bLeftMotor = new Jaguar (RobotMap.backLeftMotor);
        bRightMotor = new Jaguar (RobotMap.backRightMotor);
        encoderLeft = new Encoder (RobotMap.encoderLeftA, RobotMap.encoderLeftB,true,CounterBase.EncodingType.k1X);
        encoderRight = new Encoder (RobotMap.encoderRightA, RobotMap.encoderRightB,false,CounterBase.EncodingType.k1X);
        encoderLeft.setDistancePerPulse(RobotMap.driveWheelsDiameter * Math.PI / RobotMap.encoderCountPerRev);
        encoderRight.setDistancePerPulse(RobotMap.driveWheelsDiameter * Math.PI / RobotMap.encoderCountPerRev);
        encoderLeft.setSamplesToAverage(1);
        encoderRight.setSamplesToAverage(1);
        proportional = RobotMap.driveStraightPTerm; 
        drive = new RobotDrive(fLeftMotor,bLeftMotor, fRightMotor, bRightMotor);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        drive.setSafetyEnabled(false);
        traction = new Solenoid(RobotMap.traction);
        lineSensor1 = new DigitalInput(RobotMap.lineSensor1);
        lineSensor2 = new DigitalInput(RobotMap.lineSensor2);
        lineSensor3 = new DigitalInput(RobotMap.lineSensor3);
        whiteLight = new Solenoid(RobotMap.whiteLight);
        blueLight = new Solenoid(RobotMap.blueLight);
        redLight = new Solenoid(RobotMap.redLight);
        
        autoSpeed = 0;
    }    
    public void initDefaultCommand() 
    {    
        // Set the default command for a subsystem here.
       //setDefaultCommand(new DriveWithJoysticks());

    }
    
    public void runLights()
    {
        whiteLight.set(lineSensor1.get());
        blueLight.set(lineSensor2.get());
        redLight.set(lineSensor3.get());
    }
    
    public void tankDrive(double leftspeed, double rightspeed)
    {
        if(leftspeed > 1.0)
        {
            leftspeed = 1.0;
        }
        else if(leftspeed < -1.0)
        {
            leftspeed = -1.0;
        }
        
        if(rightspeed > 1.0)
        {
            rightspeed = 1.0;
        }
        else if(rightspeed < -1.0)
        {
            rightspeed = -1.0;
        }
        
        fLeftMotor.set(leftspeed);
        bLeftMotor.set(leftspeed);
        fRightMotor.set(-rightspeed);
        bRightMotor.set(-rightspeed);
        //System.out.println("tank drive: " + leftspeed + "," + rightspeed);
    }
    
    public void driveStraight(double speed)
    {
        driveStraight(speed, false);
    }
    
    //for rotation: positive speed -> clockwise (rotate flag must be true) 
    public void driveStraight(double speed, boolean rotate)
    {
        double leftMotorValue;
        double rightMotorValue;
        
        if(rotate)
        {
            //rotating in place
            if(speed<0)
            {
                    leftMotorValue = speed + getEncoderAbsDifference();
                    rightMotorValue = -speed + getEncoderAbsDifference();
            }
            else
            {
                    leftMotorValue = speed - getEncoderAbsDifference();
                    rightMotorValue = -speed - getEncoderAbsDifference();
            }
        }
        else
        {
            //driving straight
            leftMotorValue = speed - getEncoderDifference();
            rightMotorValue = speed + getEncoderDifference();
            
            if(speed >= 0)
            {
                if(leftMotorValue < 0)
                {
                    leftMotorValue = 0;
                }
                if(rightMotorValue < 0)
                {
                    rightMotorValue = 0;
                }
            }
            else
            {
                if(leftMotorValue > 0)
                {
                    leftMotorValue = 0;
                }
                if(rightMotorValue > 0)
                {
                    rightMotorValue = 0;
                }
            }
        }
        
        tankDrive(leftMotorValue, rightMotorValue);
    }
    
    public void startEncoders()
    {
        encoderLeft.start();
        encoderRight.start();
    }
    
    public void stopEncoders()
    {
        encoderLeft.stop();
        encoderRight.stop();
    }
    
    public void resetEncoders()
    {
        encoderLeft.reset();
        encoderRight.reset();
    }
    
    //inches
    public double avgEncoderdistance()
    {
        return (encoderLeft.getDistance()+encoderRight.getDistance())/2.0;
    }
    
    public double avgEncoderAbsdistance()
    {
        return (Math.abs(encoderLeft.get())+Math.abs(encoderRight.get()))/2.0;
    }
    
    public double avgEncoderCount()
    {
        return (encoderLeft.get()+encoderRight.get())/2.0;
    }
    
    public double avgEncoderAbsCount()
    {
        return (Math.abs(encoderLeft.get())+Math.abs(encoderRight.get()))/2.0;
    }
    
    //for straight drive
    public double getEncoderDifference()
    {
        double diff = (encoderLeft.getRate()-encoderRight.getRate())* Moose.pref.getDouble("DriveStraightPTerm", proportional);
        if (diff >.5)
        {
            diff = .5;
        }
        else if (diff < -.5)
        {
            diff = -.5;
        }
        return diff;
    }
    
    //for rotating in place
    public double getEncoderAbsDifference()
    {
        double diff = (Math.abs(encoderLeft.getRate())-Math.abs(encoderRight.getRate()))* Moose.pref.getDouble("DriveStraightPTerm", proportional);
        if (diff >.5)
        {
            diff = .5;
        }
        else if (diff < -.5)
        {
            diff = -.5;
        }
        return diff;
    }
    
    public void TractionDown()
    {
        traction.set(true);
    }
    
    public void TractionUp()
    {
        traction.set(false);
    }
    
    //for straight mecanum drive
    public void mecanumFeedback(double motors1,double motors2)
    {
        fLeftMotor.set(motors1);
        bRightMotor.set(-motors2);
        bLeftMotor.set(-motors2);
        fRightMotor.set(motors1);
    }
    
    public void mecanumDriveCartesian(double xVal, double yVal, double rotation, double gyroAngle)
    {
        drive.mecanumDrive_Cartesian(xVal, yVal, rotation, gyroAngle);
    }
    
    public void updateStatus()
    {
        SmartDashboard.putNumber("Encoder Left Rate",encoderLeft.getRate());
        SmartDashboard.putNumber("Encoder Right Rate",encoderRight.getRate());
        SmartDashboard.putNumber("Encoder Left Count",encoderLeft.get());
        SmartDashboard.putNumber("Encoder Right Count",encoderRight.get());
        SmartDashboard.putNumber("front left",fLeftMotor.get());
        SmartDashboard.putNumber("front right",fRightMotor.get());
        SmartDashboard.putNumber("Encoder Difference",getEncoderDifference());
    }
    
}
