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

public class Climb extends SubsystemBase {
  
  private static CANSparkMax climb1 = new CANSparkMax(6, MotorType.kBrushless);
  private static CANSparkMax climb2 = new CANSparkMax(7, MotorType.kBrushless);

  public Climb() {
    climb2.setInverted(true);
  }

  public void climbUp(double setSpeed){
    climb1.set(setSpeed);
    climb2.set(setSpeed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
