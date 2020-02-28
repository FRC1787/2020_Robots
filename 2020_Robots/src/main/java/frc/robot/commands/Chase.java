/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;

public class Chase extends CommandBase {
  /**
   * Creates a new Chase.
   */
  public Chase(DriveTrain subsystem, Vision subsystemTwo) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(subsystemTwo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.gyro.navX.reset();
    DriveTrain.left1E.setPosition(0); //resets the encoder values to 0
    DriveTrain.left2E.setPosition(0);
    DriveTrain.right1E.setPosition(0);
    DriveTrain.right2E.setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.gyro.navX.reset();
    DriveTrain.seekDrive(10, "limelight", "exact");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.moveRightSide(0);
    DriveTrain.moveLeftSide(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.rightStick.getRawButton(Constants.DRIVETRAIN_OVERRIDE_BUTTON);
  }
}
