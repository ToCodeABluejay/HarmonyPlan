package planning;
import java.util.*;
/** A class to represent a set of production technologies in a more compact
 *  form than as an input output table or matrix. It can take advantage
 *  of the sparse character of large io tables.
 *
 *   Copyright (C) 2018 William Paul Cockshott
 *   Copyright (C) 2022 Gabriel James Bauer
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see https://www.gnu.org/licenses/.
 */
public class TechnologyComplex {

    public Vector< Technique > techniques= new Vector<>();
    public String[] productIds;
    public boolean[] nonproduced;
    Vector< Vector<Integer>> producerIndex =null;
    Vector< Vector<Integer>> userIndex =null;
    public boolean[] nonfinal;
    
    public Vector< Vector<Integer> > buildUserIndex()
    /** return a vector indexed by product each of whose elements is a vector of indices of techniques that use the product */
    {
        if(userIndex==null) {
            Vector<Vector<Integer> > index = new Vector<>();
            for (int i=0; i<productCount(); i++)
                index.add(new Vector<>());

            for (int i =0; i<techniqueCount(); i++) {
                Technique t= techniques.elementAt(i);
                for (int j=0; j<t.inputCodes.length; j++) {
                    index.elementAt(t.inputCodes[j]).add(i);
                }

            }
            userIndex=index;
            return index;
        }
        return userIndex;
    }

    public Vector< Vector<Integer> > buildProducerIndex()
    /** return a vector indexed by product each of whose elements is a vector of  indices of techniques that make that product */
    {
        if(producerIndex==null) {
            Vector<Vector<Integer> > index = new Vector<>();
            for (int i=0; i<productCount(); i++)
                index.add(new Vector<>());
            for (int i =0; i<techniqueCount(); i++) {
                Technique t= techniques.elementAt(i);
                index.elementAt(t.productCode).add(i);
                if(t instanceof JointProductionTechnique J) {
                    for (int j=0; j<J.coproductCodes.length; j++)
                        index.elementAt(J.coproductCodes[j]).add(i);
                }
            }
            producerIndex=index;
            return index;
        }
        return producerIndex;
    }
    public TechnologyComplex(int NumberOfProducts) {
        productIds= new String[NumberOfProducts];
        nonproduced=new boolean[NumberOfProducts];
        nonfinal=new boolean[NumberOfProducts];
    }
    public void addTechnique(Technique t) {
        techniques.add(t);
    }
    public void setProductName(int productCode,String productName) {

        productIds[productCode]=productName;

    }

    public String allheadings()
    /** return a comma separated string of all the product and technology names, products first then techniques */
    {
        StringBuilder s=new StringBuilder();
        for(String p:productIds)s.append(",").append(p);
        for(Technique t:techniques) s.append(",").append(t.getIdentifier());
        return s.toString();
    }
    public int productCount() {
        return productIds.length;
    }
    public int techniqueCount() {
        return techniques.size();
    }
}
