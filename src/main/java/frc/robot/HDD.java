package frc.robot;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueCenterCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueCenterOut;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueLoadCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueLoadOut;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueWallCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineBlueWallOut;
import frc.robot.commands.autonomous.routines.AutoRoutineRedCenterCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineRedCenterOut;
import frc.robot.commands.autonomous.routines.AutoRoutineRedLoadCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineRedLoadOut;
import frc.robot.commands.autonomous.routines.AutoRoutineRedWallCharge;
import frc.robot.commands.autonomous.routines.AutoRoutineRedWallOut;
import frc.robot.playerconfigs.PlayerConfigBase;
import frc.robot.playerconfigs.configs.AnthonyController;
import frc.robot.playerconfigs.configs.MentorController;
import frc.robot.playerconfigs.configs.RyanController;

public class HDD {    
    public static SendableChooser<SequentialCommandGroup> auto_chooser = new SendableChooser<>();
    public static SendableChooser<PlayerConfigBase> driver_chooser = new SendableChooser<>();
    public static SendableChooser<PlayerConfigBase> codriver_chooser = new SendableChooser<>();
    
    //Field display to Shuffleboard
    public static Field2d m_field;
    public static Field2d logo;

    public static SequentialCommandGroup desiredMode;
    public static SequentialCommandGroup prevMode;

    public static void initBot(){

        auto_chooser.setDefaultOption("Blue Center Out", new AutoRoutineBlueCenterOut());
        auto_chooser.addOption("Blue Center Charge", new AutoRoutineBlueCenterCharge());
        auto_chooser.addOption("Blue Wall Out", new AutoRoutineBlueWallOut());
        auto_chooser.addOption("Blue Wall Charge", new AutoRoutineBlueWallCharge());
        auto_chooser.addOption("Blue Load Out", new AutoRoutineBlueLoadOut());
        auto_chooser.addOption("Blue Load Charge", new AutoRoutineBlueLoadCharge());
        auto_chooser.addOption("Red Center Out", new AutoRoutineRedCenterOut());
        auto_chooser.addOption("Red Center Charge", new AutoRoutineRedCenterCharge());
        auto_chooser.addOption("Red Wall Out", new AutoRoutineRedWallOut());
        auto_chooser.addOption("Red Wall Charge", new AutoRoutineRedWallCharge());
        auto_chooser.addOption("Red Load Out", new AutoRoutineRedLoadOut());
        auto_chooser.addOption("Red Load Charge", new AutoRoutineRedLoadCharge());

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
        
    }
}
