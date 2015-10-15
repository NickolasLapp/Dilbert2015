package team008_0_0_0_3;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;

public class Artillery extends BaseRobot {

	public Artillery(RobotController rc) {
		super(rc);
	}

	@Override
	public void run() {
		while (true) {
			try {

				Robot enemyBots[] = rc.senseNearbyGameObjects(Robot.class,
						rc.getType().sensorRadiusSquared, enemyTeam);

				for (Robot enemy : enemyBots) {
					MapLocation loc;
					if (!rc.canSenseObject(enemy))
						continue;
					loc = rc.senseRobotInfo(enemy).location;

					if (loc.distanceSquaredTo(hqLoc) > GameConstants.ARTILLERY_SPLASH_RADIUS_SQUARED) {
						if (rc.isActive() && rc.canAttackSquare(loc)) {
							rc.attackSquare(loc);
							rc.yield();
						}
					}
				}
			} catch (GameActionException e) {
				e.printStackTrace();
			}

		}

	}
}
