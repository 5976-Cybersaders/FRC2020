/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MasterSlaveTestSubsystem extends SubsystemBase {
  /**
   * Creates a new MasterSlaveTestSubsystem.
   */ 
  private WPI_TalonSRX master;
  private BaseMotorController slave;
  private SpeedControllerGroup controllerGroup;
  private static double expoFactor = 0.2; 

  //make list of talons and motor
  private List<BaseMotorController> controllerList;

  public MasterSlaveTestSubsystem() {
    master = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
    slave = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
    slave.follow(master);
    //grouping master and slave
    controllerGroup = new SpeedControllerGroup(master, (SpeedController)slave);

    //creating Arrays/individual list
    controllerList = Arrays.asList(master, slave);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double speed) {
    controllerGroup.set(speed);
  }

  public WPI_TalonSRX getMaster() { return this.master; }
  public BaseMotorController getSlave() { return this.slave; }
  public SpeedControllerGroup getControllerGroup() { return this.controllerGroup; }

  public void invertMotor() {

    List<BaseMotorController> talonsToNotInvert = controllerList;

    talonsToNotInvert.forEach(talon -> {
      talon.setSensorPhase(true);
      talon.setInverted(false);
    });
  }
}
