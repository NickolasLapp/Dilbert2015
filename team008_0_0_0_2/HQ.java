package team008_0_0_0_2;

import java.util.Random;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.Upgrade;

public class HQ extends BaseRobot {

	private final Upgrade[] toResearch = new Upgrade[] { Upgrade.PICKAXE };

	public HQ(RobotController rc) {
		super(rc);
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (rc.isActive())
					spawnRobotPreferNoMine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rc.yield();
		}
	}

	private void spawnRobotPreferNoMine() throws GameActionException {
		Direction d = adjacentMoveableDirectionsNoMine(dirsCloseToEnemy, hqLoc);
		if (d != null && rc.canMove(d))
			rc.spawn(d);
	}

}
