package org.firstinspires.ftc.teamcode.common.commandbase.wallauto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ArmCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.PivotStateCommand;
import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Location;

public class StackDepositCommand extends SequentialCommandGroup {
    public StackDepositCommand(int distance){
        super(
                new ExtensionCommand(distance),
                new WaitCommand(250),
                new EndEffectorCommand(IntakeSubsystem.ClawState.INTERMEDIATE, Globals.ALLIANCE == Location.RED ? ClawSide.LEFT : ClawSide.RIGHT),
                new WaitCommand(250),
                new EndEffectorCommand(IntakeSubsystem.ClawState.OPEN, Globals.ALLIANCE == Location.RED ? ClawSide.LEFT : ClawSide.RIGHT),
                new WaitCommand(250),
                new EndEffectorCommand(IntakeSubsystem.ClawState.CLOSED, ClawSide.BOTH),
                new ArmCommand(0.2),
                new ExtensionCommand(0),
                new WaitCommand(100),
                new PivotStateCommand(IntakeSubsystem.PivotState.STORED)
        );
    }
}
