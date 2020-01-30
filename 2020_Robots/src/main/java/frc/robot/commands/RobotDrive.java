/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveTrain;

import frc.robot.RobotContainer;

public class RobotDrive extends CommandBase {
  /**
   * Creates a new RobotDrive.
   */
  public RobotDrive(DriveTrain subsystem) {
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.driveTrain.left1E.setPosition(0); //Resets all the neo encoders to 0
    RobotContainer.driveTrain.left2E.setPosition(0);
    RobotContainer.driveTrain.right2E.setPosition(0);
    RobotContainer.driveTrain.right2E.setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.driveTrain.moveLeftSide(RobotContainer.rightStick.getY() + RobotContainer.rightStick.getX()); // reads Joystick values and converts them to drive values for each half of the robot
    RobotContainer.driveTrain.moveRightSide(RobotContainer.rightStick.getY() - RobotContainer.rightStick.getX());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.driveTrain.moveLeftSide(0);
    RobotContainer.driveTrain.moveRightSide(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
