package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.HeadsDownDisplay;
import frc.robot.Robot;
import frc.robot.commands.DrivetrainTeleopCommand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogGyro;
//import edu.wpi.first.wpilibj.SerialPort;
//import com.kauailabs.navx.frc.AHRS;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase{
    private CANSparkMax motorLeft0;
    private CANSparkMax motorLeft1;
    private CANSparkMax dropMotorLeftEngage;
    private CANSparkMax motorRight0;
    private CANSparkMax motorRight1;
    private CANSparkMax dropMotorRightEngage;
    public RelativeEncoder m_leftEncoder0;
    public RelativeEncoder m_rightEncoder0; 
    public RelativeEncoder m_leftEncoder1;
    public RelativeEncoder m_rightEncoder1;
    public RelativeEncoder m_dropEncoderLeft; 
    public RelativeEncoder m_dropEncoderRight; 

    public final MecanumDrive m_drive;

    //public AHRS gyro = new AHRS(SerialPort.Port.kUSB);
    public AnalogGyro gyro = new AnalogGyro(1);

    public MecanumDriveOdometry odometry;
    
    public double initPose = 0.0;

    public Drivetrain (int l0, int l1, int r0, int r1, int rdm, int ldm){
        motorLeft0 = new CANSparkMax(l0, MotorType.kBrushless);
        motorLeft1 = new CANSparkMax(l1, MotorType.kBrushless);
        motorRight0 = new CANSparkMax(r0, MotorType.kBrushless);
        motorRight1 = new CANSparkMax(r1, MotorType.kBrushless);
        dropMotorLeftEngage = new CANSparkMax(ldm, MotorType.kBrushless);
        dropMotorRightEngage = new CANSparkMax(rdm, MotorType.kBrushless);

        motorLeft0.setSmartCurrentLimit(Constants.kDrivetrainCurrentLimit);
        motorLeft1.setSmartCurrentLimit(Constants.kDrivetrainCurrentLimit);
        motorRight0.setSmartCurrentLimit(Constants.kDrivetrainCurrentLimit);
        motorRight1.setSmartCurrentLimit(Constants.kDrivetrainCurrentLimit);
        dropMotorLeftEngage.setSmartCurrentLimit(Constants.kDropWheelCurrentLimit);
        dropMotorRightEngage.setSmartCurrentLimit(Constants.kDropWheelCurrentLimit);

        dropMotorLeftEngage.enableSoftLimit(SoftLimitDirection.kForward, true);
        dropMotorLeftEngage.enableSoftLimit(SoftLimitDirection.kReverse, true);
        dropMotorRightEngage.enableSoftLimit(SoftLimitDirection.kForward, true);
        dropMotorRightEngage.enableSoftLimit(SoftLimitDirection.kReverse, true);

        dropMotorLeftEngage.setSoftLimit(SoftLimitDirection.kForward, 2);
        dropMotorLeftEngage.setSoftLimit(SoftLimitDirection.kReverse, 0);
        dropMotorRightEngage.setSoftLimit(SoftLimitDirection.kForward, 2);
        dropMotorRightEngage.setSoftLimit(SoftLimitDirection.kReverse, 0);

        motorRight1.setInverted(true);
        motorRight0.setInverted(true);

        motorLeft0.burnFlash();
        motorLeft1.burnFlash();
        motorRight0.burnFlash();
        motorRight1.burnFlash();
        dropMotorRightEngage.burnFlash();
        dropMotorLeftEngage.burnFlash();

        m_leftEncoder0 = motorLeft0.getEncoder();
        m_rightEncoder0 = motorRight0.getEncoder();
        m_leftEncoder1 = motorLeft1.getEncoder();
        m_rightEncoder1 = motorRight1.getEncoder();
        m_dropEncoderLeft = dropMotorLeftEngage.getEncoder();
        m_dropEncoderRight = dropMotorRightEngage.getEncoder();

        m_leftEncoder0.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);
        m_rightEncoder0.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);
        m_leftEncoder1.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);
        m_rightEncoder1.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);
        m_dropEncoderLeft.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);
        m_dropEncoderRight.setPositionConversionFactor(Constants.kDrivetrainDistancePerPulse);

        
        m_drive = new MecanumDrive(motorLeft0, motorLeft1, motorRight0, motorRight1);
        resetEncoders();
        odometry = new MecanumDriveOdometry(Constants.kinematics, Rotation2d.fromDegrees(getHeading()), getWheelPositions());
        
    }

    //Every scheduler cycle, we pass our XBox controls so we can control the drivetrain and update its pose in the dashboards
    @Override
    public void periodic(){
        setDefaultCommand(new DrivetrainTeleopCommand());
    }

    public void updateDrivetrain(){
        // Update the odometry in the periodic block
        odometry.update(gyro.getRotation2d(), getWheelPositions());
        HeadsDownDisplay.m_field.setRobotPose(odometry.getPoseMeters());
    }

    public Pose2d getPose(){
        return odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(gyro.getRotation2d(), getWheelPositions(), pose);
    }

    public MecanumDriveWheelSpeeds getWheelSpeeds(){
        return new MecanumDriveWheelSpeeds(
            m_leftEncoder0.getVelocity(), 
            m_leftEncoder1.getVelocity(), 
            m_rightEncoder0.getVelocity(), 
            m_rightEncoder1.getVelocity()
        ); 
    }

    public MecanumDriveWheelPositions getWheelPositions() {
        return new MecanumDriveWheelPositions(
            m_leftEncoder0.getPosition(),
            m_rightEncoder0.getPosition(),
            m_leftEncoder1.getPosition(),
            m_rightEncoder1.getPosition()
        );
    }

    /**
     * Drives the robot at given x, y and theta speeds. Speeds range from [-1, 1] and the linear
     * speeds have no effect on the angular speed. THIS IS TO BE USED IN TELEOP
     *
     * @param xSpeed Speed of the robot in the x direction (forward/backwards).
     * @param ySpeed Speed of the robot in the y direction (sideways).
     * @param rot Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the field.
     */
    @SuppressWarnings("ParameterName")
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        if (fieldRelative) {
        m_drive.driveCartesian(xSpeed, ySpeed, rot, new Rotation2d(-gyro.getAngle()));
        } else {
        m_drive.driveCartesian(xSpeed, ySpeed, rot);
        }
        m_drive.feed();
    }

    
    /** Sets the front left drive MotorController to a voltage. FOR USE IN AUTO ONLY */
    public void setDriveMotorControllersVolts(MecanumDriveMotorVoltages volts) {
        motorLeft0.setVoltage(volts.frontLeftVoltage);
        motorRight0.setVoltage(volts.rearLeftVoltage);
        motorLeft1.setVoltage(volts.frontRightVoltage);
        motorRight1.setVoltage(volts.rearRightVoltage);
    }

    //Articulation of drop wheels
    public void setDropWheels(int level){
        double marginL = level - m_dropEncoderLeft.getPosition();
        double marginR = level - m_dropEncoderRight.getPosition();
        if(Math.abs(marginL) > 1){
            dropMotorLeftEngage.setVoltage(12*marginL/Math.abs(marginL));
        } else {dropMotorLeftEngage.setVoltage(0);}
        if(Math.abs(marginR) > 1){
            dropMotorRightEngage.setVoltage(12*marginR/Math.abs(marginR));
        } else {dropMotorRightEngage.setVoltage(0);}
    }

    //Snap to specfic direction
    public void snap(int direction){
        double setpoint = 6 * (Math.abs(getHeading() - direction));
        setDriveMotorControllersVolts(
            new MecanumDriveMotorVoltages(
                setpoint, 
                setpoint, 
                setpoint, 
                setpoint));
        m_drive.feed();
    }

    public void targetAlign(){
        double yDirection = 0;
        double xDirection = 0;
        if(Math.abs(Robot.limelight.getTX()) > 2){
            yDirection = Constants.kAlignmentSpeed * Robot.limelight.getTX()/Math.abs(Robot.limelight.getTX());
        } else if (Robot.limelight.getTX() < Robot.limelight.targetArea){
            xDirection = -Constants.kAlignmentSpeed;
        }
        drive(xDirection,yDirection,0,true);
        m_drive.feed();
    }

    public void resetEncoders() {
        m_leftEncoder0.setPosition(0);
        m_rightEncoder0.setPosition(0);
        m_leftEncoder1.setPosition(0);
        m_rightEncoder1.setPosition(0);
    }

    public void setMaxOutput(double maxOutput) {
        m_drive.setMaxOutput(maxOutput);
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public double getHeading(){
        return -gyro.getAngle();
    }

    public double getPitch(){
        return 1;//Robot.drivetrain.gyro.getPitch();
    }

    public double getDWL(){
        return Robot.drivetrain.m_dropEncoderLeft.getPosition();
    }

    public double getDWR(){
        return Robot.drivetrain.m_dropEncoderRight.getPosition();
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        return -gyro.getRate();
    }
}