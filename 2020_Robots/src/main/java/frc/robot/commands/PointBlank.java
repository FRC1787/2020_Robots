/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;

public class PointBlank extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public static Timer autoTimer = new Timer();

  public PointBlank(DriveTrain drivetrain, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    autoTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (autoTimer.get() < 2.6) {
      DriveTrain.tankDrive(-.2,-.2);
    }
    else if (autoTimer.get() > 2.6 && autoTimer.get() < 7.2) {
      //RobotContainer.shooter.periodic();
      Shooter.shootTimer.start();
      DriveTrain.tankDrive(0,0);
      Shooter.bootlegShoot(4900, 1);
      if (autoTimer.get() > 4.6 && autoTimer.get() < 7.2) {
        Shooter.accelerator.set(-1);
      }
      else {
        Shooter.accelerator.set(0);
      }
    }
    else if (autoTimer.get() > 7.2 && autoTimer.get() < 9.8) {
      DriveTrain.tankDrive(.2, .2);
      Shooter.bootlegShoot(0, 0);
      Shooter.shootTimer.stop();
      Shooter.shootTimer.reset();
      RobotContainer.gyro.navX.reset();
    }
    else if (autoTimer.get() > 9.8) {
      RobotContainer.driveTrain.seekDrive(180, "navX", "exact");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.tankDrive(0,0);
    Shooter.bootlegShoot(0, 0);
    autoTimer.stop();
    autoTimer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return autoTimer.get() >= 15;
  }
}
