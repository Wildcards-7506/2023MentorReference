package frc.robot;

public class PlayerConfigs {
    
    //drivetrain
    public static double xMovement;
    public static double yMovement;
    public static double turnMovement;
    public static double turnSpeed;
    public static double driveSpeed;
    public static int snap;
    public static boolean NOX;

    public static double fineControl;
    public static double fxMovement;
    public static double fyMovement;
    public static double fturnMovement;
    public static double fturnSpeed;
    public static double fdriveSpeed;

    public static double modeSwitch;

    //limelight
    public static boolean switchPipeline;

    //Arm Control
    public static boolean groundPosition;
    public static boolean midPosition;
    public static boolean hiPosition;
    public static boolean collectPosition;

    //Claw
    public static boolean openClaw;
    public static boolean closeClaw;

    public static void getDriverConfig(){
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
    }

    public static void getCoDriverConfig(){  
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