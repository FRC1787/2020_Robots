/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick; 
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Intake;

import frc.robot.commands.RobotDrive;
import frc.robot.commands.Shoot;
import frc.robot.commands.TurnToTarget;
import frc.robot.commands.Chase;
import frc.robot.commands.Follow;
import frc.robot.commands.ClimbUp;
import frc.robot.commands.IntakeBawls;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  /* Subsystems */
  public final static DriveTrain driveTrain = new DriveTrain();
  public final static Gyro gyro = new Gyro();
  public final static Vision vision = new Vision();
  public final static Shooter shooter = new Shooter();
  public final static Climb climb = new Climb();
  public final static Intake intake = new Intake();

  /* Commands */
  public final static RobotDrive robotDrive = new RobotDrive(driveTrain);
  public final static TurnToTarget turnToTarget = new TurnToTarget();
  //public final static Shoot shoot = new Shoot(shooter);

  /* OI */
  public static Joystick rightStick = new Joystick(0);
  public static Joystick leftStick = new Joystick(1);
  public Button rightTrigger = new JoystickButton(rightStick, 1);
  public Button rightBack = new JoystickButton(rightStick, 2); //back top button
  public Button rightTopLeft = new JoystickButton(rightStick, 3); //top right button
  public Button driveTrainOverride = new JoystickButton(rightStick, 5);
  public Button leftTrigger = new JoystickButton(leftStick, 1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    driveTrain.setDefaultCommand(robotDrive);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //rightTrigger.whenPressed(new TurnToTarget());
    //rightBack.whenPressed(new Chase(driveTrain, vision));
    //rightTrigger.whenPressed(new )
    rightTopLeft.whenPressed(new Follow(driveTrain, vision));
    rightBack.whenPressed(new Shoot(shooter));
    leftTrigger.whenPressed(new ClimbUp(climb));
    rightTrigger.whileHeld(new IntakeBawls(intake));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
   //public Command getAutonomousCommand() {
  //}
}
