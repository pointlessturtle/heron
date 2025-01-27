package org.firstinspires.ftc.teamcode.common.util;

import static org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem.*;
import static java.lang.Math.toRadians;

import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;

public class OuttakeKinematics {
    public static double t_extension = 0.0;
    public static double t_angle = 0.0;

    public static void calculateTarget(double height) {
        final double pivotHeight = .11;
        final double offSet = 40 * inchesToMeters - max_extension - .34;

        t_angle = Math.min(3 * Math.PI / 2 + Math.atan((pivotHeight - height) / offSet), toRadians(180));
        t_extension = MathUtils.clamp(Math.sqrt((height - pivotHeight) * (height - pivotHeight) + offSet * offSet) - .31, 0, .75);
    }
}
