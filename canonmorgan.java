/**
 * MIT License
 *
 * Copyright (c) 2018 Mehmet Aziz Yirik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


/**
 * @author Mehmet Aziz Yirik
 *
 * The test class for the implementations of Morgan enumaration and canonization of the molecule based on the Morgan numbering.
 * For more information, Morgan's paper is also referenced below.
 * 
 * Reference: Morgan, H. L. "The generation of a unique machine description for chemical structures-a technique 
 * developed at chemical abstracts service." Journal of Chemical Documentation 5.2 (1965): 107-113.
 */





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
