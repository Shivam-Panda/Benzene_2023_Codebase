package team4384.robot.component;

import com.revrobotics.CANSparkMax;
import team4384.robot.constants.PidConfig;

/**
 * Benzene Bot representation for Rev SPARK MAX
 */
public class BbSparkMax extends CANSparkMax {

    public BbSparkMax(int deviceId) {
        super(deviceId, MotorType.kBrushless);
        this.restoreFactoryDefaults();
    }

    /**
     * Configure PID values
     *
     * @param pidConfig PID Configuration object with desired values
     */
    public void configurePid(PidConfig pidConfig) {
        getPIDController().setP(pidConfig.p);
        getPIDController().setI(pidConfig.i);
        getPIDController().setD(pidConfig.d);
        getPIDController().setFF(pidConfig.ff);
        getPIDController().setIZone(pidConfig.iZone);
        getPIDController().setOutputRange(-1, 1);
    }

    /**
     * Set PID to be activated
     *
     * @param value value of unit depending on control type
     * @param ctrl  Control Type (Position, Velocity etc.)
     */
    public void setPidReference(double value, ControlType ctrl) {
        getPIDController().setReference(value, ctrl);
    }

}