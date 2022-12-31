package planning;
import java.io.*;
import java.util.*;
/** A class to compute the harmony function described in Towards a New Socialism
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
public class Harmony {
    public static double H(double target, double netoutput)
    /** the harmony function itself */
    {
        double scale =  (netoutput-target  )/target;
        if (scale<0) return scale - (scale*scale)*0.5;
        return Math.log(scale+1);
    }
    
    public static double dH(double target, double netoutput)
    /** the derivative of the harmony function
     * evaluated numerically so as to be independent of the H function */
    {
        double epsilon = 0.000001;
        double base = H(target,netoutput);
        double basePlusEpsilon = H(target, epsilon+netoutput);
        return (basePlusEpsilon - base)/epsilon;
        // Analytic soln
        //  double scale =  (netoutput-target  )/target;
        //  if (scale<0) return 1-scale;
        //  return 1/(1+scale);
    }
}
