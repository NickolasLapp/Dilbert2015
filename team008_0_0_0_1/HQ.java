package team008_0_0_0_1;

import java.util.Random;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.Upgrade;

public class HQ extends BaseRobot {

	private final Upgrade[] toResearch = new Upgrade[] { Upgrade.PICKAXE };

	public HQ(RobotController rc) {
		this.rc = rc;
		this.random = new Random();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (rc.isActive()) {
					if (random.nextInt(3) != 0)
						researchTech();
					if (rc.isActive())
						spawnSoldier();
				}
			} catch (Exception e) {
				e.printStackTrace();
					}
			rc.yield();
		}
				}

	private void researchTech() throws GameActionException {
		Upgrade upgrade = nextUpgrade();
		if (upgrade != null)
			rc.researchUpgrade(upgrade);
	}

	private Upgrade nextUpgrade() throws GameActionException {
		for (Upgrade upgrade : toResearch)
			if (rc.checkResearchProgress(upgrade) != upgrade.numRounds)
				return upgrade;
		return null;

	}

	private void spawnSoldier() throws GameActionException {
		Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
		dir = closestDirNotMine(rc, dir);
		if (dir != null)
			rc.spawn(dir);
	}

	private static Direction closestDirNotMine(RobotController rc, Direction dir) {
		for (int i = 0; i < 8 && (!rc.canMove(dir) || rc.senseMine(rc.getLocation().add(dir)) != null); i++) {
			dir = dir.rotateLeft();
		}
		if (rc.canMove(dir))
			return dir;
		return null;
		}
			}
