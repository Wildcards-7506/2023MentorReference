package frc.robot.commands.autonomous.routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.autonomous.AutoCommands;
import frc.robot.commands.autonomous.AutoTrajectoryReader;

public class AutoRoutineRedWallCharge extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/RedWallCharge/RWA.wpilib.json";
  String file_path_b = "paths/RedWallCharge/RWB.wpilib.json";
  String file_path_c = "paths/RedWallCharge/RWCC.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);
  private Trajectory traj_path_b = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_b);
  private Trajectory traj_path_c = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_c);

  //Commands
  private Command movementA = AutoCommands.drivetrainMotion(traj_path_a);
  private Command movementB = AutoCommands.drivetrainMotion(traj_path_b);
  private Command movementC = AutoCommands.drivetrainMotion(traj_path_c);

  public AutoRoutineRedWallCharge(){
    
    addCommands(
        new InstantCommand(AutoCommands::postAlign, Robot.drivetrain),
        new InstantCommand(AutoCommands::autoScore, Robot.crane),
        movementA,
        new InstantCommand(AutoCommands::autoCollect, Robot.crane),
        movementB,
        new InstantCommand(AutoCommands::autoScore, Robot.crane),
        movementC,
        new InstantCommand(AutoCommands::chargeAlign, Robot.drivetrain)
      );
  }
} 