package org.team1540.manhattan.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.manhattan.OI;
import org.team1540.manhattan.Robot;
import org.team1540.manhattan.subsystems.SwerveWheel;

public class SwerveDrive extends Command {

    //TODO: distance between each wheel axle on the length and width (what units though)
    public final double L = 20;
    public final double W = L;

    public SwerveDrive() {
        for (SwerveWheel wheel : Robot.swerveWheels) {
            requires(wheel);
        }
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("strafe X ", OI.getStrafeXAxis());
        SmartDashboard.putNumber("strafe Y ", OI.getStrafeYAxis());
        SmartDashboard.putNumber("rotate ", OI.getRotateXAxis());
//        System.out.println("strafe: " + OI.getStrafeXAxis() + ", " + OI.getStrafeYAxis() + "   rotate: " + OI.getRotateXAxis());
        swerve(OI.getStrafeXAxis(), OI.getStrafeYAxis(), OI.getRotateXAxis());
    }

    public void swerve(double strafeX, double strafeY, double rotateX) {

//        //take 2
//        double sideToRadiusRatio = L / Math.sqrt((L * L) + (W * W));
//        rotateX = 0;
//
//        double xPos = strafeX + rotateX * sideToRadiusRatio;
//        double xNeg = strafeX - rotateX * sideToRadiusRatio;
//        double yPos = strafeY + rotateX * sideToRadiusRatio;
//        double yNeg = strafeY - rotateX * sideToRadiusRatio;
//
//        double fls = Math.atan2(xPos, yPos);
//        double frs = Math.atan2(xPos, yNeg);
//        double bls = Math.atan2(xNeg, yPos);
//        double brs = Math.atan2(xNeg, yNeg);

        //take 1
        double r = Math.sqrt((L * L) + (W * W));
//        strafeY *= -1;

        double a = strafeX - rotateX * (L / r);
        double b = strafeX + rotateX * (L / r);
        double c = strafeY - rotateX * (W / r);
        double d = strafeY + rotateX * (W / r);

        double backRightSpeed = Math.sqrt((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

//        double backRightAngle = Math.atan2(a, d) / Math.PI / 2 + 0.5;
//        double backLeftAngle = Math.atan2(a, c) / Math.PI / 2 + 0.5;
//        double frontRightAngle = Math.atan2(b, d) / Math.PI / 2 + 0.5;
//        double frontLeftAngle = Math.atan2(b, c) / Math.PI / 2 + 0.5;

        double frontRightAngle = Math.atan2(a, d) / Math.PI / 2 + 0.5;
        double frontLeftAngle = Math.atan2(a, c) / Math.PI / 2 + 0.5;
        double backRightAngle = Math.atan2(b, d) / Math.PI / 2 + 0.5;
        double backLeftAngle = Math.atan2(b, c) / Math.PI / 2 + 0.5;

        SmartDashboard.putNumber("angle A ", backRightAngle);
        SmartDashboard.putNumber("angle B ", frontRightAngle);
        SmartDashboard.putNumber("angle C ", backLeftAngle);
        SmartDashboard.putNumber("angle D ", frontLeftAngle);

        if (strafeX == 0 && strafeY == 0 && rotateX == 0) {
            for (SwerveWheel wheel : Robot.swerveWheels) {
                wheel.spin(0);
                wheel.setSpeed(0);
            }
        }
        else {
            Robot.swerveA.drive(backRightSpeed, backRightAngle);
            Robot.swerveB.drive(frontRightSpeed, frontRightAngle);
            Robot.swerveC.drive(backLeftSpeed, backLeftAngle);
            Robot.swerveD.drive(frontLeftSpeed, frontLeftAngle);
        }

    }

    //TODO: Have an "emergency" tank drive mode that can be used if rotation fails or if you simply want to drive tank style

    @Override
    protected boolean isFinished() {
        return false;
    }

}
