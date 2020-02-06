/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Vision extends SubsystemBase {
  
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  public static double lX;
  public static double lY;
  public static double lArea;

  public Vision() {

  }

  public static double distanceOutput(double hullArea)
  {
    if (lArea < hullArea)
    return hullArea*10;
    
    else
    return hullArea;
  }

  public static double distanceToTarget(){
    double distance = (Math.tan(lY)/7.5);
    return distance;
  }

  @Override
  public void periodic() {
    NetworkTableEntry tx = table.getEntry("tx"); this.lX = tx.getDouble(0.0);
    NetworkTableEntry ty = table.getEntry("ty"); this.lY = ty.getDouble(0.0);
    NetworkTableEntry ta = table.getEntry("ta"); this.lArea = ta.getDouble(0.0);
  }
}
