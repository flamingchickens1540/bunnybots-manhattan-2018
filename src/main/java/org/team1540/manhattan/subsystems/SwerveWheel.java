package org.team1540.manhattan.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.manhattan.Robot;
import org.team1540.rooster.wrappers.ChickenTalon;

public class SwerveWheel extends Subsystem {

    public final String name;
    public boolean disabled = false;

    private ChickenTalon angleTalon;
    private ChickenTalon speedTalon;
    private DigitalInput magnetSensor;

    private final boolean backwards;

    public double total = 0;
    public double total2 = 0;

    private static int angleCountsPerRev = 7958;
    // A
    private static int angleFudgeFactor = (int) (angleCountsPerRev * 0.05);

    private static final int wiggleRoom = 20;
    private double encCheckpoint = -1000;

    public boolean isCalibrated = false;
    private boolean prevMagnetState = false;
    private double prevAngle = 0;

    private boolean pidAngle = false;

    private int realSetpoint = 0;

    public SwerveWheel(String name, int angleNum, int speedNum, int magnetNum, boolean backwards) {
        this.name = name;
        angleTalon = new ChickenTalon(angleNum);
        speedTalon = new ChickenTalon(speedNum);

        magnetSensor = new DigitalInput(magnetNum);
        this.backwards = !backwards;

//        angleTalon.setInverted(true);
        angleTalon.setBrake(true);
        angleTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        angleTalon.setSensorPhase(true);

        angleTalon.configMotionAcceleration(2000);
        angleTalon.configMotionCruiseVelocity(800);
        angleTalon.configPeakOutputForward(1);
        angleTalon.configPeakOutputReverse(-1);

        angleTalon.config_kP(0, 2);
        angleTalon.config_kI(0, 0);
        angleTalon.config_kD(0, 15);
        angleTalon.config_kF(0, 0.45874);
//        angleTalon.config_kP(0, 8);
//        angleTalon.config_kI(0, 0);
//        angleTalon.config_kD(0, 10);
//        angleTalon.config_kF(0, 0.45874);

//        speedTalon.setControlMode(ControlMode.PercentOutput);
//        angleTalon.setControlMode(ControlMode.PercentOutput);
//        angleTalon.setControlMode(ControlMode.Position);

//        angleTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
//        speedTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    public void drive(double speed, double angle) {

//        System.out.println("A:   speed: " + speed + "   angle: " + angle);

//        SmartDashboard.putNumber("Angle Input", angle);

        setTargetAngle(angle);
        /*
        These will be in a range of -1 to 1, if you wish to turn this range into real degrees then simply multiply by 180.
        figure out which direction to rotate the wheel
         */

        setSpeed(speed);
    }

    @Override
    public void periodic() {

        if (disabled) {
            isCalibrated = true;
        }

        if (getMagnetState()) {
            System.out.println(name + " magnet true");
        }

//        SmartDashboard.putNumber(name + " total ", total);
////        SmartDashboard.putNumber(name + " total2", total2);
//
//        if (getMagnetState() && !prevMagnetState) {
//            setAngleEnc(0);
//        }
//
//        if (!getMagnetState() && getAngleEnc() > total) {
//            total = getAngleEnc();
//        }
//        if (getMagnetState() && getAngleEnc() > total2) {
//            total2 = getAngleEnc();
//        }

//        SmartDashboard.putNumber("Angle Enc", getAngleEnc());
//        SmartDashboard.putBoolean("Magnet State", getMagnetState());
//        SmartDashboard.putNumber("enc velocity", angleTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber(name + " current ", angleTalon.getOutputCurrent());

//         reset the encoder whenever it crosses the magnet sensor
        if (getMagnetState() && !prevMagnetState && getAngleEnc() - encCheckpoint > 0) {
//            System.out.println("1");
            setAngleEnc(0);
            if (pidAngle) {
                setTargetPos(realSetpoint);
            }
            isCalibrated = true;
        }
        else if (!getMagnetState() && prevMagnetState && getAngleEnc() - encCheckpoint < 0) {
//            System.out.println("2");
            setAngleEnc(angleCountsPerRev);
            if (pidAngle) {
                setTargetPos(realSetpoint);
            }
            isCalibrated = true;
        }

        if (Math.abs(getAngleEnc() - encCheckpoint) >= wiggleRoom) {
            encCheckpoint = getAngleEnc();
        }
//
        prevMagnetState = getMagnetState();
        prevAngle = getAngleEnc();

        // backup encoder resetting
        if (getAngleEnc() > angleCountsPerRev + angleFudgeFactor) {
//            System.out.println("3");
            setAngleEnc(angleFudgeFactor);
            if (pidAngle) {
                setTargetPos(realSetpoint);
            }
        }
        else if (getAngleEnc() < 0 - angleFudgeFactor) {
//            System.out.println("4");
            setAngleEnc(angleCountsPerRev - angleFudgeFactor);
            if (pidAngle) {
                setTargetPos(realSetpoint);
            }
        }

    }

    public double getAngleEnc() {
        return angleTalon.getSelectedSensorPosition() ;// angleCountsPerRev;
    }

    public double getEncSpeed() {
        return speedTalon.getSelectedSensorVelocity();
    }

    private void setAngleEnc(int angle) {
        angleTalon.setSelectedSensorPosition(angle);
    }

    public boolean getMagnetState() {
        return magnetSensor.get();
    }

    public void setTargetAngle(double angle) {
        pidAngle = true;
//        System.out.println("set target angle");
        int targetPos = (int) (angle * angleCountsPerRev);

        double currentAngle = getAngleEnc();

        double tempSetpoint = 0;
        if (Math.abs(targetPos - currentAngle) < angleCountsPerRev / 2) {
            tempSetpoint = targetPos;
        }
        else if (targetPos > currentAngle) {
            tempSetpoint = -(angleCountsPerRev - targetPos);
            realSetpoint = targetPos;
        }
        else {
            tempSetpoint = angleCountsPerRev + targetPos;
            realSetpoint = targetPos;
        }

        //TODO: be really smart and see if halfway across is closer, then go there and negate wheel speed

        setTargetPos(tempSetpoint);
    }

    private void setTargetPos(double angle) {
        angleTalon.set(ControlMode.MotionMagic, angle);
    }

    public void spin(double speed) {
//        System.out.println("spin " + speed);
        angleTalon.set(ControlMode.PercentOutput, speed);
        pidAngle = false;
    }

    public void setSpeed(double speed) {
//        if (backwards) speed *= -1;
        speedTalon.set(ControlMode.PercentOutput, speed);
    }



    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(Robot.swerveDriveCommand);
    }

}
