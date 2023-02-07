package frc.robot.commands.autonomous.autonomous_actions;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.subsystem_commands.AutoClawPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoExtenderPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoRotatorPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoScoringAlign;
import frc.robot.subsystems.Crane.EndEffectorState;

public class AutoScore extends SequentialCommandGroup {

  public AutoScore(int pipeline){
    
    addCommands(
        new ParallelCommandGroup(
          new AutoScoringAlign(pipeline),
          new AutoRotatorPosition(Constants.kRotatorHi),
          new AutoExtenderPosition(Constants.kExtenderHi),
          new AutoClawPosition(EndEffectorState.PLACEMENT, false)),
        new AutoClawPosition(EndEffectorState.PLACEMENT, true),
        //Return to Close
        new ParallelCommandGroup(
          new AutoClawPosition(EndEffectorState.IDLE, false),
          new AutoRotatorPosition(Constants.kRotatorClosed),
          new AutoExtenderPosition(Constants.kRotatorClosed))
      );
  }
} 