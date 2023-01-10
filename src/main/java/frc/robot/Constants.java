package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class Constants {
    //Motor IDs
        //Drivetrain
        public static final int LEFT_DRIVE_TRAIN_0 = 0;
        public static final int LEFT_DRIVE_TRAIN_1 = 9;
        public static final int RIGHT_DRIVE_TRAIN_0 = 20;
        public static final int RIGHT_DRIVE_TRAIN_1 = 10;
        public static final int RIGHT_DROP_DRIVE = 5;
        public static final int LEFT_DROP_DRIVE = 14;
        public static final int RIGHT_DROP_ENGAGE = 6;
        public static final int LEFT_DROP_ENGAGE = 15;

        //Crane
        public static final int ROTATOR = 1;
        public static final int EXTENDER = 2;
        public static final int CLAW = 3;

    //Controller Assignments
        public static final int DRIVER_CONTROLLER_0 = 0;
        public static final int DRIVER_CONTROLLER_1 = 1;
        
        //Control Axis
        public static final int LEFT_STICK_X = 0;
        public static final int LEFT_STICK_Y = 1;
        public static final int RIGHT_STICK_X = 2;
        public static final int RIGHT_STICK_Y = 3;

        //Control D-Pad
        public static final int DPAD_X = 2;
        public static final int DPAD_Y = 3;

        //Control Buttons
        public static final int BUTTON_A = 2;
        public static final int BUTTON_B = 3;
        public static final int BUTTON_X = 1;
        public static final int BUTTON_Y = 4;
        public static final int LEFT_BUMPER = 5;
        public static final int RIGHT_BUMPER = 6;

        public static final int LEFT_TRIGGER = 7;
        public static final int RIGHT_TRIGGER = 8;

        public static final int BUTTON_BACK = 9;
        public static final int BUTTON_START = 10;
        public static final int LEFT_JOYSTICK_BUTTON = 11;
        public static final int RIGHT_JOYSTICK_BUTTON = 12;

        //FMS Data
        public static String teamColor;
        
    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 0.5;
        public static final double kPYController = 0.5;
        public static final double kPThetaController = 0.5;
    
        // Constraint for the motion profilied robot angle controller
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

        public static final  TrajectoryConfig kconfig =  
            new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(DriveConstants.kinematics);
    }

    public static final class DriveConstants {
        public static final double kTrackwidthMeters = Units.inchesToMeters(19.25);
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = 0.7;
        // Distance between centers of front and back wheels on robot
        //Current Gear ratio is 9:1 - THIS MAY CHANGE
        public static final double kEncoderDistancePerPulse = 1/9 * Units.inchesToMeters(4);

        public static MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackwidthMeters / 2),
            new Translation2d(kWheelBase / 2, -kTrackwidthMeters / 2),
            new Translation2d(-kWheelBase / 2, kTrackwidthMeters / 2),
            new Translation2d(-kWheelBase / 2, -kTrackwidthMeters / 2));

            public static final SimpleMotorFeedforward kFeedforward =
            new SimpleMotorFeedforward(1, 0.8, 0.15);
    
            // Example value only - as above, this must be tuned for your drive!
            public static final double kPFrontLeftVel = 0.5;
            public static final double kPRearLeftVel = 0.5;
            public static final double kPFrontRightVel = 0.5;
            public static final double kPRearRightVel = 0.5;

            //Speed Variables
            public static final double RAMP_RATE = 0.01;
            public static final int DROP_WHEEL_DISTANCE = 16;
    }

    public static final class CraneConstants {
        public static final double kRotateEncoderDistancePerPulse = 1/125 * 360;
        public static final double kExtendEncoderDistancePerPulse = 0.125;
        public static final double kClawEncoderDistancePerPulse = 1.0;

        public static final double kRotatorKP = 1.0;
        public static final double kRotatorGround = 240.0;
        public static final double kRotatorMid = 200.0;
        public static final double kRotatorHi = 180.0;
        public static final double kRotatorCollect = 40.0;

        public static final double kExtenderGround = 10.0;
        public static final double kExtenderMid = 10.0;
        public static final double kExtenderHi = 10.0;
        public static final double kExtenderCollect = 10.0;
        public static final double kExtenderClosed = 0.0;
        
        public static double kClawOpen = 12.0;
        public static double kClawClosed = 0.0;
    }
}