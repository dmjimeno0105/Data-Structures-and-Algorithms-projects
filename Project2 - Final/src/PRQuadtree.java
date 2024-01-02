import java.util.LinkedList;

/**
 * PRQuadtree class
 *
 * @author Dominic Jimeno (dmjimeno0105)
 *
 * @version 9/27/2022
 */
public class PRQuadtree {
    private PRQuadtreeNode flyweight;
    private PRQuadtreeNode root;
    private int size;

    /**
     * Creates an empty PRQuadtree
     */
    public PRQuadtree() {
        flyweight = new EmptyNode();
        root = flyweight;
        size = 0;
    }


    /**
     * Getter method for size
     *
     * @return size
     */
    public int size() {
        return size;
    }


    /**
     * Inserts point into PRQuadtree
     *
     * @param pt
     *            point to be inserted
     */
    public void insert(Point pt) {
        // TODO
        root = root.insert(pt, 0, 0, 1024);
        size++;
    }


    /**
     * removes a point from quadtree given coordinates of point
     *
     * @param x
     *            x coordinate of point
     * @param y
     *            y coordinate of point
     * @return point
     *         the removed point, null if point with specified coordinates
     *         doesn't exist
     */
    public Point[] remove(int x, int y) {
        // TODO
        Point[] result = { null };
        PRQuadtreeNode tempPoint = root.remove(x, y, 0, 0, 1024, result);

        if (result == null) {
            return null;
        }
        root = tempPoint;
        size--;

        return result;
    }


    /**
     * removes a point from quadtree given point
     *
     * @param pt
     *            point to be removed
     * @return point
     *         the removed point, null if point doesn't exist
     */
    public Point[] remove(Point pt) {
        Point[] result = { null };
        PRQuadtreeNode tempPoint = root.remove(pt, 0, 0, 1024, result);

        if (result == null) {
            return null;
        }
        root = tempPoint;
        size--;

        return result;
    }


    /**
     * obtains linked list of duplicate points within quadtree
     *
     * @return list
     *         list of duplicate points
     */
    public LinkedList<String> duplicates() {
        LinkedList<String> results = new LinkedList<String>();
        root.getDuplicates(results);

        return results;
    }


    /**
     * searches a given region for all points contained in it
     *
     * @param x
     *            x coordinate of region
     * @param y
     *            y coordinate of region
     * @param w
     *            width of region
     * @param h
     *            height of region
     * @param numEncountered
     *            number of nodes visited in tree
     * @return list
     *         list of all points found within region
     */
    public LinkedList<Point> regionSearch(
        int x,
        int y,
        int w,
        int h,
        int[] numEncountered) {
        LinkedList<Point> result = new LinkedList<Point>();
        result = root.regionSearch(x, y, w, h, result, 0, 0, 1024,
            numEncountered);

        return result;
    }


    // ========================= dump =========================
    /**
     * creates a dump of the contents of the prquadtree
     *
     * @return contents
     *         contents of tree in string format
     */
    public LinkedList<String> dump() {
        int[] numEncountered = { 0 };
        LinkedList<String> nodes = new LinkedList<String>();

        // if tree has no points
        if (size == 0) {
            nodes.add("Node at 0, 0, 1024: Empty");
            nodes.add("QuadTree Size: 1 QuadTree Nodes Printed.");
        }
        else {
            // 0, 0 is top left corner of entire region
            // 1024 is the border width of the entire region
            // nodes is the linkedlist of strings to be printed one by one
            // 0 is the initial spacing
            // numEncountered counts the number of nodes printed
            root.getContents(0, 0, 1024, nodes, 0, numEncountered);
            nodes.add("QuadTree Size: " + numEncountered
                + " QuadTree Nodes Printed.");
        }

        return nodes;
    }
    // ====================== end of dump =====================

    // ====================== emptynode class =====================
    /**
     * EmptyNode inner class
     *
     * @author Dominic Jimeno (dmjimeno0105)
     *
     * @version 9/27/2022
     *
     */
    public class EmptyNode implements PRQuadtreeNode {

        /**
         * EmptyNode default constructor
         */
        public EmptyNode() {
            // Purposely left empty because EmptyNodes should be null
        }


