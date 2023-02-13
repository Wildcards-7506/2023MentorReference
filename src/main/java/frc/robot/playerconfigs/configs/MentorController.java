package frc.robot.playerconfigs.configs;

import frc.robot.Robot;
import frc.robot.playerconfigs.PlayerConfigs;

public class MentorController extends PlayerConfigs{

    //DRIVER CONTROLS
    public void getDriverConfig(){
        //drivetrain
        yMovement = Robot.controller0.getLeftX();
        xMovement = Robot.controller0.getLeftY();
        turnMovement = Robot.controller0.getRightX();
        snap0 = Robot.controller0.getPOV() == 0;
        snap90 = Robot.controller0.getPOV() == 90;
        snap270 = Robot.controller0.getPOV() == 270;
        snap180 = Robot.controller0.getPOV() == 180;
        alignDrivetrain = Robot.controller0.getL1Button();
        kRampRate = 0.03;
        turnSpeed = 0.7;
        driveSpeed = 1;

        //Fine Drivetrain Control
        fineControlToggle = Robot.controller0.getR1Button();
        fineTurnSpeed = 0.2;
        fineDriveSpeed = 0.2;

        //Drivetrain change
        modeSwitch = Robot.controller0.getR2Button();

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
        groundPosition = Robot.controller1.getPOV() == 180;
        midPosition = Robot.controller1.getPOV() == 90;
        hiPosition = Robot.controller1.getPOV() == 0;
        collectPosition = Robot.controller1.getPOV() == 270;
        fineCraneControl = Robot.controller1.getLeftBumper();
        fineRotateControl = Robot.controller1.getRightY();
        fineExtendControl = Robot.controller1.getLeftY();

        //Claw Operation
        clawOperate = Robot.controller1.getRightBumper();
        rollerCollect = Robot.controller1.getRightTriggerAxis() > 0.5;
    }
}