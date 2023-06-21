package droidgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;

public class DroidGame extends JPanel implements ActionListener {

    private final int UKURAN = 30; 
    private final int BARIS = 20; 
    private final int KOLOM = 20; 

    private int[][] peta;
    private int visionRadius = 1;
    private int visionRadiusAwal = 1;
    private Droid droidMerah;
    private Droid droidHijau;
    private Droid droidHijauAwal;
    private Timer timer;

    private JButton tombolMulai;
    private JButton tombolBerhenti;
    private JButton tombolAcakPeta;
    private JButton tombolAcakDroidMerah;
    private JButton tombolAcakDroidHijau;
    private JButton tombolTambahDroidMerah;
    private JButton tombolPandanganDroidMerah;
    private JButton tombolPandanganDroidHijau;
    private JSlider sliderJarakPandang;
    private JLabel labelJarakPandang;

    private boolean pandanganDroidMerah = false;
    private boolean pandanganDroidHijau = true;

    private boolean permainanBerakhir;
    
    private ArrayList<Droid> droidMerahList; 

    public DroidGame() {
        setPreferredSize(new Dimension(UKURAN * KOLOM + 220, UKURAN * BARIS));

        peta = generatePeta();

        droidMerah = new Droid(0, 0, Color.RED);
        droidHijau = new Droid(BARIS - 1, KOLOM - 1, Color.GREEN);

        timer = new Timer(500,this);

        tombolMulai = new JButton("Mulai");
        tombolMulai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });

        tombolBerhenti = new JButton("Berhenti");
        tombolBerhenti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
            }
        });

        tombolAcakPeta = new JButton("Acak Peta");
        tombolAcakPeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acakPeta();
            }
        });

        tombolAcakDroidMerah = new JButton("Acak Droid Merah");
        tombolAcakDroidMerah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acakDroidMerah();
            }
        });

        tombolAcakDroidHijau = new JButton("Acak Droid Hijau");
        tombolAcakDroidHijau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acakDroidHijau();
            }
        });
        
        tombolTambahDroidMerah = new JButton("Tambah Droid Merah");
        tombolTambahDroidMerah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tambahDroidMerah();
            }
        });
        
        tombolPandanganDroidMerah = new JButton("Pandangan Droid Merah");
        tombolPandanganDroidMerah.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                pandanganDroidMerah();
            }
        });
        
        sliderJarakPandang = new JSlider(1, 5, visionRadius);
        sliderJarakPandang.setMajorTickSpacing(1);
        sliderJarakPandang.setPaintTicks(true);
        sliderJarakPandang.setPaintLabels(true);
        sliderJarakPandang.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            visionRadius = sliderJarakPandang.getValue();
            repaint();
            }
        });

        labelJarakPandang = new JLabel(" Jarak Pandang Droid Hijau ");
        labelJarakPandang.setForeground(Color.BLACK);
        
        tombolPandanganDroidHijau = new JButton("Pandangan Droid Hijau");
        tombolPandanganDroidHijau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pandanganDroidHijau();
            }
        });


        tombolMulai.setBounds(UKURAN * KOLOM + 20, 10, 180, 30);
        tombolBerhenti.setBounds(UKURAN * KOLOM + 20, 50, 180, 30);
        tombolAcakPeta.setBounds(UKURAN * KOLOM + 20, 90, 180, 30);
        tombolAcakDroidMerah.setBounds(UKURAN * KOLOM + 20, 130, 180, 30);
        tombolAcakDroidHijau.setBounds(UKURAN * KOLOM + 20, 170, 180, 30);
        tombolTambahDroidMerah.setBounds(UKURAN * KOLOM + 20, 210, 180, 30);
        tombolPandanganDroidMerah.setBounds(UKURAN * KOLOM + 20, 250, 180, 30);
        labelJarakPandang.setBounds(UKURAN * KOLOM + 30, 290, 180, 30);
        sliderJarakPandang.setBounds(UKURAN * KOLOM + 20, 320, 180, 50);
        tombolPandanganDroidHijau.setBounds(UKURAN * KOLOM + 20, 380, 180, 30);

        setLayout(null);
        add(tombolMulai);
        add(tombolBerhenti);
        add(tombolAcakPeta);
        add(tombolAcakDroidMerah);
        add(tombolAcakDroidHijau);
        add(tombolTambahDroidMerah);
        add(tombolPandanganDroidMerah);
        add(sliderJarakPandang);
        add(labelJarakPandang);
        add(tombolPandanganDroidHijau);

    permainanBerakhir = false;
    droidMerahList = new ArrayList<>();
}

    private int[][] generatePeta() {
    int[][] peta = new int[BARIS][KOLOM];

    for (int baris = 0; baris < BARIS; baris++) {
        for (int kolom = 0; kolom < KOLOM; kolom++) {
            if (Math.random() < 0.3) {
                peta[baris][kolom] = 1; 
            } else {
                peta[baris][kolom] = 0; 
            }
        }
    }
  
    peta[0][0] = 0;

    if (peta[BARIS - 2][KOLOM - 1] != 1 && peta[BARIS - 1][KOLOM - 2] != 1) {
        peta[BARIS - 1][KOLOM - 1] = 0; 
    }

    return peta;
}

