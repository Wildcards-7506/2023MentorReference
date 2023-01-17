package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;
import frc.robot.Constants;

public class CraneTOCom extends CommandBase{
    public CraneTOCom() {
        addRequirements(Robot.crane);
    }

    @Override
    public void execute(){
        if (PlayerConfigBase.collectPosition){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderCollect);
        } else if (PlayerConfigBase.groundPosition){
            Robot.crane.setArmPosition(Constants.kRotatorGround);
            Robot.crane.setExtendPosition(Constants.kExtenderGround);
        } else if (PlayerConfigBase.midPosition){
            Robot.crane.setArmPosition(Constants.kRotatorMid);
            Robot.crane.setExtendPosition(Constants.kExtenderMid);
        } else if (PlayerConfigBase.hiPosition){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHi);
        } else if (Robot.crane.getExtender() > Constants.kExtenderClosed){
            Robot.crane.setArmPosition(Constants.kRotatorExtendLimit);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
        } else {
            Robot.crane.setArmPosition(Constants.kRotatorClosed);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
        }
        
        if (PlayerConfigBase.openClaw){
            Robot.crane.setClaw(Constants.kClawOpen);
        } else {
            Robot.crane.setClaw(Constants.kClawClosed);
        }
    }
}
