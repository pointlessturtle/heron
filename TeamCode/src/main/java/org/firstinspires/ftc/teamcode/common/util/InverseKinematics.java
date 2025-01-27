package org.firstinspires.ftc.teamcode.common.util;

import static java.lang.Math.toRadians;

import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;

public class InverseKinematics {
    public static double t_extension = 0.0;
    public static double t_angle = 0.0;

    public static void calculateTarget(double target_extension) {
        final double pivotHeight = .11;
        final double borderHeight = .052;
        final double offSet = .02;
        target_extension = Math.min(target_extension, ExtensionSubsystem.max_extension - .06);

        t_extension = MathUtils.clamp(Math.sqrt((pivotHeight - borderHeight) * (pivotHeight - borderHeight) + target_extension * target_extension) - .31, 0, .75);
        t_angle = MathUtils.clamp(Math.PI / 2 + Math.atan((borderHeight + offSet - pivotHeight) / target_extension), toRadians(75), toRadians(90));
    }
}