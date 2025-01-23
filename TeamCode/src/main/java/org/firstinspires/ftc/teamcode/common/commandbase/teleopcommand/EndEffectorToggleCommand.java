package org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class EndEffectorToggleCommand extends ConditionalCommand {
        public EndEffectorToggleCommand(RobotHardware robot) {
            super(
                    new EndEffectorCommand(IntakeSubsystem.EndEffectorState.RELEASE),
                    new EndEffectorCommand(IntakeSubsystem.EndEffectorState.INTAKING),

                    () -> (robot.intake.getEndEffectorState() == (IntakeSubsystem.EndEffectorState.INTAKING))
            );
        }
}