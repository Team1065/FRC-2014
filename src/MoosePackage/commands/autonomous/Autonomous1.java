/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Team1065
 */
public class Autonomous1 extends CommandGroup {
    
    public Autonomous1() {
        //distance can be changed to inches instead of counts if desired
        addSequential(new DriveToDistance(.5,3150));
        addSequential(new StopAndWait(.2));
        addSequential(new PickupPosition(true,1));
        //addSequential(new RollerSpeed(.4,.1));
        addSequential(new ShootOnce(215,100000));
    }
}
