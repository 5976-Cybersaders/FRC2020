/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class MaintainShooterWheelSpeed extends CommandBase {
  /**
   * Creates a new MaintainShooterWheelSpeed.
   */
  private ShooterSubsystem shooterSubsystem;
  private double desiredVelocity;

  public MaintainShooterWheelSpeed(ShooterSubsystem shooterSubsystem, double desiredVelocity) {
    this.shooterSubsystem = shooterSubsystem;
    this.desiredVelocity = desiredVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    /* Factory Default all hardware to prevent unexpected behaviour */
    getTalon().configFactoryDefault();

    /* Config sensor used for Primary PID [Velocity] */
    getTalon().configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    getTalon().setSensorPhase(true);

		/* Config the peak and nominal outputs */

		getTalon().configNominalOutputForward(0, Constants.kTimeoutMs);
		getTalon().configNominalOutputReverse(0, Constants.kTimeoutMs);
		getTalon().configPeakOutputForward(1, Constants.kTimeoutMs);
		getTalon().configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */

		getTalon().config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kF, Constants.kTimeoutMs);
		getTalon().config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kP, Constants.kTimeoutMs);
		getTalon().config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kI, Constants.kTimeoutMs);
		getTalon().config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kD, Constants.kTimeoutMs);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double motorOutput = getTalon().getMotorOutputPercent();
    double targetVelocityUnitsPer100ms = desiredVelocity * 4096 / 600;
    getTalon().set(ControlMode.Velocity, targetVelocityUnitsPer100ms);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    getTalon().set(ControlMode.Velocity, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private WPI_TalonSRX getTalon() {
    return null;
  }

  
}
