package sk.uniza.fri.sklad.miestnosti;

import sk.uniza.fri.sklad.predmety.Regal;

import java.util.ArrayList;

/**
 * VelkySklad je potomok Miestnoti, ktory obsahuje zoznam regalov s tovarom, ktory je mozne upravovat.
 * @author Juraj
 */
public class VelkySklad extends Miestnost implements ISkladovaMiestnost  {
    private final ArrayList<Regal> zoznamRegalov;   //zoznam regalov v miestnosti

    /**
     * Vytvori velky sklad bez regalov s popisom z parametra.
     * @param popisMiestnosti popis miestnosti
     */
    public VelkySklad(String popisMiestnosti) {
        super(popisMiestnosti);
        this.zoznamRegalov = new ArrayList<>();
    }

    /**
     * Vrati volny regal v miestnosti.
     * @return volny regal
     */
    @Override
    public Regal getRegal() {
        if (this.zoznamRegalov.size() <= 0) {
            System.out.println("Miestnost neobsahuje regale.");
            return null;
        } else {
            for (Regal regal : this.zoznamRegalov) {
                if (regal.maVolneMiesto()) {
                    return regal;
                }
            }
            System.out.println("Regale su plne.");
            return null;

        }
    }

    /**
     * Vrati regal s danym indexom z parametra.
     * @param index index regalu
     * @return regal
     */
    public Regal getRegal(int index) {
        if (index <= this.zoznamRegalov.size() && index > 0) {
            return this.zoznamRegalov.get(index - 1);
        } else {
            System.out.println("Regal neexistuje.");
            return null;
        }
    }

    /**
     * Vrati string obsahujuci informacie o regaloch v miestnosti a tovaru v nich.
     * @return string obsahujuci informacie o regaloch v miestnosti a tovaru v nich
     */
    @Override
    public String vypisZoznamuRegalov() {
        String out = "Vypis regalov v miestnosti " + this.getPopisMiestnosti() + ":" + '\n';
        StringBuilder zoznam = new StringBuilder();
        if (this.zoznamRegalov.size() == 0) {
            zoznam = new StringBuilder("V miestnosti sa nenachadzaju regale.");
        }
        for (int i = 0; i < this.zoznamRegalov.size(); i++) {
            zoznam.append(i + 1).append(". ").append(this.zoznamRegalov.get(i).toString());
        }
        zoznam = new StringBuilder(zoznam.toString().indent(2));
        out = out + zoznam;
        return out;

    }

    /**
     * Prida novy regal s danou kapacitou z parametra.
     * @param kapacita kapacita regala
     */
    public void pridajRegal(int kapacita) {
        if (kapacita <= 0) {
            System.out.println("Zadal si neplatnu velkost regala.");
            return;
        }
        this.zoznamRegalov.add(new Regal(kapacita));
        System.out.println("Regal bol uspesne pridany.");
    }

    /**
     * Odstrani regal s danym indexom z parametra.
     * @param index index regalu
     */
    public void odstranRegal(int index) {
        if (index <= this.zoznamRegalov.size() && index > 0) {
            if (this.zoznamRegalov.get(index - 1).jePrazdny()) {
                this.zoznamRegalov.remove(index - 1);
                System.out.println("Regal bol uspesne odstraneny.");
            } else {
                System.out.println("Regal nieje prazdny.");
            }
        } else {
            System.out.println("Regal neexistuje.");
        }
    }

    /**
     * Vrati zoznam regalov v miestnosti.
     * @return zoznam regalov
     */
    public ArrayList<Regal> getZoznamRegalov() {
        return this.zoznamRegalov;
    }
}
