package org.firstinspires.ftc.teamcode.common.commandbase.cycleautocommand;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.hardware.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Location;

public class FirstDepositCommand extends SequentialCommandGroup {
    public FirstDepositCommand() {
        super(
                new EndEffectorCommand(IntakeSubsystem.ClawState.INTERMEDIATE, (Globals.ALLIANCE == Location.BLUE ? ClawSide.LEFT : ClawSide.RIGHT)),
                new WaitCommand(250),
                new EndEffectorCommand(IntakeSubsystem.ClawState.OPEN, (Globals.ALLIANCE == Location.BLUE ? ClawSide.LEFT : ClawSide.RIGHT)),
                new WaitCommand(250)

        );
    }
}
