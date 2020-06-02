package com.company.graphs;

import com.company.model.Connection;
import com.company.model.PointPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;


public class Graph extends JComponent {

    // region 0. Constants

    private static final int EMPTY = -1;
    private static final int NOT_DEFINED = -1;
    private static final double NULL_MESSAGE = -1;
    private static final int INFINITY = -1;
    private static final boolean IT_WAS = true;
    private static final boolean FIND_NEW_NEIGHBOR = true;
    private static final int RADIUS = 50;
    public static final int NOT_TYPE = 0;
    public static final int BREADTH_FIRST_SEARCH = 1;
    public static final int DEPTH_FIRST_SEARCH = 2;
    public static final int DIJKSTRA = 3;
    public static final int KRUSKAL = 4;
    private static final Color COLOR_TMP = Color.GRAY;
    private static final Color COLOR_MAIN = Color.BLACK;
    private static final Color COLOR_ALG = Color.RED;

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int algorithmType;
    private int numberPoints;
    private int indexFromPoint;
    private int indexToPoint;
    private boolean blnCanDrawPoint;
    private boolean blnCanConnect;
    private boolean blnCanDelete;

    private int[][] matPoint;
    private int[][] matConnection;
    private List<PointPosition> pointPositionList;
    private ArrayList<Connection> connectionList;
    private ArrayList<Connection> connectionListAll;


    private String strText;
    private boolean algorithmRunning;
    private int indexKruskal;
    private int[] comp;
    private double[] distance;
    private boolean[] b;
    private Queue<Integer> queue;
    private Deque<Integer> stack;
    private Vector<ArrayList<Integer>> wayFromInitialPoint;
    private Vector<ArrayList<Integer>> adjacencyList;

    // endregion

    // region 2. Constructor

    public Graph() {
        this.algorithmType = NOT_TYPE;
        this.numberPoints = 0;
        this.blnCanDrawPoint = false;
        this.blnCanConnect = false;
        this.blnCanDelete = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.connectionList = new ArrayList<>();
        this.connectionListAll = new ArrayList<>();
        this.adjacencyList = new Vector<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();

                if(blnCanDrawPoint && matPoint[x][y] == EMPTY) {
                    setNewPoint(x, y);
                } else if(blnCanConnect) {
                    setNewConnection(x, y);
                } else if(blnCanDelete) {
                    switchDelete(x, y);
                } else if(algorithmType != NOT_TYPE && matPoint[x][y] != EMPTY) {
                    switch (algorithmType){
                        case BREADTH_FIRST_SEARCH:
                            breadthFirstSearch(matPoint[x][y]);
                            break;

                        case DEPTH_FIRST_SEARCH:
                            depthFirstSearch(matPoint[x][y]);
                            break;

                        case DIJKSTRA:
                            dijkstra(matPoint[x][y]);
                            break;
                    }
                }

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent event) {}