private void acakPeta() {
    peta = generatePeta();

    while (peta[droidMerah.getBaris()][droidMerah.getKolom()] == 1 || peta[droidHijau.getBaris()][droidHijau.getKolom()] == 1) {
        peta = generatePeta();
    }

    repaint();
}

private void acakDroidMerah() {
    int barisBaru;
    int kolomBaru;
    do {
        barisBaru = (int) (Math.random() * BARIS);
        kolomBaru = (int) (Math.random() * KOLOM);
    } while (peta[barisBaru][kolomBaru] == 1 || (barisBaru == droidHijau.getBaris() && kolomBaru == droidHijau.getKolom()));

    droidMerah = new Droid(barisBaru, kolomBaru, Color.RED);

    for (Droid droid : droidMerahList) {
        do {
            barisBaru = (int) (Math.random() * BARIS);
            kolomBaru = (int) (Math.random() * KOLOM);
        } while (peta[barisBaru][kolomBaru] == 1 || (barisBaru == droidHijau.getBaris() && kolomBaru == droidHijau.getKolom()));

        droid.setBaris(barisBaru);
        droid.setKolom(kolomBaru);
    }

    repaint();
}

private void acakDroidHijau() {
    int barisBaru;
    int kolomBaru;
    do {
        barisBaru = (int) (Math.random() * BARIS);
        kolomBaru = (int) (Math.random() * KOLOM);
    } while (peta[barisBaru][kolomBaru] == 1 || (barisBaru == droidMerah.getBaris() && kolomBaru == droidMerah.getKolom()));
    
    droidHijau = new Droid(barisBaru, kolomBaru, Color.GREEN);
    droidHijau = new Droid(barisBaru, kolomBaru, Color.GREEN);
    visionRadius = visionRadiusAwal; 
    sliderJarakPandang.setValue(visionRadius); 
    
    repaint();
}

private void tambahDroidMerah() {
    int barisBaru;
    int kolomBaru;
    do {
        barisBaru = (int) (Math.random() * BARIS);
        kolomBaru = (int) (Math.random() * KOLOM);
    } while (peta[barisBaru][kolomBaru] == 1 || (barisBaru == droidHijau.getBaris() && kolomBaru == droidHijau.getKolom()));

    Droid droidBaru = new Droid(barisBaru, kolomBaru, Color.RED);
    droidMerahList.add(droidBaru);
   
    repaint();
}

