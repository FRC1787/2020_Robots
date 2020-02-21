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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Climb extends SubsystemBase {
  
  private static CANSparkMax climb1 = new CANSparkMax(9, MotorType.kBrushless);
  private static CANSparkMax climb2 = new CANSparkMax(7, MotorType.kBrushless);

  private static Solenoid brakeEngaged = new Solenoid(0);
  private static Solenoid brakeDisengaged = new Solenoid(1);

  public static final Timer climbTimer = new Timer();

  public static boolean liftState = true;


  public void liftCheck(){
    if (RobotContainer.leftStick.getRawButtonPressed(1)){
      Climb.liftState = !Climb.liftState;
    }
  }
  public Climb() {
    climb2.setInverted(true);
    //lifterBrake(true);
    climbTimer.reset();
  }

  public static void climbRun(double setSpeed){
    climb1.set(setSpeed);
    climb2.set(setSpeed);
  }

  public static void lifterBrake(boolean state){
    brakeEngaged.set(state);
    brakeDisengaged.set(!state);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    liftCheck();
  }
}
