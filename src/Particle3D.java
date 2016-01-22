/** A class for particles moving in 3 dimensions, with methods for reading a particle from a scanner, returning kinetic energy, finding relative separation, and updating position and velocity.
 * @author A. Baker
 * @author S. Rigby
 * JAVADOC COMMENTS?
 */
 
import java.util.Scanner;

public class Particle3D 
{
 
    // All of the values of these variables have been set to NaN to show that they have not been initalised yet.
     Vector3D position = new Vector3D(Double.NaN, Double.NaN, Double.NaN);
     Vector3D velocity = new Vector3D(Double.NaN, Double.NaN, Double.NaN);
     
     private double mass;
    
    // This String will hold the label of the particle in the simulation 
    String Name = new String();
    
    public Particle3D()
    {
        // This constructor will initalise the Particle3D into the state where its position and velocity are both the zero vector
        position.setX(0);
        position.setY(0);
        position.setZ(0);
        velocity.setX(0);
        velocity.setY(0);
        velocity.setZ(0);
    }
    
    public Particle3D(double posX, double posY, double posZ, double velX, double velY, double velZ, double mass, String Name)
    {
        //This constructor will set the position and velocity vectors to the given values.
        position.setX(posX);
        position.setY(posY);
        position.setZ(posZ);
        
        velocity.setX(velX);
        velocity.setY(velY);
        velocity.setZ(velZ);
        
        this.mass=mass;
        this.Name=Name;
    }
    
    //Set and Get methods for the above vectors and the properties of the particle. 
    //Vector3D methods can be used as getters for components x, y, z.
    public Vector3D getPosition()
    {
        return position;
    }
    
    public Vector3D getVelocity()
    {
        return velocity;
    }
    
    public double getMass()
    {
        return mass;
    }
    
    
    
   
    public void setPosition(double x,double y,double z)
    {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
    }
    
    public void setVelocity(double x,double y,double z)
    {
        velocity.setX(x);
        velocity.setY(y);
        velocity.setZ(z);
    }
    
    public void setMass(double mass)
    {
        this.mass=mass;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    
    //toString Method to print out the states of the particle in a readable format, namely:
    // Namely <label> <PosX> <PosY> <PosZ>
    public String toString()
    {
        return Name + " (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")";
    }
    
  //Method to read parameters from an input file and assign them to a particle
    public void readScanner(Scanner input)
    {
        position.setX(input.nextDouble());
        position.setY(input.nextDouble());
        position.setZ(input.nextDouble());
        velocity.setX(input.nextDouble());
        velocity.setY(input.nextDouble());
        velocity.setZ(input.nextDouble());
        mass=input.nextDouble();
        Name = input.next();
    }
     
    //Method to return a particle's kinetic energy
    public double kineticEnergy()
    {
        return 0.5 * mass * velocity.magnitudeSquared();
    }
    
    //Method to return the gravitational potential energy betwen two particles
    public static double GravitationalPotential(Particle3D big,Particle3D small)
    {
        double R2 = small.InstanceDistanceBetweenVectors(big.getPosition());
        return - small.getMass()*big.getMass() * 1/R2;
    }
    
    //Method to update the velocity using a given timestep dt and the force vector
    public void velocityUpdate(double dt, Vector3D force)
    {
        velocity.addScaledVector(force, dt/mass);         
    }
    
    //Method to update the position to first order terms in the Taylor Expansion
    public void positionUpdate(double dt)
    {
        position.addScaledVector(velocity, dt);         
    }
    
    //Method to update the position to second order terms in the Taylor Expansion
    public void secondOrderPositionUpdate(double dt,Vector3D force)
    {
        position.addScaledVector(velocity, dt);
        position.addScaledVector(force, Math.pow(dt, 2)/(2*mass));         
    }
    
    //Static method to find the distance between two particles
    public double distanceBetweenVectors(Vector3D pos1, Vector3D pos2)
    {
        double xDis = pos1.getX()-pos2.getX();
        double yDis = pos1.getY()-pos2.getY();
        double zDis = pos1.getZ()-pos2.getZ();
         
        return Math.sqrt(Math.pow(xDis, 2)+Math.pow(yDis, 2)+Math.pow(zDis, 2));
    }
    
    //Instance method to find the distance between two particles
    public double InstanceDistanceBetweenVectors(Vector3D pos1)
    {
        double xDis = pos1.getX()-position.getX();
        double yDis = pos1.getY()-position.getY();
        double zDis = pos1.getZ()-position.getZ();
         
        return Math.sqrt(Math.pow(xDis, 2)+Math.pow(yDis, 2)+Math.pow(zDis, 2));
    }
        
    //Method to find the vector between two particles     
    public Vector3D VectorDistance(Vector3D pos1,Vector3D pos2)
    {
        Vector3D Dis = new Vector3D();
        Dis.setX(pos1.getX()-pos2.getX());
        Dis.setY(pos1.getY()-pos2.getY());
        Dis.setZ(pos1.getZ()-pos2.getZ());
        return Dis;
    }
 
    
    
    //Method to return the gravitational force vector acting on the orbiting particle, labelled here as 'small', due to the central particle, labelled here as 'big'
    public static Vector3D GravitationalForce(Particle3D small,Particle3D big)
    {
        //Calculates the modulus squared of the distance between the particles
        double R2 = (small.InstanceDistanceBetweenVectors(big.getPosition()));
        R2=Math.pow(R2, 2);
        //Computes the unit vector between the particles
        Vector3D rHat = small.VectorDistance(small.getPosition(), big.getPosition());
        rHat.scalarMultiplication(1/Math.sqrt(R2));
        //Computes the gravitational force vector by multiplying the unit vector by the magnitude		
        rHat.scalarMultiplication((big.getMass()*small.getMass()*(-1/R2)));      
        return rHat;
    }
 
    
}
