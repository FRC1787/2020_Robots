/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.commands.RobotDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

public class DriveTrain extends SubsystemBase {

  /*Spark Max Motor Controller Objects*/
  private static CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private static CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private static CANSparkMax right1 = new CANSparkMax(3, MotorType.kBrushless);
  private static CANSparkMax right2 = new CANSparkMax(4, MotorType.kBrushless);

  /*Neo Motor Encoder Objects*/
  public static CANEncoder left1E = new CANEncoder(left1);
  public static CANEncoder left2E = new CANEncoder(left2);
  public static CANEncoder right1E = new CANEncoder(right1);
  public static CANEncoder right2E = new CANEncoder(right2);

  public DriveTrain() {
    left1.setIdleMode(IdleMode.kBrake);
    right1.setIdleMode(IdleMode.kBrake);
    left2.setIdleMode(IdleMode.kBrake);
    right2.setIdleMode(IdleMode.kBrake);

    right1.setInverted(true);
    right2.setInverted(true);
    left1.setInverted(false);
    left2.setInverted(false);

    left1E.setPosition(0); //Resets all the neo encoders to 0
    left2E.setPosition(0);
    right2E.setPosition(0);
    right2E.setPosition(0);
  }

  public void moveLeftSide(final double speed) {
    left1.set(speed);
    left2.set(speed);
  }

  public void moveRightSide(final double speed) {
    right1.set(speed);
    right2.set(speed);
  }

  // average encoder value on the left side of the robot
  public static double leftEncoder() {
    return -(left1E.getPosition() + left2E.getPosition()) / 2.0;
  }

  // average encoder value on the right side of the robot
  public static double rightEncoder() {
    return (right1E.getPosition() + right2E.getPosition()) / 2.0;
  }

  public static double rightDistance(){
    return ((((right1E.getPosition() + right2E.getPosition()) / 2.0) / 8.05) * 4 * Math.PI);
  }

  public static double leftDistance(){
    return -((((left1E.getPosition() + left2E.getPosition()) / 2.0) / 8.05) * 4 * Math.PI);
  }

  // % output of the right side of the robot
  public static double averageRightsideOutput() {
    return (right1.get() + right2.get()) / 2;
  }

  // % output of the left side of the robot
  public static double averageLeftsideOutput() {
    return (left1.get() + left2.get()) / 2;
  }

  // sets % values for each side of the robot individually
  public static void tankDrive(final double leftSide, final double rightSide) {
    left1.set(leftSide);
    right1.set(rightSide);
    left2.set(leftSide);
    right2.set(rightSide);
  }

  // drives both sides of the robot based on values from a feedback sensor and a
  // target position
  public static void seekDrive(double destination, String feedBackSensor, String seekType) {
    if (feedBackSensor.equals("navX") && !seekType.equals("follow")){
      tankDrive(pIDDrive(destination, Gyro.navXRotAngle(), feedBackSensor, seekType),
          pIDDrive(destination, Gyro.navXRotAngle(), feedBackSensor, seekType));
    }

    else if (feedBackSensor.equals("encoder")) { 
      tankDrive(pIDDrive(destination, leftEncoder()/48, feedBackSensor, seekType),
          pIDDrive(destination, rightEncoder()/48, feedBackSensor, seekType));
    }

    else if (feedBackSensor.equals("limeLight") && !seekType.equals("follow")) {
      tankDrive(pIDDrive(destination, Vision.distanceToTarget(), feedBackSensor, seekType),
          pIDDrive(destination, Vision.distanceToTarget(), feedBackSensor, seekType));
    }

    else if (feedBackSensor.equals("limeLight") && seekType.equals("chase")) {
      tankDrive(pIDDrive(destination, Vision.lArea, feedBackSensor, seekType),
          pIDDrive(destination, Vision.lArea, feedBackSensor, seekType));
    }

    else if (seekType.equals("follow")) {
      tankDrive(-pIDDrive(destination, Vision.lArea, feedBackSensor, seekType) + pIDDrive(Vision.lX, Gyro.navXRotAngle(), "navX", seekType),
      -pIDDrive(destination, Vision.lArea, feedBackSensor, seekType) - pIDDrive(Vision.lX, Gyro.navXRotAngle(), "navX", seekType));
    }
  }

  // Outputs a drive value based on sensor inputs and a target value
  public static double pIDDrive(double targetDistance, double actualValue, String feedBackSensor,
      final String seekType) // enter target distance in feet
  {
    if (feedBackSensor.equals("navX")) {
      Constants.proportionalTweak = 0.0067; // 0.0065 0.0047
      Constants.integralTweak = 0.0000; // .000007
      Constants.DerivativeTweak = -0.0;
      Constants.okErrorRange = 0.0;
    }

    else if (feedBackSensor.equals("encoder") || feedBackSensor.equals("limeLight")) {
      Constants.proportionalTweak = 0.0057; // placeholers until ideal values for linear drive are found
      Constants.integralTweak = 0.0;
      Constants.DerivativeTweak = -0.0001;
      Constants.okErrorRange = 15;
    }

    else {
      Constants.proportionalTweak = 0; // these just stay zero
      Constants.integralTweak = 0;
      Constants.DerivativeTweak = 0;
      Constants.okErrorRange = 0;
    }

    if (seekType.equals("exact") || seekType.equals("follow")) {
      Constants.error = targetDistance - actualValue;
    }

    else if (seekType.equals("oneWay")) {
      Constants.error = noNegative(Math.abs(targetDistance - (actualValue)));
    }

    Constants.proportional = Constants.error; // Math for determining motor output based on PID values
    Constants.derivative = (Constants.previousError - Constants.error) / 0.02;
    Constants.integral += Constants.previousError;
    Constants.previousError = Constants.error;

    if ((Constants.error > Constants.okErrorRange || Constants.error < -Constants.okErrorRange)) // && !(targetDistance < actualValue && seekType == "oneWay")
    {
      Constants.pIDMotorVoltage = truncateMotorOutput((Constants.proportionalTweak * Constants.proportional)
          + (Constants.DerivativeTweak * Constants.derivative) + (Constants.integralTweak * Constants.integral),
          feedBackSensor);
      return Constants.pIDMotorVoltage;
    }

    if ((targetDistance - actualValue < 0) && seekType.equals("oneWay")) {
      return 0;
    }

    else {
      Constants.proportional = 0;
      Constants.integral = 0;
      Constants.derivative = 0;
      Constants.previousError = 0;
      return 0;
    }
  }

  private static double truncateMotorOutput(double motorOutput, String feedBackSensor) // Whatever the heck Jake and Van did
  {
    if (feedBackSensor.equals("encoder") || feedBackSensor.equals("limeLight"))// sets max motor % to 50 if using encoders or Limelight feedback to drive
    {
      if (motorOutput > .9)
        return .9;

      else if (motorOutput < -.9)
        return -.9;

      else
        return motorOutput;
    }

    if (feedBackSensor.equals("navX"))// sets max motor % to 40 if using navx to drive
    {
      if (motorOutput > 1)
        return 1;

      else if (motorOutput < -1)
        return -1;

      else
        return motorOutput;
    }

    else
      return 0;
  }

  // returns 0 if input is below zero and returns the input if it is above 0
  public static double noNegative(final double input)
    {
      if (input >= 0) 
      return input;
      
      else 
      return 0;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
