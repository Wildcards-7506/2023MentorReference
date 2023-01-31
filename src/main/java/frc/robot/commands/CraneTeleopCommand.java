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
        //Rotator & Extender
        //COLLECTION
        if (PlayerConfigs.collectPosition){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderCollect);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() + Constants.kArticulatorOffset);
        } else if (PlayerConfigs.groundPosition){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderGround);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() + Constants.kArticulatorOffset);
        } else if (PlayerConfigs.midPosition){
            Robot.crane.setArmPosition(Constants.kRotatorMid);
            Robot.crane.setExtendPosition(Constants.kExtenderMid);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() - Constants.kArticulatorOffset);
            Robot.drivetrain.targetAlign();
        } else if (PlayerConfigs.hiPosition){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHi);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() - Constants.kArticulatorOffset);
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
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator());    
        //STORAGE
        } else {
            Robot.crane.setArmPosition(Constants.kRotatorClosed);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator());
        }
        
        //Claw
        if (PlayerConfigs.release){
            if(Robot.crane.clawMode){
                Robot.crane.setClaw(Constants.kClawOpen);
            } else {
                Robot.crane.setRoller(8);
            }
        } else if (PlayerConfigs.collect & !Robot.crane.clawMode){
            Robot.crane.setRoller(-8);
        } else {
            if(Robot.crane.clawMode){
                Robot.crane.setClaw(Constants.kClawClosed); 
            } else {
                Robot.crane.setRoller(0);
            }
        }
    }
}
