/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  private Talon climberArmMotor;
  private Talon climberLiftMotor;
  /**
   * Creates a new ClimberSubsystem.
   */
  public ClimberSubsystem() {
    climberArmMotor = new Talon(Constants.CLIMBER_ARM_TALON_ID);
    climberLiftMotor = new Talon(Constants.CLIMBER_LIFT_TALON_ID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Talon getClimberArmMotor(){
    return climberArmMotor;
  }

  public Talon getClimberLiftMotor(){
    return climberLiftMotor;
  }
}
