/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoosePackage.commands.autonomous;

import MoosePackage.commands.CommandBase;

/**
 *
 * @author atenc_000
 */

//set speed to a negative value [-1,0) to go backwards. distance should allways be positive
public class MecanumToDistance extends CommandBase {
    private double speed, distance;
    public MecanumToDistance(double speed, double distance) {
        requires(drive);
        this.speed = speed;
	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drive.startEncoders();
        drive.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drive.mecanumFeedback(speed-drive.getEncoderDifference(), speed+drive.getEncoderDifference());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drive.avgEncoderCount()) >= distance;
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.stopEncoders();
        drive.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
