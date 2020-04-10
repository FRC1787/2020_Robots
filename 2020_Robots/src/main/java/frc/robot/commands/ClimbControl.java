/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Climb;


public class ClimbControl extends CommandBase {
  


  public ClimbControl(Climb subsystem) {
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climb.climbTimer.reset();
    RobotContainer.climb.climbTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!Climb.liftState && !(RobotContainer.leftStick.getRawButton(3) || RobotContainer.leftStick.getRawButton(4))) {
      RobotContainer.climb.climbRun(-RobotContainer.leftStick.getY());
    }
    else if (!Climb.liftState && RobotContainer.leftStick.getRawButton(3)) {
      RobotContainer.climb.climbLeft(-RobotContainer.leftStick.getY());
      RobotContainer.climb.climbRight(0);
    }
    else if (!Climb.liftState && RobotContainer.leftStick.getRawButton(4)) {
      RobotContainer.climb.climbRight(-RobotContainer.leftStick.getY());
      RobotContainer.climb.climbLeft(0);
    }
    else {
      Climb.climbRun(0);
    }
    RobotContainer.climb.lifterBrake(Climb.liftState);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Climb.climbRun(0);
    Climb.climbTimer.stop();
    Climb.climbTimer.reset();
    
    /*if (interrupted) {
      Climb.lifterBrake(false);
    }
    else if (!interrupted) {
      Climb.lifterBrake(true);
    }*/
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Climb.liftState;
  }
}
