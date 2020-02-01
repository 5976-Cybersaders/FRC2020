/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//frc.robot.commands or frc.robot.commands.drivetraincommands
package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;//import TalonSRX motor
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.XboxController;//wpilibj
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;//inherit DriveTrainSubsystem

public class TeleOpTankDrive extends CommandBase {
  private DriveTrainSubsystem driveTrain;
  private WPI_TalonSRX rightMaster;
  private WPI_TalonSRX leftMaster;
  private WPI_VictorSPX rightSlave;
  private WPI_VictorSPX leftSlave;
  private XboxController controller;
  private int count, interval;

  public TeleOpTankDrive(XboxController controller, DriveTrainSubsystem driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.controller = controller;
    this.driveTrain = driveTrain;
    leftMaster = driveTrain.getLeftMaster();
    rightMaster = driveTrain.getRightMaster();
    rightSlave = driveTrain.getRightSlave();
    leftSlave = driveTrain.getLeftSlave();
    addRequirements(driveTrain);

    count=interval=50;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Drive train STARTING!!!");
    driveTrain.invertMotor();
    initTalon(rightMaster);
    initTalon(leftMaster);
  }

  private void initTalon(BaseMotorController talon) {
    talon.selectProfileSlot(1, 0);
    talon.configPeakOutputForward(1, 0);
    talon.configPeakOutputReverse(-1, 0);
    talon.configNominalOutputForward(0, 0);
    talon.configNominalOutputReverse(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Negative below is intentional to reverse direction of joystick input.
    double left = -controller.getY(Hand.kLeft);
    double right = -controller.getY(Hand.kRight);
    this.driveTrain.drive(left, right);
    reportExecute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  
  private void reportExecute() {
    if (count++ >= interval) {
        //ReportHelper.reportTeleOp(leftMaster, "Left Master"); //TODO: create ReportHelper?
        //ReportHelper.reportTeleOp(rightMaster, "Right Master");
        System.out.println();
        count = 0;
    }
}
}
