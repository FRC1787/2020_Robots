/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeDrum = new CANSparkMax(80, MotorType.kBrushless);
  private CANSparkMax intake = new CANSparkMax(90, MotorType.kBrushless);

  private static I2C.Port i2cPort = I2C.Port.kOnboard;
  public static ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  public static Timer intakeTimer = new Timer();
  public static Timer ballTimer = new Timer();

  public static double proximity;
  public static boolean ballReady;
  public static double ballCount = 0;
  public static boolean ballFull;
  public static double intakeTime;
  public static boolean ballStuck;

  public Intake() {
  }

  public void intakeStage1(double setSpeed){
    intakeDrum.set(setSpeed);
  }

  public static void ballCheck() {
    if (Intake.ballReady) {
      ballTimer.start();
      if (ballTimer.get() >= .5 && Intake.ballReady) {
        Intake.ballStuck = true;
      }
    }
    else if (Intake.proximity > 20) {
    Intake.ballStuck = false;
    ballTimer.stop();
    ballTimer.reset();
    }
  }

  public void intakeStage2(double setSpeed) {
    if (Intake.ballReady && !Intake.ballFull && !Intake.ballStuck) {
      intakeTimer.reset();
      intakeTimer.start();
      if (Intake.intakeTime < .2) {
        intake.set(setSpeed);
      }
      else {
        intake.set(0);
      }
    }
    else{
      intake.set(0);
      intakeTimer.stop();
      intakeTimer.reset();
    }
  }

  @Override
  public void periodic() {
    Intake.proximity = colorSensor.getProximity();
    Intake.ballReady = (Intake.proximity > 20);
    Intake.ballFull = (Intake.ballCount > 5);
    Intake.intakeTime = intakeTimer.get();
  }
}
