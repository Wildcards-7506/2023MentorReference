package frc.robot.playerconfigs.configs;

import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigBase;

public class AnthonyController extends PlayerConfigBase {
    
    //DRIVER CONTROLS
    public void getDriverConfig(){
        //drivetrain
        yMovement = Robot.controller0.getLeftX();
        xMovement = Robot.controller0.getLeftY();
        turnMovement = Robot.controller0.getRightX();
        snap = Robot.controller0.getPOV();
        turnSpeed = 0.7;
        driveSpeed = 1;

        //Drivetrain change
        modeSwitch = Robot.controller0.getR2Axis();

        //Cargo Signals
        coneSignal = Robot.controller0.getTriangleButton();
        cubeSignal = Robot.controller0.getSquareButton();
        unlockLED = Robot.controller0.getCircleButton();

    }

    //CODRIVER CONTROLS
    public void getCoDriverConfig(){
        //limelight
        switchPipeline = Robot.controller1.getStartButton();

        //Arm Positions
        groundPosition = Robot.controller1.getAButton();
        midPosition = Robot.controller1.getBButton();
        hiPosition = Robot.controller1.getYButton();
        collectPosition = Robot.controller1.getXButton();

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