package team008_0_0_0_2;

import java.util.Random;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

public class Soldier extends BaseRobot {

	public Soldier(RobotController rc) {
		super(rc);
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (rc.isActive())
					retreatOrAttack();
				if (rc.isActive())
					if (groupAllyNotCloseToHQ() != 0) {
						rc.yield();
						continue;
					}
				if (rc.isActive())
					moveToMiddle();
				if (rc.isActive())
					removeNeutralEnemyMineAround();
				if (rc.isActive())
					moveOffMine();
				if (rc.isActive())
					layMine();

			} catch (Exception e) {
				e.printStackTrace();
			}
			rc.yield();
		}
	}

	private int groupAllyNotCloseToHQ() throws GameActionException {
		if (rc.getLocation().distanceSquaredTo(hqLoc) < 10)
			return 0;
		Robot[] allyBots = rc.senseNearbyGameObjects(Robot.class, 14, rc.getTeam());
		if (allyBots.length < 6 && random.nextInt(2) != 0)
			return -1;
		return 0;
	}

	private void retreatOrAttack() throws GameActionException {
		Robot[] enemyBots = rc.senseNearbyGameObjects(Robot.class, 8, enemyTeam);
		if (enemyBots.length == 0)
			return;
		else {
			int numAllies = rc.senseNearbyGameObjects(Robot.class, 6, rc.getTeam()).length;

			Direction[] towardsEnemy = someAdjacentDirs(
					rc.getLocation().directionTo(rc.senseRobotInfo(enemyBots[0]).location));
			if (numAllies > enemyBots.length - 2 || rc.getLocation().distanceSquaredTo(hqLoc) < 10)
				moveTowardTheseDirs(towardsEnemy);
			else
				moveTowardTheseDirs(reverseDirs(towardsEnemy));

		}

	}

	private void moveToMiddle() throws GameActionException {
		Direction towardEnemyHQ = rc.getLocation().directionTo(enemyHqLoc);
		Direction[] toEnemy = nAdjacentDirs(towardEnemyHQ, 4);

		Direction d = adjacentMoveableDirectionsNoMine(toEnemy, rc.getLocation());
		if (d != null && rc.canMove(d))
			rc.move(d);
	}

	private void moveTowardTheseDirs(Direction[] theseDirs) throws GameActionException {
		for (Direction d : theseDirs)
			if (rc.canMove(d)) {
				rc.move(d);
				return;
			}
	}

	private void moveTowardTheseDirsNoMine(Direction[] theseDirs) throws GameActionException {
		Direction d = adjacentMoveableDirectionsNoMine(theseDirs, rc.getLocation());
		if (d != null && rc.canMove(d))
			rc.move(d);
	}

	private void removeNeutralEnemyMineAround() throws GameActionException {
		MapLocation curLoc = rc.getLocation();
		for (Direction d : dirsCloseToEnemy) {
			Team mineTeam = rc.senseMine(curLoc.add(d));
			if (mineTeam != null && mineTeam != rc.getTeam()) {
				rc.defuseMine(curLoc.add(d));
				return;
			}
		}
	}

	private void layMine() throws GameActionException {
		if (rc.senseMine(rc.getLocation()) != rc.getTeam())
			rc.layMine();
	}

	private void moveOffMine() throws GameActionException {
		Team curLocMineTeam = rc.senseMine(rc.getLocation());
		if (curLocMineTeam != null && curLocMineTeam != rc.getTeam()) {
			for (Direction d : dirsCloseToEnemy) {
				if (rc.canMove(d)) {
					curLocMineTeam = rc.senseMine(rc.getLocation().add(d));
					if (curLocMineTeam == null || curLocMineTeam == rc.getTeam())
						rc.move(d);
				}
			}
		}
	}
}
