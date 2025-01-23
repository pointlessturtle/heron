package org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.util.InverseKinematics;

public class IntakeExtendCommand extends SequentialCommandGroup {
    public IntakeExtendCommand(RobotHardware robot, double increment) {
        super(
                new InstantCommand(() -> robot.extension.incrementExtension(increment)),
                new InstantCommand(() -> InverseKinematics.calculateTarget(robot.extension.getExtension())),
                new ConditionalCommand(
                        new org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.IntakeCommand(robot),
                        new InstantCommand(),
                        () -> Globals.IS_INTAKING)

        );
    }
    public IntakeExtendCommand(double extension, RobotHardware robot){
        super(
                new InstantCommand(() -> robot.extension.setExtension(extension)),
                new InstantCommand(() -> InverseKinematics.calculateTarget(robot.extension.getExtension())),
                new ConditionalCommand(
                        new org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.IntakeCommand(robot),
                        new InstantCommand(),
                        () -> Globals.IS_INTAKING)

        );
    }
}
