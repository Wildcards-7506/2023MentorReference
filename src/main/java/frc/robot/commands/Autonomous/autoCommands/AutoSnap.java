package frc.robot.commands.autonomous.autoCommands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoSnap extends CommandBase{
    
    int angle;
    double rotation;

    /** Creates a new Drivetrain Snap-to-angle Command. */
    public AutoSnap(int angle) {
        this.angle = angle;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.drivetrain.drive(0.0, 0.0, 0.3, false);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.drivetrain.drive(0,0,0,true);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return  (Math.abs(angle-Robot.drivetrain.getHeading()) < 5);
    }
}