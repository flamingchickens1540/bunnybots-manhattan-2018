package org.team1540.manhattan;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1540.manhattan.commands.*;
import org.team1540.manhattan.subsystems.CubeYeeter;
import org.team1540.manhattan.subsystems.SwerveWheel;
import org.team1540.rooster.util.SimpleCommand;

public class Robot extends IterativeRobot {

    public static CubeYeeter yeeterLeft = new CubeYeeter(2);
    public static CubeYeeter yeeterRight = new CubeYeeter(3);

    /*

    D-----B     ^ front
    |     |
    |     |
    C-----A

     */

    public static SwerveWheel swerveA = new SwerveWheel("A", RobotMap.angleA, RobotMap.wheelA, RobotMap.magnetA, true); // back R
    public static SwerveWheel swerveB = new SwerveWheel("B", RobotMap.angleB, RobotMap.wheelB, RobotMap.magnetB, false); // front R
    public static SwerveWheel swerveC = new SwerveWheel("C", RobotMap.angleC, RobotMap.wheelC, RobotMap.magnetC, true); // back L
    public static SwerveWheel swerveD = new SwerveWheel("D", RobotMap.angleD, RobotMap.wheelD, RobotMap.magnetD, false); // front L

    public static SwerveWheel[] swerveWheels = new SwerveWheel[] {swerveA, swerveB, swerveC, swerveD};

    public static SwerveWheel currentWheel = swerveA;

    public static Command swerveDriveCommand = new SwerveDrive();
    private static Command testCommand = new OneWheel();
//    private static Command calibrateCommand = new CalibrateSwerve();

    private static SendableChooser<SwerveWheel> wheelChooser;

    private static DigitalInput testMagent = new DigitalInput(9);

    @Override
    public void robotInit() {

        wheelChooser = new SendableChooser<>();
        for (SwerveWheel wheel : swerveWheels) {
            wheelChooser.addObject(wheel.name, wheel);
        }
//        wheelChooser.addDefault(swerveA.name, swerveA);
//        wheelChooser.addObject(swerveB.name, swerveB);
//        wheelChooser.addObject(swerveC.name, swerveC);
//        wheelChooser.addObject(swerveD.name, swerveD);
        SmartDashboard.putData("Wheel Chooser", wheelChooser);

//        OI.calibrateButton.whenPressed(calibrateCommand);
//        OI.stopCalibrationButton.whenPressed(new SimpleCommand("stop calibration", calibrateCommand::cancel));
//        OI.testbutton.whenPressed(new InstantTest());
//        OI.testbutton.whenPressed(new SimpleCommand("start one wheel code", testCommand::start));

        OI.leftYeetButton.whenPressed(new YeetCube(yeeterLeft));
        OI.rightYeetButton.whenPressed(new YeetCube(yeeterRight));
//        OI.leftYeetButton.whenPressed(new SimpleCommand("left yeet", yeeterLeft::yeet) {
//        });
//        OI.rightYeetButton.whenPressed(new SimpleCommand("right yeet", yeeterLeft::unYeet) {
//        });

        SmartDashboard.putNumber("setpoint1", 0);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {

//        currentWheel = wheelChooser.getSelected();

//        testCommand.start();
//        currentWheel.total = 0;
//        for (SwerveWheel wheel : swerveWheels) {
//            wheel.total = 0;
//            wheel.total2 = 0;
//        }
//        swerveDriveCommand.start();
//        calibrateCommand.start();
        new CalibrateSwerve().start();

//        Robot.swerveC.disabled = true;
    }

    @Override
    public void testInit() {

    }

    @Override
    public void robotPeriodic() {
//        SmartDashboard.putBoolean("current magnet", currentWheel.getMagnetState());

        for (SwerveWheel wheel : swerveWheels) {
            SmartDashboard.putBoolean("magnet " + wheel.name + " ", wheel.getMagnetState());
            SmartDashboard.putNumber("angle enc " + wheel.name + " ", wheel.getAngleEnc());
            SmartDashboard.putNumber("speed enc " + wheel.name + " ", wheel.getEncSpeed());
        }

        Scheduler.getInstance().run();

//        System.out.println("test magnet sensor:   " + testMagent.get());
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopPeriodic() {
//        System.out.println("current wheel:   angle enc: " + currentWheel.getAngleEnc() + "   speed enc: " + currentWheel.getEncSpeed());
//        SmartDashboard.putBoolean("CURRENT WHEEL magnet", currentWheel.getMagnetState());
//        SmartDashboard.putNumber("CURRENT WHEEL encoder", currentWheel.getAngleEnc());
//        SmartDashboard.putBoolean("yeeter state ", yeeterLeft.getState());
    }
}
