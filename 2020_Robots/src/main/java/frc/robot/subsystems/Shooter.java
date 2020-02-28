/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends SubsystemBase {


  public static CANSparkMax shooter1 = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax shooter2 = new CANSparkMax(12, MotorType.kBrushless);
  private static CANSparkMax accelerator = new CANSparkMax(10, MotorType.kBrushless);
  private static CANSparkMax hood = new CANSparkMax(11, MotorType.kBrushed);

  public static CANEncoder shootE1 = new CANEncoder(shooter1);
  public static CANEncoder shootE2 = new CANEncoder(shooter2);
  public static CANEncoder acceleratorE = new CANEncoder(hood);
  public static CANEncoder hoodE = new CANEncoder(hood, EncoderType.kQuadrature, 8192);

  public static Timer shootTimer = new Timer();
  public static Timer intakeShootTimer = new Timer();

  public static double rampTime;
  public static double intakeTime;
  public static boolean rampOK = false;
  //public static boolean hoodControl = false;
  public static String hoodState;

  public Shooter() {
    shootTimer.stop();
    intakeShootTimer.stop();
    shootTimer.reset();
    intakeShootTimer.reset();
  }



  // SHOOTER //

  public static void shoot(double setSpeed) {
    shooter1.set(-setSpeed);
    shooter2.set(setSpeed);

    //shootTimer.start();
    if (Shooter.rampOK) { 
      accelerator.set(-.1);
    }
    else {
      accelerator.set(0);
    }
  }

  public static void bootlegShoot(double setRPM, double accelSpeed) {
    if (Math.abs(shootE1.getVelocity()) < setRPM) {
      shooter1.set(-1);
    }
    else if (Math.abs(shootE1.getVelocity()) > setRPM) {
      shooter1.set(0);
    }
    
    if (Math.abs(shootE2.getVelocity()) < setRPM) {
      shooter2.set(1);
    }
    else if (Math.abs(shootE2.getVelocity()) > setRPM) {
      shooter2.set(0);
    }

    if (Shooter.rampOK) { 
      accelerator.set(-accelSpeed);
    }
    else {
      accelerator.set(0);
    }
  }


  // HOOD //

  public static void setHood(double setSpeed) {
    hood.set(setSpeed);

    /*if (Shooter.hoodControl) {
     hood.set(setSpeed);
    }
    else if (!Shooter.hoodControl) {
     hood.set(0);
    } */
  }
  
  public static void hoodControlState() {

    /*if (RobotContainer.leftStick.getRawButtonPressed(2)) {
      Shooter.hoodControl = !Shooter.hoodControl;
    }*/
  }

  public static void hoodBack() { //Sets the hood to a good position for shooting from in front of the high goal
    double deltaPos = (-0.075806 - hoodE.getPosition()) * (20);
    hood.set(deltaPos);
  }

  public static void hoodForward() { //sets the hood to a good position for shooting from the trench
    double deltaPos = (-0.420898 - hoodE.getPosition()) * (20);
    hood.set(deltaPos);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println(String.format("S1: %s, S2: %s",shooter1.getInverted(), shooter2.getInverted()));
    Shooter.rampTime = shootTimer.get();
    Shooter.intakeTime = intakeShootTimer.get();
    Shooter.rampOK = (Shooter.rampTime > 2); //|| (Shooter.shootTime > .5); //COURT THIS IS THE RAMP TIME
  }
}
