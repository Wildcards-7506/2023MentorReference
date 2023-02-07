package frc.robot.commands.autonomous.subsystem_commands;

import frc.robot.Robot;
import frc.robot.subsystems.Crane.EndEffectorState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoClawPosition extends CommandBase{
    
    EndEffectorState state;
    boolean actionFlag;
    Timer timer = null;

    /** Creates a new Claw Positioning Command. */
    public AutoClawPosition(EndEffectorState setPoint, boolean action) {
        this.state = setPoint;
        this.actionFlag = action;
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
        Robot.crane.setEndEffector(state, actionFlag);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.crane.setEndEffector(state, actionFlag);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get() >= 0.5;
    }
}