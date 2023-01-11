package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CraneTOCom;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Crane extends SubsystemBase{
    private CANSparkMax craneRotator;
    private CANSparkMax craneExtender;
    private CANSparkMax clawManipulator;
    public RelativeEncoder m_rotateEncoder;
    public RelativeEncoder m_extendEncoder; 
    public RelativeEncoder m_clawEncoder;
    private PIDController pid = new PIDController(Constants.kRotatorKP, 0, 0);

    public Crane (int rotator, int extender, int claw){
        craneRotator = new CANSparkMax(rotator, MotorType.kBrushless);
        craneExtender = new CANSparkMax(extender, MotorType.kBrushless);
        clawManipulator = new CANSparkMax(claw, MotorType.kBrushless);

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
        updateArm();
    }

    public void updateArm(){
        SmartDashboard.putNumber("Rotator", m_rotateEncoder.getPosition());
        SmartDashboard.putNumber("Extender", m_extendEncoder.getPosition());
        SmartDashboard.putNumber("Claw", m_clawEncoder.getPosition());
    }

    public void setArmPosition(double setting){
        double setpoint = pid.calculate(getRotator(), setting);
        craneRotator.setVoltage(12*setpoint);
    }

    public void setExtendPosition(double setting){
        if (Math.abs(setting-getExtender()) > 0.5){
            double voltage = 12 * (setting-getExtender())/Math.abs(setting-getExtender());
            craneExtender.setVoltage(voltage);
        } 
    }

    public void setClaw(boolean open){
        if(open & getClaw() < Constants.kClawOpen){
            clawManipulator.setVoltage(12);
        } else if(!open & getClaw() > Constants.kClawClosed) {
            clawManipulator.setVoltage(-12);
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
