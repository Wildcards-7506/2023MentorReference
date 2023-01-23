package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;

public class LEDTOCom extends CommandBase{
    private boolean locked = false;
    
    public LEDTOCom() {
        addRequirements(Robot.ledStrip);
    }

    @Override
    public void execute(){
        if (Robot.limelight.getTV() != 0){
            if(Robot.limelight.getTX() < 5){
                Robot.ledStrip.solid(60, 255, 255);
            } else{
                Robot.ledStrip.solid(15, 255, 255);
            }
            locked = false;
        } else if (PlayerConfigBase.coneSignal){
            Robot.ledStrip.solid(30, 255, 255);
            locked = true;
        } else if (PlayerConfigBase.cubeSignal){
            Robot.ledStrip.solid(150, 255, 255);
            locked = true;
        } else if (PlayerConfigBase.unlockLED){
            locked = false;
        } else if (!locked){
            Robot.ledStrip.teamColor();
        }
    }
}
