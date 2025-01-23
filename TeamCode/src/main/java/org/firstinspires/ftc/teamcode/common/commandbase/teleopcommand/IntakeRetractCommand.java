package org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.IntakeExtendCommand;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;

public class IntakeRetractCommand extends SequentialCommandGroup {
    public IntakeRetractCommand(RobotHardware robot) {
        super(
                new IntakeExtendCommand(0, robot),
                new
        );
    }
}
