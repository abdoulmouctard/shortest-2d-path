import java.io.PrintStream;
//import java.util.Scanner;


class Postscript {
    static String nom = "POSTSCRIPT.eps";
    static PrintStream old;

    public static void init(String name) {
        nom = name;
        try {
            PrintStream old = System.out;
            PrintStream ps = new PrintStream(nom);
            System.setOut(ps);
            System.out.print("%!PS\n");
        } catch (Exception e) {
        }
    }

    public static void englob_x1_y1_x2_y2(int x1, int x2, int y1, int y2) {
        System.out.print("%%BoundingBox " + x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
    }

    public static void newpath() {
        System.out.print("newpath\n");
    }

    public static void moveto(int x, int y) {
        System.out.print(x + " " + y + " moveto\n");
    }

    public static void lineto(int x, int y) {
        System.out.print(x + " " + y + " lineto\n");
    }

    public static void setcolor(double r, double v, double b) {
        System.out.print(r + " " + v + " " + b + " setrgbcolor\n");
    }

    public static void setlinewidth(int w) {
        System.out.print(w + " setlinewidth\n");
    }

    public static void closepath() // fermer le contour courant
    {
        System.out.print("closepath\n");
    }


    public static void stroke() // por tracer
    {
        System.out.print("stroke\n");
    }

    public static void close() {
        try {
            System.out.print("showpage\n");
            System.setOut(old);
        } catch (Exception e) {
        }
    }

    public static void main(String arg[]) {
        init("myPS.eps");
        englob_x1_y1_x2_y2(0, 0, 500, 500);
        newpath();
        setlinewidth(5);
        setcolor(1., 0., 0.);
        moveto(100, 100);
        lineto(400, 100);
        lineto(400, 400);
        lineto(100, 400);
        //lineto( 100, 100);
        closepath();
        stroke();

        newpath();
        setlinewidth(1);
        setcolor(0., 0., 0.);
        moveto(200, 200);
        lineto(300, 300);
        moveto(200, 300);
        lineto(300, 200);
        stroke();

        close();
    }


}

class LI { // gerer les listes d'entiers
    int elt;
    LI next;

    static LI cons(int x, LI tail) {
        LI cell = new LI();
        cell.elt = x;
        cell.next = tail;
        return cell;
    }

    static boolean isempty(LI liste) {
        return liste == null;
    }

    static int head(LI liste) {
        return liste.elt;
    }

    static LI tail(LI liste) {
        return liste.next;
    }

    static LI iaj(int i, int j) {
        if (i == j) return cons(i, null);
        return cons(i, iaj(i + 1, j));
    }
}

class Pt {
    double x, y;

    static Pt point(double u, double v) {
        Pt p = new Pt();
        p.x = u;
        p.y = v;
        return p;
    }

    void afficher(int nb) {

        System.out.print("X= " + this.x + " Y= " + this.y + "  " + nb);
        System.out.println();


    }
}

class LP {   // GERER LES LISTES DE POINTS
    Pt elt;
    LP next;

    static LP cons(Pt x, LP tail) {
        LP cell = new LP();
        cell.elt = x;
        cell.next = tail;
        return cell;
    }

    static boolean isempty(LP liste) {
        return liste == null;
    }

    static Pt head(LP liste) {
        return liste.elt;
    }

    static LP tail(LP liste) {
        return liste.next;
    }
}

class Arc {
    int v;
    double d;

    static Arc arc(int sommet, double distance) {
        Arc arc = new Arc();
        arc.v = sommet;
        arc.d = distance;
        return arc;
    }
}

class LA  // GERER LES LISTES D'ARCS
{
    Arc elt;
    LA next;

    static LA cons(Arc x, LA tail) {
        LA cell = new LA();
        cell.elt = x;
        cell.next = tail;
        return cell;
    }

    static boolean isempty(LA liste) {
        return liste == null;
    }

    static Arc head(LA liste) {
        return liste.elt;
    }

    static LA tail(LA liste) {
        return liste.next;
    }
}


public class Dijkstra {
    static int width = 1000, height = 1000;
    Pt[] tabpts;
    LA[] tabarcs;
    double[] dist; // distance a la source
    int[] pred; // tableau des predecesseurs dans le PCC de la source au sommet
    int t;

    static double square(double d) {
        return d * d;
    }


    // rend la liste des elements de rang pair (le premier est de rang 0 donc pair)
    static public LP pairs(LP l) {
        if (null == l) return null;
        if (LP.isempty(LP.tail(l))) return LP.cons(LP.head(l), null);
        return LP.cons(LP.head(l), pairs(LP.tail(LP.tail(l))));
    }

