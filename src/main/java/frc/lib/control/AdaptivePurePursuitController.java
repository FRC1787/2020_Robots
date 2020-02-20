package frc.lib.control;

import lombok.Getter;

public class AdaptivePurePursuitController {

//    @Getter private final Lookahead lookahead;

    public AdaptivePurePursuitController() {}


    public static final class Lookahead {
        @Getter private final double minDistance, maxDistance;
        @Getter private final double minSpeed, maxSpeed;

        @Getter private final double deltaDistance, deltaSpeed;

        public Lookahead(double minDistance, double maxDistance, double minSpeed, double maxSpeed) {
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.minSpeed = minSpeed;
            this.maxSpeed = maxSpeed;
            deltaDistance = maxDistance - minDistance;
            deltaSpeed = maxSpeed - minSpeed;
        }

        public double getLookaheadBySpeed(double speed) {
            double lookahead = deltaDistance * (speed - minSpeed) / deltaSpeed + minDistance;
            return Double.isNaN(lookahead) ? minDistance : Math.max(minDistance, Math.min(maxDistance, lookahead));
        }
    }
}
