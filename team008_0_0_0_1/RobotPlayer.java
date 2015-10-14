package team008_0_0_0_1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

/**
 * The example funcs player is a player meant to demonstrate basic usage of the
 * most common commands. Robots will move around randomly, occasionally mining
 * and writing useless messages. The HQ will spawn soldiers continuously.
 */
public class RobotPlayer {
	public static void run(RobotController rc) {
		BaseRobot robot;
		if (rc.getType() == RobotType.HQ) {
			robot = new HQ(rc);
			robot.run();
		} else if (rc.getType() == RobotType.SOLDIER) {
			robot = new Soldier(rc);
			robot.run();
		}
	}

	}