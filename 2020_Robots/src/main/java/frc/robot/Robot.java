/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Vision;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climb;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  protected int farfar37;
  protected double internetSpeed = 2.0;
  transient boolean bruhMoment = true;

  private Compressor compressor = new Compressor();
  private RobotContainer robotContainer;

  public Command autoCommand;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    robotContainer = new RobotContainer();
    compressor.setClosedLoopControl(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    this.setDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    Vision.ledSet(0);
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    Intake.extendIntake();

    this.autoCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (this.autoCommand != null) {
      autoCommand.schedule();
      
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    } 
    Shooter.shootTimer.reset();
    Shooter.intakeShootTimer.reset();
    Climb.lifterBrake(true);
    Intake.extendIntake();
    //Shooter.hoodControl = false;
    Vision.ledSet(1);
    Vision.cameraSet(1);
    //Shooter.hoodE.
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void setDashboard() { //sends values to SmartDashboard to be read externally
    SmartDashboard.putNumber("Right Encoder", DriveTrain.rightEncoder());
    SmartDashboard.putNumber("left Encoder", DriveTrain.leftEncoder());
    SmartDashboard.putNumber("Right Output", DriveTrain.averageRightsideOutput());
    SmartDashboard.putNumber("Left Output", DriveTrain.averageLeftsideOutput());
    SmartDashboard.putNumber("X Offset", Vision.lX);
    SmartDashboard.putNumber("Y Offset", Vision.lY);
    SmartDashboard.putNumber("Hull Area", Vision.lArea);
    SmartDashboard.putNumber("Rotation", Gyro.navXRotAngle());
    SmartDashboard.putNumber("Roll", Gyro.navX.getRoll());
    SmartDashboard.putNumber("Pitch", Gyro.navX.getPitch());
    SmartDashboard.putNumber("CSensor Distance", Intake.proximity);
    SmartDashboard.putNumber("Right Distance", DriveTrain.rightDistance());
    SmartDashboard.putNumber("Left Distance", DriveTrain.leftDistance());
    SmartDashboard.putNumber("Shoot1 RPM", Shooter.shootE1.getVelocity());
    SmartDashboard.putNumber("Shoot2 RPM", Shooter.shootE2.getVelocity());
    SmartDashboard.putNumber("Hood Position", Shooter.hoodE.getPosition());
    SmartDashboard.putNumber("Shoot Ramp Time", Shooter.shootTimer.get());
    SmartDashboard.putNumber("Accelerator RPM", Shooter.acceleratorE.getVelocity());
  }
}
