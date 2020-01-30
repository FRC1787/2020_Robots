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

public class ClimbUp extends CommandBase {
  /**
   * Creates a new ClimbUp.
   */
  public ClimbUp(Climb subsystem) {
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climb.climbUp(RobotContainer.leftStick.getY());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climb.climbUp(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !RobotContainer.leftStick.getRawButton(1);
  }
}
