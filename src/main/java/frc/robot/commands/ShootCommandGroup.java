/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MotorBasedSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new ShootCommandGroup.
   */
  public ShootCommandGroup(
    ShooterSubsystem shooterSubsystem, 
    DriveTrainSubsystem driveTrainSubsystem, 
    CameraSubsystem cameraSubsystem, 
    MotorBasedSubsystem conveyorSubsystem, 
    long moveConveyorForwardTimeMS,
    double upperValue, double lowerValue
  ) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new SnapToTargetCommand(driveTrainSubsystem, cameraSubsystem, 0),
      new ParallelRaceGroup(
        new ActivateConveyorForShootingCommandGroup(conveyorSubsystem, moveConveyorForwardTimeMS),
        new ActivateShooterMotorsCommand(shooterSubsystem, upperValue, lowerValue)
      )
    );
  }
}