private void pandanganDroidMerah() {
    pandanganDroidMerah = !pandanganDroidMerah;
    if (pandanganDroidMerah) {
        droidHijauAwal = droidHijau;
        droidHijau = null; 
    } else {
       
        if (peta[BARIS - 2][KOLOM - 1] != 1 && peta[BARIS - 1][KOLOM - 2] != 1) {
            droidHijau = droidHijauAwal;
        }
    }
    repaint();
}

private void pandanganDroidHijau() {
    pandanganDroidHijau = !pandanganDroidHijau;
    repaint();
}

private void gerakDroid() {
    if (!permainanBerakhir) {
        droidMerah.gerak();
        droidHijau.gerak();

        if (droidMerah.getBaris() == droidHijau.getBaris() && droidMerah.getKolom() == droidHijau.getKolom()) {
            permainanBerakhir = true;
            timer.stop();
        }

        for (Droid droid : droidMerahList) {
            droid.gerak();
            if (droid.getBaris() == droidHijau.getBaris() && droid.getKolom() == droidHijau.getKolom()) {
                permainanBerakhir = true;
                timer.stop();
            }
        }

        repaint();
    }
}

private class Droid {
    private int baris;
    private int kolom;
    private Color warna;

    public Droid(int baris, int kolom, Color warna) {
        this.baris = baris;
        this.kolom = kolom;
        this.warna = warna;
    }

    public int getBaris() {
        return baris;
    }

    public int getKolom() {
        return kolom;
    }

    public Color getWarna() {
        return warna;
    }
     public void setBaris(int barisBaru) {
        this.baris = barisBaru;
    }

    public void setKolom(int kolomBaru) {
        this.kolom = kolomBaru;
    }

  public void gerak() {
    if (droidMerah == this) {
        gerakDroidMerah();
    } else if (droidMerahList.contains(this)) {
        gerakDroidMerah();
    } else {
        gerakDroidHijau();
    }
}

private void gerakDroidMerah() {
    List<Point> validMoves = new ArrayList<>();
     for (int i = 0; i < 4; i++) {
        int[] pergeseranBaris = {-1, 1, 0, 0};
        int[] pergeseranKolom = {0, 0, -1, 1};
        int barisBaru = baris + pergeseranBaris[i];
        int kolomBaru = kolom + pergeseranKolom[i];
         if (gerakanValid(barisBaru, kolomBaru)) {
            validMoves.add(new Point(barisBaru, kolomBaru));
        }
    }
    
    boolean droidHijauVisible = isVisible(new Point(baris, kolom), 
            new Point(droidHijau.getBaris(), droidHijau.getKolom()));
     if (droidHijauVisible) {
       
        validMoves.sort(Comparator.comparingDouble(move -> 
                heuristicCost(move, new Point(droidHijau.getBaris(), droidHijau.getKolom()))));
    } else {
      
        Collections.shuffle(validMoves);
    }
     if (!validMoves.isEmpty()) {
        Point nextMove = validMoves.get(0);
        baris = nextMove.x;
        kolom = nextMove.y;
    }
}

