/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MotorBasedSubsystem;
import edu.wpi.first.wpilibj2.command.;


public class ActivateMotorCommand extends CommandBase {

  private MotorBasedSubsystem subsystem;
  private double speed;
  private long runTimeMS;
  private long t0;
  /**
   * Creates a new ActivateMotorCommand.
   */
  public ActivateMotorCommand(MotorBasedSubsystem subsystem, double speed) {
    this(subsystem, speed, 0);
  }

  public ActivateMotorCommand(MotorBasedSubsystem subsystem, double speed, long runTimeMS) {
    this.subsystem = subsystem;
    this.speed = speed;
    this.runTimeMS = runTimeMS;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    t0 = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.getMotor().set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.getMotor().set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (runTimeMS > 0 && System.currentTimeMillis() - t0 > runTimeMS);
  }
}
