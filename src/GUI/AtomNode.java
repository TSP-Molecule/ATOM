package GUI;

import structures.Atom;

public class AtomNode {
    AtomNode parent = null;
    Atom self = null;
    double x = 0;
    double y = 0;
    double z = 0;

    public AtomNode(Atom self, double x, double y, double z, AtomNode parent) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.parent = parent;
        this.self = self;
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

    public AtomNode getParent() {
        return parent;
    }

    public Atom getAtom() {
        return self;
    }
}
