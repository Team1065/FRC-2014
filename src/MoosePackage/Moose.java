/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package MoosePackage;


import MoosePackage.commands.autonomous.Autonomous1;
import MoosePackage.commands.autonomous.Autonomous3;
import MoosePackage.commands.autonomous.Autonomous2;
import MoosePackage.commands.autonomous.Autonomous4;
import MoosePackage.commands.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Moose extends IterativeRobot {

    //Command autonomousCommand;
    Command initDrive, initPickUp, initShooter;
    
    Command auto1,auto2,auto3, auto4;
    
    Compressor compressor;
    
    public static Preferences pref;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();
        pref = Preferences.getInstance();
        initDrive = new DriveWithJoysticks();
        initPickUp = new PickUpTeleOp ();
        initShooter = new ShooterTeleop();
        compressor = new Compressor(RobotMap.preassureSwitch, RobotMap.compressor);
        auto1 = new Autonomous1();
        auto2 = new Autonomous2();
        auto3 = new Autonomous3();
        auto4 = new Autonomous4();
    }

    public void autonomousInit() {
        compressor.stop();
        try {
            switch(CommandBase.oi.getAuto())
            {
                case 1: auto1.start();
                        break;
                case 2: auto2.start();
                        break;
                case 3: auto3.start();
                        break;
                case 4: auto4.start();
                        break;
                default: auto1.start();
                        break;
            }
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //autonomousCommand.cancel();
        
        compressor.start();
        initDrive.start();
        initPickUp.start();
        initShooter.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        try {
            if(CommandBase.oi.getCompressorSwitch() || 
                    CommandBase.shooter.isShooting()){
                compressor.stop();
            }
            else{
                compressor.start();
            }
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        
        //CommandBase.drive.updateStatus();
        //CommandBase.oi.updateStatus();
        //CommandBase.pickup.updateStatus();
        //CommandBase.shooter.updateStatus();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
