package frc.robot.commands.autonomous.autonomous_actions;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.subsystem_commands.AutoDropWheelPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoPitchCorrect;
import frc.robot.commands.autonomous.subsystem_commands.AutoSnap;

public class AutoBalance extends SequentialCommandGroup {

  public AutoBalance(int angle){
    
    addCommands(
      new AutoSnap(angle),
      new AutoDropWheelPosition(Constants.kDropWheelDistance),
      new AutoPitchCorrect()
    );
  }
} 