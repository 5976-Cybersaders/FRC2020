/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class AutoDriveCommand extends CommandBase {

   private DriveTrainSubsystem driveTrainSubsystem;
   private double leftSpeed;
   private double rightSpeed; 
   private long driveTimeMS;
   private long startTime;
  /**
   * Creates a new AutoDriveCommand.
   */
  public AutoDriveCommand(DriveTrainSubsystem driveTrainSubsystem, double leftSpeed, double rightSpeed, long driveTimeMS) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.driveTimeMS = driveTimeMS;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrainSubsystem.initialize();
    startTime = System.currentTimeMillis();
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrainSubsystem.drive(leftSpeed, rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrainSubsystem.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return driveTimeMS > 0 && (System.currentTimeMillis() - startTime) >= driveTimeMS;
  }
}
