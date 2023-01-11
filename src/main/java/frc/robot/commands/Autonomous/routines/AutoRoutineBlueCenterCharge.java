package frc.robot.commands.autonomous.routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.autonomous.AutoCommands;
import frc.robot.commands.autonomous.AutoTrajectoryReader;

public class AutoRoutineBlueCenterCharge extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/BlueCenter/BCA.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);

  //Commands
  private Command movementA = AutoCommands.drivetrainMotion(traj_path_a);

  public AutoRoutineBlueCenterCharge(){
    
    addCommands(
        new InstantCommand(AutoCommands::postAlign, Robot.drivetrain),
        new InstantCommand(AutoCommands::autoScore, Robot.crane),
        movementA,
        new InstantCommand(AutoCommands::chargeAlign, Robot.drivetrain)
      );
  }
} 