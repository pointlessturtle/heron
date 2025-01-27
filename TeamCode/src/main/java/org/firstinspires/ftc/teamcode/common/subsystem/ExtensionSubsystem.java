package org.firstinspires.ftc.teamcode.common.subsystem;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.hardware.Sensors;
import org.firstinspires.ftc.teamcode.common.util.MathUtils;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

@Config
public class ExtensionSubsystem extends WSubsystem {

    private final RobotHardware robot = RobotHardware.getInstance();
    private double intakeExtension = 0;
    public IntSupplier liftTicks;
    public DoubleSupplier armAngle;
    private final double ticks_per_meter = 1142.65087348;
    public static final double inchesToMeters = 0.0254;
    public static final double max_extension = 40 * inchesToMeters;

    public double feedforward = 0.0;

    public ExtensionSubsystem() {
        this.liftTicks = () -> robot.intSubscriber(Sensors.SensorType.EXTENSION_ENCODER);
        this.armAngle = () -> robot.doubleSubscriber(Sensors.SensorType.ARM_ENCODER);
    }

    @Override
    public void periodic() {
        double kG = 0.18;
        double dist_cog_cor_max = 10.161;
        double arm_reference_angle = robot.armActuator.getTargetPosition();

        double offset = -(robot.armActuator.getPosition() / Math.PI) * 50;
        double extension = (liftTicks.getAsInt() + offset) / 26.0;

        double m_1 = 1.07;
        double m_2 = 0.36;
        double m_3 = 0.16;
        double m_4 = 0.44;

        double length_r = 1.3;
        double length_last = 6.0;

        double dist_cog = (m_2 * (extension + 1) + m_3 * (2 * extension - 1) + m_4 * (2 * extension + 0.5 * length_last)) / (m_1 + m_2 + m_3 + m_4);
        double dist_cog_cor = Math.sqrt(Math.pow(dist_cog, 2) + Math.pow(length_r, 2));

        double dist_ratio = dist_cog_cor / dist_cog_cor_max;

        feedforward = kG * Math.cos(arm_reference_angle) * dist_ratio;

        robot.armActuator.updateFeedforward(feedforward);
        robot.extensionActuator.setOffset(-(robot.armActuator.getPosition() / Math.PI) * 50);

        robot.armActuator.setCurrentPosition(armAngle.getAsDouble());
        robot.extensionActuator.setCurrentPosition(liftTicks.getAsInt());

        double error = robot.extensionActuator.getOverallTargetPosition() - robot.extensionActuator.getPosition();
        double feedforward = 0.1 * Math.abs(Math.cos(robot.armActuator.getPosition())) * Math.signum(error);


//        robot.extensionActuator.updateFeedforward(Math.abs(error) > 10 ? feedforward : 0);

        robot.armActuator.periodic();
        robot.extensionActuator.periodic();
    }

    @Override
    public void read() {

    }

    @Override
    public void write() {
        robot.armActuator.write();
        robot.extensionActuator.write();
    }

    @Override
    public void reset() {

    }

    public double getExtension() {
        return (intakeExtension * ticks_per_meter);
    }

    public void setExtension(double extension) {
        this.intakeExtension = extension;
    }

    public void incrementExtension(double amount) {
        this.intakeExtension = MathUtils.clamp(getExtension() + amount, 0, .2);
    }
}
