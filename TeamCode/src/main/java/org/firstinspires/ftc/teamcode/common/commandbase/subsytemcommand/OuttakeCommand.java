package org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.util.OuttakeKinematics;

public class OuttakeCommand extends SequentialCommandGroup {
    public OuttakeCommand(RobotHardware robot) {
        super(
                new InstantCommand(() -> robot.armActuator.setTargetPosition(OuttakeKinematics.t_angle)),
                new InstantCommand(() -> robot.extensionActuator.setTargetPosition(OuttakeKinematics.t_extension))
        );
    }
}
