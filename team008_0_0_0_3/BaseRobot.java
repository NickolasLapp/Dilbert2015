package team008_0_0_0_3;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;

public abstract class BaseRobot {
	protected Direction[] dirsCloseToEnemy;
	protected Direction dirToEnemy;
	protected RobotController rc;
	protected final MapLocation hqLoc;
	protected final MapLocation enemyHqLoc;
	protected final Team enemyTeam;

	protected BaseRobot(RobotController rc) {
		this.rc = rc;
		this.hqLoc = rc.senseHQLocation();
		this.enemyHqLoc = rc.senseEnemyHQLocation();
		this.dirToEnemy = hqLoc.directionTo(enemyHqLoc);

		enemyTeam = rc.getTeam() == Team.A ? Team.B : Team.A;
	}

	public abstract void run();

	protected Direction[] getNCloseDirs(Direction dir, int n) {
		Direction towards[] = new Direction[n];
		towards[0] = dir;

		Direction tempDir = dir;
		for (int i = 0; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateLeft();
			towards[2 * i + 1] = tempDir;
		}

		tempDir = dir;
		for (int i = 1; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateRight();
			towards[2 * i] = tempDir;
		}
		return towards;
	}

}
