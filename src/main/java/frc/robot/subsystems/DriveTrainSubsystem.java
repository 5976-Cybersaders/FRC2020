/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.List;
import java.util.Arrays;

//start initializing right HERE
public class DriveTrainSubsystem extends SubsystemBase {

  private boolean isInitilized = false;
  private WPI_TalonSRX rightMaster, leftMaster;
  private BaseMotorController rightSlave, leftSlave;
  //private WPI_VictorSPX rightSlave, leftSlave;
  private SpeedControllerGroup leftSide, rightSide;//set control group
  private static double expoFactor = 0.2; 

  //make list of talons and motor
  private List<BaseMotorController> rightControllers;
  private List<BaseMotorController> leftControllers;

  /**
   * Creates a new DriveTrainSubsystem.
   */
  public DriveTrainSubsystem() {
    rightMaster = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
    leftMaster = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
    rightSlave  = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
    leftSlave   = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
    //grouping master and slave
    rightSide = new SpeedControllerGroup(rightMaster, (SpeedController)rightSlave);
    leftSide = new SpeedControllerGroup(leftMaster, (SpeedController)leftSlave);

    //creating Arrays/individual list
    rightControllers = Arrays.asList(rightMaster, rightSlave);
    leftControllers = Arrays.asList(leftMaster, leftSlave);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double leftSpeed, double rightSpeed) {
    leftSide.set(adjustSpeed(leftSpeed));
    rightSide.set(adjustSpeed(rightSpeed));
  }
  
  public void visionDrive(double leftSpeed, double rightSpeed) {
    leftSide.set(leftSpeed);
    rightSide.set(rightSpeed);
  }

  private double adjustSpeed(double d) {
    if (Math.abs(d) < 0.03) return 0;
    return Math.signum(d) * Math.pow(Math.abs(d), Math.pow(4, expoFactor));
  }
  
  public WPI_TalonSRX getLeftMaster() { return this.leftMaster; }
  public BaseMotorController getLeftSlave() { return this.leftSlave; }
  public WPI_TalonSRX getRightMaster() { return this.rightMaster; }
  public BaseMotorController  getRightSlave() { return this.rightSlave; }
  
  public SpeedControllerGroup getLeftSide() { return this.leftSide; }
  public SpeedControllerGroup getRightSide() { return this.rightSide; }

  public void initialize() {
    if (! isInitilized){
      System.out.println("Drive train initializing!!!");
      invertMotor();
      initTalon(rightMaster);
      initTalon(leftMaster);
      leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
      isInitilized = true;
    }
  }

  private void initTalon(BaseMotorController talon) {
    talon.selectProfileSlot(1, 0);
    talon.configPeakOutputForward(1, 0);
    talon.configPeakOutputReverse(-1, 0);
    talon.configNominalOutputForward(0, 0);
    talon.configNominalOutputReverse(0, 0);
  }

  private void invertMotor() {

    List<BaseMotorController> talonsToInvert = rightControllers, talonsToNotInvert = leftControllers;

    talonsToInvert.forEach(talon -> {
      talon.setSensorPhase(true);
      talon.setInverted(true);
    });
    talonsToNotInvert.forEach(talon -> {
      talon.setSensorPhase(true);
      talon.setInverted(false);
    });
  }

}
