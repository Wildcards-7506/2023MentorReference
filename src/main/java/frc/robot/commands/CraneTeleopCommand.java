package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;
import frc.robot.Constants;

public class CraneTeleopCommand extends CommandBase{
    public CraneTeleopCommand() {
        addRequirements(Robot.crane);
    }

    @Override
    public void execute(){
        //COLLECTION
        if (PlayerConfigs.collectPosition){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderCollect);
        } else if (PlayerConfigs.groundPosition){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderGround);
        } else if (PlayerConfigs.midPosition){
            Robot.crane.setArmPosition(Constants.kRotatorMid);
            Robot.crane.setExtendPosition(Constants.kExtenderMid);
            Robot.drivetrain.targetAlign();
        } else if (PlayerConfigs.hiPosition){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHi);
            Robot.drivetrain.targetAlign();
        //SAFETY
            //Ground to storage - Avoid frame collision
        } else if (Robot.crane.getRotator() < Constants.kRotatorCollect & Robot.crane.getExtender() > Constants.kExtenderClosed){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            //Scoring to storage - Height Limit
        } else if (Robot.crane.getRotator() > Constants.kRotatorCollect & Robot.crane.getExtender() > Constants.kExtenderHeightLimit){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHeightLimit);    
        //STORAGE
        } else {
            Robot.crane.setArmPosition(Constants.kRotatorClosed);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
        }
        
        if (PlayerConfigs.openClaw){
            Robot.crane.setClaw(Constants.kClawOpen);
        //     Robot.crane.setRoller(8);
        // } else if (PlayerConfigBase.closeClaw){
        //     Robot.crane.setRoller(-8);
        } else {
            Robot.crane.setClaw(Constants.kClawClosed);
        }
    }
}
