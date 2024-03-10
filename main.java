public class main {

	public static void main(String[] args) {
		Game g = Game.newGame(4);
		System.out.println("2048\n"
						+ "wasd - move");
		boolean s = false;
		while(!s) {
			System.out.println(display(g.getGrid()));
			@SuppressWarnings("resource")
			String in = new java.util.Scanner(
						System.in).nextLine()+" ";
			boolean x = false, undo = false;
			if(in.charAt(0)=='w') {
				x = g.move(Game.UP);
			} else if(in.charAt(0)=='s') {
				x = g.move(Game.DOWN);
			} else if(in.charAt(0)=='a') {
				x = g.move(Game.LEFT);
			} else if(in.charAt(0)=='d') {
				x = g.move(Game.RIGHT);
			} else if(in.charAt(0)=='u') {
				x = g.undo();
				undo = true;
			} else if(in.charAt(0)=='r') {
				g = new Game(4);
				x = true;
			} else if(in.charAt(0)=='e') {
				s = !s;
				x = true;
			} else {
				System.out.println("Invalid input. ");
				x = true;
			}
			if(!x) {
				System.out.println("Error. ");
			} else if(undo) {

			}
		}
	}

	public static String display(int[][] g) {
		String out = "", temp = "";
		out += "-".repeat((g[0].length*5)+1)+"\n";
		for(int i = 0; i < g.length; i++) {
			out += "|    ".repeat(g[i].length)+"|\n";
			for(int j = 0; j < g[i].length; j++) {
				temp = g[i][j]!=0?g[i][j]+"":" ";
				out += "|"+" ".repeat(
						4-temp.length())+temp;
			}	out += "|\n";
			out += "|    ".repeat(g[i].length)+"|\n";
			out += "-".repeat((g[i].length*5)+1)+"\n";
		}
		return out;
	}


}

class Game {

	private int[][] grid;
	private int[][] prev;

	public static final int
			UP = 1111,
			DOWN = 2222,
			LEFT = 33333,
			RIGHT = 4444,
			UNDO = 5555;

	public Game(int dimension) {
		this.setGrid(new int[dimension][dimension]);
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public void setPrev(int[][] prev) {
		this.prev = prev;
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public int[][] getPrev() {
		return this.prev;
	}

	public static Game newGame(int d) {
		Game g = new Game(d);
		g.addNum();
		return g;
	}

	public boolean move(int direction) {
		boolean stat = false;
		switch(direction) {
		case UP:
			stat = up();
			break;
		case DOWN:
			stat = down();
			break;
		case LEFT:
			stat = left();
			break;
		case RIGHT:
			stat = right();
			break;
		default:
			stat = false;
			break;
		}	if(stat) {
			addNum();
		}	return stat;
	}

	public boolean undo() {
		if(prev!=null) {
			grid = prev;
			prev = null;
			return true;
		} else {
			return false;
		}
	}

	private boolean up() {
		boolean noChanges =  true;
		for(int i = 0; i < grid[0].length; i++) {
			int[] t = new int[grid.length];
			for(int j = 0; j < grid.length; j++) {
				t[j] = grid[j][i];
			}	t = mve(t);
			if(t != null) {
				noChanges = false;
				for(int j = 0; j < grid.length; j++) {
					grid[j][i] = t[j];
				}
			}
		}	return !noChanges;
	}

	private boolean down() {
		boolean noChanges =  true;
		for(int i = 0; i < grid[0].length; i++) {
			int[] t = new int[grid.length]; int k = 0;
			for(int j = grid.length-1; j >= 0; j--) {
				t[k++] = grid[j][i];
			}	t = mve(t);
			if(t != null) {
				noChanges = false; k = 0;
				for(int j = grid.length-1; j >= 0; j--) {
					grid[j][i] = t[k++];
				}
			}
		}	return !noChanges;
	}

	private boolean left() {
		boolean noChanges =  true;
		for(int i = 0; i < grid.length; i++) {
			int[] t = new int[grid[i].length];
			for(int j = 0; j < grid[i].length; j++) {
				t[j] = grid[i][j];
			}	t = mve(t);
			if(t != null) {
				noChanges = false;
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j] = t[j];
				}
			}
		}	return !noChanges;
	}

	private boolean right() {
		boolean noChanges =  true;
		for(int i = 0; i < grid.length; i++) {
			int[] t = new int[grid[i].length];
			int k = 0;
			for(int j = grid[i].length-1; j >= 0; j--) {
				t[k++] = grid[i][j];
			}	t = mve(t);
			if(t != null) {
				noChanges = false; k = 0;
				for(int j = grid[i].length-1; j >= 0; j--) {
					grid[i][j] = t[k++];
				}
			}
		}	return !noChanges;
	}

	public static int[] mve(int[] a) {
		if(!chk(a)) { return null; }
		for(int i = 0; i < a.length-1; i++) {
			boolean lol = true;
			if(a[i]==0) {
				for(int j = i+1; j < a.length && lol; j++) {
					if(a[j] != 0) {
						a[i] = a[j];
						a[j] = 0;
						lol = false;
					}
				}
			}	lol = true;
			for(int j = i+1; j < a.length && lol; j++) {
				if(a[i] == a[j]) {
					a[i] += a[j];
					a[j] = 0;
					lol = false;
				} else if (a[j] == 0) {

				} else if (a[j] != 0
						&& a[i] != 0
						&& a[j] != a[i]) {
					lol = false;
				}
			}
		}	return a;
	}

	public static boolean chk(int[] a) {
		boolean n = false, has = n; int p = 0;
		for(int i = a.length-1; i >= 0; i--) {
			if(a[i] == p && p != 0) {
				return true;
			}	if(a[i]	!= 0) {
				has = true;	p = a[i];
			}	if(has && a[i] == 0) {
				return true;
			}
		}	return false;
	}

	public boolean noEmptySpace() {
		return getEmptyGridLoc().length==0;
	}

	public void addNum() {
		int[][] l = getEmptyGridLoc();
		int r1 = ((int) (Math.random()*100)),
		r2 = ((int)(Math.random()*l.length));
		r1 = (r1<=14)?4:2;	int[] rr = l[r2];
		grid[rr[0]][rr[1]] = r1;
	}

	private int[][] getEmptyGridLoc() {
		int[][] l = new int[0][2];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j]==0) {
					int[] lol = {i, j};
					l = add(l, lol);
				}
			}
		}
		return l;
	}

	private int[][] add(int[][] a, int[] b) {
		int[][] c = new int[a.length+1][2];
		for(int i = 0; i < a.length; i++) {
			c[i] = a[i];
		}	c[c.length-1] = b;
		return c;
	}

}
