package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;

public class DrivetrainTeleopCommand extends CommandBase{

    private boolean driveMode = true;
    double prevX = 0;
    double prevY = 0;
    double prevTurn = 0;
    double xSpeed;
    double ySpeed;
    double turnSpeed;

    public DrivetrainTeleopCommand() {
        addRequirements(Robot.drivetrain);
    }

    @Override
    public void execute(){
        turnSpeed = Constants.kRampRate * (PlayerConfigs.turnMovement * PlayerConfigs.turnSpeed) + (1-Constants.kRampRate) * prevTurn;
        xSpeed = Constants.kRampRate * (PlayerConfigs.driveSpeed * PlayerConfigs.xMovement) + (1-Constants.kRampRate) * prevX;
        ySpeed = Constants.kRampRate * (PlayerConfigs.driveSpeed * PlayerConfigs.yMovement) + (1-Constants.kRampRate) * prevY;

        //Mecanum Drive, Strafing Enabled
        if(driveMode){
            if (PlayerConfigs.snap0){
                Robot.drivetrain.snap(0);
            } else if (PlayerConfigs.snap180){
                Robot.drivetrain.snap(180);
            } else if(PlayerConfigs.groundPosition || PlayerConfigs.hiPosition || PlayerConfigs.midPosition || PlayerConfigs.collectPosition){
                Robot.drivetrain.drive( PlayerConfigs.fxMovement * PlayerConfigs.fdriveSpeed, 
                                        PlayerConfigs.fyMovement * PlayerConfigs.fdriveSpeed, 
                                        PlayerConfigs.fturnSpeed * PlayerConfigs.fturnSpeed, 
                                        true);
            } else {
                Robot.drivetrain.setDropWheels(0);
                Robot.drivetrain.drive(xSpeed, ySpeed, turnSpeed, true);
            }
        } else {
            //Tank Drive, Strafing Disabled
            Robot.drivetrain.setDropWheels(Constants.kDropWheelDistance);
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