/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

  private WPI_TalonSRX upperShooterMotor, lowerShooterMotor;
  /**
   * Creates a new ShooterSubsystem
   */
  public ShooterSubsystem() {
    upperShooterMotor = new WPI_TalonSRX(Constants.UPPER_SHOOTER_TALON_ID);
    lowerShooterMotor = new WPI_TalonSRX(Constants.LOWER_SHOOTER_TALON_ID);
    initTalon(upperShooterMotor);
    initTalon(lowerShooterMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public WPI_TalonSRX getUpperShooterMotor(){
    return upperShooterMotor;
  }

  public WPI_TalonSRX getLowerShooterMotor(){
    return lowerShooterMotor;
  }
  private void initTalon(WPI_TalonSRX talon){
    talon.setSensorPhase(true);
    talon.setInverted(false);
  }
}
