package frc.robot.commands.autonomous.autoCommands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoExtenderPosition extends CommandBase{
    
    double extenderSetPoint;

    /** Creates a new Extention Positioning Command. */
    public AutoExtenderPosition(double setPoint) {
        this.extenderSetPoint = setPoint;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.crane.setExtendPosition(extenderSetPoint);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.crane.setExtendPosition(extenderSetPoint);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(Robot.crane.getExtender() - extenderSetPoint) <= 0.5;
    }
}