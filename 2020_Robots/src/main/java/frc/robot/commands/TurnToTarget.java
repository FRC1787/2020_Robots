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

import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.DriveTrain;

public class TurnToTarget extends CommandBase {
  /**
   * Creates a new TurnToTarget.
   */
  public TurnToTarget(DriveTrain drivetrain, Vision vision) {
    addRequirements(drivetrain);
    addRequirements(vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.gyro.navX.reset();
    RobotContainer.vision.ledSet(3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Gyro.navX.reset();
    DriveTrain.seekDrive(Vision.lX, "navX", "exact");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.moveRightSide(0);
    DriveTrain.moveLeftSide(0);
    RobotContainer.vision.ledSet(1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.rightStick.getRawButton(Constants.DRIVETRAIN_OVERRIDE_BUTTON);
  }
}
