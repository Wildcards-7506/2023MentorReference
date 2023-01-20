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
        public static final int LEFT_DRIVE_TRAIN_0 = 1;
        public static final int LEFT_DRIVE_TRAIN_1 = 10;
        public static final int RIGHT_DRIVE_TRAIN_0 = 20;
        public static final int RIGHT_DRIVE_TRAIN_1 = 11;
        public static final int RIGHT_DROP_ENGAGE = 5;
        public static final int LEFT_DROP_ENGAGE = 16;

        //Crane
        public static final int ROTATOR = 2;
        public static final int EXTENDER = 3;
        public static final int CLAW = 4;

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

    //Drive Constants   

        //Speed and Power Limits
        public static final int kDrivetrainCurrentLimit = 30;
        public static final double kRampRate = 0.01;
        public static final int kDropWheelDistance = 16;
        public static final double kAlignmentSpeed = 0.2;
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

        //Movement Controller Constants
        public static final double kPXController = 0.5;
        public static final double kPYController = 0.5;
        public static final double kPThetaController = 0.5;
        public static final double ffKS = 1;
        public static final double ffKV = 0.8;
        public static final double ffKA = 0.15;

        //Wheel Controller Constants
        public static final double kPFrontLeftVel = 0.5;
        public static final double kPRearLeftVel = 0.5;
        public static final double kPFrontRightVel = 0.5;
        public static final double kPRearRightVel = 0.5;
        
        //Robot Size Parameters
        public static final double kTrackwidthMeters = Units.inchesToMeters(20.176);
        public static final double kTrackLengthMeters = Units.inchesToMeters(21.911);;
        public static final double driveTrainGearRatio = 1/9;
        public static final double kEncoderDistancePerPulse = driveTrainGearRatio * Math.PI * Units.inchesToMeters(8);

        // Robot Movement Profiles
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

        public static MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
            new Translation2d(kTrackLengthMeters / 2, kTrackwidthMeters / 2),
            new Translation2d(kTrackLengthMeters / 2, -kTrackwidthMeters / 2),
            new Translation2d(-kTrackLengthMeters / 2, kTrackwidthMeters / 2),
            new Translation2d(-kTrackLengthMeters / 2, -kTrackwidthMeters / 2));

        public static final  TrajectoryConfig kconfig =  
            new TrajectoryConfig(
                kMaxSpeedMetersPerSecond,
                kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(kinematics);

        public static final SimpleMotorFeedforward kFeedforward =
        new SimpleMotorFeedforward(ffKS, ffKV, ffKA);

    //Crane Constants
        public static final int kRotateCurrentLimit = 30;
        public static final double kRotateEncoderDistancePerPulse = 1/625 * 360;
        public static final double kRotatorKP = 1.0;
        public static final double kRotatorGround = 10.0;
        public static final double kRotatorHi = 210.0;
        public static final double kRotatorMid = 220.0;
        public static final double kRotatorCollect = 60.0;
        public static final double kRotatorClosed = 0.0;

        public static final int kExtenderCurrentLimit = 30;
        public static final double kExtendEncoderDistancePerPulse = 0.125;
        public static final double kExtenderGround = 24.0;
        public static final double kExtenderMid = 2.5;
        public static final double kExtenderHi = 20.0;
        public static final double kExtenderCollect = 12.0;
        public static final double kExtenderClosed = 0.0;
        public static final double kExtenderHeightLimit = 20.0;
        
        public static final int kClawCurrentLimit = 10;
        public static final double kClawEncoderDistancePerPulse = 1/125 * 360;
        public static final double kClawKP = 1.0;
        public static final double kClawOpen = 85.0;
        public static final double kClawClosed = 0.0;
}