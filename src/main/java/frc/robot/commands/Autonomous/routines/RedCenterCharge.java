package frc.robot.commands.autonomous.routines;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.autoCommands.AutoTrajectoryReader;
import frc.robot.commands.autonomous.sequentialCommands.AutoBalance;
import frc.robot.commands.autonomous.sequentialCommands.AutoMecanumCommand;
import frc.robot.commands.autonomous.sequentialCommands.AutoScore;

public class RedCenterCharge extends SequentialCommandGroup {
  // required PathWeaver file paths
  String file_path_a = "paths/RedCenter/RCA.wpilib.json";
  
  // trajectories
  private Trajectory traj_path_a = AutoTrajectoryReader.generateTrajectoryFromFile(file_path_a);

  //Commands
  private Command movementA = AutoMecanumCommand.drivetrainMotion(traj_path_a);

  public RedCenterCharge(){
    
    addCommands(
        new AutoScore(0),
        movementA,
        new AutoBalance(0)
      );
  }
} 