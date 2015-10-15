package team008_0_0_0_3;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

public class Soldier extends BaseRobot {
	public Behavior behavior;

	public Soldier(RobotController rc) {
		super(rc);
		try {
			if (rc.readBroadcast(hqLoc.hashCode()) == 1) {
				behavior = Behavior.ECON_BOT;
			} else
				behavior = Behavior.ATTACK_BOT;
		} catch (GameActionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				switch (behavior) {
				case ATTACK_BOT:
					attackMethod();
					break;
				case ECON_BOT:
					econMethod();
					break;
				default:
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			rc.yield();
		}
	}

	private void econMethod() throws GameActionException {
		MapLocation closest = getClosestEncampments();
		if (closest != null) {
			captureEncampment(closest);
			moveMineSafelyTowardLoc(closest);
		}
	}

	private MapLocation getClosestEncampments() throws GameActionException {
		MapLocation closeEncampments[] = rc.senseEncampmentSquares(hqLoc, 100,
				Team.NEUTRAL);
		if (closeEncampments.length == 0) {
			behavior = Behavior.ATTACK_BOT;
			rc.yield();
			return null;
		}
		return minDistLoc_HQ(closeEncampments);
	}

	private MapLocation minDistLoc_HQ(MapLocation[] closeEncampments) {
		int MinDist = Integer.MAX_VALUE;
		MapLocation toReturn = null;
		for (int i = 0; i < closeEncampments.length; i++) {
			if (closeEncampments[i].distanceSquaredTo(hqLoc) * 3
					+ closeEncampments[i].distanceSquaredTo(enemyHqLoc) < MinDist) {
				MinDist = closeEncampments[i].distanceSquaredTo(hqLoc)
						+ closeEncampments[i].distanceSquaredTo(enemyHqLoc);
				toReturn = closeEncampments[i];
			}
		}
		return toReturn;
	}

	private void captureEncampment(MapLocation mapLocation)
			throws GameActionException {
		if (!rc.getLocation().equals(mapLocation))
			return;
		if (rc.isActive() && rc.senseEncampmentSquare(rc.getLocation()))
			rc.captureEncampment(RobotType.ARTILLERY);
	}

	private void attackMethod() throws GameActionException {
		surroundEnemyIfOutnumber();
		moveMineSafelyTowardLoc(enemyHqLoc);
	}

	private void surroundEnemyIfOutnumber() throws GameActionException {
		if (!rc.isActive())
			return;

		MapLocation myLoc = rc.getLocation();

		Robot[] enemyBots = rc.senseNearbyGameObjects(Robot.class, 14,
				enemyTeam);
		if (enemyBots.length == 0)
			return;

		Robot[] allyBots = rc.senseNearbyGameObjects(Robot.class, 14,
				rc.getTeam());

		MapLocation toMove;
		if (allyBots.length > enemyBots.length
				|| myLoc.distanceSquaredTo(hqLoc) < 50) /*
														 * Move toward the
														 * enemy. Close to hq or
														 * outnumber
														 */
			toMove = rc.senseRobotInfo(enemyBots[0]).location;
		else if (allyBots.length == 0) /* move toward HQ */
			toMove = hqLoc;
		else
			toMove = rc.senseRobotInfo(allyBots[0]).location;

		Direction towards[] = getNCloseDirs(myLoc.directionTo(toMove), 6);
		moveSafelyTowardLoc(towards);
	}

	protected void moveSafelyTowardLoc(Direction[] towards)
			throws GameActionException {
		if (!rc.isActive())
			return;
		for (Direction d : towards) {
			if (rc.canMove(d) && rc.senseMine(rc.getLocation().add(d)) == null) {
				rc.move(d);
				return;
			}
		}
	}

	protected void moveMineSafelyTowardLoc(MapLocation mapLocation)
			throws GameActionException {
		if (!rc.isActive())
			return;
		Direction towards[] = getNCloseDirs(
				rc.getLocation().directionTo(mapLocation), 4);

		moveSafelyTowardLoc(towards);
		mineToward(towards);
	}

	private void mineToward(Direction[] towards) throws GameActionException {
		if (!rc.isActive())
			return;
		for (Direction d : towards) {
			Team mineTeam = rc.senseMine(rc.getLocation().add(d));
			if (rc.canMove(d)
					&& (mineTeam == enemyTeam || mineTeam == Team.NEUTRAL)) {
				rc.defuseMine(rc.getLocation().add(d));
				return;
			}
		}
	}
}