        /**
         * Inserts a new leaf node with the specified point's attributes in
         * place of
         * the empty node
         *
         * @param pt
         *            point to be inserted
         * @param x
         *            x coordinate of the point
         * @param y
         *            y coordinate of the point
         * @param border
         *            the point's associated border size
         * @return new node that was inserted
         */
        public PRQuadtreeNode insert(Point pt, int x, int y, int border) {
            // TODO
            LeafNode newNode = new LeafNode();
            newNode.insert(pt, x, y, border);
            return newNode;
        }


        // ================== emptynode getContents() =================
        /**
         * emptynode getContents()
         *
         * Iterates through quad tree nodes list and formats each node's x and y
         * coordinates, as well as their border sizes into a string to be output
         * for dump
         *
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param result
         *            list containing point attributes in string format
         * @param spacing
         *            integer value used for handling the spacing required for
         *            dump
         *            output
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return result
         */
        public LinkedList<String> getContents(
            int x,
            int y,
            int border,
            LinkedList<String> result,
            int spacing,
            int[] numEncountered) {
            StringBuilder str = new StringBuilder();
            StringBuilder handleSpacing = new StringBuilder();

            // this just adds spacing spaces to handleSpacing
            for (int i = 0; i < spacing; i++) {
                handleSpacing.append("  ");
            }
            // this adds the required spacing before the actual contents
            str.append(handleSpacing.toString());
            // this adds the contents of the node region
            str.append("Node at " + x + ", " + y + ", " + border + ": Empty");
            // this adds the str string to the linkedlist of strings
            result.add(str.toString());
            // nodes encountered count increases by one
            numEncountered[0]++;
            // return the linkedlist of strings
            return result;
        }
        // ================== emptynode getContents() =================


        /**
         * Intended behavior: Finds duplicate nodes within the quad tree
         * Actual behavior: this is an empty node, so just return the input list
         * of
         * nodes
         *
         * @param nodes
         *            list of duplicate points in the quad tree
         * @return list of duplicate points in the quad tree, in string format
         */

        public LinkedList<String> getDuplicates(LinkedList<String> nodes) {
            return nodes;
        }


        /**
         * Intended behavior: removes a point given that point's x and y
         * coordinates
         * Actual behavior: Tries to remove empty node from tree, does nothing
         * in
         * return
         *
         * @param removePt
         *            point to be removed
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param removed
         *            point to remove, extracted and put into an array of remove
         *            points
         * @return this
         */

        public PRQuadtreeNode remove(
            Point removePt,
            int x,
            int y,
            int border,
            Point removed) {

            return this;
        }


        /**
         * Intended behavior: removes a point given that point's x and y
         * coordinates
         * Actual behavior: Tries to remove empty node from tree, does nothing
         * in
         * return
         *
         * @param removeX
         *            x coordinate of point to be removed
         * @param removeY
         *            y coordinate of point to be removed
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param removed
         *            point to remove, extracted and put into an array of remove
         *            points
         * @return this
         */

        public PRQuadtreeNode remove(
            int removeX,
            int removeY,
            int x,
            int y,
            int border,
            Point removed) {
            // TODO
            return this;
        }


        /**
         * Search a region for points
         *
         * @param regionX
         *            x coordinate of region
         * @param regionY
         *            y coordinate of region
         * @param w
         *            width of region
         * @param h
         *            height of region
         * @param result
         *            all points found within the region
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return result
         */
        public LinkedList<Point> regionSearch(
            int regionX,
            int regionY,
            int w,
            int h,
            LinkedList<Point> result,
            int x,
            int y,
            int border,
            int numEncountered) {
            numEncountered++;
            return result;
        }


        @Override
        public boolean equals(Object node) {
            return ((PRQuadtreeNode)node).size() == 0;
        }


        @Override
        public int size() {
            return size();
        }


        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public LinkedList<Point> regionSearch(
            int regionX,
            int regionY,
            int w,
            int h,
            LinkedList<Point> result,
            int x,
            int y,
            int border,
            int[] numEncountered) {
            // TODO Auto-generated method stub
            return null;
        }


        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public PRQuadtreeNode remove(
            int removeX,
            int removeY,
            int x,
            int y,
            int border,
            Point[] removed) {
            // TODO Auto-generated method stub
            return null;
        }


        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public PRQuadtreeNode remove(
            Point removePoint,
            int x,
            int y,
            int border,
            Point[] removed) {
            // TODO Auto-generated method stub
            return null;
        }

    }


