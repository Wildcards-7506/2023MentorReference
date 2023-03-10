// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.playerconfigs.PlayerConfigs;
import frc.robot.subsystems.Crane;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SignalLight;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {  
  private SequentialCommandGroup autoMode;
  public PlayerConfigs driver;
  public PlayerConfigs coDriver;
  //Subsystem Declarations

  public static final Drivetrain drivetrain = new Drivetrain(
    Constants.LEFT_DRIVE_TRAIN_0,
    Constants.LEFT_DRIVE_TRAIN_1,
    Constants.RIGHT_DRIVE_TRAIN_0,
    Constants.RIGHT_DRIVE_TRAIN_1,
    Constants.RIGHT_DROP_ENGAGE,
    Constants.LEFT_DROP_ENGAGE
  );

  public static final Crane crane = new Crane(
    Constants.ROTATOR0,
    Constants.ROTATOR1,
    Constants.EXTENDER,
    Constants.CLAW,
    Constants.ARTICULATOR,
    true
  );
  
  public static final Limelight limelight = new Limelight();

  public static final SignalLight signalLight = new SignalLight(0,30);

  //Controllers
  public static final PS4Controller controller0 = new PS4Controller(Constants.DRIVER_CONTROLLER_0);
  public static final XboxController controller1 = new XboxController(Constants.DRIVER_CONTROLLER_1);

  //Test Timer & Flag
  Timer timer = new Timer();

  /*
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    HeadsDownDisplay.initBot();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    HeadsDownDisplay.m_field.setRobotPose(drivetrain.odometry.getPoseMeters());
    SmartDashboard.putNumber("Match Time",Timer.getMatchTime());

    SmartDashboard.putData(CommandScheduler.getInstance());
  }

  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    //Need LED Indicator Here
    autoMode = HeadsDownDisplay.auto_chooser.getSelected();
    autoMode.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    drivetrain.m_drive.feed();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    SignalLight.teamColor = DriverStation.getAlliance();
    driver = HeadsDownDisplay.driver_chooser.getSelected();
    coDriver = HeadsDownDisplay.codriver_chooser.getSelected();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    driver.getDriverConfig();
    coDriver.getCoDriverConfig();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    HeadsDownDisplay.updateStartupConfig();
    signalLight.rainbow();
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    crane.resetEncoders();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    SmartDashboard.putNumber("Rotator", crane.getRotator());
    SmartDashboard.putNumber("Extender", crane.getExtender());
    SmartDashboard.putNumber("Claw", crane.getClaw());
    SmartDashboard.putNumber("Articulator", crane.getArticulator());
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
