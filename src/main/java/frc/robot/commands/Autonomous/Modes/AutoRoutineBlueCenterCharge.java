package frc.robot.commands.Autonomous.Modes;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Autonomous.AutoCommands;
import frc.robot.commands.Autonomous.AutoTrajectoryReader;

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