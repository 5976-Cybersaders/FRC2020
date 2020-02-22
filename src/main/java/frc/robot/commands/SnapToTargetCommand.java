/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardMap;
//import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

public class SnapToTargetCommand extends CommandBase {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private int deadBandCounter;
  private double deadband;
  private double kp;
  private double min_cmd;
  private int txCounter;
  //private boolean shouldWeGoLeft;
  //private boolean isSnapMode;
  private int pipeline;

  private VisionCommandData commandData = new VisionCommandData();

  public SnapToTargetCommand(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, int pipeline) {
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.pipeline = pipeline;
    limelight = cameraSubsystem.getLimelight();
    addRequirements(driveTrainSubsystem, cameraSubsystem);
    //setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    limelight.setPipeline(pipeline);
    initCameraSettings(CamMode.kvision, LedMode.kforceOn); //TODO: determine stream type once second camera is plugged in
    deadBandCounter = 0;
    deadband = SmartDashboardMap.VISION_DEADBAND.getValue();
    kp = SmartDashboardMap.VISION_KP.getValue(); 
    min_cmd = SmartDashboardMap.VISION_MIN_CMD.getValue();
    txCounter = 5; // so that it is reported on the first iteration
    System.out.println("\n\n\n**************************************************");
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    commandData.refresh();
    if(!commandData.getTv()) {
      System.out.println("Waiting for target!!! " + commandData.getTx());
      driveTrainSubsystem.drive(0, 0);
      return;
    }
   
    handleSnapMode();

    driveTrainSubsystem.visionDrive(commandData.getLeftSpeed(), commandData.getRightSpeed());

    if(txCounter >= 5) {
      commandData.report();
      txCounter = 0;
    } else {
      txCounter++;
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interupted) {
    //maybe need leds on?
    initCameraSettings(CamMode.kdriver, LedMode.kforceOn);
  }

  private void initCameraSettings(CamMode camMode, LedMode ledMode){
    limelight.setCamMode(camMode);
    limelight.setLEDMode(ledMode);
  }

  private void handleSnapMode(){
    double maxSpeed = SmartDashboardMap.VISION_SNAP_MAX_SPEED.getDouble();
    double headingError = -commandData.getTx();
    if (commandData.getTx() > deadband){
      commandData.setSteerAdjust(kp * headingError - min_cmd);
      deadBandCounter = 0;
    } else if (commandData.getTx() < -deadband){
      commandData.setSteerAdjust(kp * headingError + min_cmd);
      deadBandCounter = 0;
    } else {
      deadBandCounter++;
    }
    if(commandData.getSteerAdjust() < -maxSpeed) {
      commandData.setSteerAdjust(-maxSpeed);
    } else if(commandData.getSteerAdjust() > maxSpeed) {
      commandData.setSteerAdjust(maxSpeed);
    }

    commandData.setLeftSpeed(commandData.getSteerAdjust()); //tx < 0 ? speed : 0;
    commandData.setRightSpeed(-commandData.getSteerAdjust()); // tx > 0 ? speed: 0;

  }

  protected int getDeadBandCounter() { return this.deadBandCounter; }
  protected DriveTrainSubsystem getDriveTrainSubsystem() { return this.driveTrainSubsystem; }



  private class VisionCommandData {
    double tx;
    boolean tv;
    double ta;
    double leftSpeed;
    double rightSpeed;
    double steerAdjust;

    public void refresh() {
      tx = limelight.getdegRotationToTarget();
      tv = limelight.getIsTargetFound();
      ta = limelight.getTargetArea();
      leftSpeed = 0;
      rightSpeed = 0;
      steerAdjust = 0;
    }



    public void report(){
      SmartDashboardMap.VISION_TX.putNumber(commandData.getTx());
      SmartDashboardMap.VISION_LEFT_SPEED.putNumber(leftSpeed);
      SmartDashboardMap.VISION_RIGHT_SPEED.putNumber(rightSpeed);
      System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Deadband counter: " + deadBandCounter +  " | Ta: " + commandData.getTa());
    }

    public double getTx() {
      return tx;
    }
 
    public double getLeftSpeed() {
      return leftSpeed;
    }

    public void setLeftSpeed(double leftSpeed) {
      this.leftSpeed = leftSpeed;
    }

    public double getRightSpeed() {
      return rightSpeed;
    }

    public void setRightSpeed(double rightSpeed) {
      this.rightSpeed = rightSpeed;
    }

    public double getSteerAdjust() {
      return steerAdjust;
    }

    public void setSteerAdjust(double steerAdjust) {
      this.steerAdjust = steerAdjust;
    }

    public boolean getTv() {
      return this.tv;
    }

    public double getTa() {
      return this.ta;
    }
  }

}
