package org.team1540.manhattan.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.manhattan.Robot;
import org.team1540.manhattan.subsystems.SwerveWheel;

public class InstantTest extends InstantCommand {

//    private SwerveWheel wheel = Robot.currentWheel;

    public InstantTest() {
//        wheel = Robot.currentWheel;
//        requires(wheel);
        for (SwerveWheel wheel : Robot.swerveWheels) {
            requires(wheel);
        }
    }

    @Override
    protected void initialize() {
//        wheel.setTargetAngle(SmartDashboard.getNumber("setpoint1", 0));
        for (SwerveWheel wheel : Robot.swerveWheels) {
            wheel.setTargetAngle(SmartDashboard.getNumber("setpoint1", 0));
        }
    }

}
