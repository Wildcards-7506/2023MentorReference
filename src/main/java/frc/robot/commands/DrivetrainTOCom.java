package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;

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
        turnSpeed = DriveConstants.RAMP_RATE * (PlayerConfigs.turnMovement * PlayerConfigs.turnSpeed) + (1-DriveConstants.RAMP_RATE) * prevTurn;
        xSpeed = DriveConstants.RAMP_RATE * (PlayerConfigs.driveSpeed * PlayerConfigs.xMovement) + (1-DriveConstants.RAMP_RATE) * prevX;
        ySpeed = DriveConstants.RAMP_RATE * (PlayerConfigs.driveSpeed * PlayerConfigs.yMovement) + (1-DriveConstants.RAMP_RATE) * prevY;

        if(driveMode & PlayerConfigs.snap == -1){
            //Mecanum Drive, Strafing Enabled
            if(PlayerConfigs.fineControl > 0.5){
                Robot.drivetrain.drive( PlayerConfigs.fxMovement * PlayerConfigs.fdriveSpeed, 
                                        PlayerConfigs.fyMovement * PlayerConfigs.fdriveSpeed, 
                                        PlayerConfigs.fturnSpeed * PlayerConfigs.fturnSpeed, 
                                        false);
            } else {
                Robot.drivetrain.setDropWheels(0, 0);
                Robot.drivetrain.drive(xSpeed, ySpeed, turnSpeed, true);
            }
        } else if (PlayerConfigs.snap != -1){
            Robot.drivetrain.snap(PlayerConfigs.snap);
        } else if (PlayerConfigs.NOX){
            Robot.drivetrain.setDropWheels(DriveConstants.DROP_WHEEL_DISTANCE, 12);
            Robot.drivetrain.drive(1,0,0,false);
        } else {
            //Tank Drive, Strafing Disabled
            Robot.drivetrain.setDropWheels(DriveConstants.DROP_WHEEL_DISTANCE, 12 * xSpeed);
            Robot.drivetrain.drive(xSpeed, 0, turnSpeed, false);
        }

        prevX = xSpeed;
        prevY = ySpeed;
        prevTurn = turnSpeed;

        if (PlayerConfigs.modeSwitch > 0.8){
            driveMode = false;
        } else {
            driveMode = true;
        }
    }
}