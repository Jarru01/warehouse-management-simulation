package sk.uniza.fri.sklad.miestnosti;

import sk.uniza.fri.sklad.osoby.Osoba;
import sk.uniza.fri.sklad.osoby.Pracovnik;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Zakladna miestnost ktora ma svoj nazov a obsahuje zoznam pracovnikov ktori sa v nej nachadzaju, s ktorymi miestnost
 * manipuluje.
 * @author Juraj
 */
public abstract class Miestnost implements Serializable {
    private final HashMap<String, Pracovnik> zoznamPracovnikov; //zoznam pracovnikov v miestnosti
    private final String popisMiestnosti;                       //popis miestnosti

    /**
     * Vytvori novu miestnost bez pracovnikov s nazvom z parametra.
     * @param popisMiestnosti nazov miestnosti
     */
    public Miestnost(String popisMiestnosti) {
        this.zoznamPracovnikov = new HashMap<>();
        this.popisMiestnosti = popisMiestnosti;
    }

    /**
     * Vrati nazov/popis miestnosti.
     * @return nazov/popis miestnosti
     */
    public String getPopisMiestnosti() {
        return this.popisMiestnosti;
    }

    /**
     * Vrati string so zoznamom pracovnikov v miestnosti.
     * @return string so zoznamom pracovnikov v miestnosti
     */
    public String vypisZoznamuPracovnikov() {

        String out = "Vypis pracovnikov v miestnosti " + this.getPopisMiestnosti() + ":" + '\n';
        int i = 1;
        StringBuilder zoznam = new StringBuilder();
        for (Osoba p : this.zoznamPracovnikov.values() ) {
            zoznam.append(i).append(". ").append(p.toString()).append('\n');
            i++;
        }
        zoznam = new StringBuilder(zoznam.toString().indent(2));
        out = out + zoznam;
        return out;
    }

    /**
     * Pokusi sa pridat pracovnika z parametra do miestnosti.
     * @param pracovnik pracovnik na pridatie
     */
    public void pridajPracovnika(Pracovnik pracovnik) {
        if (pracovnik == null) {
            System.out.println("Neexistujuci pracovnik!");
            return;
        }
        if (this.zoznamPracovnikov.get(pracovnik.getId()) == null) {
            this.zoznamPracovnikov.put(pracovnik.getId(), pracovnik);
        } else {
            System.out.println("Pracovnik sa uz nachadza v miestnosti!");
        }

    }

    /**
     * Pokusi sa odobrat pracovnika z parametra z miestnosti.
     * @param pracovnik pracovnik na odobratie
     */
    public void odoberPracovnika(Pracovnik pracovnik) {
        if (pracovnik == null) {
            System.out.println("Neexistujuci pracovnik!");
            return;
        }
        if (this.zoznamPracovnikov.get(pracovnik.getId()) != null) {
            this.zoznamPracovnikov.remove(pracovnik.getId());
        } else {
            System.out.println("Pracovnik sa nenachadza v miestnosti!");
        }

    }

}
