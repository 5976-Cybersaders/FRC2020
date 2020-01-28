/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

  private WPI_TalonSRX upperShooterMotor, lowerShooterMotor;
  private Servo shooterGateServo;
  private Talon shooterFeedMotor;
  /**
   * Creates a new ShooterSubsystem
   */
  public ShooterSubsystem() {
    
    shooterFeedMotor=new Talon(Constants.SHOOTER_FEED_TALON_ID);

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

  public Servo getShooterGateServo(){
    return shooterGateServo;
  }

  public Talon getShooterFeedMotor(){
    return shooterFeedMotor;
  }
}
