/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.XBoxButton.RawButton;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.MasterSlaveTestCommand;
import frc.robot.commands.ShootCommandGroup;
import frc.robot.commands.SnapToTargetCommand;
import frc.robot.commands.TeleOpTankDrive;
import frc.robot.commands.autonomous.AutoShootHighThenCrossLineCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MasterSlaveTestSubsystem;
import frc.robot.subsystems.MotorBasedSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

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
  private final boolean driveTrainEnabled = true;
  private final boolean cameraEnabled = true;
  private final boolean conveyorEnabled = false;
  private final boolean intakeEnabled = false;
  private final boolean shooterEnabled = false;
  private final boolean masterSlaveTestEnabled = false;

  private final DriveTrainSubsystem driveTrainSubsystem;
  private final MotorBasedSubsystem conveyorSubsystem;
  private final MotorBasedSubsystem intakeSubsystem;
  private final ShooterSubsystem shooterSubsystem;
  private final MasterSlaveTestSubsystem testSubsystem;
  private final CameraSubsystem cameraSubsystem;

  private final AutoShootHighThenCrossLineCommand autoShootHighThenCrossLineCommand ;

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
    if (intakeEnabled) {
      intakeSubsystem= new MotorBasedSubsystem(Constants.INTAKE_TALON_ID);
    } else {
      intakeSubsystem = null;
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
    if (conveyorEnabled) {
      conveyorSubsystem = new MotorBasedSubsystem(Constants.CONVEYOR_TALON_ID);
    } else {
      conveyorSubsystem = null;
    }
    if (driveTrainEnabled && cameraEnabled && shooterEnabled && conveyorEnabled) {
      autoShootHighThenCrossLineCommand  = new AutoShootHighThenCrossLineCommand( 
         shooterSubsystem, 
         driveTrainSubsystem, 
         cameraSubsystem, 
         conveyorSubsystem, 
         5000, //conveyorTime ms
         SmartDashboardMap.UPPER_SHOOTER_TARGET_SPEED_RPM.getDouble(), //upperShooterRpm
         SmartDashboardMap.LOWER_SHOOTER_TARGET_SPEED_RPM.getDouble(), //lowerShooterRpm
         0.5, //leftDriveSpeed
         0.5, //rightDriveSpeed 
         5000); //driveTimeMs
    } else autoShootHighThenCrossLineCommand = null;
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
      XBoxButton button = new XBoxButton(primaryController, RawButton.Y);
      Command c = new MasterSlaveTestCommand(testSubsystem);
      button.whileHeld(c);
    }
    if (driveTrainEnabled && cameraEnabled ){
      XBoxButton button = new XBoxButton(primaryController, RawButton.X);
      Command c = new SnapToTargetCommand(driveTrainSubsystem, cameraSubsystem,0);
      button.whileHeld(c);
    }
    if (conveyorEnabled && intakeEnabled ) {
      XBoxTrigger button = new XBoxTrigger(primaryController, Hand.kRight);
      Command c = new IntakeCommandGroup(intakeSubsystem, conveyorSubsystem);
      button.whileActiveOnce(c);
    }
    if (shooterEnabled && driveTrainEnabled && cameraEnabled && conveyorEnabled) {
      XBoxTrigger button = new XBoxTrigger(primaryController, Hand.kLeft);
      Command c = new ShootCommandGroup(shooterSubsystem, driveTrainSubsystem, cameraSubsystem, conveyorSubsystem, 0l,
        SmartDashboardMap.UPPER_SHOOTER_TARGET_SPEED_RPM.getDouble(), SmartDashboardMap.LOWER_SHOOTER_TARGET_SPEED_RPM.getDouble());
      button.whileActiveOnce(c);
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
