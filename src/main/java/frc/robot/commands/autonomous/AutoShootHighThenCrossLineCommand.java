/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ShootCommandGroup;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MotorBasedSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoShootHighThenCrossLineCommand extends SequentialCommandGroup {
  /**
   * Creates a new AutoShootThenCrossLineCommand.
 * @param leftDriveSpeed 
   */
  public AutoShootHighThenCrossLineCommand( 
    ShooterSubsystem shooterSubsystem, 
    DriveTrainSubsystem driveTrainSubsystem, 
    CameraSubsystem cameraSubsystem, 
    MotorBasedSubsystem conveyorSubsystem, 
    long moveConveyorForwardTimeMS,
    double upperShooterRpm, double lowerShooterRpm,
    double leftDriveSpeed, double rightDriveSpeed, long driveTimeMs) {

    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new ShootCommandGroup(shooterSubsystem, driveTrainSubsystem, cameraSubsystem, conveyorSubsystem, moveConveyorForwardTimeMS, upperShooterRpm, lowerShooterRpm),
      new AutoDriveCommand(driveTrainSubsystem, leftDriveSpeed, rightDriveSpeed, driveTimeMs)
    );
  }
}
