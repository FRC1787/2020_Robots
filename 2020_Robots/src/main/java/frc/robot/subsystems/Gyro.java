/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.kauailabs.navx.frc.AHRS;

public class Gyro extends SubsystemBase {
  /**
   * Creates a new Gyro.
   */
  public Gyro() {

  }

  /* NavX object */
  public static AHRS navX = new AHRS();

  //returns rotational angle of the robot in degrees
  public static double navXRotAngle() {
    return navX.getYaw();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
