package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;
import frc.robot.Constants;
import frc.robot.subsystems.Crane.EndEffectorState;

public class CraneTeleopCommand extends CommandBase{
    public EndEffectorState endEffectorState;
    
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
            Robot.crane.setEndEffector(EndEffectorState.INTAKE, PlayerConfigs.clawOperate);
        } else if (PlayerConfigs.groundPosition){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderGround);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() + Constants.kArticulatorOffset);
            Robot.crane.setEndEffector(EndEffectorState.INTAKE, PlayerConfigs.clawOperate);
        } else if (PlayerConfigs.midPosition){
            Robot.crane.setArmPosition(Constants.kRotatorMid);
            Robot.crane.setExtendPosition(Constants.kExtenderMid);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() - Constants.kArticulatorOffset);
            Robot.crane.setEndEffector(EndEffectorState.PLACEMENT, PlayerConfigs.clawOperate);
        } else if (PlayerConfigs.hiPosition){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHi);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator() - Constants.kArticulatorOffset);
            Robot.crane.setEndEffector(EndEffectorState.PLACEMENT, PlayerConfigs.clawOperate);
        //SAFETY
            //Ground to storage - Avoid frame collision
        } else if (Robot.crane.getRotator() < Constants.kRotatorCollect & Robot.crane.getExtender() > Constants.kExtenderClosed){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.crane.setEndEffector(EndEffectorState.IDLE,false);
            //Scoring to storage - Height Limit
        } else if (Robot.crane.getRotator() > Constants.kRotatorCollect & Robot.crane.getExtender() > Constants.kExtenderHeightLimit){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHeightLimit);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator());
            Robot.crane.setEndEffector(EndEffectorState.IDLE,false);
        //Fine Control
        } else if (PlayerConfigs.fineCraneControl){
            Robot.crane.setArmPosition(Robot.crane.getRotator() + PlayerConfigs.fineRotateControl * Constants.fineRotateSpeed);
            Robot.crane.setExtendPosition(Robot.crane.getExtender() + PlayerConfigs.fineExtendControl);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator());
            Robot.crane.setEndEffector(EndEffectorState.PLACEMENT, PlayerConfigs.clawOperate);
        //STORAGE
        } else {
            Robot.crane.setArmPosition(Constants.kRotatorClosed);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.crane.setArticulatorPosition(-Robot.crane.getRotator());
            Robot.crane.setEndEffector(EndEffectorState.IDLE,false);
        }
    }
}
