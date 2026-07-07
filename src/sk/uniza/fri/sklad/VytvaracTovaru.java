package sk.uniza.fri.sklad;

import sk.uniza.fri.sklad.miestnosti.ISkladovaMiestnost;
import sk.uniza.fri.sklad.miestnosti.MalySklad;
import sk.uniza.fri.sklad.miestnosti.Miestnost;
import sk.uniza.fri.sklad.miestnosti.VelkySklad;
import sk.uniza.fri.sklad.osoby.Zakaznik;
import sk.uniza.fri.sklad.predmety.Regal;
import sk.uniza.fri.sklad.predmety.Tovar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Trieda sluzi na vytvorenie tovaru pomocou dialogoveho okna, do ktoreho zakaznik zadava udaje o tovare.
 * @author Juraj
 */
public class VytvaracTovaru {
    private final JDialog dialog;   //dialogove okno
    private JPanel hlavnyPanel;     //hlavny panel
    private JTextField id;          //text field na zadanie ID
    private JTextField nazov;       //text field na zadanie nazvu
    private JTextField vaha;        //text field na zadanie vahy
    private JTextField priezvisko;  //text field na zadanie priezviska
    private JTextField meno;        //text field na zadanie mena
    private JButton ulozButton;     //button na ulozenie tovaru
    private JButton zatvorButton;   //button na uzatvorenie okna
    private boolean oknoZatvorene;  //boolean reprezentujuci ci sa okno zatvorilo

    private final HashMap<String, Miestnost> zoznamMiestnosti;  //zoznam miestnosti skladu
    private final Zakaznik zakaznik;                            //prihlaseny zakaznik

