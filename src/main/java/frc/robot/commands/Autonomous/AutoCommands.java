package frc.robot.commands.autonomous;

import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;

public class AutoCommands {
    public static double clawPosition = Constants.kClawClosed;
    //Returns a series of waypoints to the controller to run complex motion paths
    public static Command drivetrainMotion(Trajectory trajectory) {
    
        MecanumControllerCommand mecanumControllerCommand =
            new MecanumControllerCommand(
                trajectory,
                Robot.drivetrain::getPose,
                Constants.kFeedforward,
                Constants.kinematics,
    
                // Position contollers
                new PIDController(Constants.kPXController, 0, 0),
                new PIDController(Constants.kPYController, 0, 0),
                new ProfiledPIDController(
                    Constants.kPThetaController, 0, 0, Constants.kThetaControllerConstraints),
    
                // Needed for normalizing wheel speeds
                Constants.kMaxSpeedMetersPerSecond,
    
                // Velocity PID's
                new PIDController(Constants.kPFrontLeftVel, 0, 0),
                new PIDController(Constants.kPRearLeftVel, 0, 0),
                new PIDController(Constants.kPFrontRightVel, 0, 0),
                new PIDController(Constants.kPRearRightVel, 0, 0),
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

    //need a better way than while loops here
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
        while ( Math.abs(Robot.crane.getRotator() - Constants.kRotatorHi) > 1 
                & Math.abs(Robot.crane.getExtender() - Constants.kExtenderHi) > 1){
            Robot.crane.setArmPosition(Constants.kRotatorHi);
            Robot.crane.setExtendPosition(Constants.kExtenderHi);
            Robot.drivetrain.m_drive.feed();
        }
        clawPosition = Constants.kClawOpen;
        while ( Math.abs(Robot.crane.getRotator() - Constants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - Constants.kExtenderClosed) > 1){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
    }

    public static void autoCollect(){
        while ( Math.abs(Robot.crane.getRotator() - Constants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - Constants.kExtenderCollect) > 1){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
        clawPosition = Constants.kClawClosed;
        while ( Math.abs(Robot.crane.getRotator() - Constants.kRotatorCollect) > 1 
                & Math.abs(Robot.crane.getExtender() - Constants.kExtenderClosed) > 1){
            Robot.crane.setArmPosition(Constants.kRotatorCollect);
            Robot.crane.setExtendPosition(Constants.kExtenderClosed);
            Robot.drivetrain.m_drive.feed();
        }
    }
}