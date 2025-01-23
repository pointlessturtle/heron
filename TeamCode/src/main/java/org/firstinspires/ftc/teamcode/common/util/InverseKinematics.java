package org.firstinspires.ftc.teamcode.common.util;

public class InverseKinematics {
    public static double t_extension = 0.0;
    public static double t_angle = 0.0;

    public static void calculateTarget(double target_extension) {
        t_extension = target_extension;
        final double pivotHeight = .11;
        final double borderHeight = .052;
        final double offSet = .02;

        t_angle = Math.PI/2 + Math.atan((borderHeight + offSet - pivotHeight) / t_extension);
    }
}
