/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake extends SubsystemBase {

  private static CANSparkMax intakeInner = new CANSparkMax(5, MotorType.kBrushless);
  private static CANSparkMax intakeFront = new CANSparkMax(6, MotorType.kBrushless);

  private static I2C.Port i2cPort = I2C.Port.kOnboard;
  //public static ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  public static Timer intakeTimer = new Timer();
  public static Timer ballTimer = new Timer();

  public static final Solenoid intakeExtended = new Solenoid(2);
  public static final Solenoid intakeRetracted = new Solenoid(3);

  //ball counting variables
  //enter pin number u want
  private static double breakerVoltageConstant = 40;
  private static final AnalogInput succitvan2 = new AnalogInput(0);
  public static double succitvan2Raw = succitvan2.getVoltage();

  //jordan u need to configure and test different values for the analog pin, voltage constant, ball delay, and samples
  private static int ballDelay = 20;
  private static int MAX_BALLS = 5;
  private static int SAMPLES = 10;
  private static int ballDelayCounter;
  private static int sampleIndex;
  private static int[] samples;

  public static double proximity;
  public static boolean ballReady;
  public static double ballCount = 0;
  public static boolean ballFull;
  public static double intakeTime;
  public static boolean ballStuck;
  public static boolean extended = true;

  public Intake() {
    this(0);
  }

  public static void extendIntake() {
    intakeExtended.set(Intake.extended);
    intakeRetracted.set(!Intake.extended);
  }


  public Intake(int ballsLoaded) {
    Intake.ballCount = ballsLoaded;
    Intake.ballDelayCounter = Intake.ballDelay;
    Intake.sampleIndex = 0;
    Intake.samples = new int[Intake.SAMPLES];
  }

  public static void intakeStage1(double setSpeed) {
    intakeInner.set(setSpeed);
    //intakeFront.set(-setSpeed);
  }

  public static boolean getBreakerState() {
    return (succitvan2.getVoltage() > Intake.breakerVoltageConstant);
  }

  public static void ballCheck() {
    //check if the balls are full
    if (ballCount >= MAX_BALLS) {
      ballFull = true;
    } 
    else {
      ballFull = false;
    }

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

    //the breaker code
    //The counter to allow a ball to clear the breaker after detection you must configure by hand
    if (Intake.ballDelayCounter >= Intake.ballDelay) {
      if (Intake.sampleIndex < Intake.SAMPLES && !Intake.ballFull) {
        //if the breaker is activated
        if (!getBreakerState()) {
          Intake.samples[sampleIndex] = 1;
        }
        else {
          Intake.samples[sampleIndex] = -1;
        }

        Intake.sampleIndex += 1;
      } 
      else {
        //iterate through samples and if the average is high enough then add ball
        int sum = 0;

        for (int i: samples) {
          sum += i;
        }

        if (sum > 0) {
          Intake.ballCount += 1;
        }

        //reset stuff
        Intake.samples = new int[SAMPLES];
        Intake.sampleIndex = 0;
        Intake.ballDelayCounter = 0;
      }
    } 
    else {
      Intake.ballDelayCounter += 1;
    }
  }

  public void intakeStage2(double setSpeed) {
    intakeFront.set(-setSpeed);
    
    /*if (Intake.ballReady && !Intake.ballFull && !Intake.ballStuck) {
      intakeTimer.reset();
      intakeTimer.start();
      if (Intake.intakeTime < .2) {
        //intake.set(setSpeed);
      }
      else {
        //intake.set(0);
      }
    }
    else{
      //intake.set(0);
      intakeTimer.stop();
      intakeTimer.reset();
    }*/
  }

  @Override
  public void periodic() {
    //functions
    ballCheck();

    //setters
    //Intake.proximity = colorSensor.getProximity();
    Intake.ballReady = (Intake.proximity > 20);
    Intake.ballFull = (Intake.ballCount > 5);
    Intake.intakeTime = intakeTimer.get();
  }
}
