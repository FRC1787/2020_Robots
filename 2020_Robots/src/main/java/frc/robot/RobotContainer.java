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
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
import frc.robot.commands.ClimbControl;
import frc.robot.commands.IntakeBawls;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.SetHood;
import frc.robot.commands.OneEighty;

import frc.robot.commands.PointBlank;

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
  public final static SetHood setHood = new SetHood(shooter, "Auto");
  //public final static TurnToTarget turnToTarget = new TurnToTarget();
  public final static Shoot shoot = new Shoot(shooter, intake);

  private final static PointBlank pointBlank = new PointBlank(driveTrain, shooter);

  /* OI */

  //Right Stick
  public static Joystick rightStick = new Joystick(0);
  public Button rightTrigger = new JoystickButton(rightStick, 1);
  public Button rightThumb = new JoystickButton(rightStick, 2); //back top button
  public Button intakeToggle = new JoystickButton(rightStick, 3); //left thumb button
  public Button reverseIntake = new JoystickButton(rightStick, 4);
  public Button driveTrainOverride = new JoystickButton(rightStick, 5);
  public Button targetButton = new JoystickButton(rightStick, 7);

  //Left Stick
  public static Joystick leftStick = new Joystick(1);
  public Button leftTrigger = new JoystickButton(leftStick, 1);
  //public Button intakeRetract = new JoystickButton(leftStick, 3);
  //public Button intakeExtend = new JoystickButton(leftStick, 4);
  public Button hoodManualButton = new JoystickButton(leftStick, 6);
  public Button hoodForward = new JoystickButton(leftStick, 7);
  public Button hoodBack = new JoystickButton(leftStick, 8);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();
    driveTrain.setDefaultCommand(robotDrive);
    shooter.setDefaultCommand(setHood);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //rightTrigger.whenPressed(new TurnToTarget());
    //rightTopLeft.whenPressed(new Follow(driveTrain, vision));
    rightThumb.whileHeld(new Shoot(shooter, intake));
    leftTrigger.whenPressed(new ClimbControl(climb));
    rightTrigger.whileHeld(new IntakeBawls(intake, 1, 1));
    reverseIntake.whileHeld(new IntakeBawls(intake, -1, -1));
    targetButton.whenPressed(new TurnToTarget(driveTrain, vision));
    //rightRight.whileHeld(new Chase(driveTrain, vision));
    intakeToggle.whenPressed(new IntakeExtend(intake));
    hoodBack.whenPressed(new SetHood(shooter, "Back"));
    hoodForward.whenPressed(new SetHood(shooter, "Forward"));
    hoodManualButton.whileHeld( new SetHood(shooter, "Manual"));
    //targetButton.whenPressed(new OneEighty(driveTrain, gyro));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
   public Command getAutonomousCommand() {
     return pointBlank;
    }
}
