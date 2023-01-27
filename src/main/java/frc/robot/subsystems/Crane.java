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
    private CANSparkMax clawArticulator;
    public RelativeEncoder m_rotateEncoder;
    public RelativeEncoder m_extendEncoder; 
    public RelativeEncoder m_clawEncoder;
    public RelativeEncoder m_articulatorEncoder;
    public SparkMaxPIDController pidRot;
    public SparkMaxPIDController pidClaw;
    public SparkMaxPIDController pidArticulator;

    public Crane (int rotator, int extender, int claw, int articulator){
        craneRotator = new CANSparkMax(rotator, MotorType.kBrushless);
        craneExtender = new CANSparkMax(extender, MotorType.kBrushless);
        clawManipulator = new CANSparkMax(claw, MotorType.kBrushless);
        if (articulator != 0){
            clawArticulator = new CANSparkMax(articulator, MotorType.kBrushless);
        }

        craneRotator.enableSoftLimit(SoftLimitDirection.kForward, true);
        craneRotator.enableSoftLimit(SoftLimitDirection.kReverse, true);
        craneExtender.enableSoftLimit(SoftLimitDirection.kForward, true);
        craneExtender.enableSoftLimit(SoftLimitDirection.kReverse, true);
        if(articulator == 0){
            clawManipulator.enableSoftLimit(SoftLimitDirection.kForward, true);
            clawManipulator.enableSoftLimit(SoftLimitDirection.kReverse, true);
        } else {
            clawManipulator.enableSoftLimit(SoftLimitDirection.kForward, false);
            clawManipulator.enableSoftLimit(SoftLimitDirection.kReverse, false);
        }

        craneRotator.setSoftLimit(SoftLimitDirection.kForward, 0);
        craneRotator.setSoftLimit(SoftLimitDirection.kReverse, 330);
        craneExtender.setSoftLimit(SoftLimitDirection.kForward, 0);
        craneExtender.setSoftLimit(SoftLimitDirection.kReverse, 48);
        if(articulator == 0){
            clawManipulator.setSoftLimit(SoftLimitDirection.kForward, 90);
            clawManipulator.setSoftLimit(SoftLimitDirection.kReverse, 0);
        }

        craneRotator.setSmartCurrentLimit(Constants.kRotateCurrentLimit);
        craneExtender.setSmartCurrentLimit(Constants.kExtenderCurrentLimit);
        clawManipulator.setSmartCurrentLimit(Constants.kClawCurrentLimit);

        m_rotateEncoder = craneRotator.getEncoder();
        m_extendEncoder = craneExtender.getEncoder();
        m_clawEncoder = clawManipulator.getEncoder();
        if(articulator != 0){
            m_articulatorEncoder = clawArticulator.getEncoder();
        }
        

        pidRot = craneRotator.getPIDController();
        pidClaw = clawManipulator.getPIDController();
        if(articulator != 0){
            pidArticulator = clawArticulator.getPIDController();
        }

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
        if(articulator != 0){
            pidClaw.setP(Constants.kArticulatorKP);
            pidClaw.setI(0);
            pidClaw.setD(0);
            pidClaw.setIZone(0);
            pidClaw.setFF(0);
            pidClaw.setOutputRange(0, Constants.kArticulatorLimit);
        }

        m_rotateEncoder.setPositionConversionFactor(Constants.kRotateEncoderDistancePerPulse);
        m_extendEncoder.setPositionConversionFactor(Constants.kExtendEncoderDistancePerPulse);
        m_clawEncoder.setPositionConversionFactor(Constants.kClawEncoderDistancePerPulse);
        if(articulator != 0){
            m_articulatorEncoder.setPositionConversionFactor(Constants.kArticulatorEncoderDistancePerPulse);
        }

        craneRotator.burnFlash();
        craneExtender.burnFlash();
        clawManipulator.burnFlash();
        clawArticulator.burnFlash();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new CraneTeleopCommand());
    }

    public void updateCrane(){
        SmartDashboard.putNumber("Rotator", m_rotateEncoder.getPosition());
        SmartDashboard.putNumber("Extender", m_extendEncoder.getPosition());
        SmartDashboard.putNumber("Claw", m_clawEncoder.getPosition());
        SmartDashboard.putNumber("Articulator", m_articulatorEncoder.getPosition());
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

    public void setArticulatorPosition(double setpoint){
        pidArticulator.setReference(setpoint, CANSparkMax.ControlType.kPosition);
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

    public double getArticulator(){
        return m_articulatorEncoder.getPosition();
    }

}
