package org.team1540.manhattan.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CubeYeeter extends Subsystem {

    private Solenoid yeeter;

    public CubeYeeter(int solenoid) {
        yeeter = new Solenoid(solenoid);
    }

    public void yeet() {
        yeeter.set(true);
        System.out.println("yeet");
    }

    public void unYeet() {
        yeeter.set(false);
        System.out.println("unyeet");
    }

    public boolean getState() {
        return yeeter.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}