    // ======================== leafnode class ======================-
    /**
     * LeafNode inner class
     *
     * @author Dominic Jimeno (dmjimeno0105)
     *
     * @version 9/27/2022
     *
     */
    public class LeafNode implements PRQuadtreeNode {

        /**
         * List of points that this has reference to
         */
        private LinkedList<Point> points;

        /**
         * LeafNode default constructor
         */
        public LeafNode() {
            points = new LinkedList<Point>();
        }


        /**
         * LeafNode constructor, initializes nodes to list specified by
         * parameter
         *
         * @param newNodes
         *            New list of points that this has reference to
         */
        public LeafNode(LinkedList<Point> newNodes) {
            points = newNodes;
        }


        /**
         * inserts a point into the PR QuadTree. If the amount of points
         * contained
         * in
         * the list of nodes of this leaf node is or exceeds 3, then it becomes
         * an
         * internal node. Because nodes in a PR quadtree must be distinct, the
         * internal node should not be created unless there are 3 or more
         * distinct
         * points contained in this
         *
         * @param pt
         *            point to be inserted
         * @param x
         *            x coordinate of the region
         * @param y
         *            y coordinate of the region
         * @param border
         *            the point's associated border size
         * @return node that was inserted
         */
        public PRQuadtreeNode insert(Point pt, int x, int y, int border) {
            // TODO
            if (points.size() < 3) {
                points.add(pt);
                return this;
            }
            int counter = 0;
            // compares point to points in the leafnode
            for (int i = 0; i < points.size(); i++) {
                if ((points.get(i)).compareTo(pt) == 0) {
                    counter++;
                }

            }

            // if point is equal to all points already in leafnode
            if (counter == points.size()) {
                points.add(pt);
                return this;
            }

            PRQuadtreeNode newInternalNode = new InternalNode();
            for (int i = 0; i < points.size(); i++) {
                newInternalNode = newInternalNode.insert(points.get(i), x, y,
                    border);
            }
            newInternalNode = newInternalNode.insert(pt, x, y, border);
            return newInternalNode;
        }


        // =================== leafnode getContents() ==================
        /**
         * leafnode getContents()
         *
         * Iterates through quad tree nodes list and formats each node's x and y
         * coordinates, as well as their border sizes into a string to be output
         * for
         * dump
         *
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param result
         *            list containing point attributes in string format
         * @param spacing
         *            integer value used for handling the spacing required for
         *            dump output
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return result
         */
        public LinkedList<String> getContents(
            int x,
            int y,
            int border,
            LinkedList<String> result,
            int spacing,
            int[] numEncountered) {
            // stringbuilders that will be added to appended to eachother and
            // added to linkedlist<string> result
            StringBuilder str = new StringBuilder();
            StringBuilder handleSpacing = new StringBuilder();
            // handles spacing before actual stuff gets printed
            for (int i = 0; i < spacing; i++) {
                handleSpacing.append("  ");
            }
            str.append(handleSpacing.toString());
            // beginning of actual stuff gets appended
            str.append("Node at " + x + ", " + y + ", " + border + ":");
            result.add(str.toString()); // and added into results
            // str.append(" ");

            for (int i = 0; i < points.size(); i++) {
                str = new StringBuilder();
                str.append(handleSpacing.toString() + "(" + points.get(i)
                    .toString() + ")");
                result.add(str.toString());
            }

            numEncountered[0]++;
            return result;
        }
        // =================== leafnode getContents() ==================


        /**
         * Finds duplicate nodes within the quad tree
         *
         * @param result
         *            list of duplicate points in the quad tree
         * @return list of duplicate points in the quad tree, in string format
         */
        public LinkedList<String> getDuplicates(LinkedList<String> result) {

            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point tempI = points.get(i);
                    Point tempJ = points.get(j);
                    if (tempI.compareTo(tempJ) == 0) {

                        StringBuilder str = new StringBuilder();
                        str.append(tempI.x());
                        str.append(", ");
                        str.append(tempI.y());

                        if (!(result.contains(str.toString()))) {
                            result.add(str.toString());
                        }
                    }
                }

            }