    // rend la liste des elements de rang impair
    static public LP impairs(LP l) {
        if (LP.isempty(l)) return null;
        return pairs(LP.tail(l));
    }

    // fusion de 2 listes de points triees
    static public LP fusion(LP l1, LP l2) {
        if (LP.isempty(l1)) return l2;
        if (LP.isempty(l2)) return l1;
        Pt e1 = LP.head(l1);
        Pt e2 = LP.head(l2);
        if (e1.x <= e2.x || (e1.x == e2.x && e1.y <= e2.y)) {
            return LP.cons(e1, fusion(LP.tail(l1), l2));
        }
        return LP.cons(e2, fusion(l1, LP.tail(l2)));
    }

    static public LP mergesort(LP l) {
        if (LP.isempty(l) || LP.isempty(LP.tail(l))) return l;
        return fusion(mergesort(pairs(l)), mergesort(impairs(l)));
    }

    int etape(TAS ls) // on met a jour les voisins du sommet a distance min de la source
    {
        if (0 == ls.nb)
            return (-1);
        int s = (int) ls.minimum();
        // ls.supprimer();

// ici s est le sommet le plus proche de la source
        for (LA la = tabarcs[s]; null != la; la = LA.tail(la))//CALCULE la distance pour quiter le sommet s a tous ces sommet suivant
        {
            Arc arc = LA.head(la);
            int v = arc.v;
            double sv = arc.d;
            if (dist[s] + sv < dist[v]) {
                dist[v] = dist[s] + sv;
                pred[v] = s;
            }
        }
        return s;
    }

    void enchainer(TAS ls) {
        for (; ; ) {
            int s = etape(ls);
            if (-1 == s) return;
            ls.supprimer();
        }
    }

    void PCC(int src) // calcul des + courts chemins
    {
        int n = tabarcs.length;
        dist = new double[n];
        for (int s = 0; s < n; s++) dist[s] = 1e10;
        dist[src] = 0.;
        pred = new int[n];
        for (int s = 0; s < n; s++) pred[s] = -1;
        pred[src] = src;
        TAS ls = new TAS(n);
        for (int i = 0; i < n; i++)
            ls.inserer(i);
        enchainer(ls);
// ici dist[] et pred[] sont corrects
    }

    void generer(int n, double rayon) {
        this.t = n;
        LP l = LP.cons(Pt.point(0., 0.), null);
        l = LP.cons(Pt.point(1., 1.), l);
        for (int i = 2; i < n; ) {
            double x = Math.random();
            double y = Math.random();
            if (0.1 <= x && x <= 0.3 && 0.1 <= y && y <= 0.3) continue;
            if (square(x - 0.6) + square(y - 0.6) < square(0.15)) continue;
            l = LP.cons(Pt.point(x, y), l);
            i++;
        }
        l = mergesort(l);
        tabpts = new Pt[n];

        for (int k = 0; l != null; l = LP.tail(l), k++) tabpts[k] = LP.head(l);
        tabarcs = new LA[n];
        for (int i = 0; i < n; i++) tabarcs[i] = null;
        Pt tabp[];

        for (int s = 0; s < 2; s++) {
            tabp = this.decouper(2, n, s, tabpts);


            if (s == 0) {

                double sx = tabp[s].x;
                double sy = tabp[s].y;
                for (int t = 1; t < tabp.length &&
                        (tabp[t].x - tabp[t - 1].x) <= rayon; t++) {
                    double tx = tabp[t].x;
                    double ty = tabp[t].y;
                    double st = Math.sqrt(square(tx - sx) + square(ty - sy));
                    if (st < rayon) {
                        tabarcs[t] = LA.cons(Arc.arc(s, st), tabarcs[t]);
                        tabarcs[s] = LA.cons(Arc.arc(t, st), tabarcs[s]);
                    }
                }
            } else {

                double sx = tabp[s].x;
                double sy = tabp[s].y;
                for (int t = (n / 2) + 1; t < n && tabp[t].x - tabp[s].x <= rayon; t++) {
                    double tx = tabp[t].x;
                    double ty = tabp[t].y;
                    double st = Math.sqrt(square(tx - sx) + square(ty - sy));
                    if (st < rayon) {
                        tabarcs[t] = LA.cons(Arc.arc(s, st), tabarcs[t]);
                        tabarcs[s] = LA.cons(Arc.arc(t, st), tabarcs[s]);
                    }
                }
            }


        }
        PCC(0);
    }


