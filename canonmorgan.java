package grouprepresentation;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang3.ArrayUtils;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.invariant.MorganNumbersTools;
import org.openscience.cdk.interfaces.IAtomContainer;

public class canonmorgan {
		
	//Reordering int array based on long comparison. Here, the long values are the morgan numbers.
	public static int[] intordlong( Integer[] array, long[] array2){
		Arrays.parallelSort(array, new Comparator<Integer>() { //Reordering indices based on the morgan number comparator.
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return Long.compare(array2[o1], array2[o2]);
            }
        });
		int[] l = ArrayUtils.toPrimitive(array); //returning as int array and in the descending order 
        ArrayUtils.reverse(l); //and in the descending order
		return l;
	}
	
	// Reordering the indices of the atoms in descending order based on Morgan numbers.
	public static int[] morganreorder(IAtomContainer ac){
		long[] morganNumbers= MorganNumbersTools.getMorganNumbers(ac);
		final Integer[] index= new Integer[morganNumbers.length];
		for (int i=0; i<morganNumbers.length;i++){
			index[i]=i;
		}
        return intordlong(index,morganNumbers);
	}
	
	
	//Simply reordering the atoms in the atomcontainer based on their Morgan reordering.
	public static IAtomContainer getMorganOrdered(IAtomContainer ac) throws CloneNotSupportedException,CDKException, NullPointerException{
		IAtomContainer ac2 = ac.getBuilder().newInstance(IAtomContainer.class);
		int[] rel= morganreorder(ac);
		for (int i=0; i<rel.length; i++){
			ac2.addAtom(ac.getAtom(rel[i]));
	    	//AtomContainerManipulator.replaceAtomByAtom(ac, ac.getAtom(i), ac.getAtom(rel[i]));
		}
	    return ac;
	}
	
	
	/**
	 * This functions are just different version of vertex reordering in descending order based on Morgan values.
	 */
	
	public static int[] getMorganOrder(IAtomContainer ac) throws CloneNotSupportedException,CDKException, NullPointerException{
		long[] morganNumbers= MorganNumbersTools.getMorganNumbers(ac);	
		int[][] morg= reverse2(morganNumbers);
		int[] relab= new int[morg.length];
		for(int i=0; i<morg.length; i++){
			relab[i]=(morg[i][1]);
		}
		return relab;
   }
	
	//Ordering an int[][] array in descending order.
	public static int[][] desorder (int[][] array){
		Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(final int[] entry1, final int[] entry2) {
                final int time1 = entry1[0];
                final int time2 = entry2[0];
                return Integer.compare(time1, time2);
            }
		});
		ArrayUtils.reverse(array);
	return array;
	}
	
	//Ordering long arrays in descending order with their indices. 
	public static int[][] reverse2(long[] input){
		final int[][] morgwind= new int[input.length][2];
		for (int i=0; i<input.length;i++){
			morgwind[i][0]= (int) input[i];
			morgwind[i][1]=i;
		}
		desorder(morgwind);
		return morgwind;
	}
	
}
