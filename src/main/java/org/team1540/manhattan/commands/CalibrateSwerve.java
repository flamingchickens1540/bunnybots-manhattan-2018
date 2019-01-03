package org.team1540.manhattan.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1540.manhattan.Robot;
import org.team1540.manhattan.subsystems.SwerveWheel;

public class CalibrateSwerve extends Command {

//    private SwerveWheel wheel = Robot.currentWheel;

    public CalibrateSwerve() {
        for (SwerveWheel wheel : Robot.swerveWheels) {
            requires(wheel);
        }
//        requires(wheel);
    }

    @Override
    protected void initialize() {
//        Robot.swerveA.setAngleEnc(4000);
//        Robot.swerveA.setTargetAngle(1000);
//        System.out.println("test");
        for (SwerveWheel wheel : Robot.swerveWheels) {
            wheel.isCalibrated = false;
        }
//        wheel.isCalibrated = false;
        System.out.println("CALIBRATING--------------------------------");
    }

    @Override
    protected void execute() {
//        wheel = Robot.currentWheel;

        for (SwerveWheel wheel : Robot.swerveWheels) {
            wheel.spin(0.8);
        }
//        wheel.spin(0.8);
    }

    @Override
    protected void end() {
        for (SwerveWheel wheel : Robot.swerveWheels) {
            wheel.spin(0);
        }
//        wheel.spin(0);
        System.out.println("CALIBRATED--------------------------------");
    }

    @Override
    protected boolean isFinished() {
        for (SwerveWheel wheel : Robot.swerveWheels) {
            if (!wheel.isCalibrated) {
                return false;
            }
        }
        return true;
//        return wheel.isCalibrated;
    }

}
