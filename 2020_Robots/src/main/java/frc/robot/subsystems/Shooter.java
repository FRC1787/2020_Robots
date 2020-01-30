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

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private final CANSparkMax shooter1 = new CANSparkMax(8, MotorType.kBrushless);
  private final CANSparkMax shooter2 = new CANSparkMax(9, MotorType.kBrushless);

  public Shooter() {
    
  }

  public void shoot(final double setSpeed){
    shooter1.set(setSpeed);
    shooter2.set(-setSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println(String.format("S1: %s, S2: %s",shooter1.getInverted(), shooter2.getInverted()));
  }
}
