package structures.enums;

import structures.Atom;
import structures.Molecule;

/**
 * Different Geometries that a molecule could have, based on steric number and number of lone pairs.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public enum Geometry {
    //Name (# Atoms Bonded to A, # Lone Pairs around A

    //Steric: 2
    Linear(2, 0, "Linear", 180),

    //Steric: 3
    TrigonalPlanar(3, 0, "Trigonal Planar", 120),
    VShape(2, 1, "V-Shape", 120), //Less than

    //Steric: 4
    Tetrahedral(4, 0, "Tetrahedral", 109),
    TrigonalPyramidal(3, 1, "Trigonal Pyramidal", 109), //Less than
    Bent(2, 2, "Bent", 109), //Much less than

    //Steric: 5
    TrigonalBipyriamidal(5, 0, "Trigonal Bipyramidal", 120), //90 between axis
    Seesaw(4, 1, "See-saw", 120), //less than 120, less than 90 between axis
    TShaped(3, 2, "T-Shaped", 90), //less than 90
    Linear5(2, 3, "Linear (5)", 180),

    //Steric: 6
    Octahedral(6, 0, "Octahedral", 90),
    SquarePyramidal(5, 1, "Square Pyramidal", 90), //less than 90
    SquarePlanar(4, 2, "Square Planar", 90),
    TShape6(3, 3, "T-Shaped (6)", 90), //less than 90
    Linear6(2, 4, "Linear (6)", 180);

    private final int bondedAtoms;
    private final int lonePairs;
    private final double bondAngle;
    private final String name;

    Geometry(int bondedAtoms, int lonePairs, String name, double bondAngle) {
        this.bondedAtoms = bondedAtoms;
        this.lonePairs = lonePairs;
        this.name = name;
        this.bondAngle = bondAngle;
    }

    public static Geometry get(int bondedAtoms, int lonePairs) {
        for (Geometry g : Geometry.values()) {
            if (g.getBondedAtoms() == bondedAtoms && g.getLonePairs() == lonePairs) {
                return g;
            }
        }
        return null;
    }

    public int getBondedAtoms() {
        return bondedAtoms;
    }

    public int getLonePairs() {
        return lonePairs;
    }

    public String getName() {
        return name;
    }

    /**
     * Calculates the molecular geometry of the center atom of a molecule
     */
    public static Geometry calculateGeometry(Molecule molecule) {
        Atom center = molecule.getCenter();
        if (center == null) return null;

        int lonePairs = center.getLonePairs();
        int attached = center.getAttachedBonds().size();

        molecule.getCenter().setGeometry(Geometry.get(attached, lonePairs));

        return center.getGeometry();

    }

    public double getBondAngle() {
        return bondAngle;
    }
}
