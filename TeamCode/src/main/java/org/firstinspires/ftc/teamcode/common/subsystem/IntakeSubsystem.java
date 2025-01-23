package org.firstinspires.ftc.teamcode.common.subsystem;

import android.widget.Switch;

import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.util.InverseKinematics;
import org.firstinspires.ftc.teamcode.common.util.MathUtils;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WSubsystem;
import org.jetbrains.annotations.NotNull;

public class IntakeSubsystem extends WSubsystem {

    private final RobotHardware robot;

    public EndEffectorState eeState = EndEffectorState.INTAKED;

    public EEPivotState pivotState = EEPivotState.FLOAT;

    public enum EndEffectorState {
        INTAKED,
        INTAKING,
        REST,
        RELEASE,
        AUTO,
    }

    public enum EEPivotState {
        FLOAT,
        INTAKE,
        DEPO
    }

    public IntakeSubsystem() {
        this.robot = RobotHardware.getInstance();
        updateState(EndEffectorState.INTAKED);
    }

    public void updateState(@NotNull EndEffectorState state) {
        robot.intakeContinousLeftServo.setPower(getEEStatePower(state));
        this.eeState = state;
    }

    @Override
    public void periodic() {
        double pos = robot.armActuator.getOverallTargetPosition();
        if (eeState == EndEffectorState.INTAKING) {
            if (pivotState == EEPivotState.FLOAT){
                robot.intakePivotActuator.setTargetPosition(.5);
            }
            else{
                robot.intakePivotActuator.setTargetPosition(0);
            }
        }
        else if (pivotState ==  EEPivotState.DEPO) {
            robot.intakePivotActuator.setTargetPosition(1);
        }
    }

    @Override
    public void read() {

    }

    @Override
    public void write() {
        robot.intakePivotActuator.write();
    }

    @Override
    public void reset() {
        updateState(EndEffectorState.INTAKING);
    }

    private double getEEStatePower(EndEffectorState state) {
        switch (state) {
            case INTAKED:
                return 0.52;
            case INTAKING:
                return 1;
            case RELEASE:
                return -1;
            default:
                return 0;
        }
    }


    public EndEffectorState getEndEffectorState() {
        return eeState;
    }
}
