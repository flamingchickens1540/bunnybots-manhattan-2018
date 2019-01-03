package org.team1540.manhattan.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.team1540.manhattan.Robot;
import org.team1540.manhattan.subsystems.CubeYeeter;

public class YeetCube extends TimedCommand {

    private CubeYeeter yeeter;

    public YeetCube(CubeYeeter whichOne) {
        super(0.2);
        yeeter = whichOne;
        requires(yeeter);
    }

    @Override
    protected void initialize() {
        yeeter.yeet();
    }

    @Override
    protected void end() {
        yeeter.unYeet();
    }

}
