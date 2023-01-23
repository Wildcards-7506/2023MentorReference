package frc.robot.commands.autonomous.routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.autoCommands.AutoTrajectoryReader;
import frc.robot.commands.autonomous.sequentialCommands.AutoCollect;
import frc.robot.commands.autonomous.sequentialCommands.AutoMecanumCommand;
import frc.robot.commands.autonomous.sequentialCommands.AutoScore;

public class BlueTriple extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/RedWallOut/BLA.wpilib.json";
  String file_path_b = "paths/RedWallOut/BLB.wpilib.json";
  String file_path_c = "paths/RedWallOut/B3C.wpilib.json";
  String file_path_d = "paths/RedWallOut/B3D.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);
  private Trajectory traj_path_b = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_b);
  private Trajectory traj_path_c = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_c);
  private Trajectory traj_path_d = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_d);

  //Commands
  private Command movementA = AutoMecanumCommand.drivetrainMotion(traj_path_a);
  private Command movementB = AutoMecanumCommand.drivetrainMotion(traj_path_b);
  private Command movementC = AutoMecanumCommand.drivetrainMotion(traj_path_c);
  private Command movementD = AutoMecanumCommand.drivetrainMotion(traj_path_d);

  public BlueTriple(){
    
    addCommands(
        new AutoScore(0),
        movementA,
        new AutoCollect(1),
        movementB,
        new AutoScore(1),
        movementC,
        new AutoCollect(0),
        movementD,
        new AutoScore(0)
      );
  }
} 