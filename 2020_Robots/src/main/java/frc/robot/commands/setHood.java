/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class SetHood extends CommandBase {

  private String setPosition;

  public SetHood(Shooter subsystem, String position) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    this.setPosition = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shooter.hoodCalibrate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (setPosition.equals("Back")) {
      Shooter.hoodBack();
    }
    else if (setPosition.equals("Forward")) {
      Shooter.hoodForward();
    }
    else if (setPosition.equals("Manual")) {
      Shooter.setHood(RobotContainer.leftStick.getRawAxis(3));
    }
    else if (setPosition.equals("Auto")) {
      Shooter.hoodAutoSet();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.setHood(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
