package team008_0_0_0_1;

import java.util.Random;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

public class Soldier extends BaseRobot {

	public Soldier(RobotController rc) {
		this.rc = rc;
		this.random = new Random();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
				if (rc.isActive())
					moveOffMine();
				if (rc.isActive())
					mineAnyAdjacent(dir);
				if (rc.isActive()) {
					moveNearest(rc, dir);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			rc.yield();
				}
			}

	private void moveOffMine() throws GameActionException {
		if (rc.senseMine(rc.getLocation()) != null)
			moveAway();
		}

	private void mineAnyAdjacent(Direction dir) throws GameActionException {
		Direction tempDir = dir;

		for (int i = 0; i < 4; i++, tempDir.rotateLeft()) {
			if (rc.senseMine(rc.getLocation().add(tempDir)) != null) {
				rc.defuseMine(rc.getLocation().add(tempDir));
				return;
			}
			}
		tempDir = dir;
		for (int i = 0; i < 4; i++, tempDir.rotateRight()) {
			if (rc.senseMine(rc.getLocation().add(tempDir)) != null) {
				rc.defuseMine(rc.getLocation().add(tempDir));
				return;
			}
		}
			}

	private void moveAway() throws GameActionException {
		for (Direction dir : Direction.values())
			if (dir != Direction.OMNI && dir != Direction.NONE && rc.canMove(dir)
					&& rc.senseMine(rc.getLocation().add(dir)) == null) {
				rc.move(dir);
				return;
			}
	}

	private void moveNearest(RobotController rc, Direction dir) throws GameActionException {
		for (int i = 0; i < 8 && !rc.canMove(dir); i++) {
			dir = dir.rotateLeft();
			}
		if (rc.canMove(dir))
				rc.move(dir);
	}
			}