 private boolean isVisible(Point start, Point end) {
    int dx = Math.abs(end.x - start.x);
    int dy = Math.abs(end.y - start.y);
    int x = start.x;
    int y = start.y;
    int n = 1 + dx + dy;
    int x_inc = (end.x > start.x) ? 1 : -1;
    int y_inc = (end.y > start.y) ? 1 : -1;
    int error = dx - dy;
    dx *= 2;
    dy *= 2;
     for (; n > 0; --n) {
        if (!gerakanValid(x, y)) {
            return false;
        }
         if (x == end.x && y == end.y) {
            return true;
        }
         int e2 = error;
        if (e2 > -dy) {
            error -= dy;
            x += x_inc;
        }
        if (e2 < dx) {
            error += dx;
            y += y_inc;
        }
    }
     return false;
}

private double heuristicCost(Point start, Point goal) {
    return Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
}
    
private void gerakDroidHijau() {
    int[][] jarak = new int[BARIS][KOLOM];
    Queue<Point> queue = new LinkedList<>();
    boolean[][] visited = new boolean[BARIS][KOLOM];
    int[] pergeseranBaris = {-1, 1, 0, 0};
    int[] pergeseranKolom = {0, 0, -1, 1};

    for (int i = 0; i < BARIS; i++) {
        Arrays.fill(jarak[i], Integer.MAX_VALUE);
    }

    queue.add(new Point(baris, kolom));
    jarak[baris][kolom] = 0;
    visited[baris][kolom] = true;

    while (!queue.isEmpty()) {
        Point current = queue.poll();
        int currBaris = current.x;
        int currKolom = current.y;

        for (int i = 0; i < 4; i++) {
            int barisBaru = currBaris + pergeseranBaris[i];
            int kolomBaru = currKolom + pergeseranKolom[i];

            if (gerakanValid(barisBaru, kolomBaru) && !visited[barisBaru][kolomBaru]) {
                queue.add(new Point(barisBaru, kolomBaru));
                jarak[barisBaru][kolomBaru] = jarak[currBaris][currKolom] + 1;
                visited[barisBaru][kolomBaru] = true;
            }
        }
    }

    int maxJarak = Integer.MIN_VALUE;
    int gerakanTerbaik = -1;

    for (int i = 0; i < 4; i++) {
        int barisBaru = baris + pergeseranBaris[i];
        int kolomBaru = kolom + pergeseranKolom[i];

        if (gerakanValid(barisBaru, kolomBaru) && jarak[barisBaru][kolomBaru] > maxJarak) {
            maxJarak = jarak[barisBaru][kolomBaru];
            gerakanTerbaik = i;
        }
    }

    if (gerakanTerbaik != -1) {
        baris += pergeseranBaris[gerakanTerbaik];
        kolom += pergeseranKolom[gerakanTerbaik];
    }
}
    
    private boolean gerakanValid(int baris, int kolom) {
        if (baris >= 0 && baris < BARIS && kolom >= 0 && kolom < KOLOM && peta[baris][kolom] == 0) {
            return true;
        }

        return false;
    }
}

@Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Menggambar peta
    for (int baris = 0; baris < BARIS; baris++) {
        for (int kolom = 0; kolom < KOLOM; kolom++) {
            int x = kolom * UKURAN;
            int y = baris * UKURAN;

            if (peta[baris][kolom] == 1) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, UKURAN, UKURAN);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(x, y, UKURAN, UKURAN);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x, y, UKURAN, UKURAN);
        }
    }

    // Menggambar droid merah
    int merahX = droidMerah.getKolom() * UKURAN;
    int merahY = droidMerah.getBaris() * UKURAN;
    g.setColor(droidMerah.getWarna());
    g.fillOval(merahX, merahY, UKURAN, UKURAN);
    
    // Menggambar droid merah tambahan 
    for (Droid droid : droidMerahList) {
        int tambahanMerahX = droid.getKolom() * UKURAN;
        int tambahanMerahY = droid.getBaris() * UKURAN;
        g.setColor(droid.getWarna());
        g.fillOval(tambahanMerahX, tambahanMerahY, UKURAN, UKURAN);
    }

    if (droidHijau != null) {
    // Menggambar droid hijau
    int hijauX = droidHijau.getKolom() * UKURAN;
    int hijauY = droidHijau.getBaris() * UKURAN;
    g.setColor(droidHijau.getWarna());
    g.fillOval(hijauX, hijauY, UKURAN, UKURAN);
    }
    
    if (permainanBerakhir) {
        g.setColor(Color.RED);
        g.drawString("Permainan Berakhir", UKURAN * KOLOM / 2 - 30, UKURAN * BARIS / 2);
    }
}

@Override
public void actionPerformed(ActionEvent e) {
    gerakDroid();
}

public static void main(String[] args) {
    JFrame frame = new JFrame("Droid Game");  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    DroidGame droidGame = new DroidGame();
    frame.add(droidGame);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }
}
