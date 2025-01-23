//
//package org.firstinspires.ftc.teamcode.common.commandbase.teleopcommand;
//
//import com.arcrobotics.ftclib.command.ConditionalCommand;
//import com.arcrobotics.ftclib.command.InstantCommand;
//import com.arcrobotics.ftclib.command.Robot;
//import com.arcrobotics.ftclib.command.SequentialCommandGroup;
//import com.arcrobotics.ftclib.command.WaitCommand;
//
//import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
//import org.firstinspires.ftc.teamcode.common.subsystem.HangSubsystem;
//
//public class ActuateHangCommand extends SequentialCommandGroup {
//    public ActuateHangCommand(double value) {
//        super(
//                new InstantCommand(() -> RobotHardware.getInstance().ptoServo.setPosition(0)),
//                new InstantCommand(() -> RobotHardware.)
//        );
//    }
//}
