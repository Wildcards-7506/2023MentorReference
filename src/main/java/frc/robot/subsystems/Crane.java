package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CraneTeleopCommand;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Crane extends SubsystemBase{
    private CANSparkMax craneRotator;
    private CANSparkMax craneExtender;
    private CANSparkMax clawManipulator;
    public RelativeEncoder m_rotateEncoder;
    public RelativeEncoder m_extendEncoder; 
    public RelativeEncoder m_clawEncoder;
    private SparkMaxPIDController pidRot;
    private SparkMaxPIDController pidClaw;

    public Crane (int rotator, int extender, int claw){
        craneRotator = new CANSparkMax(rotator, MotorType.kBrushless);
        craneExtender = new CANSparkMax(extender, MotorType.kBrushless);
        clawManipulator = new CANSparkMax(claw, MotorType.kBrushless);

        craneRotator.enableSoftLimit(SoftLimitDirection.kForward, true);
        craneRotator.enableSoftLimit(SoftLimitDirection.kReverse, true);
        craneExtender.enableSoftLimit(SoftLimitDirection.kForward, true);
        craneExtender.enableSoftLimit(SoftLimitDirection.kReverse, true);
        clawManipulator.enableSoftLimit(SoftLimitDirection.kForward, true);
        clawManipulator.enableSoftLimit(SoftLimitDirection.kReverse, true);

        craneRotator.setSoftLimit(SoftLimitDirection.kForward, 0);
        craneRotator.setSoftLimit(SoftLimitDirection.kReverse, 330);
        craneExtender.setSoftLimit(SoftLimitDirection.kForward, 0);
        craneExtender.setSoftLimit(SoftLimitDirection.kReverse, 48);
        clawManipulator.setSoftLimit(SoftLimitDirection.kForward, 90);
        clawManipulator.setSoftLimit(SoftLimitDirection.kReverse, 0);

        craneRotator.setSmartCurrentLimit(Constants.kRotateCurrentLimit);
        craneExtender.setSmartCurrentLimit(Constants.kExtenderCurrentLimit);
        clawManipulator.setSmartCurrentLimit(Constants.kClawCurrentLimit);

        m_rotateEncoder = craneRotator.getEncoder();
        m_extendEncoder = craneExtender.getEncoder();
        m_clawEncoder = clawManipulator.getEncoder();

        pidRot = craneRotator.getPIDController();
        pidClaw = clawManipulator.getPIDController();

        pidRot.setP(Constants.kRotatorKP);
        pidRot.setI(0);
        pidRot.setD(0);
        pidRot.setIZone(0);
        pidRot.setFF(0);
        pidRot.setOutputRange(0, Constants.kRotatorMid);
        pidClaw.setP(Constants.kClawKP);
        pidClaw.setI(0);
        pidClaw.setD(0);
        pidClaw.setIZone(0);
        pidClaw.setFF(0);
        pidClaw.setOutputRange(0, Constants.kClawClosed);

        m_rotateEncoder.setPositionConversionFactor(Constants.kRotateEncoderDistancePerPulse);
        m_extendEncoder.setPositionConversionFactor(Constants.kExtendEncoderDistancePerPulse);
        m_clawEncoder.setPositionConversionFactor(Constants.kClawEncoderDistancePerPulse);

        craneRotator.burnFlash();
        craneExtender.burnFlash();
        clawManipulator.burnFlash();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new CraneTeleopCommand());
    }

    public void updateCrane(){
        SmartDashboard.putNumber("Rotator", m_rotateEncoder.getPosition());
        SmartDashboard.putNumber("Extender", m_extendEncoder.getPosition());
        SmartDashboard.putNumber("Claw", m_clawEncoder.getPosition());
    }

    public void setArmPosition(double setpoint){
        pidRot.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void setExtendPosition(double setting){
        if (Math.abs(setting-getExtender()) > 0.5){
            double voltage = 12 * (setting-getExtender())/Math.abs(setting-getExtender());
            craneExtender.setVoltage(voltage);
        } 
    }

    public void setClaw(double setpoint){
        pidClaw.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void setRoller(double setpoint){
        clawManipulator.setVoltage(setpoint);
    }

    public double getRotator(){
        return m_rotateEncoder.getPosition();
    }

    public double getExtender(){
        return m_extendEncoder.getPosition();
    }

    public double getClaw(){
        return m_clawEncoder.getPosition();
    }

}
