package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CraneTOCom;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Crane extends SubsystemBase{
    private CANSparkMax craneRotator;
    private CANSparkMax craneExtender;
    private CANSparkMax clawManipulator;
    public RelativeEncoder m_rotateEncoder;
    public RelativeEncoder m_extendEncoder; 
    public RelativeEncoder m_clawEncoder;
    private PIDController pidRot = new PIDController(Constants.kRotatorKP, 0, 0);
    private PIDController pidClaw = new PIDController(Constants.kClawKP, 0, 0);

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

        craneRotator.setSmartCurrentLimit(Constants.kCraneCurrentLimit);
        craneExtender.setSmartCurrentLimit(Constants.kExtenderCurrentLimit);
        clawManipulator.setSmartCurrentLimit(Constants.kClawCurrentLimit);

        m_rotateEncoder = craneRotator.getEncoder();
        m_extendEncoder = craneExtender.getEncoder();
        m_clawEncoder = clawManipulator.getEncoder();

        m_rotateEncoder.setPositionConversionFactor(Constants.kRotateEncoderDistancePerPulse);
        m_extendEncoder.setPositionConversionFactor(Constants.kExtendEncoderDistancePerPulse);
        m_clawEncoder.setPositionConversionFactor(Constants.kClawEncoderDistancePerPulse);
    }

    @Override
    public void periodic(){
        setDefaultCommand(new CraneTOCom());
    }

    public void updateCrane(){
        SmartDashboard.putNumber("Rotator", m_rotateEncoder.getPosition());
        SmartDashboard.putNumber("Extender", m_extendEncoder.getPosition());
        SmartDashboard.putNumber("Claw", m_clawEncoder.getPosition());
    }

    public void setArmPosition(double setting){
        double setpoint = pidRot.calculate(getRotator(), setting);
        craneRotator.setVoltage(12*setpoint);
    }

    public void setExtendPosition(double setting){
        if (Math.abs(setting-getExtender()) > 0.5){
            double voltage = 12 * (setting-getExtender())/Math.abs(setting-getExtender());
            craneExtender.setVoltage(voltage);
        } 
    }

    public void setClaw(double setpoint){
        double voltage = pidClaw.calculate(getClaw(), setpoint);
        if (voltage > 0.5){
            craneExtender.setVoltage(voltage);
        } else {
            clawManipulator.setVoltage(0);
        }
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