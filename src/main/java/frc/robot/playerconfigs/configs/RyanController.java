package frc.robot.playerconfigs.configs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;

public class RyanController extends PlayerConfigBase{

    //DRIVER CONTROLS
    public void getDriverConfig(){
        //drivetrain
        yMovement = Robot.controller0.getLeftX();
        xMovement = Robot.controller0.getLeftY();
        turnMovement = Robot.controller0.getRightX();
        snap = Robot.controller0.getPOV();
        NOX = Robot.controller0.getR1Button();
        turnSpeed = 0.7;
        driveSpeed = 1;

        //Drivetrain change
        modeSwitch = Robot.controller0.getR2Axis();
        SmartDashboard.putNumber("yMove:", yMovement);
        SmartDashboard.putNumber("xMove:", xMovement);
        SmartDashboard.putNumber("turnMove:", turnMovement);
        SmartDashboard.putNumber("snap:", snap);
        SmartDashboard.putBoolean("NOX:", NOX);
    }

    //CODRIVER CONTROLS
    public void getCoDriverConfig(){  
        //limelight
        switchPipeline = Robot.controller1.getStartButton();

        //Arm Positions
        groundPosition = Robot.controller1.getPOV() == 180;
        midPosition = Robot.controller1.getPOV() == 90;
        hiPosition = Robot.controller1.getPOV() == 0;
        collectPosition = Robot.controller1.getPOV() == 270;

        //Claw Operation
        openClaw = Robot.controller1.getRightBumper();

        //Fine Drivetrain Control
        fineControl = Robot.controller1.getRightTriggerAxis();
        fyMovement = Robot.controller1.getLeftX();
        fxMovement = Robot.controller1.getLeftY();
        fturnMovement = Robot.controller1.getRightX();
        fturnSpeed = 0.2;
        fdriveSpeed = 0.2;
    }
}