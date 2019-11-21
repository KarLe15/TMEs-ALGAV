package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import supportGUI.Circle;
import supportGUI.DiamRace;
import supportGUI.Line;

public class DefaultTeam {

    // calculDiametre: ArrayList<Point> --> Line
    //   renvoie une pair de points de la liste, de distance maximum.
    public Line calculDiametre(ArrayList<Point> points) {
        if (points.size()<3) {
            return null;
        }

        Point p=points.get(0);
        Point q=points.get(1);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Line(p,q);
    }

    // calculDiametreOptimise: ArrayList<Point> --> Line
    //   renvoie une pair de points de la liste, de distance maximum.
    public Line calculDiametreOptimise(ArrayList<Point> points) {
        if (points.size()<3) {
            return null;
        }

        Point p=points.get(1);
        Point q=points.get(2);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Line(p,q);
    }

    // calculCercleMin: ArrayList<Point> --> Circle
    //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    public Circle calculCercleMin(ArrayList<Point> points) {
        if (points.isEmpty()) {
            return null;
        }

        Point center=points.get(0);
        int radius=100;

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return new Circle(center,radius);
    }


    /*******************************************/


    public double produitVectoriel(Point pa, Point pb, Point pOther) {
        double x = pb.getX() - pa.getX();
        double y = pb.getY() - pa.getY();
        double xp =  (pb.getX() - pOther.getX());
        double yp =  (pb.getY() - pOther.getY());
        return x*yp - y*xp ;
    }

    public List<Point> filtrageParPixel(List<Point> points){
        List<Point> res = new ArrayList<>(points);
        for (int i = 0; i < points.size(); i++) {
            Point A = points.get(i);
            for (int j = 0; j < points.size(); j++) {
                if(i != j){
                    Point B = points.get(j);
                    int distXAB = (int)Math.abs(A.getX() - B.getX());
                    int distYAB = (int)Math.abs(A.getY() - B.getY());
                    for (int k = 0; k < points.size(); k++) {
                        if(k != j && k!= i){
                            Point C = points.get(k);
                            if( A.getX() == B.getX() && B.getX() == C.getX() ){
                                int distYAC = (int)Math.abs(C.getY() - A.getY());
                                int distYBC = (int)Math.abs(C.getY() - B.getY());
                                if( distYAB > distYAC && distYAB > distYBC){
                                    res.remove(C);
                                }else if (distYAC > distYAB && distYAC > distYBC){
                                    res.remove(B);
                                }else if( distYBC > distYAB && distYBC > distYAC){
                                    res.remove(A);
                                }
                            }else if ( A.getY() == B.getY() && B.getY() == C.getY() ){
                                int distXAC = (int)Math.abs(C.getX() - A.getX());
                                int distXBC = (int)Math.abs(C.getX() - B.getX());
                                if( distXAB > distXAC && distXAB > distXBC){
                                    res.remove(C);
                                }else if (distXAC > distXAB && distXAC > distXBC){
                                    res.remove(B);
                                }else if( distXBC > distXAB && distXBC > distXAC){
                                    res.remove(A);
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;

    }

    // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
    //   renvoie l'enveloppe convexe de la liste.
    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
        if (points.size()<3) {
            return null;
        }

//        points = (ArrayList<Point>) filtrageParPixel(points);
        ArrayList<Point> enveloppe = new ArrayList<Point>();

        int length = points.size();
        for (int i = 0; i < length; i++) {
            Point A = points.get(i);
            for (int j = 0; j < length; j++) {
                if(j == i){
                    continue;
                }
                Point B = points.get(j);
                boolean pos = false;
                boolean first = true;
                List<Point> temps = new ArrayList<>();
                int k;
                for (k = 0; k < length; k++) {
                    if(k ==j || k == i){
                        continue;
                    }
                    Point C = points.get(k);
                    double produitVect = produitVectoriel(A, B, C);
                    if (produitVect == 0.) {
                        temps.add(C);
                        continue;
                    }
                    if (first) {
                        pos = produitVect > 0;
                        first = false;
                    }
                    if (pos != (produitVect > 0)) {
                        break;
                    }
                }
                if (k == length) {
                    temps.add(A);
                    temps.add(B);
                    Point toAdd1 = new Point();
                    Point toAdd2 = new Point();
                    double max = -1;
                    for (int l = 0; l < temps.size(); l++) {
                        for (int m = l+1; m < temps.size(); m++) {
                            Point p1 = temps.get(l);
                            Point p2 = temps.get(m);

                            double distance = getDistanceOf2Points(p1, p2);
                            if (distance > max) {
                                max = distance;
                                toAdd1 = p1;
                                toAdd2 = p2;
                            }
                        }
                    }
                    if(!enveloppe.contains(toAdd1)){
                        enveloppe.add(toAdd1);
                    }
                    if(!enveloppe.contains(toAdd2)){
                        enveloppe.add(toAdd2);
                    }
                    //                enveloppe.addAll(temps);

                }
            }
//            System.out.println(i);
        }


        return enveloppe;
    }

    private double getDistanceOf2Points(Point p1, Point p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return (deltaX * deltaX) + (deltaY * deltaY) ;
    }



}
