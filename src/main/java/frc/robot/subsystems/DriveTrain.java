package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.can.BetterSpark;
import frc.lib.scheduling.Loop;
import frc.lib.scheduling.Scheduler;
import frc.lib.subsystem.CommandSubsystem;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;
import lombok.Getter;

public final class DriveTrain extends CommandSubsystem {

    // Signal drive train instance
    private static DriveTrain instance;

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }

        return instance;
    }

    private final IO io;
    private final BetterSpark primaryRight, primaryLeft, secondaryRight, secondaryLeft;
    private final CANEncoder rightEncoder, leftEncoder;

    public DriveTrain() {
        super("Drive");

        // Initialize IO handler
        this.io = new IO();

        // Initialize motor controllers
        this.primaryRight = new BetterSpark(Constants.FR_CH);
        this.primaryLeft = new BetterSpark(Constants.FL_CH);
        this.secondaryRight = new BetterSpark(Constants.BR_CH);
        this.secondaryLeft = new BetterSpark(Constants.BL_CH);

        // Configure Sparks
        configureSparks();

        // Initialize encoders
        rightEncoder = primaryRight.getEncoder();
        leftEncoder = primaryLeft.getEncoder();
    }

    private void configureSparks() {
        // Invert right side
        primaryRight.setInverted(true);
        primaryLeft.setInverted(false);
        secondaryRight.setInverted(true);
        secondaryLeft.setInverted(false);

        // Apply current limit
        primaryRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        primaryLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        secondaryRight.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        secondaryLeft.setSmartCurrentLimit(Constants.CURRENT_LIMIT);

        primaryLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        primaryRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        secondaryRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        secondaryLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    private void configureEncoders() {
        // TODO determine encoder inversion
        rightEncoder.setInverted(false);
        leftEncoder.setInverted(false);

        rightEncoder.setVelocityConversionFactor(1.0);
        leftEncoder.setVelocityConversionFactor(1.0);
        rightEncoder.setPositionConversionFactor(1.0);
        leftEncoder.setPositionConversionFactor(1.0);

        // reset encoder positions
        rightEncoder.setPosition(0.0);
        leftEncoder.setPosition(0.0);
    }

    public void sendSignal(DriveSignal signal) {
        io.rightOutput = signal.getRight();
        io.leftOutput = signal.getLeft();
    }

    @Override
    public void check() {
        primaryRight.set(0);
        primaryLeft.set(0);
        secondaryRight.set(0);
        secondaryLeft.set(0);
    }

    public void reset() {
        // reset encoder positions
        rightEncoder.setPosition(0.0);
        leftEncoder.setPosition(0.0);
    }

    @Override
    public void readIO() {
        // Update encoder position values
        io.rightPosition = rightEncoder.getPosition();
        io.leftPosition = leftEncoder.getPosition();

        // Update encoder velocity values
        io.rightVelocity = rightEncoder.getVelocity();
        io.leftVelocity = leftEncoder.getVelocity();

        // MAKE SURE TICKS PER REVOLUTION IS CORRECT
    }

    @Override
    public void writeIO() {
        // Write motor values
        primaryRight.set(ControlType.kDutyCycle, io.rightOutput);
        secondaryRight.set(ControlType.kDutyCycle, io.rightOutput);
        primaryLeft.set(ControlType.kDutyCycle, io.leftOutput);
        secondaryLeft.set(ControlType.kDutyCycle, io.leftOutput);

        primaryLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        secondaryLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        primaryRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        secondaryRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("rightOutput", io.rightOutput);
        SmartDashboard.putNumber("leftOutput", io.leftOutput);
        SmartDashboard.putNumber("rightPosition", io.rightPosition);
        SmartDashboard.putNumber("leftPosition", io.leftPosition);
        SmartDashboard.putNumber("rightDistance", getRightDistance());
        SmartDashboard.putNumber("leftDistance", getLeftDistance());
        SmartDashboard.putNumber("rightIPS", getRightVelocity());
        SmartDashboard.putNumber("leftIPS", getLeftVelocity());
        SmartDashboard.putNumber("rightVelocity", io.rightVelocity);
        SmartDashboard.putNumber("leftVelocity", io.leftVelocity);
    }

    public double getRightVelocity() {
        return getRightRPS() * Constants.wheelDiameterA * Math.PI;
    }

    public double getLeftVelocity() {
        return getLeftRPS() * Constants.wheelDiameterA * Math.PI;
    }

    public double getLeftRPS() {
        return io.leftVelocity * Constants.VTR;
    }

    public double getRightRPS() {
        return io.rightVelocity * Constants.VTR;
    }

    public double getRightDistance() {
        return io.rightPosition * Constants.IPT;
    }

    public double getLeftDistance() {
        return io.leftPosition * Constants.IPT;
    }

    public double getLinearVelocity() {
        return (getRightVelocity() + getLeftVelocity()) / 2.0;
    }

    @Override
    public void registerLoops(Scheduler scheduler) {
        scheduler.register(new DriveLoop());
    }

    public IO getIO() {
        return this.io;
    }

    private final class DriveLoop implements Loop {

        @Override
        public void onStart(double timestamp) {
            leftEncoder.setPosition(0);
            rightEncoder.setPosition(0);
        }

        @Override
        public void tick(double timestamp) {
//            io.rightOutput = 0.1;
//            io.leftOutput = 0.1;
        }

        @Override
        public void onStop(double timestamp) {
            leftEncoder.setPosition(0);
            rightEncoder.setPosition(0);
            RobotTracker.getInstance().reset();
        }
    }

    @Getter public static class IO {
        private double timestamp;
        private double rightOutput, leftOutput;
        private double rightPosition, leftPosition;
        private double rightVelocity, leftVelocity;
        private int tpr;
    }
}