    /**
     * Naplni atributy z parametrov, inicializuje a vytvori dialogove okno.
     * @param zoznamMiestnosti zoznam miestnosti
     * @param zakaznik prihlaseny zakaznik
     */
    public VytvaracTovaru(HashMap<String, Miestnost> zoznamMiestnosti, Zakaznik zakaznik) {
        this.zoznamMiestnosti = zoznamMiestnosti;
        this.zakaznik = zakaznik;
        this.oknoZatvorene = false;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Nastala chyba pri nastaveni temy GUI");
        }
        this.dialog = new JDialog();
        this.dialog.setTitle("Ulozenie tovaru");
        this.dialog.setContentPane(this.hlavnyPanel);
        this.dialog.setModal(true);
        this.dialog.setAlwaysOnTop(true);
        this.dialog.pack();
        this.dialog.setResizable(false);
        this.dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.ulozButton.setVisible(false);
        this.pridajListenery();
        this.dialog.setVisible(true);
    }

    /**
     * Vytvori novy tovar podla zadanych udajov, ak to je mozne.
     * @return Tovar
     */
    public Tovar vytvorTovar() {
        if (this.oknoZatvorene) {
            return null;
        }
        if (!((ISkladovaMiestnost)this.zoznamMiestnosti.get("prijemTovaru")).getRegal().maVolneMiesto()) {
            System.out.println("Regal v miestnosti na prijem tovaru je plny.");
            return null;
        }
        if (this.skontrolujVsetko()) {
            return new Tovar(this.id.getText(), this.nazov.getText(), Integer.parseInt(this.vaha.getText()), this.zakaznik,
                    new Zakaznik(this.meno.getText(), this.priezvisko.getText(),
                            (MalySklad)this.zoznamMiestnosti.get("prijemTovaru"), (MalySklad)this.zoznamMiestnosti.get("vydajTovaru")));
        } else {
            System.out.println("Vyplnene udaje nie su platne.");
            return null;
        }

    }

    /**
     * Prida vsetkym interaktivnym prvkom v okne listenery.
     */
    private void pridajListenery() {

        this.id.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                VytvaracTovaru.this.nastavViditelnostTlacitka();
            }
        });
        this.nazov.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                VytvaracTovaru.this.nastavViditelnostTlacitka();

            }
        });
        this.vaha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                VytvaracTovaru.this.nastavViditelnostTlacitka();
            }
        });
        this.meno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                VytvaracTovaru.this.nastavViditelnostTlacitka();

            }
        });
        this.priezvisko.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                VytvaracTovaru.this.nastavViditelnostTlacitka();

            }
        });

        this.ulozButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VytvaracTovaru.this.dialog.dispose();
            }
        });

        this.zatvorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VytvaracTovaru.this.oknoZatvorene = true;
                VytvaracTovaru.this.dialog.dispose();
            }
        });
    }

    /**
     * Skontroluje ci zadane id obsahuje aj ine znaky ako cisla, ak ano tak vrati false
     * @return boolean
     */
    private boolean skontrolujId() {
        if (this.id.getText().length() <= 0) {
            return false;
        }
        for (int i = 0; i < this.id.getText().length(); i++) {
            if (!Character.isDigit(this.id.getText().charAt(i))) {
                return false;
            }
        }
        return this.overUnikatnostID(this.id.getText());
    }

    /**
     * Skontroluje ci zadany nazov obsahuje aj ine znaky ako pismena/cisla, ak ano, vrati false
     * @return boolean
     */
    private boolean skontrolujNazov() {
        if (this.nazov.getText().length() <= 0) {
            return false;
        }
        for (int i = 0; i < this.nazov.getText().length(); i++) {
            if (!Character.isLetterOrDigit(this.nazov.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Skontroluje ci zadany nazov obsahuje aj ine znaky ako cisla, ak ano, vrati false
     * @return boolean
     */
    private boolean skontrolujVahu() {
        if (this.vaha.getText().length() <= 0) {
            return false;
        }
        for (int i = 0; i < this.vaha.getText().length(); i++) {
            if (!Character.isDigit(this.vaha.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * Skontroluje ci zadany nazov obsahuje aj ine znaky ako pismena, ak ano, vrati false
     * @return boolean
     */
    private boolean skontrolujMeno() {
        if (this.meno.getText().length() <= 0) {
            return false;
        }
        for (int i = 0; i < this.meno.getText().length(); i++) {
            if (!Character.isLetter(this.meno.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * Skontroluje ci zadany nazov obsahuje aj ine znaky ako pismena, ak ano, vrati false
     * @return boolean
     */
    private boolean skontrolujPriezvisko() {
        if (this.priezvisko.getText().length() <= 0) {
            return false;
        }
        for (int i = 0; i < this.priezvisko.getText().length(); i++) {
            if (!Character.isLetter(this.priezvisko.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Skontroluje vsetky polia ci obsahuju platne udaje, ak ano, vrati true
     * @return boolean
     */
    private boolean skontrolujVsetko() {
        return (this.skontrolujMeno() && this.skontrolujVahu() && this.skontrolujNazov() && this.skontrolujId() && this.skontrolujPriezvisko());
    }

    /**
     * Zobrazi tlacidlo na ulozenie tovaru ak su vsetky udaje vyplnene spravne.
     */
    private void nastavViditelnostTlacitka() {
        this.ulozButton.setVisible(this.skontrolujVsetko());
    }

    /**
     * Overi ci sa tovar so zadanym id uz nenachadza v sklade. Ak ano, vrati false
     * @param id id tovaru
     * @return boolean
     */
    private boolean overUnikatnostID(String id) {
        for (Miestnost m : this.zoznamMiestnosti.values()) {
            if (m instanceof VelkySklad) {
                for (Regal r : ((VelkySklad)m).getZoznamRegalov()) {
                    for (Tovar t : r.getZoznamTovaru()) {
                        if (t != null) {
                            if (t.getId().equals(id)) {
                                return false;
                            }
                        }
                    }
                }
            } else {
                for (Tovar t : ((ISkladovaMiestnost)m).getRegal().getZoznamTovaru()) {
                    if (t != null) {
                        if (t.getId().equals(id)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }



}
