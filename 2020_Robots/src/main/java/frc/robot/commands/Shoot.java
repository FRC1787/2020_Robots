/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.RobotContainer;

public class Shoot extends CommandBase {
  /**
   * Creates a new Shoot.
   */
  public Shoot(Shooter shooter, Intake intake) {
    addRequirements(shooter, intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shooter.shootTimer.start();
    Shooter.intakeShootTimer.start();
    Vision.ledSet(3);
    Vision.cameraSet(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //RobotContainer.shooter.shoot(1); // COURT THIS SETS THE SPEED
    Shooter.bootlegShoot(5100, .8);

    if (Shooter.intakeTime > 2.2 && Shooter.intakeTime < 2.4) {
      Intake.intakeStage1(1);
    }
    else {
      Intake.intakeStage1(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Vision.ledSet(1);
    Vision.cameraSet(1);
    Shooter.bootlegShoot(0,0);
    Intake.intakeStage1(0);
    Shooter.shootTimer.stop();
    Shooter.shootTimer.reset();
    Shooter.intakeShootTimer.stop();
    Shooter.intakeShootTimer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !RobotContainer.rightStick.getRawButton(2);
  }
}
