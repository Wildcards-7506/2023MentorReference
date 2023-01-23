package frc.robot.commands.autonomous.sequentialCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.autoCommands.AutoScoringAlign;
import frc.robot.commands.autonomous.autoCommands.AutoClawPosition;
import frc.robot.commands.autonomous.autoCommands.AutoExtenderPosition;
import frc.robot.commands.autonomous.autoCommands.AutoRotatorPosition;

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