package frc.robot.commands.autonomous.autonomous_routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.autonomous_actions.AutoBalance;
import frc.robot.commands.autonomous.autonomous_actions.AutoDrive;
import frc.robot.commands.autonomous.autonomous_actions.AutoScore;
import frc.robot.commands.autonomous.subsystem_commands.AutoTrajectoryReader;

public class BlueCenterCharge extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/BlueCenter/BCA.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);

  //Commands
  private Command movementA = AutoDrive.drivetrainMotion(traj_path_a);

  public BlueCenterCharge(){
    
    addCommands(
        new AutoScore(Constants.cone),
        movementA,
        new AutoBalance(0)
      );
  }
} 