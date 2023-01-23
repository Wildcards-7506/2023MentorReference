package frc.robot.commands.autonomous.sequentialCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.autoCommands.AutoDropWheelPosition;
import frc.robot.commands.autonomous.autoCommands.AutoPitchCorrect;
import frc.robot.commands.autonomous.autoCommands.AutoSnap;

public class AutoBalance extends SequentialCommandGroup {

  public AutoBalance(int angle){
    
    addCommands(
      new AutoSnap(angle),
      new AutoDropWheelPosition(Constants.kDropWheelDistance),
      new AutoPitchCorrect()
    );
  }
} 