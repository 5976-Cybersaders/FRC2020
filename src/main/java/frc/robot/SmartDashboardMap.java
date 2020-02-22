/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Vector;

/**
 * Add your docs here.
 */
public class SmartDashboardMap {
    
    private static final Vector<JudanValue> all = new Vector<>();

    public static final JudanValue SHOOTER_kP = makeValue("P-Value Shooter", 0.09);
    public static final JudanValue SHOOTER_kI = makeValue("I-Value Shooter", 0);
    public static final JudanValue SHOOTER_kD = makeValue("D-Value Shooter", 0.01);
    public static final JudanValue SHOOTER_kF = makeValue("F-Value Shooter", 0.32);
    public static final JudanValue SHOOTER_ALLOWABLE_ERROR = makeValue("Shooter Allowable Error", 500);
    public static final JudanValue SHOOTER_PEAK_VOLTAGE = makeValue("Shooter Peak Voltage", 1);
    public static final JudanValue SHOOTER_NOMINAL_VOLTAGE = makeValue("Shooter Nominal Voltage", 0.075);

    public static final JudanValue SHOOTER_ENCODER_POSITION = makeValue("Shooter Encoder Position", 0);
    public static final JudanValue SHOOTER_SPEED_RPM = makeValue("Shooter Speed RPM", 0);
    public static final JudanValue SHOOTER_TARGET_SPEED_RPM = makeValue("Shooter Target Speed RPM", 400);

    // Vision
    // Used to determine when snapping is complete.  We need 'n' iterations where Tx is w/in +/- this value in degrees.
    public static final JudanValue VISION_DEADBAND = makeValue("Vision_Deadband", 1.5);
    // Used when snapping to determine snap speed proportional to Tx.
    public static final JudanValue VISION_KP = makeValue("Vision_KP", -0.025);
    // Used when snapping to help ensure enough speed is applied to force robot into the vision deadband.
    public static final JudanValue VISION_MIN_CMD = makeValue("Vision_Min_Cmd", -0.2); // previously -0.1
    // Used when driving after a snap.  A value of -1 will cause the robot to drive straight forward.  The larger the positive value the harder the robot will overdrive.
    public static final JudanValue VISION_OVER_DRIVE_FACTOR = makeValue("Vision Overdrive", -.075);
    // Used to control rate of acceleration/deceleration during drive mode.  
    public static final JudanValue VISION_DRIVE_FACTOR_FOR_SPEED = makeValue("Vision Factor speed", 30);
    // Max speed allowed when driving after a snap.
    public static final JudanValue VISION_DRIVE_MODE_SPEED = makeValue("Vision drive mode speed", 0.5);
    // Used to stop from driving forward.  A lower value stops us sooner.
    public static final JudanValue VISION_DRIVE_MAX_TA = makeValue("Vision Max TA", 3.5);
    // Used to control how far we can drive until we consider the target is about to go out of view.
    public static final JudanValue VISION_DRIVE_MAX_TX = makeValue("Vision Max TX", 34);
    // Used to limit max speed during snapping.  The idea is, we may be able to limit oscillation if we don't go too fast during a snap.
    public static final JudanValue VISION_SNAP_MAX_SPEED = makeValue("Vision Snap Max Speed", .5);
    // Determines number of cycles within deadband to exit snap mode.
    public static final JudanValue DEADBAND_COUNTER = makeValue("DeadbandCounter", 6);

    // Used for reporting output only.
    public static final JudanValue VISION_TX = makeValue("Vision_tx", 90);
    // Used for reporting output only.
    public static final JudanValue VISION_LEFT_SPEED = makeValue("Vision left speed", 0);
    // Used for reporting output only.
    public static final JudanValue VISION_RIGHT_SPEED = makeValue("Vision right speed", 0);
    
    public static void reportAll() {
        all.forEach(SmartDashboardMap::report);
    }

    private static JudanValue makeValue(String name, double defaultValue) {
        JudanValue value = new JudanValue(name, defaultValue);
        all.add(value);
        return value;
    }

    private static JudanValue makeValue(String name, String defaultValue) {
        JudanValue value = new JudanValue(name, defaultValue);
        all.add(value);
        return value;
    }

    private static void report(JudanValue variable) {
        System.out.println(variable.getKey() + ": " + variable.getDouble());
    }
}
