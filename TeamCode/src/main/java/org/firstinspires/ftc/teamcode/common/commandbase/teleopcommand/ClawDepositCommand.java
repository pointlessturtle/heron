package org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.ClawSide;
import org.firstinspires.ftc.teamcode.common.commandbase.subsytemcommand.EndEffectorCommand;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class ClawDepositCommand extends SequentialCommandGroup {
    public ClawDepositCommand() {
        super(
                new ConditionalCommand(
                        new EndEffectorCommand(IntakeSubsystem.EndEffectorState.RELEASE),
                        new EndEffectorCommand(IntakeSubsystem.EndEffectorState.INTAKED),
                        () -> (RobotHardware.getInstance().intake.getEndEffectorState() == IntakeSubsystem.EndEffectorState.INTAKED)
                )
        );
    }
}
