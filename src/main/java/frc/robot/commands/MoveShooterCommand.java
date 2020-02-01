/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPositionSubsystem;

public class MoveShooterCommand extends CommandBase {
  private ShooterPositionSubsystem shooterPositionSubsystem;
  private boolean isOnTop;

  /**
   * Creates a new MoveShooterCommand.
   */
  public MoveShooterCommand(ShooterPositionSubsystem shooterPositionSubsystem) {
    this.shooterPositionSubsystem = shooterPositionSubsystem;
    addRequirements(shooterPositionSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isOnTop = shooterPositionSubsystem.getForwardLimitSwitch().get();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isOnTop) {
      shooterPositionSubsystem.getShooterPositioner().set(.175);
    } else {
      shooterPositionSubsystem.getShooterPositioner().set(-.175);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isOnTop = !isOnTop;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isOnTop) 
      return shooterPositionSubsystem.getReverseLimitSwitch().get();
    else 
      return shooterPositionSubsystem.getForwardLimitSwitch().get();
  }
}
