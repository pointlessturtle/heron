package org.firstinspires.ftc.teamcode.common.commandbase.preloadautocommand;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.PivotStateCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class YellowPixelRetractCommand extends SequentialCommandGroup {
    public YellowPixelRetractCommand() {
        super(
                new ExtensionCommand(0),
                new WaitCommand(50),
                new PivotStateCommand(IntakeSubsystem.PivotState.STORED),
                new EndEffectorCommand(IntakeSubsystem.ClawState.CLOSED, ClawSide.BOTH)
        );
    }
}