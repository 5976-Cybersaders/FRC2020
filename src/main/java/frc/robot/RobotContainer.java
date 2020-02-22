/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.XBoxButton.RawButton;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.MasterSlaveTestCommand;
import frc.robot.commands.SnapToTargetCommand;
import frc.robot.commands.TeleOpTankDrive;
import frc.robot.commands.autonomous.AutoShootHighThenCrossLineCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.MasterSlaveTestSubsystem;
import frc.robot.subsystems.ShooterPositionSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final XboxController primaryController = new XboxController(0);
  private final XboxController secondaryController = new XboxController(1);
  private final boolean driveTrainEnabled = false;
  private final boolean climberEnabled = false;
  private final boolean intakeEnabled = false;
  private final boolean shooterPositionEnabled = false;
  private final boolean shooterEnabled = false;
  private final boolean cameraEnabled = true;
  private final boolean masterSlaveTestEnabled = true;

  private final ClimberSubsystem climberSubsystem;
  private final DriveTrainSubsystem driveTrainSubsystem;
  private final IntakeSubsystem intakeSubsystem;
  private final ShooterPositionSubsystem shooterPositionSubsystem;
  private final ShooterSubsystem shooterSubsystem;
  private final MasterSlaveTestSubsystem testSubsystem;
  private final CameraSubsystem cameraSubsystem;

  private final AutoShootHighThenCrossLineCommand autoShootHighThenCrossLineCommand = new AutoShootHighThenCrossLineCommand();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    if (driveTrainEnabled) {
      driveTrainSubsystem = new DriveTrainSubsystem();
      driveTrainSubsystem.setDefaultCommand(new TeleOpTankDrive(primaryController, driveTrainSubsystem));
    } else {
      driveTrainSubsystem = null;
    }
    if (climberEnabled) {
      climberSubsystem= new ClimberSubsystem();
    } else {
      climberSubsystem = null;
    }
    if (intakeEnabled) {
      intakeSubsystem= new IntakeSubsystem();
    } else {
      intakeSubsystem = null;
    }
    if (shooterPositionEnabled) {
      shooterPositionSubsystem= new ShooterPositionSubsystem();
    } else {
      shooterPositionSubsystem = null;
    }
    if (shooterEnabled) {
      shooterSubsystem= new ShooterSubsystem();
    } else {
      shooterSubsystem = null;
    }
    if (cameraEnabled) {
      cameraSubsystem= new CameraSubsystem();
    } else {
      cameraSubsystem = null;
    }
    if (masterSlaveTestEnabled) {
      testSubsystem = new MasterSlaveTestSubsystem();
    } else {
      testSubsystem = null;
    }
    
    configureButtonBindings();
  }


  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    if (masterSlaveTestEnabled) {
      XBoxButton testButton = new XBoxButton(primaryController, RawButton.Y);
      Command c = new MasterSlaveTestCommand(testSubsystem);
      testButton.whileHeld(c);
      System.out.println("bound successfully");
    }
    if (driveTrainEnabled && cameraEnabled ){
      XBoxButton testButton = new XBoxButton(primaryController, RawButton.X);
      Command c = new SnapToTargetCommand(driveTrainSubsystem, cameraSubsystem,0);
      testButton.whileHeld(c);
    }
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoShootHighThenCrossLineCommand;
  }
}
