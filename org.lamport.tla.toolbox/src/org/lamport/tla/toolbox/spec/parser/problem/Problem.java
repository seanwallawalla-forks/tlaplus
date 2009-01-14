package org.lamport.tla.toolbox.spec.parser.problem;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;

/**
 * A holder for information around the problem message
 * 
 * @author zambrovski
 * @version $Id$
 */
public class Problem implements IAdaptable
{
    
    public final static int ABORT   = 16;
    public final static int ERROR   = 32;
    public final static int WARNING = 64;
    public final static int ALL = ABORT + ERROR + WARNING;

    public Location         location = null;
    public String           message = null;
    public int              type;

    /**
     * Constructs an empty holder of particular problem type
     * 
     * @param type
     *            use {@link Problem#ABORT}, {@link Problem#ERROR}, {@link Problem#WARNING}
     */
    public Problem(int type)
    {
        this(null, null, type);
    }

    /**
     * Constructs a holder object
     * 
     * @param moduleName
     * @param coordinates
     *            beginLine, beginColumn, endLine, endColumn
     * @param message
     * @param type
     *            use {@link Problem#ABORT}, {@link Problem#ERROR}, {@link Problem#WARNING}
     */
    public Problem(String moduleName, int[] coordinates, String message, int type)
    {
        this(new Location(moduleName, coordinates), message, type);
    }

    /**
     * Constructs a holder object
     * 
     * @param location
     * @param message
     * @param type
     *            use {@link Problem#ABORT}, {@link Problem#ERROR}, {@link Problem#WARNING}
     */
    public Problem(Location location, String message, int type)
    {
        this.location = location;
        this.message = message;
        this.type = type;

        if (location == null)
        {
            this.location = Location.nilLocation();
        }
    }

    /**
     * Formats location
     * 
     * @return
     */
    public String getFormattedLocation()
    {
        return "from line " + location.beginLine + ", column " + location.beginColumn + " to line " + location.endLine
                + ", column " + location.endColumn + " of module " + location.moduleName;
    }

    
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter)
    {
        // lookup the IAdapterManager service
        IAdapterManager manager = Platform.getAdapterManager();
        // forward the request to IAdapterManager service
        return manager.getAdapter(this, adapter);

    }

    
    /**
     * Represents a location in TLA module
     * 
     * @author zambrovski
     */
    public static class Location
    {
        public static Location nilLocation(){
            return new Location("-- unknown --", new int[] { -1, -1, -1, -1 });
        }

        public int             beginLine;
        public int             beginColumn;
        public int             endLine;
        public int             endColumn;
        public String          moduleName;

        /**
         * Creates a location
         * @param beginColumn
         * @param beginLine
         * @param endColumn
         * @param endLine
         * @param moduleName
         */
        public Location(String moduleName, int beginLine, int beginColumn, int endLine, int endColumn)
        {
            this(moduleName, new int[]{beginLine, beginColumn, endLine, endColumn});
        }

        /**
         * Creates a location
         * @param moduleName
         * @param coordinates an array of beginLine, beginColumn, endLine, endColumn
         */
        public Location(String moduleName, int[] coordinates)
        {
            if (moduleName == null) 
            {
                moduleName = "-- unknown --";
            }
            this.moduleName = moduleName;
            
            if (coordinates == null)
            {
                coordinates = new int[] { -1, -1, -1, -1 };
            } 
            
            this.beginLine = coordinates[0];
            this.beginColumn = coordinates[1];
            this.endLine = coordinates[2];
            this.endColumn = coordinates[3];
        }

        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + beginColumn;
            result = prime * result + beginLine;
            result = prime * result + endColumn;
            result = prime * result + endLine;
            result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
            return result;
        }

        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Location other = (Location) obj;
            if (beginColumn != other.beginColumn)
                return false;
            if (beginLine != other.beginLine)
                return false;
            if (endColumn != other.endColumn)
                return false;
            if (endLine != other.endLine)
                return false;
            if (moduleName == null)
            {
                if (other.moduleName != null)
                    return false;
            } else if (!moduleName.equals(other.moduleName))
                return false;
            return true;
        }

        
        

    }
}
