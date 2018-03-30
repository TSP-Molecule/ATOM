package GUI;

import structures.Atom;
import structures.Bond;

public class BondNode {
    Bond bond = null;
    double x = 0;
    double y = 0;
    double z = 0;
    double ang = 0;

    Atom one = null;
    Atom two = null;

    public BondNode(Bond b, double x, double y, double z, double ang) {
        bond = b;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ang = ang;

        one = bond.getAtoms().get(0);
        two = bond.getAtoms().get(1);

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getAng() {
        return ang;
    }

    public Atom getOne() {
        return one;
    }

    public Atom getTwo() {
        return two;
    }
}
