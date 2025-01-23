package org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.util.InverseKinematics;

public class IntakeCommand extends SequentialCommandGroup {
    public IntakeCommand(RobotHardware robot) {
        super(
                new InstantCommand(() -> robot.armActuator.setTargetPosition(InverseKinematics.t_angle)),
                new InstantCommand(() -> robot.extensionActuator.setTargetPosition(InverseKinematics.t_extension))
        );
    }
}
//    public IntakeCommand(RobotHardware robot, double target){
//        super(
//                new InstantCommand(() -> robot.extension.setExtension(target)),
//                new InstantCommand(() -> InverseKinematics.calculateTarget(robot.extension.getExtension())),
//                new ConditionalCommand(
//                        new IntakeCommand(robot),
//                        new InstantCommand(),
//                        () -> Globals.IS_INTAKING)
//
//        );
//    }