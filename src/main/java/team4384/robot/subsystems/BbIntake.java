package team4384.robot.subsystems;

import com.revrobotics.CANSparkMax;
import team4384.robot.component.BbSparkMax;

import static team4384.robot.constants.RobotMap.*;

public class BbIntake {
    private final BbSparkMax IntakeMotor = new BbSparkMax(INTAKE);

    public BbIntake() {
        IntakeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void suckCube() {
        IntakeMotor.set(CUBE_SPEED);
    }

    public void spitCube(double mul) {
        IntakeMotor.set(-mul);
    }
    public void spitCube() {
        IntakeMotor.set(-CUBE_SPEED);
    }

    public void suckCone() {
        IntakeMotor.set(-CONE_SPEED);
    }

    public void spitCone() {
        IntakeMotor.set(CONE_SPEED);
    }

    public void stop() {
        IntakeMotor.stopMotor();
    }
}
