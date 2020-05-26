package com.company.graphs;

import com.company.model.Children;
import com.company.model.Connection;
import com.company.model.PointPosition;
import com.company.model.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;


public class BinaryTree extends JComponent {

    // region 0. Constants

    private static final int EMPTY = -1;
    private static final int NOT_DEFINED = -1;
    private static final boolean LEFT = false;
    private static final boolean RIGHT = true;
    private static final int RADIUS = 50;
    public static final int NOT_TYPE = 0;
    public static final int PREORDER = 1;
    public static final int INORDER = 2;
    public static final int POSTORDER = 3;
    private static final int GO_LEFT = 0;
    private static final int GO_RIGHT = 1;
    private static final int GO_FATHER = 2;

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
    private List<PointPosition> pointPositionList;
    private List<Children> childrenList;
    private TreeNode root;


    private String strText;
    private boolean algorithmRunning;
    private TreeNode tmpNode;
    private TreeNode tmpRoot;
    private Vector<Integer> fatherList;
    private List<Integer> output;
    private int[] directionForNumber;

    // endregion

    // region 2. Constructor

    public BinaryTree() {
        this.algorithmType = NOT_TYPE;
        this.numberPoints = 0;
        this.blnCanDrawPoint = false;
        this.blnCanConnect = false;
        this.blnCanDelete = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.root = null;

        this.fatherList = new Vector<>();

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
                    drawCircle(event.getX(), event.getY(), Color.GRAY);
                } else if(blnCanConnect) {
                    if(indexFromPoint != NOT_DEFINED && indexToPoint == NOT_DEFINED){
                        redrawImage();
                        if(matPoint[event.getX()][event.getY()] == EMPTY) {
                            drawConnection(
                                    pointPositionList.get(indexFromPoint).getX(),
                                    pointPositionList.get(indexFromPoint).getY(),
                                    event.getX(), event.getY(), Color.GRAY
                            );
                        }else{
                            drawConnection(indexFromPoint, matPoint[event.getX()][event.getY()], Color.GRAY);
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
        if(this.blnCanConnect)
            this.blnCanDrawPoint = false;
        redrawPoints();
    }

    private void setNewConnection(int x, int y){
        if(matPoint[x][y] != EMPTY) {
            if (this.pointPositionList.get(matPoint[x][y]).getY() >= this.pointPositionList.get(this.indexFromPoint).getY()){
                removeLastPoint();
                this.clearIndexes();
                this.blnCanConnect = false;
                redrawImage();
            }else if(this.indexToPoint == NOT_DEFINED && matPoint[x][y] != this.indexFromPoint){
                this.indexToPoint = matPoint[x][y];
                drawAndSetConnection();

                this.clearIndexes();
                this.blnCanConnect = false;
                this.blnCanDrawPoint = true;
                redrawImage();
            }
        }
    }

    private void switchDelete(int x, int y){
        if(this.matPoint[x][y] != EMPTY){
            deletePoint(x, y);
            //this.blnCanDelete = false;
        }
    }

    public void clearIndexes(){
        this.indexFromPoint = this.indexToPoint = NOT_DEFINED;
    }

    public void nextStep(){
        if(this.algorithmRunning) {
            switch (algorithmType) {
                case PREORDER:
                    preorderNext();
                    break;

                case INORDER:
                    inorderNext();
                    break;

                case POSTORDER:
                    postorderNext();
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

        this.graphics.setPaint(Color.black);

        this.repaint();

    }

    // endregion

    // region 6. Draw Point

    private void drawAndSetPoint(int x, int y){
        if(this.graphics != null && this.blnCanDrawPoint){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            setNewElementPoint(x, y);
            if(this.root == null){
                this.root = new TreeNode(this.numberPoints - 1);
            }else{
                this.blnCanConnect = true;
                this.indexFromPoint = this.numberPoints - 1;
            }
            drawCircle(x, y, Color.BLACK);
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

            if(color != Color.GRAY) {
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

    private void drawAndSetConnection(){
        Children children = this.childrenList.get(this.indexToPoint);

        int father = this.indexToPoint;
        int child = this.indexFromPoint;

        if(this.pointPositionList.get(father).getX() > this.pointPositionList.get(child).getX()){
            if(children.getLeft() == Children.NOT_DEFINED){
                children.setLeft(child);

                drawConnection(this.indexFromPoint, this.indexToPoint, Color.BLACK);
                setNewElementToNode(this.indexToPoint, this.indexFromPoint);
            }else{
                removeLastPoint();
            }
        }else if(this.pointPositionList.get(father).getX() < this.pointPositionList.get(child).getX()){
            if(children.getRight() == Children.NOT_DEFINED){
                children.setRight(child);

                drawConnection(this.indexFromPoint, this.indexToPoint, Color.BLACK);
                setNewElementToNode(this.indexToPoint, this.indexFromPoint);
            }else{
                removeLastPoint();
            }
        }

        redrawImage();

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

    private void drawConnection(int x1, int y1, int x2, int y2, Color color){
        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(
                x1, y1, x2, y2);
        this.graphics.setStroke(new BasicStroke(1));
        redrawNumbers();

        this.repaint();
    }

    // endregion

    // region 8. Matrix functions and methods

    private void createMatrix(){
        this.matPoint = new int[this.getSize().width][this.getSize().height];
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                this.matPoint[x][y] = EMPTY;
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

        this.childrenList.add(new Children());

        this.numberPoints++;
    }


    // endregion

    // region 9. Redraw functions

    public void redrawImage(TreeNode root, Color color){
        redrawPoints(root, color);
        redrawConnections(root, color);
    }

    public void redrawImage(){
        clearImage();
        redrawPoints();
        redrawConnections(this.root, Color.BLACK);
    }

    private void redrawConnections(TreeNode root, Color color) {
        if(root != null){
            int from = root.getVal();
            if (root.getLeft() != null) {
                int to = root.getLeft().getVal();
                drawConnection(from, to, color);
            }
            if(root.getRight() != null){
                int to = root.getRight().getVal();
                drawConnection(from, to, color);
            }
            redrawConnections(root.getLeft(), color);
            redrawConnections(root.getRight(), color);
        }
    }

    private void redrawPoints(TreeNode root, Color color){
        if(root != null){
            int num = root.getVal();
            PointPosition point = this.pointPositionList.get(num);
            int x = point.getX();
            int y = point.getY();
            if(this.matPoint[x][y] == point.getName()) {
                drawCircle(x, y, color);
            }

            redrawPoints(root.getLeft(), color);
            redrawPoints(root.getRight(), color);
        }
    }

    private void redrawPoints(){
        for(PointPosition point : this.pointPositionList){
            int x = point.getX();
            int y = point.getY();
            if(this.matPoint[x][y] == point.getName()) {
                drawCircle(x, y, Color.BLACK);
            }
        }
    }

    private void redrawNumbers(){
        for(PointPosition point : this.pointPositionList){

            int x = point.getX();
            int y = point.getY();

            if(this.matPoint[x][y] != EMPTY && point.getName() == matPoint[x][y]) {

                this.graphics.setPaint(Color.WHITE);
                this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);
            }

        }
    }


    // endregion

    // region 10. Delete methods

    private void removeLastPoint(){
        int num = this.numberPoints - 1;

        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if(matPoint[x][y] == num) {
                    matPoint[x][y] = EMPTY;
                }
            }
        }

        this.pointPositionList.remove(num);
        this.numberPoints--;
    }

    private void deletePoint(int x1, int y1){

        int num = this.matPoint[x1][y1];
        boolean[] b = new boolean[this.numberPoints];

        deleteNode(this.root, num, b);

        if(this.root.getVal() == num)
            this.root = null;

        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if(this.matPoint[x][y] != EMPTY && (b[this.matPoint[x][y]] || this.matPoint[x][y] == num)) {
                    this.matPoint[x][y] = EMPTY;
                }
            }
        }

        redrawImage();

    }

    // endregion

    // region 11. TreeNode methods

    private void setNewElementToNode(int father, int child){
        if(this.pointPositionList.get(father).getX() > this.pointPositionList.get(child).getX()){
            saveToNode(this.root, father, child, LEFT);
        }else{
            saveToNode(this.root, father, child, RIGHT);
        }
    }

    private void saveToNode(TreeNode root, int father, int child, boolean direction){
        if(root == null)
            return;

        if(root.getVal() == father){
            if(direction == LEFT){
                root.setLeft(new TreeNode(child));
            }else{
                root.setRight(new TreeNode(child));
            }
        }else{
            saveToNode(root.getLeft(), father, child, direction);
            saveToNode(root.getRight(), father, child, direction);
        }
    }

    private TreeNode findRootByIndex(TreeNode root, int index){
        if(root == null){
            return null;
        }else {

            if (root.getVal() == index) {
                return root;
            } else {
                TreeNode findLeft = findRootByIndex(root.getLeft(), index);
                if(findLeft != null){
                    return findLeft;
                }

                TreeNode findRight = findRootByIndex(root.getRight(), index);
                if(findRight != null){
                    return findRight;
                }
            }
        }

        return null;
    }

    private void deleteNode(TreeNode root, int num, boolean[] b){
        if(root != null){
            if(root.getVal() == num){
                b[root.getVal()] = true;
                this.childrenList.set(num, new Children());

                if(root.getLeft() != null)
                    deleteNode(root.getLeft(), root.getLeft().getVal(), b);
                if(root.getRight() != null)
                    deleteNode(root.getRight(), root.getRight().getVal(), b);

            }else{
                deleteNode(root.getLeft(), num, b);
                deleteNode(root.getRight(), num, b);
                Children children = this.childrenList.get(root.getVal());

                if(root.getLeft() != null && root.getLeft().getVal() == num) {
                    root.setLeft(null);
                    children.setLeft(Children.NOT_DEFINED);
                }

                if(root.getRight() != null && root.getRight().getVal() == num) {
                    root.setRight(null);
                    children.setRight(Children.NOT_DEFINED);
                }
            }
        }
    }

    private void deleteNode(TreeNode root, int father, int child){
        if(root != null){
            if(root.getVal() == father){
                if(root.getLeft() != null && root.getLeft().getVal() == child){
                    root.setLeft(null);
                }else if(root.getRight() != null && root.getRight().getVal() == child){
                    root.setRight(null);
                }
            }else{
                deleteNode(root.getLeft(), father, child);
                deleteNode(root.getRight(), father, child);
            }
        }
    }

    // endregion

    // region 12. Getters and Setters

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
        if(this.blnCanConnect){
            removeLastPoint();
            redrawImage();
            this.blnCanDrawPoint = false;
        }else {
            this.blnCanDrawPoint = blnCanDrawPoint;
        }
    }

    public boolean getAlgorithmRunning() {
        return algorithmRunning;
    }

    public String getStrText() {
        return strText;
    }

    public boolean getBlnCanConnect() {
        return blnCanConnect;
    }

    public boolean getBlnCanDrawPoint() {
        return blnCanDrawPoint;
    }

    public boolean getBlnCanDelete() {
        return blnCanDelete;
    }

    public boolean getBlnCanRunAlgorithm(){
        return (this.root != null);
    }

    private String getMessage() {
        StringBuilder mess = new StringBuilder("Output: ");
        for (Integer num : this.output){
            mess.append(num).append(" ");
        }
        return String.valueOf(mess);
    }

    public String getStrFatherList(){
        StringBuilder strFatherList = new StringBuilder();

        createFatherList();
        for (int i = 0; i < this.fatherList.size(); i++) {
            if(this.fatherList.get(i) != null){
                strFatherList.append(i).append(" => ").append(this.fatherList.get(i)).append("\n");
            }
        }

        return String.valueOf(strFatherList);
    }

    // endregion

    // region 13. Father List

    private void createFatherList(){
        this.fatherList.clear();
        this.fatherList.setSize(this.numberPoints);

        for (int father = 0; father < this.childrenList.size(); father++) {
            int left = this.childrenList.get(father).getLeft();
            int right = this.childrenList.get(father).getRight();

            if(left != Children.NOT_DEFINED)
                this.fatherList.set(left, father);

            if(right != Children.NOT_DEFINED)
                this.fatherList.set(right, father);

        }

    }

    // endregion

    // region 14. Preorder

    private void preorderNext(){
        int num = this.tmpNode.getVal();

        if(this.directionForNumber[num] == GO_LEFT && !this.output.contains(num)){
            this.output.add(num);
            this.strText = " Print: " + num + "!";
            return;
        }

        switch (this.directionForNumber[num]){
            case GO_LEFT:
                TreeNode leftNode = this.tmpNode.getLeft();
                if(leftNode != null){
                    saveToNode(this.tmpRoot, num, leftNode.getVal(), LEFT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = leftNode;

                    this.strText = "The path continues on the left side.";
                }else{
                    this.strText = "The path can't continue on the left side.";
                }
                break;

            case GO_RIGHT:
                TreeNode rightNode = this.tmpNode.getRight();
                if(rightNode != null){
                    saveToNode(this.tmpRoot, num, rightNode.getVal(), RIGHT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = rightNode;

                    this.strText = "The path continues on the right side.";
                }else{
                    this.strText = "The path can't continue on the right side.";
                }
                break;

            case GO_FATHER:
                TreeNode fatherNode;
                if(this.fatherList.get(num) == null)
                    fatherNode = null;
                else
                    fatherNode = findRootByIndex(this.root, this.fatherList.get(num));

                if(fatherNode != null){
                    deleteNode(this.tmpRoot, fatherNode.getVal(), num);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = fatherNode;

                    this.strText = "Returning to the root.";
                }else{
                    redrawImage();
                    JOptionPane.showMessageDialog(this, getMessage());

                    this.strText = "End of the algorithm.";
                    this.algorithmRunning = false;
                }
                break;
        }

        this.directionForNumber[num]++;

    }

    public void preorder(){
        createFatherList();
        this.directionForNumber = new int[this.numberPoints];
        this.output = new ArrayList<>();
        this.tmpNode = this.root;
        this.tmpRoot = new TreeNode(this.root.getVal());
        redrawImage(this.tmpRoot, Color.RED);

        this.algorithmRunning = true;
    }

    // endregion

    // region 15. Inorder

    private void inorderNext(){
        int num = this.tmpNode.getVal();

        if(this.directionForNumber[num] == GO_RIGHT && !this.output.contains(num)){
            this.output.add(num);
            this.strText = " Print: " + num + "!";
            return;
        }

        switch (this.directionForNumber[num]){
            case GO_LEFT:
                TreeNode leftNode = this.tmpNode.getLeft();
                if(leftNode != null){
                    saveToNode(this.tmpRoot, num, leftNode.getVal(), LEFT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = leftNode;

                    this.strText = "The path continues on the left side.";
                }else{
                    this.strText = "The path can't continue on the left side.";
                }
                break;

            case GO_RIGHT:
                TreeNode rightNode = this.tmpNode.getRight();
                if(rightNode != null){
                    saveToNode(this.tmpRoot, num, rightNode.getVal(), RIGHT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = rightNode;

                    this.strText = "The path continues on the right side.";
                }else{
                    this.strText = "The path can't continue on the right side.";
                }
                break;

            case GO_FATHER:
                TreeNode fatherNode;
                if(this.fatherList.get(num) == null)
                    fatherNode = null;
                else
                    fatherNode = findRootByIndex(this.root, this.fatherList.get(num));

                if(fatherNode != null){
                    deleteNode(this.tmpRoot, fatherNode.getVal(), num);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = fatherNode;

                    this.strText = "Returning to the root.";
                }else{
                    redrawImage();
                    JOptionPane.showMessageDialog(this, getMessage());

                    this.strText = "End of the algorithm.";
                    this.algorithmRunning = false;
                }
                break;
        }

        this.directionForNumber[num]++;
    }

    public void inorder(){
        createFatherList();
        this.directionForNumber = new int[this.numberPoints];
        this.output = new ArrayList<>();
        this.tmpNode = this.root;
        this.tmpRoot = new TreeNode(this.root.getVal());
        redrawImage(this.tmpRoot, Color.RED);

        this.algorithmRunning = true;
    }

    // endregion

    // region 16. Postorder

    private void postorderNext(){
        int num = this.tmpNode.getVal();

        if(this.directionForNumber[num] == GO_FATHER && !this.output.contains(num)){
            this.output.add(num);
            this.strText = " Print: " + num + "!";
            return;
        }

        switch (this.directionForNumber[num]){
            case GO_LEFT:
                TreeNode leftNode = this.tmpNode.getLeft();
                if(leftNode != null){
                    saveToNode(this.tmpRoot, num, leftNode.getVal(), LEFT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = leftNode;

                    this.strText = "The path continues on the left side.";
                }else{
                    this.strText = "The path can't continue on the left side.";
                }
                break;

            case GO_RIGHT:
                TreeNode rightNode = this.tmpNode.getRight();
                if(rightNode != null){
                    saveToNode(this.tmpRoot, num, rightNode.getVal(), RIGHT);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = rightNode;

                    this.strText = "The path continues on the right side.";
                }else{
                    this.strText = "The path can't continue on the right side.";
                }
                break;

            case GO_FATHER:
                TreeNode fatherNode;
                if(this.fatherList.get(num) == null)
                    fatherNode = null;
                else
                    fatherNode = findRootByIndex(this.root, this.fatherList.get(num));

                if(fatherNode != null){
                    deleteNode(this.tmpRoot, fatherNode.getVal(), num);
                    redrawImage();
                    redrawImage(this.tmpRoot, Color.RED);
                    this.tmpNode = fatherNode;

                    this.strText = "Returning to the root.";
                }else{
                    redrawImage();
                    JOptionPane.showMessageDialog(this, getMessage());

                    this.strText = "End of the algorithm.";
                    this.algorithmRunning = false;
                }
                break;
        }

        this.directionForNumber[num]++;
    }

    public void postorder(){
        createFatherList();
        this.directionForNumber = new int[this.numberPoints];
        this.output = new ArrayList<>();
        this.tmpNode = this.root;
        this.tmpRoot = new TreeNode(this.root.getVal());
        redrawImage(this.tmpRoot, Color.RED);

        this.algorithmRunning = true;
    }

    // endregion

}
