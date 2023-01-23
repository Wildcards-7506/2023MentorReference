package frc.robot.commands.autonomous.subsystem_commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoScoringAlign extends CommandBase{
    
    int pipeline;
    double xOffset;
    double yOffset;

    /** Creates a new Scoring Alignment Command. */
    public AutoScoringAlign(int pipelineInput) {
        this.pipeline = pipelineInput;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.limelight.updateData();
        if(pipeline == 0){
            Robot.limelight.conePipeline();
        } else {Robot.limelight.cubePipeline();}
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.limelight.updateData();
        xOffset = 0.2 * Robot.limelight.getTX()/Math.abs(Robot.limelight.getTX());
        yOffset = -0.2 * (Robot.limelight.targetArea - Robot.limelight.getTA());
        Robot.drivetrain.drive(yOffset, xOffset, 0, true);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.drivetrain.drive(0,0,0,true);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return  Robot.limelight.getTV() == 0.0 ||
                ((Robot.limelight.getTX() < 1) && 
                (Math.abs(Robot.limelight.targetArea - Robot.limelight.getTA()) < 0.1));
    }
}