package org.firstinspires.ftc.teamcode.common.commandbase.cycleautocommand;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ArmCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ArmLiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.PivotStateCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class DepositRetractionCommand extends SequentialCommandGroup {
    public DepositRetractionCommand() {
        super(
                new ExtensionCommand(0),
                new WaitCommand(50),
                new ArmCommand(0.1),
                new ArmLiftCommand(0.3),
                new EndEffectorCommand(IntakeSubsystem.ClawState.CLOSED, ClawSide.BOTH),
                new PivotStateCommand(IntakeSubsystem.PivotState.STORED)
        );
    }
}
