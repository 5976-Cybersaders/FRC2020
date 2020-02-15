/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterPositionSubsystem extends SubsystemBase {
  
  /**
   * Creates a new ShooterPosition.
   */
  private Talon shooterPositioner;
  private DigitalInput forwardLimitSwitch;
  private DigitalInput reverseLimitSwitch;
  //private Spark sparkPositioner;

  public ShooterPositionSubsystem() {
    shooterPositioner = new Talon(Constants.SHOOTER_POSITIONER_TALON_ID);
    forwardLimitSwitch = new DigitalInput(1);
    reverseLimitSwitch = new DigitalInput(2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Talon getShooterPositioner(){
    return shooterPositioner;
  }
  
  public DigitalInput getReverseLimitSwitch(){
    return reverseLimitSwitch;
  }

  public DigitalInput getForwardLimitSwitch(){
    return forwardLimitSwitch;
  }
}