            @Override
            public void mouseMoved(MouseEvent event) {
                if(blnCanDrawPoint){
                    redrawImage();
                    drawCircle(event.getX(), event.getY(), COLOR_TMP);
                } else if(blnCanConnect) {
                    if(indexFromPoint != NOT_DEFINED && indexToPoint == NOT_DEFINED){
                        redrawImage();
                        if(matPoint[event.getX()][event.getY()] == EMPTY) {
                            drawConnection(
                                    pointPositionList.get(indexFromPoint).getX(), pointPositionList.get(indexFromPoint).getY(), event.getX(), event.getY(), COLOR_TMP
                            );
                        }else{
                            drawConnection(indexFromPoint, matPoint[event.getX()][event.getY()], COLOR_TMP);
                        }
                    }
                }
            }
        });

    }

    // endregion

    // region 3. Main functions

    private void setNewPoint(int x, int y){
        drawAndSetPoint(x, y);
        //this.blnCanDrawPoint = false;
        redrawPoints();
    }

    private void setNewConnection(int x, int y){
        if(matPoint[x][y] != EMPTY){
            if(this.indexFromPoint == NOT_DEFINED){
                this.indexFromPoint = matPoint[x][y];
            } else if(this.indexToPoint == NOT_DEFINED && matPoint[x][y] != this.indexFromPoint && getWeightFromConnectionList(this.indexFromPoint, this.matPoint[x][y]) == -1){
                this.indexToPoint = matPoint[x][y];
                double weight = openWindow();

                if (weight != NULL_MESSAGE && weight >= 0) {
                    drawAndSetConnection(weight);
                }

                this.clearIndexes();
                //this.blnCanConnect = false;
                redrawImage();
            }
        }
    }

    private void switchDelete(int x, int y){
        if(this.matPoint[x][y] != EMPTY){
            deletePoint(x, y);
            //this.blnCanDelete = false;
        } else if(this.matConnection[x][y] != EMPTY){
            deleteConnection(x, y);
            //this.blnCanDelete = false;
        }
    }

    private double openWindow(){

        String string = JOptionPane.showInputDialog(this, "What's the weight?");

        double weight;

        if(string == null)
            weight = NULL_MESSAGE;
        else {
            try {
                weight = Double.parseDouble(string);
            } catch (Exception error) {
                weight = 0.0;
            }
        }
        return weight;

    }

    public void clearIndexes(){
        this.indexFromPoint = this.indexToPoint = NOT_DEFINED;
    }

    public void nextStep(){
        if(this.algorithmRunning) {
            switch (algorithmType) {
                case BREADTH_FIRST_SEARCH:
                    breadthFirstSearchNext();
                    break;

                case DEPTH_FIRST_SEARCH:
                    depthFirstSearchNext();
                    break;

                case DIJKSTRA:
                    dijkstraNext();
                    break;

                case KRUSKAL:
                    kruskalNext();
                    break;
            }
        }
    }

    // endregion

    // region 4. Painting

    @Override
    protected void paintComponent(Graphics g) {

        if(this.image == null){
            this.image = this.createImage(this.getSize().width, this.getSize().height);
            createMatrix();

            this.graphics = (Graphics2D) this.image.getGraphics();
            this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            this.clearImage();
        }
        g.drawImage(this.image, 0, 0, null);

    }

    // endregion

    // region 5. Clear the paint

    public void clearImage(){
        this.graphics.setPaint(Color.white);

        this.graphics.fillRect(0, 0, getSize().width, getSize().height);
        this.graphics.setPaint(getForeground());

        this.graphics.setPaint(COLOR_MAIN);

        this.repaint();

    }

    // endregion

    // region 6. Draw Point

    private void drawAndSetPoint(int x, int y){
        if(this.graphics != null && this.blnCanDrawPoint){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            setNewElementPoint(x, y);
            drawCircle(x, y, COLOR_MAIN);
        }
    }

    private void drawCircle(PointPosition pointPosition, Color color){
        if(this.graphics != null){

            int x = pointPosition.getX();
            int y = pointPosition.getY();

            this.graphics.setPaint(color);
            this.graphics.fillOval(
                    x - RADIUS /2,y - RADIUS /2, RADIUS, RADIUS
            );

            if(color != Color.GRAY) {
                this.graphics.setPaint(Color.WHITE);
                this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);
            }

            this.repaint();
        }
    }

    public void drawCircle(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            if(color != COLOR_TMP) {
                this.graphics.setPaint(Color.WHITE);
                this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);
            }

            this.repaint();
        }
    }

    private void drawRing(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.drawOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            this.repaint();
        }
    }

    // endregion

    // region 7. Draw Connections

    private void drawAndSetConnection(double weight){
        drawConnection(this.indexFromPoint, this.indexToPoint, Color.CYAN);
        setNewElementConn(weight);
        drawConnection(this.indexFromPoint, this.indexToPoint, COLOR_MAIN, weight);
    }

    private void drawConnection(int indexFromPoint, int indexToPoint, Color color){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(
                pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());

        this.graphics.setStroke(new BasicStroke(1));
        redrawNumbers();

        this.repaint();
    }

    private void drawConnection(int indexFromPoint, int indexToPoint, Color color, double weight){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        int x1 = pointFrom.getX();
        int y1 = pointFrom.getY();
        int x2 = pointTo.getX();
        int y2 = pointTo.getY();

        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(x1, y1, x2, y2);
        this.graphics.setStroke(new BasicStroke(1));

        int strX = (x1 + x2)/2;
        int strY = (y1 + y2)/2;

        if((x1 <= x2 && y1 <= y2) || (x2 <= x1 && y2 <= y1)){
            this.graphics.drawString(String.valueOf(weight), strX + 5, strY - 6);
        }else {
            this.graphics.drawString(String.valueOf(weight), strX + 6, strY + 16);
        }

        redrawNumbers();

        this.repaint();
    }

    private void drawConnection(int x1, int y1, int x2, int y2, Color color){
        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(
                x1, y1, x2, y2);
        this.graphics.setStroke(new BasicStroke(1));
        redrawNumbers();

        this.repaint();
    }

    private void drawWay(int index){
        ArrayList<Integer> way = this.wayFromInitialPoint.get(index);
        for (int i = 0; i < way.size() - 1; i++) {
            int from = way.get(i);
            int to = way.get(i+1);
            drawCircle(this.pointPositionList.get(from), COLOR_ALG);
            drawCircle(this.pointPositionList.get(to), COLOR_ALG);
            drawConnection(from, to, COLOR_ALG, getWeightFromConnectionList(from, to));
        }
        drawCircle(this.pointPositionList.get(way.get(0)), COLOR_ALG);
    }

    // endregion

    // region 8. Matrix functions and methods

    private void createMatrix(){
        this.matPoint = new int[this.getSize().width][this.getSize().height];
        this.matConnection = new int[this.getSize().width][this.getSize().height];
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                matPoint[x][y] = EMPTY;
                matConnection[x][y] = EMPTY;
            }
        }
    }

    private void setNewElementPoint(int pointX, int pointY){
        BufferedImage bufferedImage = (BufferedImage) this.image;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.matPoint[x][y] = this.numberPoints;
                }
            }
        }

        PointPosition point = new PointPosition(numberPoints, pointX, pointY);
        this.pointPositionList.add(point);

        this.numberPoints++;
    }

    private void setNewElementConn(double weight){
        BufferedImage bufferedImage = (BufferedImage) this.image;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.matConnection[x][y] = this.connectionListAll.size();
                }
            }
        }


        this.connectionList.add(
                new Connection(this.indexFromPoint, this.indexToPoint, weight)
        );
        this.connectionListAll.add(
                new Connection(this.indexFromPoint, this.indexToPoint, weight)
        );

    }

    // endregion

    // region 9. Redraw functions

    public void redrawImage(){
        clearImage();
        redrawPoints();
        redrawConnections();
    }

    private void redrawConnections() {
        for (Connection connection : this.connectionList){
            drawConnection(connection.getFromPoint(), connection.getToPoint(), COLOR_MAIN, connection.getWeight());
        }
    }

    private void redrawPoints(){
        for(PointPosition point : this.pointPositionList){
            int x = point.getX();
            int y = point.getY();
            if(this.matPoint[x][y] == point.getName()) {
                drawCircle(x, y, COLOR_MAIN);
            }
        }
    }

    private void redrawNumbers(){
        for(PointPosition point : this.pointPositionList){

            int x = point.getX();
            int y = point.getY();

            if(this.matPoint[x][y] == EMPTY){
                continue;
            }

            this.graphics.setPaint(Color.WHITE);
            this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);

        }
    }

    public void redrawImage(boolean[] b){
        clearImage();
        redrawPoints();
        redrawConnections(b);
    }

    private void redrawConnections(boolean[] b) {
        int index = 0;
        for (Connection connection : this.connectionList){
            if(b[index])
                drawConnection(connection.getFromPoint(), connection.getToPoint(), COLOR_MAIN, connection.getWeight());
            index++;
        }
    }

    // endregion

    // region 10. Delete methods

    private void deletePointFromMatrix(int num){
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if(this.matPoint[x][y] == num){
                    this.matPoint[x][y] = EMPTY;
                }
            }
        }
    }

    private void deleteConnectionFromMatrix(int ind){
        Connection con = this.connectionListAll.get(ind);

        PointPosition fromPos = this.pointPositionList.get(con.getFromPoint());
        PointPosition toPos = this.pointPositionList.get(con.getToPoint());

        int xMin = Math.min(fromPos.getX(), toPos.getX());
        int xMax = Math.max(fromPos.getX(), toPos.getX());
        int yMin = Math.min(fromPos.getY(), toPos.getY());
        int yMax = Math.max(fromPos.getY(), toPos.getY());

        int xStart = Math.max(0, xMin - 10);
        int xEnd = Math.min(this.getSize().width, xMax + 10);
        int yStart = Math.max(0, yMin - 10);
        int yEnd = Math.min(this.getSize().height, yMax + 10);

        for(int x = xStart; x < xEnd; x++){
            for (int y = yStart; y < yEnd; y++) {
                if(this.matConnection[x][y] == ind){
                    this.matConnection[x][y] = EMPTY;
                }
            }
        }
    }

    private void deleteConnectionsNumber(int num){
        for (int i = 0; i < this.connectionListAll.size(); i++) {
            int from = this.connectionListAll.get(i).getFromPoint();
            int to = this.connectionListAll.get(i).getToPoint();

            if (from == num || to == num)
                deleteConnectionByIndex(i);
        }
    }

    private void deletePoint(int x, int y){
        int num = this.matPoint[x][y];
        deletePointFromMatrix(num);
        deleteConnectionsNumber(num);
        redrawImage();
    }

    private void deleteConnectionByIndex(int ind){
        Connection con = this.connectionListAll.get(ind);

        for (int i = 0; i < this.connectionList.size(); i++) {
            if(this.connectionList.get(i).equals(con)){
                this.connectionList.remove(i);
                break;
            }
        }

        deleteConnectionFromMatrix(ind);
    }

    private void deleteConnection(int x, int y){
        deleteConnectionByIndex(this.matConnection[x][y]);
        redrawImage();
    }

    // endregion

    // region 11. Getters and Setters

    public void setBlnCanDelete(boolean blnCanDelete) {
        this.blnCanDelete = blnCanDelete;
    }

    public void setAlgorithmType(int algorithmType) {
        this.algorithmType = algorithmType;
    }

    public void setBlnCanConnect(boolean blnCanConnect) {
        this.blnCanConnect = blnCanConnect;
    }

    public void setBlnCanDrawPoint(boolean blnCanDrawPoint) {
        this.blnCanDrawPoint = blnCanDrawPoint;
    }

    public boolean getAlgorithmRunning() {
        return algorithmRunning;
    }

    public String getStrText() {
        return strText;
    }

    private double getWeightFromConnectionList(int a, int b){
        for (Connection connection : connectionList){
            if((connection.getFromPoint() == a && connection.getToPoint() == b) || (connection.getFromPoint() == b && connection.getToPoint() == a))
                return connection.getWeight();
        }
        return -1.0;
    }

    public boolean getBlnCanDrawPoint() {
        return blnCanDrawPoint;
    }

    public boolean getBlnCanConnect() {
        return blnCanConnect;
    }

    public boolean getBlnCanDelete() {
        return blnCanDelete;
    }

    public String getStrConnectionList(){
        StringBuilder strConList = new StringBuilder();

        for(Connection connection : this.connectionList){
            strConList.append(connection.getFromPoint()).append(" - ").append(connection.getToPoint()).append("     |  ").append(connection.getWeight()).append("\n");
        }
        return strConList.toString();
    }

    // endregion

    //region 12. Adjacency list methods

    private void addToAdjacencyList(int from, int to){
        ArrayList<Integer> arrayList = adjacencyList.get(from);
        arrayList.add(to);
        adjacencyList.set(from, arrayList);
    }

    private void createAdjacencyList(){
        this.adjacencyList.clear();
        this.adjacencyList.setSize(this.numberPoints);
        for (int i = 0; i < this.numberPoints; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            adjacencyList.set(i, list);
        }

        for (Connection connection : connectionList){
            int from = connection.getFromPoint();
            int to = connection.getToPoint();
            addToAdjacencyList(from, to);
            addToAdjacencyList(to, from);
        }
    }

    // endregion

    // region 13. Breadth-First Search

    private int watchNeighborBFS(int index, boolean[] b, Queue<Integer> queue){
        ArrayList<Integer> neighbors = adjacencyList.get(index);

        int numberOfNeighbours = 0;

        for(Integer neighbor : neighbors){
            if(b[neighbor] != IT_WAS){
                drawConnection(index, neighbor, COLOR_ALG, this.getWeightFromConnectionList(index, neighbor));
                drawRing(pointPositionList.get(neighbor).getX(), pointPositionList.get(neighbor).getY(), COLOR_ALG);
                queue.add(neighbor);
                b[neighbor] = IT_WAS;
                numberOfNeighbours++;
            }
        }
        return numberOfNeighbours;
    }

    private void breadthFirstSearchNext(){
        if(!queue.isEmpty()){
            int index = queue.remove();
            drawCircle(pointPositionList.get(index).getX(), pointPositionList.get(index).getY(), COLOR_ALG);
            int number = watchNeighborBFS(index, b, queue);
            this.strText = "Watching the neighbor(s) of the point " + String.valueOf(index) + "  and finding " + number + " new neighbor(s).";
        }
        if(queue.isEmpty()){
            this.algorithmRunning = false;
        }

    }

    private void breadthFirstSearch(int first){
        createAdjacencyList();
        this.queue = new LinkedList<>();
        this.b = new boolean[this.numberPoints];
        this.queue.add(first);
        this.b[first] = IT_WAS;
        this.algorithmRunning = true;
        redrawImage();
        drawRing(pointPositionList.get(first).getX(), pointPositionList.get(first).getY(), COLOR_ALG);
    }

    // endregion

    // region 14. Depth-First Search

    private boolean watchNeighborDFS(int index, boolean[] b, Deque<Integer> stack){
        ArrayList<Integer> neighbors = adjacencyList.get(index);

        for(Integer neighbor : neighbors){
            if(b[neighbor] != IT_WAS){
                stack.push(neighbor);
                b[neighbor] = IT_WAS;

                drawRing(pointPositionList.get(neighbor).getX(), pointPositionList.get(neighbor).getY(), COLOR_ALG);
                drawConnection(index, neighbor, COLOR_ALG, this.getWeightFromConnectionList(index, neighbor));

                this.strText = "Watching the neighbor(s) of the point " + index + " and finding the " + neighbor + " point.";
                return FIND_NEW_NEIGHBOR;
            }
        }

        return !FIND_NEW_NEIGHBOR;
    }

    private void depthFirstSearchNext(){
        if(!stack.isEmpty()){
            int index = this.stack.getFirst();
            boolean cond = watchNeighborDFS(index, this.b, this.stack);
            drawCircle(pointPositionList.get(index), COLOR_ALG);
            if(cond != FIND_NEW_NEIGHBOR){
                stack.pop();
                this.strText = "Watching the neighbor(s) of the point " + index + " and there is no neighbor.";
            }
        }
        if(stack.isEmpty()){
            this.algorithmRunning = false;
        }
    }

    private void depthFirstSearch(int first){
        createAdjacencyList();
        this.stack = new ArrayDeque<>();
        this.b = new boolean[this.numberPoints + 1];
        this.stack.push(first);
        this.b[first] = IT_WAS;
        this.algorithmRunning = true;
        redrawImage();
        drawRing(pointPositionList.get(first).getX(), pointPositionList.get(first).getY(), COLOR_ALG);
    }

    // endregion

    // region 15. Dijkstra

    private int findMinimalDistanceIndex(){
        int ind = -1;
        for (int i = 0; i < this.numberPoints; i++) {
            if(this.distance[i] != INFINITY){
                if((ind == -1 || this.distance[i] < this.distance[ind]) && this.b[i] != IT_WAS){
                    ind = i;
                }
            }
        }
        return ind;
    }


    private String watchNeighbourDij(int index, double[] dis){
        ArrayList<Integer> neighbors = adjacencyList.get(index);
        StringBuilder message = new StringBuilder();

        for(Integer neighbor : neighbors){
            double newDis = dis[index] + getWeightFromConnectionList(index, neighbor);
            if(dis[neighbor] == INFINITY || newDis < dis[neighbor]){
                dis[neighbor] = newDis;
                ArrayList<Integer> way = (ArrayList<Integer>) this.wayFromInitialPoint.get(index).clone();
                way.add(neighbor);
                this.wayFromInitialPoint.set(neighbor, way);
                if(message.toString().equals("")){
                    message = new StringBuilder(String.valueOf(neighbor));
                }else {
                    message.append(", ").append(neighbor);
                }
                drawConnection(index, neighbor, COLOR_ALG);
                drawCircle(this.pointPositionList.get(neighbor), COLOR_ALG);

            }
        }
        return message.toString();
    }


    private boolean[] numbersInTable(){
        boolean[] b = new boolean[this.numberPoints];
        Arrays.fill(b, false);

        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if(this.matPoint[x][y] != EMPTY){
                    b[this.matPoint[x][y]] = true;
                }
            }
        }

        return b;
    }

    private String getMessage(){
        StringBuilder mess = new StringBuilder();

        int first = 0;
        for (int i = 0; i < this.numberPoints; i++) {
            if(this.distance[i] == 0) {
                first = i;
                break;
            }
        }

        boolean b[] = numbersInTable();
        for (int i = 0; i < this.numberPoints; i++) {
            if(!b[i] || i == first)
                continue;

            if(this.distance[i] != INFINITY)
                mess.append("From the point ").append(first).append(" to the point ").append(i).append(" the weight of the shortest way is ").append(this.distance[i]).append(".\n");
            else
                mess.append("Between the points ").append(first).append(" and ").append(i).append(" there isn't any way\n");
        }
        return mess.toString();
    }

    private void dijkstraNext(){
        redrawImage();
        int ind = findMinimalDistanceIndex();
        if(ind != -1){
            String message = watchNeighbourDij(ind, this.distance);
            this.b[ind] = IT_WAS;
            if(!message.equals("")) {
                this.strText = "From the point" + ind + " to " + message + " the actual shortest way(s).";
                drawWay(ind);
            }else{
                this.strText = "From the point " + ind + " there is no more way.";
            }
        }
        if(ind == -1){
            JOptionPane pane = new JOptionPane();
            JDialog dialog = pane.createDialog(this,"Output");
            pane.setMessage(getMessage());
            dialog.setSize(new Dimension(500,300));
            dialog.show();
            //JOptionPane.showMessageDialog(this, getMessage());

            this.algorithmRunning = false;
        }
    }

    private void dijkstra(int first){
        createAdjacencyList();
        this.distance = new double[this.numberPoints];
        this.b = new boolean[this.numberPoints];
        this.wayFromInitialPoint = new Vector<>();
        this.wayFromInitialPoint.setSize(this.numberPoints);
        for (int i = 0; i < this.numberPoints; i++) {
            this.distance[i] = INFINITY;
            ArrayList<Integer> list = new ArrayList<>();
            if(i == first)
                list.add(first);
            this.wayFromInitialPoint.set(i, list);
        }
        this.distance[first] = 0;
        this.algorithmRunning = true;
        redrawImage();
        drawRing(pointPositionList.get(first).getX(), pointPositionList.get(first).getY(), COLOR_ALG);
    }

    // endregion

    // region 16. Kruskal

    private void sortingConnectionList(){
        boolean ok = true;
        int len = connectionList.size();

        while(ok){
            ok = false;
            for (int i = 0; i < len - 1; i++) {
                if(connectionList.get(i).getWeight() > connectionList.get(i+1).getWeight()){
                    Connection tmp = connectionList.get(i);
                    connectionList.set(i, connectionList.get(i+1));
                    connectionList.set(i+1, tmp);

                    ok = true;
                }
            }

            len--;
        }
    }

    private void connectTwoComponent(int from, int to){
        int number = this.comp[to];
        for (int i = 0; i < this.numberPoints; i++) {
            if(this.comp[i] == number){
                this.comp[i] = this.comp[from];
            }
        }
    }

    private void kruskalNext(){
        if(this.indexKruskal < connectionList.size()){
            int from = connectionList.get(this.indexKruskal).getFromPoint();
            int to = connectionList.get(this.indexKruskal).getToPoint();
            double weight = connectionList.get(this.indexKruskal).getWeight();
            if(comp[from] != comp[to]){
                this.b[this.indexKruskal] = true;
                connectTwoComponent(from, to);

                drawCircle(pointPositionList.get(from), COLOR_ALG);
                drawCircle(pointPositionList.get(to), COLOR_ALG);
                drawConnection(from, to, COLOR_ALG, weight);

                this.strText = "Watching the point " + String.valueOf(from) + " and  " + String.valueOf(to) + " and connect them.";
            } else{
                this.strText = "Watching the point" + String.valueOf(from) + " and the " + String.valueOf(to) + "  and don't connect them.";
            }

            this.indexKruskal++;
        }
        else if(this.indexKruskal == connectionList.size()) {
            redrawImage(this.b);
            this.strText = "Get the optimal tree graph.";
            this.algorithmRunning = false;
        }
    }

    public void kruskal(){
        sortingConnectionList();

        this.comp = new int[this.numberPoints];
        this.b = new boolean[this.connectionList.size()];
        for (int i = 0; i < this.numberPoints; i++) {
            this.comp[i] = i;
        }

        this.indexKruskal = 0;
        this.algorithmRunning = true;
    }

    // endregion

}
