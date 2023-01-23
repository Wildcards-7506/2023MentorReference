package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;

public class SignalLightTeleopCommand extends CommandBase{
    private boolean locked = false;
    
    public SignalLightTeleopCommand() {
        addRequirements(Robot.signalLight);
    }

    @Override
    public void execute(){
        if (Robot.limelight.getTV() != 0){
            if(Robot.limelight.getTX() < 5){
                Robot.signalLight.solid(60, 255, 255);
            } else{
                Robot.signalLight.solid(15, 255, 255);
            }
            locked = false;
        } else if (PlayerConfigs.coneSignal){
            Robot.signalLight.solid(30, 255, 255);
            locked = true;
        } else if (PlayerConfigs.cubeSignal){
            Robot.signalLight.solid(150, 255, 255);
            locked = true;
        } else if (PlayerConfigs.unlockLED){
            locked = false;
        } else if (!locked){
            Robot.signalLight.teamColor();
        }
    }
}
