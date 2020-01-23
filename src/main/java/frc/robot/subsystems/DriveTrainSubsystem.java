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

public class DriveTrainSubsystem extends SubsystemBase {

  private WPI_TalonSRX rightMaster, rightSlave;
  private WPI_TalonSRX leftMaster, leftSlave;

  /**
   * Creates a new DriveTrainSubsystem.
   */
  public DriveTrainSubsystem() {
    rightMaster = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
    rightSlave  = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
    leftMaster  = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
    leftSlave   = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
