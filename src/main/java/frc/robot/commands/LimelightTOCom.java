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

        if (Robot.limelight.getTV() != 0){
            if(Robot.limelight.getTX() < 5){
                Robot.ledStrip.solid(60, 255, 255);
                Robot.lightSet = true;
            } else{
                Robot.ledStrip.solid(15, 255, 255);
                Robot.lightSet = true;

            }
        }
    }
}