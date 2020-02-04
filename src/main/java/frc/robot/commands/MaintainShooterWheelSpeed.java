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
  private double desiredUpperVelocity;
  private double desiredLowerVelocity;
  private  double targetLowerVelocityUnitsPer100ms;
  private double targetUpperVelocityUnitsPer100ms;
  

  public MaintainShooterWheelSpeed(ShooterSubsystem shooterSubsystem, double desiredUpperVelocity, double desiredLowerVelocity) {
    this.shooterSubsystem = shooterSubsystem;
    this.desiredUpperVelocity = desiredUpperVelocity;
    this.desiredLowerVelocity = desiredLowerVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetUpperVelocityUnitsPer100ms = desiredUpperVelocity * 4096 / 600;
    targetLowerVelocityUnitsPer100ms = desiredLowerVelocity * 4096 / 600;
    initTalon(shooterSubsystem.getLowerShooterMotor());
    initTalon(shooterSubsystem.getUpperShooterMotor());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //double motorOutput = getTalon().getMotorOutputPercent();
    shooterSubsystem.getUpperShooterMotor().set(ControlMode.Velocity, targetUpperVelocityUnitsPer100ms);
    shooterSubsystem.getLowerShooterMotor().set(ControlMode.Velocity, targetLowerVelocityUnitsPer100ms);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.getUpperShooterMotor().set(ControlMode.Velocity, 0);
    shooterSubsystem.getLowerShooterMotor().set(ControlMode.Velocity, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


  private void initTalon(WPI_TalonSRX talon){
    /* Factory Default all hardware to prevent unexpected behaviour */
    talon.configFactoryDefault();

    /* Config sensor used for Primary PID [Velocity] */
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    talon.setSensorPhase(true);

		/* Config the peak and nominal outputs */

		talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */

		talon.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kF, Constants.kTimeoutMs);
		talon.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kP, Constants.kTimeoutMs);
		talon.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kI, Constants.kTimeoutMs);
		talon.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kD, Constants.kTimeoutMs);
  }



  
}
