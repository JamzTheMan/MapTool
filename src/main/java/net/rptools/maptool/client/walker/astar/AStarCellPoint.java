/*
 * This software Copyright by the RPTools.net development team, and licensed under the Affero GPL Version 3 or, at your option, any later version.
 *
 * MapTool Source Code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License * along with this source Code. If not, please visit <http://www.gnu.org/licenses/> and specifically the Affero license text
 * at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.client.walker.astar;

import net.rptools.maptool.model.CellPoint;

public class AStarCellPoint extends CellPoint implements Comparable<AStarCellPoint> {
	AStarCellPoint parent;
	double h;
	double g;
	double f;

	public AStarCellPoint() {
		super(0, 0);
	}

	public AStarCellPoint(int x, int y) {
		super(x, y);
	}

	public AStarCellPoint(CellPoint p) {
		super(p.x, p.y);
	}

	public double cost() {
		return h + g;
	}
	
	@Override
	public int compareTo(AStarCellPoint other) {
		return Double.compare(f, other.f);
	}
}
