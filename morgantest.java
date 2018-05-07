package grouprepresentation;

import java.io.IOException;
import org.openscience.cdk.Atom;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

/**
 * @author Mehmet Aziz Yirik
 *
 * The test class for the implementations of Morgan enumaration and canonization of the molecule based on the Morgan numbering.
 * For more information, Morgan's paper is also referenced below.
 * 
 * Reference: Morgan, H. L. "The generation of a unique machine description for chemical structures-a technique 
 * developed at chemical abstracts service." Journal of Chemical Documentation 5.2 (1965): 107-113.
 */
public class morgantest {
	
	public static void main(String[] args) throws IOException, CDKException, CloneNotSupportedException, NullPointerException{
		
		//First, an atomcontainer is created manually adding the atoms and bonds.
		
		//Adding the hydrogen and carbon atoms
		IAtomContainer acontainer = new org.openscience.cdk.AtomContainer();

        acontainer.addAtom(new Atom("C")); //1
        acontainer.addAtom(new Atom("C")); //2
        acontainer.addAtom(new Atom("C")); //3
        acontainer.addAtom(new Atom("H")); //4
        acontainer.addAtom(new Atom("H")); //5
        acontainer.addAtom(new Atom("H")); //6
        acontainer.addAtom(new Atom("H")); //7
        acontainer.addAtom(new Atom("H")); //8
        acontainer.addAtom(new Atom("H")); //9
              
        //Adding the hydrogen atom bonded bonds.
        acontainer.addBond(0, 3, IBond.Order.SINGLE);
        acontainer.addBond(0, 4, IBond.Order.SINGLE);
        acontainer.addBond(1, 5, IBond.Order.SINGLE);
        acontainer.addBond(1, 6, IBond.Order.SINGLE);
        acontainer.addBond(2, 7, IBond.Order.SINGLE);
        acontainer.addBond(2, 8, IBond.Order.SINGLE);
        acontainer.addBond(0, 1, IBond.Order.SINGLE);
        acontainer.addBond(0, 2, IBond.Order.SINGLE);
        acontainer.addBond(1, 2, IBond.Order.SINGLE);
        
        /**
         * getMorganOrder function reorders atom indices based on their Morgan values.
         *
         */
        
        int[] reorder=canonmorgan.getMorganOrder(acontainer);
        
        /**
         * getMorganOrdered functions reorders atoms of the atomcontainer based on Morgan values.
         */
        
        IAtomContainer reordered=canonmorgan.getMorganOrdered(acontainer);
        
	}
}
