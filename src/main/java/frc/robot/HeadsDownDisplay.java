package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.autonomous_routines.BlueCenterCharge;
import frc.robot.commands.autonomous.autonomous_routines.BlueCenterOut;
import frc.robot.commands.autonomous.autonomous_routines.BlueLoadCharge;
import frc.robot.commands.autonomous.autonomous_routines.BlueLoadOut;
import frc.robot.commands.autonomous.autonomous_routines.BlueWallCharge;
import frc.robot.commands.autonomous.autonomous_routines.BlueWallOut;
import frc.robot.commands.autonomous.autonomous_routines.RedCenterCharge;
import frc.robot.commands.autonomous.autonomous_routines.RedCenterOut;
import frc.robot.commands.autonomous.autonomous_routines.RedLoadCharge;
import frc.robot.commands.autonomous.autonomous_routines.RedLoadOut;
import frc.robot.commands.autonomous.autonomous_routines.RedWallCharge;
import frc.robot.commands.autonomous.autonomous_routines.RedWallOut;
import frc.robot.playerconfigs.PlayerConfigs;
import frc.robot.playerconfigs.configs.AnthonyController;
import frc.robot.playerconfigs.configs.MentorController;
import frc.robot.playerconfigs.configs.RyanController;

public class HeadsDownDisplay {    
    private static PowerDistribution pdp = new PowerDistribution();
    public static SendableChooser<SequentialCommandGroup> auto_chooser = new SendableChooser<>();
    public static SendableChooser<PlayerConfigs> driver_chooser = new SendableChooser<>();
    public static SendableChooser<PlayerConfigs> codriver_chooser = new SendableChooser<>();
    
    //Field display to Shuffleboard
    public static Field2d m_field;
    public static Field2d logo;

    public static SequentialCommandGroup desiredMode;
    public static SequentialCommandGroup prevMode;

    public static void initBot(){

        auto_chooser.setDefaultOption("Blue Center Out", new BlueCenterOut());
        auto_chooser.addOption("Blue Center Charge", new BlueCenterCharge());
        auto_chooser.addOption("Blue Wall Out", new BlueWallOut());
        auto_chooser.addOption("Blue Wall Charge", new BlueWallCharge());
        auto_chooser.addOption("Blue Load Out", new BlueLoadOut());
        auto_chooser.addOption("Blue Load Charge", new BlueLoadCharge());
        auto_chooser.addOption("Red Center Out", new RedCenterOut());
        auto_chooser.addOption("Red Center Charge", new RedCenterCharge());
        auto_chooser.addOption("Red Wall Out", new RedWallOut());
        auto_chooser.addOption("Red Wall Charge", new RedWallCharge());
        auto_chooser.addOption("Red Load Out", new RedLoadOut());
        auto_chooser.addOption("Red Load Charge", new RedLoadCharge());

        driver_chooser.setDefaultOption("Ryan", new RyanController());
        driver_chooser.addOption("Mentor", new MentorController());

        codriver_chooser.setDefaultOption("Anthony", new AnthonyController());
        codriver_chooser.addOption("Mentor", new MentorController());

        // Put the choosers on the dashboard
        SmartDashboard.putData(auto_chooser);
        SmartDashboard.putData(driver_chooser);
        SmartDashboard.putData(codriver_chooser);
        SmartDashboard.putNumber("Startup Time",1.5);
        
        // Create and push Field2d to SmartDashboard.
        m_field = new Field2d();

        SmartDashboard.putData(m_field);
        LiveWindow.disableAllTelemetry();
        LiveWindow.enableTelemetry(Robot.drivetrain.gyro);
    }

    public static void updateStartupConfig(){
        desiredMode = auto_chooser.getSelected();
    }

    public static void updateHDD(){
        Robot.crane.updateCrane();
        Robot.drivetrain.updateDrivetrain();
        SmartDashboard.putData(pdp);
    }
}
