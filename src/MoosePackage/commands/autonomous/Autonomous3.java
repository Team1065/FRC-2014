/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import MoosePackage.Moose;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Team1065
 */
public class Autonomous3 extends CommandGroup {
    
    public Autonomous3() {
        addSequential(new RollerSpeed(-.4,.05));
        addSequential(new PickupPosition(true,.1));
        //addSequential(new RampSpeedInDistance(-.6,50));
        addSequential(new DriveToDistance(-.3,50));
        addSequential(new DriveToDistance(-.5,100));
        addSequential(new StopAndWait(.5));
        //addSequential(new RampSpeedInDistance(.6,700));
        addSequential(new DriveToDistance(.25,100));
        addSequential(new DriveToDistance(.3,250));
        addSequential(new DriveToDistance(.55,Moose.pref.getDouble("Auto3Dis", 2000)));
        addSequential(new DriveToDistance(.2,350));
        addSequential(new StopAndWait(.3));
        addSequential(new RollerSpeed(0,.05));
        addSequential(new ShootOnce(215,100000));
        addSequential(new RollerSpeed(-.70,.05));
        addSequential(new DriveToDistance(-.3,100));
        addSequential(new StopAndWait(2.2));
        addSequential(new RollerSpeed(0.0,1));
        addSequential(new ShootOnce(215,100000));
    }
}
