package frc.robot.commands.autonomous.subsystem_commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoClawPosition extends CommandBase{
    
    double clawSetPoint;
    Timer timer = null;

    /** Creates a new Claw Positioning Command. */
    public AutoClawPosition(double setPoint) {
        this.clawSetPoint = setPoint;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.crane.setClaw(clawSetPoint);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.crane.setClaw(clawSetPoint);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get() >= 0.5;
    }
}