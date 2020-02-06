/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private static CANSparkMax shooter1 = new CANSparkMax(8, MotorType.kBrushless);
  private static CANSparkMax shooter2 = new CANSparkMax(9, MotorType.kBrushless);
  private static WPI_TalonSRX shooterFeed = new WPI_TalonSRX(1);

  public static Timer shootTimer = new Timer();

  public static double rampTime;
  public static boolean rampOK;

  public Shooter() {
    //shootTimer.reset();
  }

  public static void shoot(double setSpeed){
    shooter1.set(-setSpeed);
    shooter2.set(setSpeed);

    //shootTimer.start();
    if (Shooter.rampOK) { 
      shooterFeed.set(setSpeed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println(String.format("S1: %s, S2: %s",shooter1.getInverted(), shooter2.getInverted()));
    Shooter.rampTime = shootTimer.get();
    rampOK = (Shooter.rampTime > 1);
  }
}
