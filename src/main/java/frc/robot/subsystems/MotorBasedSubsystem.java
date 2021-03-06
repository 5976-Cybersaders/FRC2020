/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorBasedSubsystem extends SubsystemBase {
  private Talon motor;
  
  public MotorBasedSubsystem(int motorID) {
    motor = new Talon(motorID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Talon getMotor(){
    return motor;
  }

}
