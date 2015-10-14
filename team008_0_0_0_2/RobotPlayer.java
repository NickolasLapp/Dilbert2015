package team008_0_0_0_2;

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
		switch (rc.getType()) {
		case ARTILLERY:
			break;
		case GENERATOR:
			break;
		case HQ:
			robot = new HQ(rc);
			robot.run();
			break;
		case MEDBAY:
			break;
		case SHIELDS:
			break;
		case SOLDIER:
			robot = new Soldier(rc);
			robot.run();
			break;
		case SUPPLIER:
			break;
		default:
			break;
		}
	}
}
