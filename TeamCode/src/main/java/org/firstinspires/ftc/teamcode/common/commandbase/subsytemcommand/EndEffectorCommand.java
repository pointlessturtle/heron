package org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class EndEffectorCommand extends InstantCommand {
    public EndEffectorCommand(IntakeSubsystem.EndEffectorState state) {
        super(
                () -> RobotHardware.getInstance().intake.updateState(state)
        );
    }
}
