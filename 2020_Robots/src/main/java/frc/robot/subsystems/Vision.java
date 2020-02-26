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
  
  public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  public static NetworkTableEntry tx; 
  public static NetworkTableEntry ty; 
  public static NetworkTableEntry ta; 
  public static NetworkTableEntry led;

  public static double lX;
  public static double lY;
  public static double lArea;
  public static double ledState;

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

  public static void ledSet(double ledState){
    Vision.table.getEntry("ledMode").setNumber(ledState);
  }

  @Override
  public void periodic() {
   Vision.tx = table.getEntry("tx"); Vision.lX = tx.getDouble(0.0);
   Vision.ty = table.getEntry("ty"); Vision.lY = ty.getDouble(0.0);
   Vision.ta = table.getEntry("ta"); Vision.lArea = ta.getDouble(0.0);
  }
}
