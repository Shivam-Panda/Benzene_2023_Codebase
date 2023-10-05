package team4384.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team4384.robot.component.BbSparkMax;
import team4384.robot.constants.PidConfig;

import static team4384.robot.constants.RobotMap.*;

public class BbArmBase {
    private final BbSparkMax leftMotor = new BbSparkMax(ARM_BASE_LEFT);
    private final BbSparkMax rightMotor = new BbSparkMax(ARM_BASE_RIGHT);
    private RelativeEncoder leftEncoder;
    private DigitalInput Limit = new DigitalInput(ARM_BASE_LIMIT_SWITCH);
    private RelativeEncoder rightEncoder;
    private final MotorControllerGroup MotorGroup = new MotorControllerGroup(leftMotor, rightMotor);
    public BbArmBase() {
        rightMotor.setInverted(true);

        rightMotor.configurePid(new PidConfig(0,0,0,0,0,0));

        rightEncoder = rightMotor.getEncoder();
        rightEncoder.setPosition(0);
        leftEncoder = leftMotor.getEncoder();
        leftEncoder.setPosition(0);

        rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setPosition(double ticks) {
       leftEncoder.setPosition(ticks);
       rightEncoder.setPosition(ticks);
    }
    public boolean isLimit() {
        return !Limit.get();
    }


    public void display() {
        SmartDashboard.putNumber("Arm Base Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Arm Base Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putBoolean("Arm Base Limit", Limit.get());
    }

    public RelativeEncoder getRightEncoder() {
        return rightEncoder;
    }

    public RelativeEncoder getLeftEncoder() {
        return leftEncoder;
    }

    public void collectionHubStance() {
        setPosition(50);
    }

    public void collectionFloorStance() {
        setPosition(10);
    }

    public void setSpeed(double speed) {
        SmartDashboard.putBoolean("auton limit", isLimit());
        if (isLimit()) return;
        MotorGroup.set(speed);
    }

    public void Up() {
        MotorGroup.set(ARM_BASE_SPEED);
    }
    public void Down() {
        if (!isLimit()) return;
        MotorGroup.set(-ARM_BASE_SPEED);
    }

    public void overrideDown() {
        MotorGroup.set(-OVERRIDE_ARM_BASE_SPEED);
    }

    public void stop() {
        MotorGroup.stopMotor();
    }
}
