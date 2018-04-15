package GUI;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import structures.Atom;

import java.util.ArrayList;

public class AtomNode {
    AtomNode parent = null;
    Atom self = null;
    double x = 0;
    double y = 0;
    double z = 0;
    Point3D axis = Rotate.Z_AXIS;
    ArrayList<BondNode> bondNodes = new ArrayList<>();

    public AtomNode(Atom self, double x, double y, double z, AtomNode parent, Point3D axis) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.parent = parent;
        this.self = self;
        this.axis = axis;
    }

    public void setBondNodes(ArrayList<BondNode> bondNodes) {
        this.bondNodes = bondNodes;
    }

    public ArrayList<BondNode> getBondNodes() {
        return bondNodes;
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

    public Point3D getAxis() {
        return axis;
    }
}
