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
import frc.robot.subsystems.Gyro;
import frc.robot.Constants;

public class OneEighty extends CommandBase {
  /**
   * Creates a new OneEighty.
   */
  public OneEighty(DriveTrain subsystem, Gyro subsystem2) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(subsystem2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.gyro.navX.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.driveTrain.seekDrive(180, "navX", "exact");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.driveTrain.tankDrive(0,0);
    RobotContainer.gyro.navX.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return DriveTrain.error < 10 || RobotContainer.rightStick.getRawButton(Constants.DRIVETRAIN_OVERRIDE_BUTTON);
  }
}
