/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;

import frc.robot.RobotContainer;

public class IntakeBawls extends CommandBase {

  private double frontSetSpeed;
  private double innerSetSpeed;
  
  public IntakeBawls(Intake subsystem, double frontSpeed, double innerSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    this.frontSetSpeed = frontSpeed;
    this.innerSetSpeed = innerSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Intake.intakeTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.intake.intakeStage1(innerSetSpeed); //COURT THIS SETS THE SPEED FOR THE INTAKE
    RobotContainer.intake.intakeStage2(frontSetSpeed); // this stays at 100
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.intake.intakeStage1(0);
    RobotContainer.intake.intakeStage2(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
