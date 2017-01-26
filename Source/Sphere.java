/*
-------------------------------------------------------------------------
Class Sphere 
equivalent to its homonymous in C.
-------------------------------------------------------------------------
This program will generate a given number of points uniformly distributed
on the surface of a sphere. The number of points is given on the command
line as the first parameter.  Thus `sphere 100' will generate 100 points 
on the surface of a sphere, and output them to stdout.
	A number of different command-line flags are provided to set the 
radius of the sphere, control the output format, or generate points on 
an ellipsoid.  The definition of the flags is printed if the program is 
run without arguments: `sphere'.
	The idea behind the algorithm is that for a sphere of radius r, the 
area of a zone of width h is always 2*pi*r*h, regardless of where the sphere 
is sliced.  The implication is that the z-coordinates of random points on a 
sphere are uniformly distributed, so that x and y can always be generated by 
a given z and a given angle.
	The default output is integers, rounded from the floating point 
computation.  The rounding implies that some points will fall outside
the sphere, and some inside.  If all are required to be inside, then
the calls to the cast (int) should be removed.  
	The flags -a, -b, -c are used to set ellipsoid axis lengths.  
Note that the points are not uniformly distributed on the ellipsoid: they 
are uniformly distributed on the sphere and that is scaled to an ellipsoid.
	Random.nextDouble() is used to generate random numbers, automatically 
seeded with time by Java.

Reference: J. O'Rourke, Computational Geometry Column 31,
Internat. J. Comput. Geom. Appl. 7 379--382 (1997);
Also in SIGACT News, 28(2):20--23 (1997), Issue 103.

Written by Joseph O'Rourke and Min Xu, June 1997.
Used in the textbook, "Computational Geometry in C."
Questions to orourke@cs.smith.edu.
--------------------------------------------------------------------
This code is Copyright 1997 by Joseph O'Rourke.  It may be freely
redistributed in its entirety provided that this copyright notice is
not removed.
--------------------------------------------------------------------
*/

import java.awt.*;
import java.io.*;
import java.util.*;

public class Sphere {
  
private int r1, r2, r3, r;
private boolean float_pt = false;


public void print_instruct()
{
  System.out.println ( "Please enter your input according to the following format:" );
  System.out.println ( "\tsphere [number of points] [-flag letter][parameter value] " );
  System.out.println ( "\t\t (Please put a space between flag letter and parameter value!) " );
  System.out.println ( "Available flags are: " );
  System.out.println ( "\t-r[parameter] \t set radius of the sphere (default: 100) " );
  System.out.println ( "\t-f            \t set output to floating point format (default: integer) ");
  System.out.println ( "\t-a[parameter] \t ellipsoid x-axis length (default: sphere radius) ");
  System.out.println ( "\t-b[parameter] \t ellipsoid y-axis length (default: sphere radius) ");
  System.out.println ( "\t-c[parameter] \t ellipsoid z-axis length (default: sphere radius) ");
}



 public static void main(String args[]) throws IOException {
   
    Sphere sph = new Sphere(args)  ;
  }


Sphere(String args[])

{

  int n;		/* number of points */
  double x, y, z, w, t;
  double R = 100.0;	/* default radius */
  int r1a, r2a, r3a;

  if ( args.length < 1 )
    {
    print_instruct();
    System.exit (1);
    }

  r = (int) R;
  r1 = r2 = r3 = r;
  TestFlags (args);
  n = 1;

  
  try 
    {
    n = Integer.parseInt( args[0] );
    }
  catch (NumberFormatException e) {System.out.println ("Invalid number of poits"); System.exit(1);}
  

     Random myRandom = new Random(); 


  while (n-->0){
    /* Generate a random point on a sphere of radius 1. */
    /* the sphere is sliced at z, and a random point at angle t
       generated on the circle of intersection. */
    z = 2.0 *  myRandom.nextDouble() - 1.0;
    t = 2.0 * Math.PI * myRandom.nextDouble();
    w = Math.sqrt( 1 - z*z );
    x = w * Math.cos( t );
    y = w * Math.sin( t );
    
    if ( float_pt == false )
      {
	r1a =(int) Math.round(r1 * x);
	r2a =(int) Math.round(r2 * y);
	r3a =(int) Math.round(r3 * z);
	System.out.println(r1a+"\t"+r2a+"\t"+r3a);
      }
    else
      System.out.println((r1*x)+"\t"+(r2*y)+"\t"+(r3*z)) ;
   
  }
 System.out.println("end");
}


public void TestFlags (String args[])
{

  int i = 1;

  /* Test for flags */
  while ( i < args.length ) {

    /* Test for radius flag */
    
    if (args[i].equals("-r")) 
      {
	i++;
	try 
	  {
	  r = Integer.parseInt(args[i]);
	    }
	  catch (NumberFormatException e) {System.out.println("Please enter a valid number for the Radius"); System.exit(1);}
	if (r==0) {
	  System.out.println ( "Invalid radius flag " );
	  System.exit(1);
	}
	else
	  r1 = r2 = r3 = r;
	 System.out.println("r, r1, r2, r3 are: "+r+r1+r2+r3);
      }
    

    /* Test whether user wants floating point output */
    if (args[i].equals("-f"))
      float_pt = true;

    /* Test for ellipsoid radius if any */
    if (args[i].equals("-a")) 
      {
	i++;
	  try{ 
	  r1 = Integer.parseInt(args[i]);
	    }
	  catch (NumberFormatException e) {System.out.println("Please enter a valid number for the Radius"); System.exit(1);}
	if (r==0) {
	  System.out.println ( "Invalid radius flag " );
	  System.exit(1);
	}
      }

 if (args[i].equals("-b")) 
      {
	i++;
	  try{ 
	  r2 = Integer.parseInt(args[i]);
	    }
	  catch (NumberFormatException e) {System.out.println("Please enter a valid number for the Radius"); System.exit(1);}
	if (r==0) {
	  System.out.println ( "Invalid radius flag " );
	  System.exit(1);
	}
      }
 if (args[i].equals("-c")) 
      {
	i++;
	  try
	    { 
	  r3 = Integer.parseInt(args[i]);
	    }
	  catch (NumberFormatException e) {System.out.println("Please enter a valid number for the Radius"); System.exit(1);}
	if (r==0) {
	  System.out.println ( "Invalid radius flag " );
	  System.exit(1);
	}
      }

    i++;
  }

  if ( r1 == 0 || r2 == 0 || r3 == 0 )
    {
    System.out.println ( "Invalid ellipsoid radius " );
    System.exit (1);
    }
}


}