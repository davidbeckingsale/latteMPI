# latteMPI #

LatteMPI is a pure Java implementation of a subset of the MPI Standard, and was written for the [CS310][3yp] module at the University of Warwick.
 
## Using latteMPI ##

### Compilation
Install the maven build tool, and the proceed to compile the project
with a command similar to:

     mvn install

### Running the project

Provided you have ssh running, and a key exisitng in ~/.ssh/id_dsa, a
program can be run with the following command:

    `java -jar latteMPI-1.0-SNAPSHOT.jar -hostfile <path-to-hostfile> \
             -n <number-of-processors> -className <name-of-Java-class>`
 
[3yp]: http://www2.warwick.ac.uk/fac/sci/dcs/teaching/modules/cs310/
