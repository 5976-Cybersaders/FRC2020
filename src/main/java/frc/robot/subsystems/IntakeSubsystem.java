/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  private Talon intakeMotor, conveyorMotor;
  
  public IntakeSubsystem() {
    intakeMotor = new Talon(Constants.INTAKE_TALON_ID);
    conveyorMotor = new Talon(Constants.INTAKE_CONVEYOR_TALON_ID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Talon getIntakeMotor(){
    return intakeMotor;
  }

  public Talon getConveyorMotor(){
    return conveyorMotor;
  }

}
