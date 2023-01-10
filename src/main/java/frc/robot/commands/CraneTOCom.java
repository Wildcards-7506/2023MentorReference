package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;
import frc.robot.Constants.CraneConstants;

public class CraneTOCom extends CommandBase{
    public CraneTOCom() {
        addRequirements(Robot.crane);
    }

    @Override
    public void execute(){
        if (PlayerConfigs.collectPosition){
            Robot.crane.setArmPosition(CraneConstants.kRotatorCollect);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderCollect);
        } else if (PlayerConfigs.groundPosition){
            Robot.crane.setArmPosition(CraneConstants.kRotatorGround);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderGround);
        } else if (PlayerConfigs.midPosition){
            Robot.crane.setArmPosition(CraneConstants.kRotatorMid);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderMid);
        } else if (PlayerConfigs.hiPosition){
            Robot.crane.setArmPosition(CraneConstants.kRotatorHi);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderHi);
        }

        Robot.crane.setClaw(PlayerConfigs.openClaw);
    }
}
