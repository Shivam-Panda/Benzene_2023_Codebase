package team4384.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4384.robot.component.BbSparkMax;

import static team4384.robot.constants.RobotMap.*;

public class BbIntakeTurner {
    private final BbSparkMax TurnMotor = new BbSparkMax(INTAKE_TURNER);
    private final DigitalInput Limit = new DigitalInput(INTAKE_TURNER_LIMIT_SWITCH);
    private Joystick joystick;

    public BbIntakeTurner() {
        TurnMotor.setInverted(true);

        TurnMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void display() {
        SmartDashboard.putBoolean("Tuner Limit", Limit.get());
    }
    public boolean isLimit() {
        return !Limit.get();
    }

    public void Down() {
        TurnMotor.set(INTAKE_TURN_SPEED);
    }

    public void setSpeed(double speed) {
        if (isLimit()) return;
        TurnMotor.set(speed);
    }

    public void Up() {
        // FIXME add Encoders
        TurnMotor.set(-INTAKE_TURN_SPEED);
    }

    public void stopMotor() {
        TurnMotor.stopMotor();
    }
}
