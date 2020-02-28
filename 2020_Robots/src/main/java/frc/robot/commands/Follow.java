/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.RobotContainer;
import frc.robot.Constants;

public class Follow extends CommandBase {
  /**
   * Creates a new Follow.
   */
  public Follow(DriveTrain subsystem, Vision subsystemTwo) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(subsystemTwo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain.left1E.setPosition(0); //Resets all the neo encoders to 0
    DriveTrain.left2E.setPosition(0);
    DriveTrain.right2E.setPosition(0);
    DriveTrain.right2E.setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveTrain.seekDrive(RobotContainer.vision.distanceOutput(2), "limeLight", "follow");
    RobotContainer.gyro.navX.reset();
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
