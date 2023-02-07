package frc.robot.commands.autonomous.autonomous_actions;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.subsystem_commands.AutoClawPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoExtenderPosition;
import frc.robot.commands.autonomous.subsystem_commands.AutoRotatorPosition;
import frc.robot.subsystems.Crane.EndEffectorState;

public class AutoCollect extends SequentialCommandGroup {

  public AutoCollect(int cargoType){
    
    addCommands(
      new ParallelCommandGroup(
        new AutoRotatorPosition(Constants.kRotatorGround),
        new AutoExtenderPosition(Constants.kExtenderGround),
        new AutoClawPosition(EndEffectorState.INTAKE, false)),
      new AutoClawPosition(EndEffectorState.IDLE, false),
      new AutoExtenderPosition(Constants.kRotatorClosed)
    );
  }
} 