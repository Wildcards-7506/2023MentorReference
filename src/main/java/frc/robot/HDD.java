package frc.robot;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueCenterCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueCenterOut;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueLoadCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueLoadOut;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueWallCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineBlueWallOut;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedCenterCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedCenterOut;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedLoadCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedLoadOut;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedWallCharge;
import frc.robot.commands.Autonomous.Modes.AutoRoutineRedWallOut;

public class HDD {    
    public static SendableChooser<SequentialCommandGroup> m_chooser = new SendableChooser<>();

    //Field display to Shuffleboard
    public static Field2d m_field;
    public static Field2d logo;

    public static SequentialCommandGroup desiredMode;
    public static SequentialCommandGroup prevMode;

    public static void initBot(){

        m_chooser.setDefaultOption("Blue Center Out", new AutoRoutineBlueCenterOut());
        m_chooser.addOption("Blue Center Charge", new AutoRoutineBlueCenterCharge());
        m_chooser.addOption("Blue Wall Out", new AutoRoutineBlueWallOut());
        m_chooser.addOption("Blue Wall Charge", new AutoRoutineBlueWallCharge());
        m_chooser.addOption("Blue Load Out", new AutoRoutineBlueLoadOut());
        m_chooser.addOption("Blue Load Charge", new AutoRoutineBlueLoadCharge());
        m_chooser.addOption("Red Center Out", new AutoRoutineRedCenterOut());
        m_chooser.addOption("Red Center Charge", new AutoRoutineRedCenterCharge());
        m_chooser.addOption("Red Wall Out", new AutoRoutineRedWallOut());
        m_chooser.addOption("Red Wall Charge", new AutoRoutineRedWallCharge());
        m_chooser.addOption("Red Load Out", new AutoRoutineRedLoadOut());
        m_chooser.addOption("Red Load Charge", new AutoRoutineRedLoadCharge());

        // Put the choosers on the dashboard
        SmartDashboard.putData(m_chooser);
        SmartDashboard.putNumber("Startup Time",1.5);
        
        // Create and push Field2d to SmartDashboard.
        m_field = new Field2d();

        SmartDashboard.putData(m_field);
        LiveWindow.disableAllTelemetry();
        LiveWindow.enableTelemetry(Robot.drivetrain.gyro);
    }

    public static void updateStartupConfig(){
        desiredMode = m_chooser.getSelected();
    }
}
