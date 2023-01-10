package frc.robot.commands.Autonomous;

import frc.robot.Robot;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.CraneConstants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;

public class AutoCommands {
    //Returns a series of waypoints to the controller to run complex motion paths
    public static Command drivetrainMotion(Trajectory trajectory) {
    
        MecanumControllerCommand mecanumControllerCommand =
            new MecanumControllerCommand(
                trajectory,
                Robot.drivetrain::getPose,
                DriveConstants.kFeedforward,
                DriveConstants.kinematics,
    
                // Position contollers
                new PIDController(AutoConstants.kPXController, 0, 0),
                new PIDController(AutoConstants.kPYController, 0, 0),
                new ProfiledPIDController(
                    AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints),
    
                // Needed for normalizing wheel speeds
                AutoConstants.kMaxSpeedMetersPerSecond,
    
                // Velocity PID's
                new PIDController(DriveConstants.kPFrontLeftVel, 0, 0),
                new PIDController(DriveConstants.kPRearLeftVel, 0, 0),
                new PIDController(DriveConstants.kPFrontRightVel, 0, 0),
                new PIDController(DriveConstants.kPRearRightVel, 0, 0),
                Robot.drivetrain::getWheelSpeeds,
                Robot.drivetrain::setDriveMotorControllersVolts, // Consumer for the output motor voltages
                Robot.drivetrain);
    
        // Reset odometry to the starting pose of the trajectory.
        Robot.drivetrain.resetOdometry(trajectory.getInitialPose());
    
        // Run path following command, then stop at the end.
        return mecanumControllerCommand.andThen(() -> Robot.drivetrain.drive(0, 0, 0, false));
      }

    public static double getPitch(){
        return 1;//Robot.drivetrain.gyro.getPitch();
    }

    public static void chargeAlign(){
        while(getPitch() != 0){
            double setpoint = 0.2 * getPitch()/Math.abs(getPitch());
            Robot.drivetrain.drive(setpoint, 0, 0, false);
            Robot.drivetrain.m_drive.feed();
        }
    }

    public static void postAlign(){
        while(Robot.limelight.getTV() != 0 & Robot.limelight.getTX() > 2.0){
            double direction = 0.2 * Robot.limelight.getTX()/Math.abs(Robot.limelight.getTX());
            Robot.drivetrain.setDriveMotorControllersVolts(new MecanumDriveMotorVoltages(direction, -direction, -direction, direction));
        }
        Robot.drivetrain.setDriveMotorControllersVolts(new MecanumDriveMotorVoltages(0, 0, 0, 0));
        Robot.drivetrain.m_drive.feed();
    }

    public static void autoScore(){
        while ( Math.abs(Robot.crane.getRotator() - CraneConstants.kRotatorHi) > 1 
                & Math.abs(Robot.crane.getExtender() - CraneConstants.kExtenderHi) > 1){
            Robot.crane.setArmPosition(CraneConstants.kRotatorHi);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderHi);
            Robot.drivetrain.m_drive.feed();
        }
        Robot.crane.setClaw(true);
        while ( Math.abs(Robot.crane.getRotator() - CraneConstants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - CraneConstants.kExtenderClosed) > 1){
            Robot.crane.setArmPosition(CraneConstants.kRotatorCollect);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
    }

    public static void autoCollect(){
        while ( Math.abs(Robot.crane.getRotator() - CraneConstants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - CraneConstants.kExtenderCollect) > 1){
            Robot.crane.setArmPosition(CraneConstants.kRotatorCollect);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
        Robot.crane.setClaw(false);
        while ( Math.abs(Robot.crane.getRotator() - CraneConstants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - CraneConstants.kExtenderClosed) > 1){
            Robot.crane.setArmPosition(CraneConstants.kRotatorCollect);
            Robot.crane.setExtendPosition(CraneConstants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
    }
}