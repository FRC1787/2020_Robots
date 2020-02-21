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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private static CANSparkMax shooter1 = new CANSparkMax(3, MotorType.kBrushless);
  private static CANSparkMax shooter2 = new CANSparkMax(12, MotorType.kBrushless);
  private static CANSparkMax accelerator = new CANSparkMax(10, MotorType.kBrushless);
  private static CANSparkMax hood = new CANSparkMax(11, MotorType.kBrushed);

  public static Timer shootTimer = new Timer();

  public static double rampTime;
  public static boolean rampOK;
  public static boolean hoodControl = false;

  public Shooter() {
    //shootTimer.reset();
  }

  public static void shoot(double setSpeed){
    shooter1.set(-setSpeed);
    shooter2.set(setSpeed);

    //shootTimer.start();
    if (Shooter.rampOK) { 
      accelerator.set(-setSpeed);
    }
  }

  public static void setHood(){
    if (Shooter.hoodControl){
     hood.set(RobotContainer.leftStick.getRawAxis(3));
    if (!Shooter.hoodControl)
     hood.set(0);
    }
  }
  
  public static void hoodControlState(){
    if (RobotContainer.leftStick.getRawButtonPressed(2)){
      Shooter.hoodControl = !Shooter.hoodControl;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println(String.format("S1: %s, S2: %s",shooter1.getInverted(), shooter2.getInverted()));
    Shooter.rampTime = shootTimer.get();
    rampOK = (Shooter.rampTime > 2); //COURT THIS IS THE RAMP TIME

    hoodControlState();
    setHood();
  }
}
