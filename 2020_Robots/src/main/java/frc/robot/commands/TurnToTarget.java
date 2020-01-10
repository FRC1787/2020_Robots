/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToTarget extends CommandBase {
  /**
   * Creates a new TurnToTarget.
   */
  public TurnToTarget() {
    addRequirements(RobotContainer.driveTrain);
    addRequirements(RobotContainer.vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.gyro.navX.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.gyro.navX.reset();
    RobotContainer.driveTrain.seekDrive(RobotContainer.vision.lX, "navX", "exact");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (RobotContainer.rightStick.getRawButton(Constants.DRIVETRAIN_OVERRIDE_BUTTON))
    return true;

    else
    return false;
  }
}