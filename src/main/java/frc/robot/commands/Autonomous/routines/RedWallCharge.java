package frc.robot.commands.autonomous.routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.autoCommands.AutoTrajectoryReader;
import frc.robot.commands.autonomous.sequentialCommands.AutoBalance;
import frc.robot.commands.autonomous.sequentialCommands.AutoCollect;
import frc.robot.commands.autonomous.sequentialCommands.AutoMecanumCommand;
import frc.robot.commands.autonomous.sequentialCommands.AutoScore;

public class RedWallCharge extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/RedWallCharge/RWA.wpilib.json";
  String file_path_b = "paths/RedWallCharge/RWB.wpilib.json";
  String file_path_c = "paths/RedWallCharge/RWCC.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);
  private Trajectory traj_path_b = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_b);
  private Trajectory traj_path_c = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_c);

  //Commands
  private Command movementA = AutoMecanumCommand.drivetrainMotion(traj_path_a);
  private Command movementB = AutoMecanumCommand.drivetrainMotion(traj_path_b);
  private Command movementC = AutoMecanumCommand.drivetrainMotion(traj_path_c);

  public RedWallCharge(){
    
    addCommands(
        new AutoScore(0),
        movementA,
        new AutoCollect(1),
        movementB,
        new AutoScore(1),
        movementC,
        new AutoBalance(180)
      );
  }
} 