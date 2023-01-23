package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.LimelightTOCom;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

    public double targetArea = 0.1;

    private NetworkTable table;
    private NetworkTableEntry tx;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry ty;
    

    //The following five methods retrieve and make data available from the Limelight Network Table
    public void updateData() {
        // update table, then update from updated table
        table = NetworkTableInstance.getDefault().getTable("limelight");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
    }

    public double getTX() {
        updateData();
        return tx.getDouble(0.0);
    }

    public double getTY() {
        updateData();
        return ty.getDouble(0.0);
    }

    public double getTA() {
        updateData();
        return ta.getDouble(0.0);
    }

    public double getTV() {
        updateData();
        return tv.getDouble(0.0);
    }

    //Switches modes - manual
    public void switchCameraMode(){
        table.getEntry("pipeline").setNumber(table.getEntry("pipeline").getDouble(0.0) == 0 ? 1 : 0);
        targetArea = (targetArea == 0.1 ? 0.5 : 0.1);
    }

    //Force Cone Mode
    public void conePipeline(){
        table.getEntry("pipeline").setNumber(0);
        targetArea = 0.1;
    }

    //Force Cube Mode
    public void cubePipeline(){
        table.getEntry("pipeline").setNumber(1);
        targetArea = 0.5;
    }


    //Every scheduler cycle, we pass our XBox controls so we can control the limelight.
    @Override
    public void periodic() {
        setDefaultCommand(new LimelightTOCom());
        // This method will be called once per scheduler run
    }
}