package frc.robot.playerconfigs;

public class PlayerConfigs {
    
    //drivetrain
    public static double xMovement;
    public static double yMovement;
    public static double turnMovement;
    public static double turnSpeed;
    public static double driveSpeed;
    public static boolean snap0;
    public static boolean snap180;

    public static double fineControl;
    public static double fxMovement;
    public static double fyMovement;
    public static double fturnMovement;
    public static double fturnSpeed;
    public static double fdriveSpeed;

    public static double modeSwitch;

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

    //Claw
    public static boolean collect;
    public static boolean release;

    //Driver controls method placeholder
    public void getDriverConfig(){}

    //Codriver controls method placeholder
    public void getCoDriverConfig(){}
}