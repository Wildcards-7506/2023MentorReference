package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;

public class DrivetrainTeleopCommand extends CommandBase{

    double prevXspeed = 0;
    double prevYspeed = 0;
    double prevRot = 0;
    double xspeed, yspeed, rot;

    public DrivetrainTeleopCommand() {
        addRequirements(Robot.drivetrain);
    }

    @Override
    public void execute(){
        //Mecanum Drive
        if(!PlayerConfigs.modeSwitch){
            double xInputSpeed = PlayerConfigs.fineControlToggle ? 
                PlayerConfigs.fineDriveSpeed * PlayerConfigs.xMovement :
                PlayerConfigs.driveSpeed * PlayerConfigs.xMovement;
            double yInputSpeed = PlayerConfigs.fineControlToggle ? 
                PlayerConfigs.fineDriveSpeed * PlayerConfigs.yMovement : 
                PlayerConfigs.driveSpeed * PlayerConfigs.yMovement;
            double inputRot = PlayerConfigs.fineControlToggle ? 
                PlayerConfigs.fineTurnSpeed * PlayerConfigs.turnMovement : 
                PlayerConfigs.turnMovement * PlayerConfigs.turnSpeed;
            //Fine Control
            if(PlayerConfigs.fineControlToggle){
                xspeed = xInputSpeed;
                yspeed = yInputSpeed;
                rot = inputRot;
            } else {
                xspeed = PlayerConfigs.kRampRate * xInputSpeed + (1 - PlayerConfigs.kRampRate) * prevXspeed;
                yspeed = PlayerConfigs.kRampRate * yInputSpeed + (1 - PlayerConfigs.kRampRate) * prevYspeed;
                rot = PlayerConfigs.kRampRate * inputRot + (1 - PlayerConfigs.kRampRate) * prevRot;
            }            
        //Tank Drive
        } else {
            //Need to add drop motors here
            double yInputSpeed = PlayerConfigs.fineControlToggle ? 
                PlayerConfigs.fineDriveSpeed * PlayerConfigs.yMovement : 
                PlayerConfigs.driveSpeed * PlayerConfigs.yMovement;
            double inputRot = PlayerConfigs.fineControlToggle ? 
                PlayerConfigs.fineTurnSpeed * PlayerConfigs.turnMovement : 
                PlayerConfigs.driveSpeed * PlayerConfigs.turnMovement * PlayerConfigs.turnSpeed;
            //Fine Control
            if(PlayerConfigs.fineControlToggle){
                xspeed = 0;
                yspeed = yInputSpeed;
                rot = inputRot;
            } else {
                xspeed = 0;
                yspeed = PlayerConfigs.kRampRate * yInputSpeed + (1 - PlayerConfigs.kRampRate) * prevYspeed;
                rot = PlayerConfigs.kRampRate * inputRot + (1 - PlayerConfigs.kRampRate) * prevRot;
            }    
        }

        prevXspeed = xspeed;
        prevYspeed = yspeed;
        prevRot = rot;

        //Snap if needed, otherwise set drive motors
        if(PlayerConfigs.snap0){
            Robot.drivetrain.snap(0);
        } else if(PlayerConfigs.snap90) {
            Robot.drivetrain.snap(90);
        } else if(PlayerConfigs.snap180) {
            Robot.drivetrain.snap(180);
        } else if(PlayerConfigs.snap270){
            Robot.drivetrain.snap(270);
        } else if(PlayerConfigs.alignDrivetrain){
            Robot.drivetrain.targetAlign();
        } else if (PlayerConfigs.brake) {
            Robot.drivetrain.drive(0, 0, 0, true);
        } else {
            Robot.drivetrain.drive(yspeed, xspeed, rot, !PlayerConfigs.modeSwitch);
        }    
    }
}