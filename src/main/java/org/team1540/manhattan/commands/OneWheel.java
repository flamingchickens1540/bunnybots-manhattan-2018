package org.team1540.manhattan.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.manhattan.OI;
import org.team1540.manhattan.Robot;
import org.team1540.manhattan.subsystems.SwerveWheel;

public class OneWheel extends Command {

    private SwerveWheel wheel = Robot.currentWheel;

    public OneWheel() {
        for (SwerveWheel wheel : Robot.swerveWheels) {
            requires(wheel);
        }
//        requires(wheel);
    }

    @Override
    protected void execute() {
        wheel = Robot.currentWheel;

//        wheel.setSpeed(OI.getStrafeYAxis());
//        wheel.spin(OI.getTestRotateXAxis());
        for (SwerveWheel wheel : Robot.swerveWheels) {
            wheel.spin(OI.getTestRotateXAxis());
        }

//        double angle = Math.abs(OI.getTestRotateXAxis());
//        double angle = SmartDashboard.getNumber("setpoint1", 0);
//        SmartDashboard.putNumber("angle setpoint ", angle);
//        wheel.setTargetAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
