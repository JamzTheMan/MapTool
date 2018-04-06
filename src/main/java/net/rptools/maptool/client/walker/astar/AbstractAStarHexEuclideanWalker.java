/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.client.walker.astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.Zone;
import net.rptools.maptool.model.ZonePoint;

public abstract class AbstractAStarHexEuclideanWalker extends AbstractAStarWalker {
	protected int[][] oddNeighborMap;
	protected int[][] evenNeighborMap;

	public AbstractAStarHexEuclideanWalker(Zone zone) {
		super(zone);
	}

	protected abstract void initNeighborMaps();

	@Override
	protected int[][] getNeighborMap(int x, int y) {
		return x % 2 == 0 ? evenNeighborMap : oddNeighborMap;
	}

	@Override
	protected double gScore(CellPoint p1, CellPoint p2) {
		return euclideanDistance(p1, p2);
	}

	@Override
	protected double hScore(CellPoint p1, CellPoint p2) {
		return euclideanDistance(p1, p2);
	}

	private double euclideanDistance(CellPoint p1, CellPoint p2) {
		ZonePoint zp1 = getZone().getGrid().convert(p1);
		ZonePoint zp2 = getZone().getGrid().convert(p2);

		int a = zp2.x - zp1.x;
		int b = zp2.y - zp1.y;

		return Math.sqrt(a * a + b * b);
	}

	@Override
	protected int calculateDistance(List<CellPoint> path, int feetPerCell) {
		int cellsMoved = path != null && path.size() > 1 ? path.size() - 1 : 0;
		return cellsMoved * feetPerCell;
	}

	@Override
	protected List<AStarCellPoint> getNeighbors(AStarCellPoint node, Set<AStarCellPoint> closedSet) {
		List<AStarCellPoint> neighbors = new ArrayList<AStarCellPoint>();
		int[][] neighborMap = getNeighborMap(node.x, node.y);

		// Find all the neighbors.
		for (int[] i : neighborMap) {
			// double terrainModifier = 0;

			AStarCellPoint neighbor = new AStarCellPoint(node.x + i[0], node.y + i[1]);

			if (closedSet.contains(neighbor))
				continue;

			// Add the cell we're coming from
			neighbor.parent = node;

			// // Don't count VBL or Terrain Modifiers
			// if (restrictMovement) {
			// // VBL Check FIXME: Add to closed set?
			// if (vblBlocksMovement(node, neighbor)) {
			// closedSet.add(node);
			// continue;
			// }
			//
			// // FIXME: add Occupied cell!
			// // Check for terrain modifiers
			// for (AStarCellPoint cell : terrainCells) {
			// if (cell.equals(neighbor)) {
			// terrainModifier += cell.terrainModifier;
			// // log.info("terrainModifier for " + cell + " = " + cell.terrainModifier);
			// }
			// }
			// }
			//
			// if (terrainModifier == 0)
			// terrainModifier = 1;

			neighbor.g = node.g + normal_cost;
			neighbor.distanceTraveled = neighbor.g;
			neighbors.add(neighbor);
			// log.info("neighbor.g: " + neighbor.getG());
		}

		return neighbors;
	}
}
