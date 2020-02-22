/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.StreamType;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.CamMode;

public class CameraSubsystem extends SubsystemBase {
  private Limelight limelight;
  private boolean isInited;
  
  /**
   * Creates a new CameraSubsystem.
   */
  public CameraSubsystem() {
    isInited = false;
    limelight = new Limelight();
    System.out.println("Camera subsystem created with limelight " + limelight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // in 2019 this was called from the robot when initing for either both autonomous or teleop mode.
  public void initLimelight(){
    if(!isInited){
      isInited = true;
      this.limelight.setCamMode(CamMode.kdriver);
      this.limelight.setLEDMode(LedMode.kforceOn);
      this.limelight.setStream(StreamType.kPiPChangeable);
    }
  }

  public Limelight getLimelight() { return limelight; }
}