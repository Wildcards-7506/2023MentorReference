package frc.robot.commands.autonomous.autonomous_routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.autonomous_actions.AutoCollect;
import frc.robot.commands.autonomous.autonomous_actions.AutoDrive;
import frc.robot.commands.autonomous.autonomous_actions.AutoScore;
import frc.robot.commands.autonomous.subsystem_commands.AutoTrajectoryReader;

public class RedTriple extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/RedWallOut/RLA.wpilib.json";
  String file_path_b = "paths/RedWallOut/RLB.wpilib.json";
  String file_path_c = "paths/RedWallOut/R3C.wpilib.json";
  String file_path_d = "paths/RedWallOut/R3D.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);
  private Trajectory traj_path_b = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_b);
  private Trajectory traj_path_c = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_c);
  private Trajectory traj_path_d = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_d);

  //Commands
  private Command movementA = AutoDrive.drivetrainMotion(traj_path_a);
  private Command movementB = AutoDrive.drivetrainMotion(traj_path_b);
  private Command movementC = AutoDrive.drivetrainMotion(traj_path_c);
  private Command movementD = AutoDrive.drivetrainMotion(traj_path_d);

  public RedTriple(){
    
    addCommands(
        new AutoScore(Constants.cone),
        movementA,
        new AutoCollect(Constants.cube),
        movementB,
        new AutoScore(Constants.cube),
        movementC,
        new AutoCollect(Constants.cone),
        movementD,
        new AutoScore(Constants.cone)
      );
  }
} 