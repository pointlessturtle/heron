package org.firstinspires.ftc.teamcode.common.util;

public class OuttakeKinematics {
    public static double t_extension = 0.0;
    public static double t_angle = 0.0;

    public static void calculateTarget(double height) {
        final double pivotHeight = .11;
        final double borderHeight = .052;
        final double offSet = .05;

        t_angle = 3 * Math.PI/2 + Math.atan((pivotHeight - height) / offSet);
        t_extension = Math.sqrt((height - pivotHeight) * (height - pivotHeight) + offSet * offSet);
    }
}
