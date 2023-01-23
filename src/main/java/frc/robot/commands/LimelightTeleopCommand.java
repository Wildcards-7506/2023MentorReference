package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;

public class LimelightTeleopCommand extends CommandBase{

    private boolean prev_StartButton = false;


    public LimelightTeleopCommand(){
        addRequirements(Robot.limelight);
    }

    @Override
    public void execute(){
        Robot.limelight.updateData();
        if(PlayerConfigs.switchPipeline != prev_StartButton){
            prev_StartButton = PlayerConfigs.switchPipeline;
            if(PlayerConfigs.switchPipeline){
                Robot.limelight.switchCameraMode();
            }
        } else if(PlayerConfigs.coneSignal){
                Robot.limelight.conePipeline();
        } else if(PlayerConfigs.cubeSignal){
            Robot.limelight.cubePipeline();
        }
    }
}