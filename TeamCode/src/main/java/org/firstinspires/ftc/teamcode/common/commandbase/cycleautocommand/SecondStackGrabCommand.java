package org.firstinspires.ftc.teamcode.common.commandbase.cycleautocommand;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ArmFloatCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ArmLiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.PivotCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.PivotStateCommand;
import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Location;

public class SecondStackGrabCommand extends SequentialCommandGroup {
    public SecondStackGrabCommand() {
        super(
                new ArmFloatCommand(true),
                new EndEffectorCommand(IntakeSubsystem.ClawState.OPEN, Globals.ALLIANCE == Location.BLUE ? ClawSide.RIGHT : ClawSide.LEFT),
                new PivotStateCommand(IntakeSubsystem.PivotState.FLAT),
                new PivotCommand(0.48),
                new WaitCommand(250),
                new ExtensionCommand(600),
                new WaitCommand(500),
                new EndEffectorCommand(IntakeSubsystem.ClawState.CLOSED, Globals.ALLIANCE == Location.BLUE ? ClawSide.RIGHT : ClawSide.LEFT),
                new WaitCommand(250),
                new ArmLiftCommand(0.63),
                new PivotStateCommand(IntakeSubsystem.PivotState.STORED),
                new ExtensionCommand(75)
        );
    }
}
