package team008_0_0_0_3;

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
				spawnTowardsEnemyHQ();
				if (Math.random() < .10) {
					rc.broadcast(hqLoc.hashCode()
							% GameConstants.BROADCAST_MAX_CHANNELS, 1);
				} else
					rc.broadcast(hqLoc.hashCode()
							% GameConstants.BROADCAST_MAX_CHANNELS, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			rc.yield();
		}
	}

	private void spawnTowardsEnemyHQ() throws GameActionException {
		if (!rc.isActive())
			return;
		Direction[] towards = getNCloseDirs(dirToEnemy, 8);
		for (Direction d : towards) {
			if (rc.canMove(d)
					&& rc.senseMine(rc.getLocation().add(d)) != Team.NEUTRAL) {
				rc.spawn(d);
				return;
			}
		}
	}
}