    void tracer_segment(int i, int j) {
        double scale = width;
        int xi = (int) (scale * tabpts[i].x);
        int yi = 10 + (int) (scale * tabpts[i].y);
        int xj = (int) (scale * tabpts[j].x);
        int yj = 10 + (int) (scale * tabpts[j].y);
        Postscript.moveto(xi, yi);
        Postscript.lineto(xj, yj);
    }

    public void dessin() {
        Postscript.init("samples.eps");
        Postscript.englob_x1_y1_x2_y2(0, width, 0, width);
        Postscript.newpath();
        double scale = width;
        for (int i = 0; i < tabpts.length; i++) {
            int xi = (int) (scale * tabpts[i].x);
            int yi = 10 + (int) (scale * tabpts[i].y);
            Postscript.moveto(xi - 1, yi);
            Postscript.lineto(xi + 1, yi);
            Postscript.moveto(xi, yi - 1);
            Postscript.lineto(xi, yi + 1);
        }
        Postscript.stroke();
        Postscript.close();

        Postscript.init("graphe.eps");
        Postscript.englob_x1_y1_x2_y2(0, width, 0, width);
        Postscript.newpath();
        for (int i = 0; i < tabarcs.length - 1; ++i) {
            for (LA l = tabarcs[i]; null != l; l = LA.tail(l)) {
                Arc arc = LA.head(l);
                int j = arc.v;
                tracer_segment(i, j);
            }
        }
        Postscript.stroke();
        Postscript.close();
        // desiner les + courts chemins
        Postscript.init("arbredesPCC.eps");
        Postscript.englob_x1_y1_x2_y2(0, width, 0, width);
        Postscript.newpath();
        for (int i = 0; i < tabarcs.length - 1; ++i) {
            int j = pred[i];
            if (j != -1) tracer_segment(i, j);
        }
        Postscript.stroke();
        Postscript.setcolor(1, 0, 0);
        for (int i = t - 1; i != 0; i = pred[i]) {
            tracer_segment(i, pred[i]);
        }
        Postscript.stroke();
        Postscript.close();

    }

    public class TAS {


        double tab[];
        int nb;

        TAS(int taille) {
            tab = new double[taille + 1];
            nb = 0;
        }

        double minimum() {
            assert (nb != 0);
            return tab[1];
        }

        void retablir_d(int k) // retablir le tas en descendant
        {
            int fg = 2 * k;
            int fd = fg + 1;
            if (fg > nb) return; // k n'a pas de fils
            if (fg == nb) // k a seulement un fils gauche
            {
                if (tab[fg] < tab[k]) {
                    swap(k, fg);
                    retablir_d(fg);
                }
                return;
            }
            // k a bien 2 fils
            if (tab[k] <= tab[fg] && tab[k] <= tab[fd]) return;
            // sinon on echange k avec son fils le plus petit
            if (tab[fg] < tab[fd]) {
                swap(k, fg);
                retablir_d(fg);
            } else {
                swap(k, fd);
                retablir_d(fd);
            }
        }

        void supprimer() {
            assert (nb != 0);
            tab[1] = tab[nb];
            nb = nb - 1;
            retablir_d(1);
        }

        void swap(int i, int j) {
            double tmp = tab[i];
            tab[i] = tab[j];
            tab[j] = tmp;
        }

        void retablir_m(int k) // retablir le tas en  montant
        {
            int papa = k / 2;
            if (0 == papa) return; // c'est ok
            if (tab[papa] > tab[k]) {
                swap(k, papa);
                retablir_m(papa);
            }
        }

        void ecrire() {
            for (int i = 1; i <= nb; i++) System.out.print(tab[i] + " ");
            System.out.println();
        }

        void inserer(double v) {
            assert (nb + 1 < tab.length);
            nb = nb + 1;
            tab[nb] = v;
            retablir_m(nb);
        }
    }


    Pt[] decouper(int nbdiv, int n, int z, Pt tab[]) {
        Pt t[] = new Pt[tab.length / nbdiv];

        int j = 0;


        if (z == 0)
            for (int i = 0; i < (z + 1) * n / nbdiv; i++) {
                // System.out.println(""+i);

                t[j] = tab[i];

            }
        else
            for (int i = z * n / nbdiv; i < (z + 1) * n / nbdiv; i++) {
                //	System.out.println(""+i);
                t[j] = tab[i];
            }
        return t;
    }


    public static void main(String arg[]) {
        Dijkstra f = new Dijkstra();
        f.generer(10000, 0.03);
        f.dessin();
    }
}






