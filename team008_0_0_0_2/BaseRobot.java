package team008_0_0_0_2;

import java.util.Random;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;

public abstract class BaseRobot {
	protected Direction[] dirsCloseToEnemy;
	protected Direction dirToEnemy;
	protected RobotController rc;
	protected Random random;
	protected final MapLocation hqLoc;
	protected final MapLocation enemyHqLoc;
	protected final Team enemyTeam;

	protected BaseRobot(RobotController rc) {
		this.rc = rc;
		this.random = new Random();
		this.hqLoc = rc.getLocation();
		this.enemyHqLoc = rc.senseEnemyHQLocation();

		enemyTeam = rc.getTeam() == Team.A ? Team.B : Team.A;

		InitDirectionsTowards();
	}

	public abstract void run();

	protected void InitDirectionsTowards() {
		dirToEnemy = hqLoc.directionTo(enemyHqLoc);
		Direction[] towards = adjacentDirections(dirToEnemy);
		dirsCloseToEnemy = towards;
	}

	protected Direction[] adjacentDirections(Direction toward) {
		Direction towards[] = new Direction[8];
		towards[0] = toward;

		Direction tempDir = toward;
		for (int i = 0; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateLeft();
			towards[2 * i + 1] = tempDir;
		}

		tempDir = toward;
		for (int i = 1; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateRight();
			towards[2 * i] = tempDir;
		}
		return towards;
	}

	protected Direction[] someAdjacentDirs(Direction toward) {
		Direction towards[] = new Direction[6];
		towards[0] = toward;

		Direction tempDir = toward;
		for (int i = 0; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateLeft();
			towards[2 * i + 1] = tempDir;
		}

		tempDir = toward;
		for (int i = 1; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateRight();
			towards[2 * i] = tempDir;
		}
		return towards;
	}

	protected Direction[] nAdjacentDirs(Direction toward, int n) {
		Direction towards[] = new Direction[n];
		towards[0] = toward;

		Direction tempDir = toward;
		for (int i = 0; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateLeft();
			towards[2 * i + 1] = tempDir;
		}

		tempDir = toward;
		for (int i = 1; i < towards.length / 2; i++) {
			tempDir = tempDir.rotateRight();
			towards[2 * i] = tempDir;
		}
		return towards;
	}

	protected Direction[] reverseDirs(Direction[] toReverse) {
		Direction reversed[] = new Direction[toReverse.length];
		for (int i = 0; i < toReverse.length; i++)
			reversed[i] = toReverse[i].opposite();
		return reversed;
	}

	public Direction adjacentMoveableDirectionsNoMine(Direction towards[], MapLocation from) {
		for (Direction d : towards) {
			Team mineTeam = rc.senseMine(from.add(d));
			if (rc.canMove(d) && (mineTeam == null) || mineTeam == rc.getTeam()) {
				return d;
			}
		}
		return null;
	}
}
