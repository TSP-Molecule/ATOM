import java.util.ArrayList;

/**
 * ElementType -
 *      @author Emily Anible
 *      AtomObject. Each instance of this object is a "physical" atom, with attributes
 *          - Element - What element is this atom?
 *          - Bonds    - What bonds object are bonded here?
 *                    - (maybe atom.getBonded() to get a list of the atoms it's bonded to)
 *      //TODO: Maybe call this something different. Is a property of Atoms now, so maybe TypeElement?
 */
public class AtomObject {


    /**
     * What element is this atom?
     */
    private final ElementType element;


    /**
     * What bonds are connected to this atom?
     */
    private ArrayList<Bond> bonds;

    /**
     * Create an Atom with both an element and attached bonds.
     *
     * @param element   The Atom's element.
     * @param bonds     ArrayList of attached bonds.
     */
    public AtomObject(ElementType element, ArrayList<Bond> bonds) {
        this.element = element;
        this.bonds = bonds;
    }


    /**
     * Create an Atom with only an element, no attached bonds.
     * @param element   The Atom's element.
     */
    public AtomObject(ElementType element) {
        this.element = element;
    }
}
