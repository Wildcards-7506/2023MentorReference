package frc.robot.playerconfigs;

public class PlayerConfigs {
    
    //drivetrain
    public static double xMovement;
    public static double yMovement;
    public static double turnMovement;
    public static double turnSpeed;
    public static double driveSpeed;
    public static boolean brake;
    public static boolean snap0;
    public static boolean snap90;
    public static boolean snap270;
    public static boolean snap180;
    public static boolean alignDrivetrain;
    public static double kRampRate;

    public static boolean fineControlToggle;
    public static double fineTurnSpeed;
    public static double fineDriveSpeed;

    public static boolean modeSwitch;

    //limelight
    public static boolean switchPipeline;

    //LEDs
    public static boolean coneSignal;
    public static boolean cubeSignal;
    public static boolean unlockLED;

    //Arm Control
    public static boolean groundPosition;
    public static boolean midPosition;
    public static boolean hiPosition;
    public static boolean collectPosition;
    public static boolean fineCraneControl;
    public static double fineRotateControl;
    public static double fineExtendControl;

    //Claw
    public static boolean rollerCollect;
    public static boolean clawOperate;

    //Driver controls method placeholder
    public void getDriverConfig(){}

    //Codriver controls method placeholder
    public void getCoDriverConfig(){}
}