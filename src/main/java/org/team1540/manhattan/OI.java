package org.team1540.manhattan;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team1540.rooster.Utilities;

public class OI {

    public static Joystick driver = new Joystick(0);
    public static Joystick copilot = new Joystick(1);

    // Buttons
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;

    public static final int LB = 5;
    public static final int RB = 6;
    public static final int BACK = 7;
    public static final int START = 8;

    // Joysticks
    public static final int LEFT_X = 0;
    public static final int LEFT_Y = 1;
    public static final int LEFT_TRIG = 2;
    public static final int RIGHT_TRIG = 3;
    public static final int RIGHT_X = 4;
    public static final int RIGHT_Y = 5;

    public static final double deadzone = 0.1;

    public static double getStrafeXAxis() {
        return Utilities.processDeadzone(driver.getRawAxis(LEFT_X), deadzone);
    }

    public static double getStrafeYAxis() {
        return Utilities.processDeadzone(-driver.getRawAxis(LEFT_Y), deadzone);
    }

    public static double getRotateXAxis() {
        return Utilities.processDeadzone(-driver.getRawAxis(RIGHT_X), deadzone);
    }

    public static double getTestRotateXAxis() { //TODO: remove
        return driver.getRawAxis(RIGHT_X);
    }

    //TODO: square inputs

    public static JoystickButton calibrateButton = new JoystickButton(driver, A);
    public static JoystickButton stopCalibrationButton = new JoystickButton(driver, B);

    public static JoystickButton testbutton = new JoystickButton(driver, X);

    public static JoystickButton leftYeetButton = new JoystickButton(copilot, LB);
    public static JoystickButton rightYeetButton = new JoystickButton(copilot, RB);

}
