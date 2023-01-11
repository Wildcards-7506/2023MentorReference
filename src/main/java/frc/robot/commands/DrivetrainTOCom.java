package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;

public class DrivetrainTOCom extends CommandBase{

    private boolean driveMode = true;
    double prevX = 0;
    double prevY = 0;
    double prevTurn = 0;
    double xSpeed;
    double ySpeed;
    double turnSpeed;

    public DrivetrainTOCom() {
        addRequirements(Robot.drivetrain);
    }

    @Override
    public void execute(){
        turnSpeed = Constants.RAMP_RATE * (PlayerConfigBase.turnMovement * PlayerConfigBase.turnSpeed) + (1-Constants.RAMP_RATE) * prevTurn;
        xSpeed = Constants.RAMP_RATE * (PlayerConfigBase.driveSpeed * PlayerConfigBase.xMovement) + (1-Constants.RAMP_RATE) * prevX;
        ySpeed = Constants.RAMP_RATE * (PlayerConfigBase.driveSpeed * PlayerConfigBase.yMovement) + (1-Constants.RAMP_RATE) * prevY;

        if(driveMode & PlayerConfigBase.snap == -1){
            //Mecanum Drive, Strafing Enabled
            if(PlayerConfigBase.fineControl > 0.5){
                Robot.drivetrain.drive( PlayerConfigBase.fxMovement * PlayerConfigBase.fdriveSpeed, 
                                        PlayerConfigBase.fyMovement * PlayerConfigBase.fdriveSpeed, 
                                        PlayerConfigBase.fturnSpeed * PlayerConfigBase.fturnSpeed, 
                                        false);
            } else {
                Robot.drivetrain.setDropWheels(0);
                Robot.drivetrain.drive(xSpeed, ySpeed, turnSpeed, true);
            }
        } else if (PlayerConfigBase.snap != -1){
            Robot.drivetrain.snap(PlayerConfigBase.snap);
        } else if (PlayerConfigBase.NOX){
            Robot.drivetrain.setDropWheels(Constants.DROP_WHEEL_DISTANCE);
            Robot.drivetrain.drive(1,0,0,false);
        } else {
            //Tank Drive, Strafing Disabled
            Robot.drivetrain.setDropWheels(Constants.DROP_WHEEL_DISTANCE);
            Robot.drivetrain.drive(xSpeed, 0, turnSpeed, false);
        }

        prevX = xSpeed;
        prevY = ySpeed;
        prevTurn = turnSpeed;

        if (PlayerConfigBase.modeSwitch > 0.8){
            driveMode = false;
        } else {
            driveMode = true;
        }
    }
}