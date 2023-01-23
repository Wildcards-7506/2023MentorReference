package frc.robot.commands.autonomous.autoCommands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoPitchCorrect extends CommandBase{
    
    boolean tilted = false;
    double angle;
    double rotation;

    /** Creates a new Auto Pitch Correction Command. */
    public AutoPitchCorrect() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //angle = Robot.drivetrain.getPitch();
        if(angle > 3){tilted = true;}
        Robot.drivetrain.drive(-0.2, 0.0, 0, false);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.drivetrain.drive(0,0,0,true);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;//tilted && Robot.drivetrain.getPitch() < 3;
    }
}