            return result;
        }


        /**
         * Iterates through list and attempts to remove a point given that
         * point's x
         * and y coordinates
         *
         * @param removeX
         *            x coordinate of point to be removed
         * @param removeY
         *            y coordinate of point to be removed
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param removed
         *            point to remove, extracted and put into an array of remove
         *            points
         * @return node that was checked
         */

        public PRQuadtreeNode remove(
            int removeX,
            int removeY,
            int x,
            int y,
            int border,
            Point[] removed) {
            // TODO
            for (int i = 0; i < points.size(); i++) {
                if (removeX == points.get(i).x() && removeY == points.get(i)
                    .y()) {
                    removed[0] = points.get(i);
                    points.remove(i);
                    break;
                }
            }
            if (points.size() == 0) {
                return flyweight;
            }
            return this;
        }


        /**
         * Iterates through list and attempts to remove a point
         *
         * @param removePt
         *            point to be removed
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param removed
         *            point to remove, extracted and put into an array of remove
         *            points
         * @return node that was checked
         */
        public PRQuadtreeNode remove(
            Point removePt,
            int x,
            int y,
            int border,
            Point[] removed) {
            for (int i = 0; i < points.size(); i++) {
                if (removePt.equals(points.get(i))) {
                    removed[0] = points.get(i);
                    points.remove(i);
                    break;
                }
            }
            if (points.size() == 0) {
                return flyweight;
            }
            return this;
        }


        /**
         * Search a region for points
         *
         * @param regionX
         *            x coordinate of region
         * @param regionY
         *            y coordinate of region
         * @param w
         *            width of region
         * @param h
         *            height of region
         * @param result
         *            all points found within the region
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return result
         */
        public LinkedList<Point> regionSearch(
            int regionX,
            int regionY,
            int w,
            int h,
            LinkedList<Point> result,
            int x,
            int y,
            int border,
            int[] numEncountered) {

            for (int i = 0; i < points.size(); i++) {
                int pointX = points.get(i).x();
                int pointY = points.get(i).y();

                if (pointX >= regionX && pointX < regionX + w
                    && pointY >= regionY && pointY < regionY + h) {
                    result.add(points.get(i));
                }
            }
            numEncountered[0]++;
            return result;

        }


        @Override
        public int size() {

            return this.size();
        }


        /**
         * stuff
         *
         * @param obj
         *            stuff
         *
         * @return boolean
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj == null)
                return false;
            if (this.getClass() == obj.getClass()) {
                PRQuadtreeNode other = (PRQuadtreeNode)obj;
                return size == ((PRQuadtreeNode)obj).size();
            }
            return false;
        }
    }


    // ========================= internalnode class =========================
    /**
     * InternalNode inner class
     *
     * @author Dominic Jimeno (dmjimeno0105)
     *
     * @version 9/27/2022
     *
     */
    public class InternalNode implements PRQuadtreeNode {
        private PRQuadtreeNode nw;
        private PRQuadtreeNode ne;
        private PRQuadtreeNode sw;
        private PRQuadtreeNode se;

        /**
         * InternalNode default constructor
         */
        public InternalNode() {
            nw = flyweight;
            ne = flyweight;
            sw = flyweight;
            se = flyweight;
        }


        /**
         * inserts point into one of four regions
         *
         * @param pt
         *            point to be inserted
         * @param x
         *            x coordinate of the region
         * @param y
         *            y coordinate of the region
         * @param border
         *            border width/length of entire region
         * @return node that was inserted
         */
        public PRQuadtreeNode insert(Point pt, int x, int y, int border) {
            // TODO
            int newBound = border / 2;
            int xBound = x + newBound;
            int yBound = x + newBound;

            // if point lies nw
            if (pt.x() < xBound && pt.y() < yBound) {

                nw = nw.insert(pt, x, y, newBound);
                return this;
            } // if point lies in ne
            else if (pt.x() >= xBound && pt.y() < yBound) {

                ne = ne.insert(pt, xBound, y, newBound);
                return this;
            } // if point lies in sw
            else if (pt.x() < xBound && pt.y() >= yBound) {

                sw = sw.insert(pt, x, yBound, newBound);
                return this;
            } // else point lies in se
            else {

                se = se.insert(pt, xBound, yBound, newBound);
                return this;
            }

        }


        // =================== internalnode getContents() ==================
        /**
         * internalnode getContents()
         *
         * Iterates through quad tree nodes list and formats each node's x and y
         * coordinates, as well as their border sizes into a string to be output
         * for
         * dump
         *
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param nodes
         *            list containing strings of node and points within
         * @param spacing
         *            integer value used for handling the spacing required for
         *            dump output
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return list containing point attributes in string format
         *
         */
        public LinkedList<String> getContents(
            int x,
            int y,
            int border,
            LinkedList<String> nodes,
            int spacing,
            int[] numEncountered) {
            // divides the border in half
            int newBound = border / 2;
            // str first deals with spacing
            String str = "";
            for (int i = 0; i < spacing; i++) {
                str = str + "  ";
            }
            // signal to the output viewer that an internalnode exists here
            str = str + "Node at " + x + ", " + y + ", " + border
                + ": Internal";
            nodes.add(str);

            // nodes and points found within this internalnode
            nodes = nw.getContents(x, y, newBound, nodes, spacing + 1,
                numEncountered);
            nodes = ne.getContents(x + newBound, y, newBound, nodes, spacing
                + 1, numEncountered);
            nodes = sw.getContents(x, y + newBound, newBound, nodes, spacing
                + 1, numEncountered);
            nodes = se.getContents(x + newBound, y + newBound, newBound, nodes,
                spacing + 1, numEncountered);

            // number of nodes found increases by one
            numEncountered[0]++;
            // return what was found within this internalnode (nodes and/or
            // points)
            return nodes;
        }
        // =================== internalnode getContents() ==================


        /**
         * Finds duplicate nodes within the quad tree
         *
         * @param nodes
         *            list of duplicate points in the quad tree
         * @return list of duplicate points in the quad tree, in string format
         */
        public LinkedList<String> getDuplicates(LinkedList<String> nodes) {

            if (nw != flyweight) {
                nodes = nw.getDuplicates(nodes);
            }
            if (ne != flyweight) {
                nodes = ne.getDuplicates(nodes);
            }
            if (sw != flyweight) {
                nodes = sw.getDuplicates(nodes);
            }
            if (se != flyweight) {
                nodes = se.getDuplicates(nodes);
            }
            return nodes;
        }


        /**
         * removes point from one of four regions
         *
         * @param ptX
         *            x coordinate of point to be removed
         * @param ptY
         *            y coordinate of point to be removed
         * @param x
         *            x coordinate of the region
         * @param y
         *            y coordinate of the region
         * @param borderWidth
         *            border width/length of entire region
         * @param removed
         *            list of points removed
         * @return node
         *         PRQuadtreeNode
         */

        public PRQuadtreeNode remove(
            int ptX,
            int ptY,
            int x,
            int y,
            int borderWidth,
            Point[] removed) {
            // TODO
            int newBound = borderWidth / 2;

            if (ptX < x + newBound && ptY < y + newBound) {
                nw = nw.remove(ptX, ptY, x, y, newBound, removed);
            }
            else if (ptX >= x + newBound && ptY < y + newBound) {
                ne = ne.remove(ptX, ptY, x + newBound, y, newBound, removed);
            }
            else if (ptX < x + newBound && ptY >= y + newBound) {
                sw = sw.remove(ptX, ptY, x, y + newBound, newBound, removed);
            }
            else {
                se = se.remove(ptX, ptY, x + newBound, y + newBound, newBound,
                    removed);
            }
            return merge();
        }


        /**
         * Intended behavior: removes a point given that point
         *
         * @param removePt
         *            point to be removed
         * @param x
         *            x coordinate within region being searched
         * @param y
         *            y coordinate within region being searched
         * @param border
         *            the point's associated border size
         * @param removed
         *            point to remove, extracted and put into an array of remove
         *            points
         * @return calls on merge class to merge internal nodes
         */
        public PRQuadtreeNode remove(
            Point removePt,
            int x,
            int y,
            int border,
            Point[] removed) {
            int newBound = border / 2;
            int ptX = removePt.x();
            int ptY = removePt.y();

            // if point lies in nw region
            if (ptX < x + newBound && ptY < y + newBound) {
                nw = nw.remove(removePt, x, y, newBound, removed);
            } // if point lies in ne region
            else if (ptX >= x + newBound && ptY < y + newBound) {
                ne = ne.remove(removePt, x + newBound, y, newBound, removed);
            } // if point lies in sw region
            else if (ptX < x + newBound && ptY >= y + newBound) {
                sw = sw.remove(removePt, x, y + newBound, newBound, removed);
            } // if point lies in se region
            else {
                se = se.remove(removePt, x + newBound, y + newBound, newBound,
                    removed);
            }
            return merge(); // call merge helper function
        }


        /**
         * Merges internal node's, if possible
         *
         * @return result
         *         merged nodes
         */
        private PRQuadtreeNode merge() {

            // if only nw has points
            if (nw.getClass().getName().compareTo("PRQuadtree$LeafNode") == 0
                && ne == flyweight && sw == flyweight && se == flyweight) {

                return nw;
            }
            // if only ne has points
            else if (ne.getClass().getName().compareTo(
                "PRQuadtree$LeafNode") == 0 && nw == flyweight
                && sw == flyweight && se == flyweight) {

                return ne;
            }
            // if only sw has points
            else if (sw.getClass().getName().compareTo(
                "PRQuadtree$LeafNode") == 0 && nw == flyweight
                && ne == flyweight && se == flyweight) {

                return sw;
            }
            // if only se has points
            else if (se.getClass().getName().compareTo(
                "PRQuadtree$LeafNode") == 0 && nw == flyweight
                && ne == flyweight && sw == flyweight) {

                return se;
            }

            else {
                LinkedList<Point> results = new LinkedList<Point>();

                // gathering all points within internal node
                if (nw.getClass().getName().compareTo(
                    "PRQuadtree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode)nw).points.size(); i++) {
                        Point point = ((LeafNode)nw).points.get(i);
                        results.add(point);
                    }

                }
                if (ne.getClass().getName().compareTo(
                    "PRQuadtree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode)ne).points.size(); i++) {
                        results.add(((LeafNode)ne).points.get(i));
                    }

                }
                if (sw.getClass().getName().compareTo(
                    "PRQuadtree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode)sw).points.size(); i++) {
                        results.add(((LeafNode)sw).points.get(i));
                    }

                }
                if (se.getClass().getName().compareTo(
                    "PRQuadtree$LeafNode") == 0) {
                    for (int i = 0; i < ((LeafNode)se).points.size(); i++) {
                        results.add(((LeafNode)se).points.get(i));
                    }

                }

                // if there are 3 or fewer points and the sub nodes within this
                // internal node are either leaves or empty
                if (results.size() == 3) {
                    return new LeafNode(results);
                }
                return this;
            }

        }


        /**
         * Searches for all points given a rectangle
         *
         * @param regionX
         *            x coordinate for region to be searched
         * @param regionY
         *            y coordinate for region to be searched
         * @param w
         *            of the region to be searched
         * @param h
         *            of the region to be searched
         * @param result
         *            list of points found within region
         * @param ptX
         *            x coordinate of point
         * @param ptY
         *            y coordinate of point
         * @param border
         *            the point's associated border size
         * @param numEncountered
         *            number of times a node is encountered during search
         * @return list of all points in the region
         */

        public LinkedList<Point> regionSearch(
            int regionX,
            int regionY,
            int w,
            int h,
            LinkedList<Point> result,
            int ptX,
            int ptY,
            int border,
            int[] numEncountered) {
            numEncountered[0]++;

            int newBound = border / 2;
            int xBound = ptX + newBound;
            int yBound = ptY + newBound;
            int xMax = regionX + w - 1;
            int yMax = regionY + h - 1;
            LinkedList<Point> inRegion = result;

            if (xBound > regionX && yBound > regionY) {

                inRegion = nw.regionSearch(regionX, regionY, w, h, result, ptX,
                    ptY, newBound, numEncountered);

            }

            if (xBound <= xMax && yBound > regionY) {

                inRegion = ne.regionSearch(regionX, regionY, w, h, inRegion,
                    xBound, ptY, newBound, numEncountered);

            }

            if (xBound > regionX && yBound <= yMax) {

                inRegion = sw.regionSearch(regionX, regionY, w, h, inRegion,
                    ptX, yBound, newBound, numEncountered);

            }

            if (xMax >= xBound && yMax >= yBound) {

                inRegion = se.regionSearch(regionX, regionY, w, h, inRegion,
                    xBound, yBound, newBound, numEncountered);

            }

            return inRegion;
        }


        @Override
        public int size() {
            return size();
        }


        @Override
        public boolean equals(Object node) {
            return ((PRQuadtreeNode)node).size() > 0;
        }

    }

}
