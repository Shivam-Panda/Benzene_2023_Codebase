// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team4384.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team4384.robot.subsystems.BbIntakeTurner;
import team4384.robot.component.BbSparkMax;
import team4384.robot.constants.CTREConfigs;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static CTREConfigs ctreConfigs;

  private Command m_autonomousCommand;
  private boolean runOnce = false;


  SendableChooser<Integer> pathChosen;
  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    ctreConfigs = new CTREConfigs();
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    SendableChooser<Integer> pathChooser = new SendableChooser<>();
    pathChooser.setDefaultOption("Cube", 0);
    pathChooser.addOption("Cone", 1);
    pathChooser.addOption("Charge Station", 2);

    SmartDashboard.putData("Path Selection", pathChooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Roll", m_robotContainer.s_Swerve.gyro.getRoll());
    m_robotContainer.UpdateSmartBoard();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    runOnce = false;
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    /*m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }*/

    pathChosen = (SendableChooser<Integer>)SmartDashboard.getData("Path Selection");
    runOnce = false;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    if (runOnce) return;
    if (pathChosen.getSelected() == 0) {
      m_robotContainer.autonomous.basic();
    }
    else if (pathChosen.getSelected() == 1) {
      m_robotContainer.autonomous.cone();
    }
    else if (pathChosen.getSelected() == 2) {
      m_robotContainer.autonomous.chargingStation();
    }
    runOnce = true;

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.HandleArmJoystick();
/*    IntakeTuner.runArcade();

    SmartDashboard.putNumber("Encoder For Left", left.getEncoder().getPosition());
    MotorControllerGroup group = new MotorControllerGroup(left, right);

    group.set(driver.getRawAxis(Joystick.kDefaultZChannel) * .15);

   if (driver.getRawButton(3)) {
       Intake.set(.15);
   }
   else if (driver.getRawButton(5)) {
       Intake.set(-.15);
   }
   else if (driver.getRawButton(4)) {
      Intake.set(.20);
  }
  else  if (driver.getRawButton(6)) {
      Intake.set(-.20);
  }
   else if (driver.getRawButton(9)) {
     Intake.set(-.55);
   }
  else {
      Intake.set(0);
  }

    IntakeTuner.runArcade();*/
}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }
}
