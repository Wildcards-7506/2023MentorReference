package frc.robot.commands.autonomous.subsystem_commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoRotatorPosition extends CommandBase{
    
    double rotatorSetPoint;
    Timer timer = null;

    /** Creates a new Rotation Positioning Command. */
    public AutoRotatorPosition(double setPoint) {
        this.rotatorSetPoint = setPoint;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.crane.setArmPosition(rotatorSetPoint);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.crane.setArmPosition(rotatorSetPoint);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(Robot.crane.getRotator() - rotatorSetPoint) <= 2;
    }
}