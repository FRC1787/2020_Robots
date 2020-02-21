package frc.robot;

import frc.lib.Utils;
import frc.lib.geometry.Pose2;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Twist2;
import frc.lib.util.DriveSignal;

public final class Kinematics {

    /* ROBOT KINEMATIC CONSTANTS */
    private final double ROBOT_MASS_KG = Double.NaN;

    private Kinematics() {
    }

    public static Twist2 forwardKinematics(double dLeft, double dRight) {
        double dTheta = (dRight - dLeft) / Constants.WHEEL_DISTANCE;
        return forwardKinematics(dLeft, dRight, dTheta);
    }

    public static Twist2 forwardKinematics(double dLeft, double dRight, double dTheta) {
        final double dx = (dLeft + dRight) / 2.0;
        return new Twist2(dx, 0.0, dTheta);
    }

    public static Twist2 forwardKinematics(Rotation2 previous, double dLeft, double dRight, Rotation2 current) {
        final double dx = (dLeft + dRight) / 2.0;
        final double dy = 0;
        return new Twist2(dx, dy, previous.inverse().rotate(current).getRadians());
    }

    public static DriveSignal inverseKinematics(Twist2 velocity) {
        if (Math.abs(velocity.deltaTheta()) < Utils.EPSILON) {
            return new DriveSignal(velocity.deltaX(), velocity.deltaY());
        }

        System.out.println(velocity);

        double deltaV = Constants.WHEEL_DISTANCE * velocity.deltaTheta() / (2 * 10); // TODO Factor
        return new DriveSignal(velocity.deltaX() - deltaV, velocity.deltaY() - deltaV);
    }

    public static Pose2 integrateForwardKinematics(Pose2 current, Twist2 kinematics) {
        return current.transform(Pose2.exp(kinematics));
    }

    //return theta in radians from distance and other variables for shooter calculations
    //inputs
    //d is Distance from the point
    //a is acceleration
    //v is the initial velocity
    //hR is the height of the robot from where we shoot
    //hT is the height of the goal we need to score at.
    public static double thetaFromDistance(double d, double a, double v, double hR, double hT){
        double element1;
        double element2;

        double a1 = (d*d + hR*hR - 2*hR*hT + hT*hT);
        double a2 = (-a*hR + a*hT + v*v);
        double a3 = (-1 * a*a * d*d - 2 * a * hR * v*v + 2 * a * hT * v*v + v*v*v*v);
        double a4 = Math.sqrt((a2 + Math.sqrt(a3)) / a1);

        element1 = (1/(2 * a1)) * ((a2 + Math.sqrt(a3))*(-2*hR + 2*hT)-a) / (a4 * v);
        element2 = ((a4 * d) / v);

        return Math.atan2(element1,element2);
    }
}
