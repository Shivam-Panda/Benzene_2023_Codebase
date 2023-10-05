package team4384.robot;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import team4384.robot.commands.*;
import team4384.robot.component.BbJoystick;
import team4384.robot.component.BbSparkMax;
import team4384.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final BbJoystick driver = new BbJoystick(0);
    private final BbJoystick Manip = new BbJoystick(1);

    /* Drive Controls */
 /*   private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;*/

    private final int translationAxis = Joystick.kDefaultYChannel;
    private final int strafeAxis = Joystick.kDefaultXChannel;
    private final int rotationAxis = Joystick.kDefaultZChannel;

    /* Driver Buttons */
    private final JoystickButton Rotate = new JoystickButton(driver, Joystick.ButtonType.kTrigger.value);
    private final JoystickButton IntakeTurnUp = new JoystickButton(Manip, 11);
    private final JoystickButton IntakeTurnDown = new JoystickButton(Manip, 12);
    private final JoystickButton IntakespitCone = new JoystickButton(Manip, 2);
    private final JoystickButton IntakespitCube = new JoystickButton(Manip, 1);
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private  BbIntakeTurner IntakeTuner = new BbIntakeTurner();
    private  BbIntake Intake = new BbIntake();
    private  BbArmBase ArmBase = new BbArmBase();
    /* Subsystems */
    public final Swerve s_Swerve = new Swerve();

    public Autonomous autonomous = new Autonomous(ArmBase, IntakeTuner, Intake, s_Swerve, s_Swerve.gyro);

  //  private BbSparkMax  left = new BbSparkMax(49);
   //private BbSparkMax right = new BbSparkMax(50);
  //  private BbArmBase armBase;


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
       s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve,
                () -> -driver.getRawAxis(translationAxis),
                () -> -driver.getRawAxis(strafeAxis),
                () -> driver.getRawAxis(rotationAxis),
                    () -> driver.getRawAxis(3),
                    () -> false,
                    Rotate,
                    new JoystickButton(driver, 3),
                    new JoystickButton(driver, 4)
            )
        );

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public void getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
       // return new exampleAuto(s_Swerve);
    }

    public void HandleArmJoystick() {

        /*

        Controls

        Right Trigger - Arm Up
        Left Trigger - Arm Down

        Left Joystick Up - Intake Forward
        Left Joystick Down - Intake Backwards

        A - Cone In/Cube Out
        B - Cube In/Cone Out
         */
        /** Intake Tuner */

        if (Manip.getRawAxis(1, 0.1) < 0) {
            SmartDashboard.putBoolean("IntakeTurner", true);
            IntakeTuner.Up();
        }
        else if (Manip.getRawAxis(1, 0.1) > 0) {
            SmartDashboard.putBoolean("IntakeTurner", false);
            IntakeTuner.Down();
        }
        else {
            SmartDashboard.putBoolean("IntakeTurner", false);
            IntakeTuner.stopMotor();
        }

        /** Intake */
        if (IntakespitCone.getAsBoolean()) {
            Intake.suckCube();
        }
        else if (IntakespitCube.getAsBoolean()) {
            if (-Manip.getRawAxis(1, .1) == -1) {
                Intake.spitCube(1);
            }
            else {
                Intake.spitCube();
            }
        }
        else if (IntakespitCube.getAsBoolean()) {
            Intake.suckCone();
        }
        else if (IntakespitCone.getAsBoolean()) {
            Intake.spitCone();
        }
        else {
            Intake.stop();
        }

        /** Arm Base */
//        if (Manip.getRawAxis(2, .1) >= 0) {
//            ArmBase.Up();
//        }
//        else if(Manip.getRawAxis(3, .1) >= 0) {
//            ArmBase.Down();
//        }
//        else {
//            ArmBase.stop();
//        }
    }

    public void UpdateSmartBoard() {
        IntakeTuner.display();
        ArmBase.display();
    }
}
