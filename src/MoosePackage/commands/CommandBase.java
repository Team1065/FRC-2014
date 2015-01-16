package MoosePackage.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import MoosePackage.OI;
import MoosePackage.subsystems.DriveSystem;
import MoosePackage.subsystems.PickUpSystem;
import MoosePackage.subsystems.ShooterSystem;

public abstract class CommandBase extends Command {

    public static OI oi;
    
    public static DriveSystem drive = new DriveSystem();
    public static PickUpSystem pickup = new PickUpSystem();
    public static ShooterSystem shooter = new ShooterSystem();

    public static void init() {
        oi = new OI();
        // Show what command your subsystem is running on the SmartDashboard
        //SmartDashboard.putData(exampleSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
