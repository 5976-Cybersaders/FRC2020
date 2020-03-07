/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.MasterSlaveTestSubsystem;

public class MasterSlaveTestCommand extends CommandBase {
  /**
   * Creates a new MasterSlaveTestCommand.
   */
  private MasterSlaveTestSubsystem testSubsystem;
  private double desiredVelocity;
  private double targetVelocityUnitsPer100ms;

  private double resetCounter = 0, pastPosition = 0, totalPosition;
  private long pastTimeMs = 0;
  
  public MasterSlaveTestCommand(MasterSlaveTestSubsystem testSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.testSubsystem = testSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    desiredVelocity = SmartDashboardMap.TEST_SHOOTER_TARGET_SPEED_RPM.getValue();
    targetVelocityUnitsPer100ms = desiredVelocity * 4096 / 600;
    initTalon(testSubsystem.getMaster());
    testSubsystem.getMaster().setSelectedSensorPosition(0);
    resetCounter = 0;
    pastPosition = 0;
    totalPosition = 0;
    System.out.println(targetVelocityUnitsPer100ms);
    pastTimeMs = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    testSubsystem.getMaster().set(ControlMode.Velocity, targetVelocityUnitsPer100ms);
    //System.out.println("target velocity: " + targetVelocityUnitsPer100ms);\
    double currentPosition = testSubsystem.getMaster().getSelectedSensorPosition();
    long currentTimeMs = System.currentTimeMillis();
    double rpm = ((currentPosition-pastPosition)/4096)*(60000/(currentTimeMs-pastTimeMs));
    System.out.println("leftMaster: " + currentPosition + "  " + testSubsystem.getMaster().getSelectedSensorVelocity() + "       "+ testSubsystem.getMaster().getClosedLoopError(0)+ " rpm="+ rpm);
    SmartDashboardMap.TEST_SHOOTER_SPEED_RPM.putNumber(rpm);
    SmartDashboardMap.TEST_SHOOTER_ENCODER_POSITION.putNumber(currentPosition);
    if (currentPosition < pastPosition) {
      resetCounter++;
      totalPosition += pastPosition;
    }
    pastPosition = currentPosition;
    pastTimeMs = currentTimeMs;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    testSubsystem.getMaster().set(0);
    totalPosition += pastPosition;
    System.out.println("desired velocity: " + desiredVelocity + " " + targetVelocityUnitsPer100ms);
    System.out.println("amount of resets: " + resetCounter);
    System.out.println("total ticks: " + pastPosition);
    System.out.println("total revolutions: " + (pastPosition / 4096.0));
    resetCounter = 0;
    totalPosition = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private void initTalon(WPI_TalonSRX talon){
    /* Factory Default all hardware to prevent unexpected behaviour */
    talon.configFactoryDefault();

    /* Config sensor used for Primary PID [Velocity] */
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    talon.setSensorPhase(true);

		/* Config the peak and nominal outputs */

    double nominalVoltage = SmartDashboardMap.SHOOTER_NOMINAL_VOLTAGE.getValue();
    double peakVoltage = SmartDashboardMap.SHOOTER_PEAK_VOLTAGE.getValue();
    double allowableError = SmartDashboardMap.SHOOTER_ALLOWABLE_ERROR.getValue();
		talon.configNominalOutputForward(nominalVoltage, Constants.kTimeoutMs);
		talon.configNominalOutputReverse(-nominalVoltage, Constants.kTimeoutMs);
		talon.configPeakOutputForward(peakVoltage, Constants.kTimeoutMs);
    talon.configPeakOutputReverse(-peakVoltage, Constants.kTimeoutMs);
    talon.configAllowableClosedloopError(0, (int)allowableError);

		/* Config the Velocity closed loop gains in slot0 */

    double kP = SmartDashboardMap.SHOOTER_kP.getValue();
    double kI = SmartDashboardMap.SHOOTER_kI.getValue();
    double kD = SmartDashboardMap.SHOOTER_kD.getValue();
    double kF = SmartDashboardMap.SHOOTER_kF.getValue();
		talon.config_kF(Constants.kPIDLoopIdx, kF, Constants.kTimeoutMs);
		talon.config_kP(Constants.kPIDLoopIdx, kP, Constants.kTimeoutMs);
		talon.config_kI(Constants.kPIDLoopIdx, kI, Constants.kTimeoutMs);
		talon.config_kD(Constants.kPIDLoopIdx, kD, Constants.kTimeoutMs);
  }
}
