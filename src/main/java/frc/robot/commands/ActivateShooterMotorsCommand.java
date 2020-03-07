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
import frc.robot.subsystems.ShooterSubsystem;

public class ActivateShooterMotorsCommand extends CommandBase {

  private ShooterSubsystem shooterSubsystem;
  private double upperRPM, lowerRPM;
  private double upperEncoderSpeed, lowerEncoderSpeed;
  private long pastTimeMs = 0;
  private double upperPastPosition = 0, lowerPastPosition = 0;
  /**
   * Creates a new ActivateShooterMotorsCommand.
   */
  public ActivateShooterMotorsCommand(ShooterSubsystem shooterSubsystem, double upperRPM, double lowerRPM) {
    this.shooterSubsystem = shooterSubsystem;
    this.upperRPM = upperRPM;
    this.lowerRPM = lowerRPM; 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //desiredVelocity = SmartDashboardMap.SHOOTER_TARGET_SPEED_RPM.getValue();
    upperEncoderSpeed = upperRPM * 4096 / 600;
    lowerEncoderSpeed = lowerRPM * 4096 / 600;
    initTalon(shooterSubsystem.getUpperShooterMotor());
    initTalon(shooterSubsystem.getLowerShooterMotor());
    
    System.out.println("UpperShooter: RPM = " + upperRPM + " EncoderSpeed = " + upperEncoderSpeed);
    System.out.println("LowerShooter: RPM = " + lowerRPM + " EncoderSpeed = " + lowerEncoderSpeed);
    pastTimeMs = System.currentTimeMillis();
    upperPastPosition = 0; lowerPastPosition = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    long currentTimeMs = System.currentTimeMillis();
    upperPastPosition = execute("Upper", shooterSubsystem.getUpperShooterMotor(), upperEncoderSpeed, currentTimeMs, upperPastPosition);
    lowerPastPosition = execute("Lower", shooterSubsystem.getLowerShooterMotor(), lowerEncoderSpeed, currentTimeMs, lowerPastPosition);
    pastTimeMs = currentTimeMs;
  }

  private double execute(String name, WPI_TalonSRX talon, double encoderSpeed, long currentTimeMs, double pastPosition) {
    talon.set(ControlMode.Velocity, encoderSpeed);
    double currentPosition = talon.getSelectedSensorPosition();
    double rpm = ((currentPosition-pastPosition)/4096)*(60000/(currentTimeMs-pastTimeMs));
    System.out.println(name + " " + currentPosition + "  " + talon.getSelectedSensorVelocity() + " "+ talon.getClosedLoopError(0)+ " rpm="+ rpm);
    if (name.toUpperCase().startsWith("UP")){
      SmartDashboardMap.UPPER_SHOOTER_SPEED_RPM.putNumber(rpm);
      SmartDashboardMap.UPPER_SHOOTER_ENCODER_POSITION.putNumber(currentPosition);
    } else{
      SmartDashboardMap.LOWER_SHOOTER_SPEED_RPM.putNumber(rpm);
      SmartDashboardMap.LOWER_SHOOTER_ENCODER_POSITION.putNumber(currentPosition);
    }
    return currentPosition;
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
    talon.setSelectedSensorPosition(0);
  }
}
