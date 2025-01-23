package org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.util.OuttakeKinematics;

public class DepositCommand extends SequentialCommandGroup {
    public DepositCommand(RobotHardware robot, double height) {
        super(
                new InstantCommand(() -> OuttakeKinematics.calculateTarget(height)),
                new InstantCommand(),
                new ConditionalCommand(
                        new OuttakeCommand(robot),
                        new InstantCommand(),
                        () -> Globals.IS_SCORING)
        );
    }
}
