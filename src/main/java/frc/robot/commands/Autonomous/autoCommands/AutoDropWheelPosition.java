package frc.robot.commands.autonomous.autoCommands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDropWheelPosition extends CommandBase{
    
    int dropWheelSetPoint;
    Timer timer = null;

    /** Creates a new Claw Positioning Command. */
    public AutoDropWheelPosition(int setPoint) {
        this.dropWheelSetPoint = setPoint;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.drivetrain.setDropWheels(dropWheelSetPoint);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return  Math.abs(Robot.drivetrain.getDWL() - dropWheelSetPoint) >= 0.5 &&
                Math.abs(Robot.drivetrain.getDWR() - dropWheelSetPoint) >= 0.5;
    }
}