package frc.robot.commands.autonomous.autonomous_actions;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.subsystem_commands.AutoClawPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoExtenderPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoRotatorPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoScoringAlign;

public class AutoScore extends SequentialCommandGroup {

  public AutoScore(int pipeline){
    
    addCommands(
        new ParallelCommandGroup(
          new AutoScoringAlign(pipeline),
          new AutoRotatorPosition(Constants.kRotatorHi),
          new AutoExtenderPosition(Constants.kExtenderHi)),
        new AutoClawPosition(Constants.kClawOpen),
        new AutoClawPosition(Constants.kClawClosed),
        //Return to Close
        new ParallelCommandGroup(
          new AutoRotatorPosition(Constants.kRotatorClosed),
          new AutoExtenderPosition(Constants.kRotatorClosed))
      );
  }
} 