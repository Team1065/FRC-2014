
package MoosePackage.subsystems;

import MoosePackage.RobotMap;
import MoosePackage.commands.PickUpTeleOp;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PickUpSystem extends Subsystem {
    
    private Solenoid actuator;
    private Victor motor;
    private DigitalInput switches;
    private boolean down;
    
    public PickUpSystem()
    {
        actuator = new Solenoid(RobotMap.pickUp);
        motor = new Victor(RobotMap.pickUpMotor);
        //switches = new DigitalInput(RobotMap.pickUpSwitch);
        down = false;
    }
    
    public void initDefaultCommand() {
        //setDefaultCommand(new PickUpTeleOp());
    }
    
    public void Up()
    {
        actuator.set(false);
    }
    public void Down()
    {
        actuator.set(true);
    }
    public void setDownState(boolean state)
    {
        down = state;
    }
    public boolean isDown()
    {
        return down;
    }
    
    public void setMotor(double speed)
    {
        motor.set(-speed);
    }
    
    public boolean getSwitch()
    {
        return switches.get();
    }
    
    public void updateStatus()
    {
        SmartDashboard.putBoolean("PickupDown",isDown());
    }
}
