package frc.robot.commands.autonomous.sequentialCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.autoCommands.AutoClawPosition;
import frc.robot.commands.autonomous.autoCommands.AutoExtenderPosition;
import frc.robot.commands.autonomous.autoCommands.AutoRotatorPosition;

public class AutoCollect extends SequentialCommandGroup {

  public AutoCollect(int cargoType){
    
    addCommands(
      new ParallelCommandGroup(
        new AutoRotatorPosition(Constants.kRotatorGround),
        new AutoExtenderPosition(Constants.kExtenderGround),
        new AutoClawPosition(Constants.kClawOpen)),
      new AutoClawPosition(Constants.kClawClosed / (1 + cargoType)),
      new AutoExtenderPosition(Constants.kRotatorClosed)
    );
  }
} 