package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;

public class LimelightTOCom extends CommandBase{

    private boolean prev_StartButton = false;

    public LimelightTOCom(){
        addRequirements(Robot.limelight);
    }

    @Override
    public void execute(){
        Robot.limelight.updateData();
        if(PlayerConfigBase.switchPipeline != prev_StartButton){
            prev_StartButton = PlayerConfigBase.switchPipeline;
            if(PlayerConfigBase.switchPipeline){
                Robot.limelight.switchCameraMode();
            }
        }
    }
}