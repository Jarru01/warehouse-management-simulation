package sk.uniza.fri.sklad.osoby;

import sk.uniza.fri.sklad.IIdentifikovatelny;
import sk.uniza.fri.sklad.miestnosti.ISkladovaMiestnost;
import sk.uniza.fri.sklad.miestnosti.Miestnost;
import sk.uniza.fri.sklad.miestnosti.VelkySklad;

import java.util.HashMap;

/**
 * Riaditel je potomok Osoby, ktory obsahuje dalsie atributy a metody specificke pre riaditela, ako napriklad prijatie,
 * vylucenie pracovnika.
 * @author Juraj
 */
public class Riaditel extends Osoba implements IIdentifikovatelny {
    private final HashMap<String, Pracovnik> zoznamPracovnikov;  //zoznam pracovnikov skladu
    private final HashMap<String, Miestnost> zoznamMiestnosti;   //zoznam miestnosti skladu
    private final String id;                                     //id riaditela

    /**
     * Vytvara riaditela s menom, priezivskom, zoznamomMiestnosti, zoznamomPracovnikov a ID z parametra.
     * @param meno meno riaditela
     * @param priezvisko priezvisko riaditela
     * @param zoznamMiestnosti zoznam miestnosti skladu
     * @param zoznamPracovnikov zoznam pracovnikov skladu
     * @param id id riaditela
     */
    public Riaditel(String meno, String priezvisko, HashMap<String, Miestnost> zoznamMiestnosti, HashMap<String, Pracovnik> zoznamPracovnikov, String id) {
        super(meno, priezvisko);
        this.zoznamPracovnikov = zoznamPracovnikov;
        this.zoznamMiestnosti = zoznamMiestnosti;
        this.id = id;
    }

    /**
     * Vrati id riaditela
     * @return id
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Vrati zoznam pracovnikov skladu.
     * @return zoznam pracovnikov
     */
    public HashMap<String, Pracovnik> getZoznamPracovnikov() {
        return this.zoznamPracovnikov;
    }

    /**
     * Vypise zoznam pracovnikov skladu vo vsetkych miestnostiach.
     */
    public void vypisZoznamPracovnikov() {
        System.out.println();
        if (this.zoznamPracovnikov.isEmpty()) {
            System.out.println("Sklad nema pracovnikov.");
        } else {
            for (Miestnost m : this.zoznamMiestnosti.values()) {
                System.out.println(m.vypisZoznamuPracovnikov());
            }
        }
    }

    /**
     * Vypise zoznam tovaru vo vsetkych miestnostiach.
     */
    public void vypisTovar() {
        System.out.println();
        for (Miestnost m : this.zoznamMiestnosti.values()) {
            System.out.println(((ISkladovaMiestnost)m).vypisZoznamuRegalov());
        }
    }

    /**
     * Prida regal s danou kapacitou do velkeho skladiska.
     * @param kapacita kapacita regalu
     */
    public void pridajRegal(int kapacita) {
        ((VelkySklad)this.zoznamMiestnosti.get("skladTovaru")).pridajRegal(kapacita);
    }

    /**
     * Odstrani regal s danym indexom z velkeho skladiska.
     * @param index index regalu
     */
    public void odstranRegal(int index) {
        ((VelkySklad)this.zoznamMiestnosti.get("skladTovaru")).odstranRegal(index);
    }


    /**
     * Prijme noveho pracovnika skladu, umiestni ho do velkeho skladu.
     * @param pracovnik pracovnik na prijatie
     */
    public void prijmiPracovnika(Pracovnik pracovnik) {
        this.zoznamPracovnikov.put(pracovnik.getId(), pracovnik);
        System.out.println("Pracovnik bol uspesne prijaty.");
    }

    /**
     * Skontroluje ci je mozne pracovnika so zadanym id vylucit. Ak ano, vyluci ho.
     * @param id id pracovnika
     */
    public void vylucPracovnika(String id) {
        if (this.zoznamPracovnikov.containsKey(id)) {
            Pracovnik prac = this.zoznamPracovnikov.get(id);
            if (prac.mamTovar()) {
                if (prac.ulozTovar()) {
                    prac.getAktualnaMiestnost().odoberPracovnika(prac);
                    this.zoznamPracovnikov.remove(prac.getId());
                    System.out.println("Pracovnik bol vyluceny.");
                } else {
                    System.out.println("Pracovnik nema kde ulozit tovar.");
                }
            } else {
                prac.getAktualnaMiestnost().odoberPracovnika(prac);
                this.zoznamPracovnikov.remove(prac.getId());
                System.out.println("Pracovnik bol vyluceny.");
            }
        } else {
            System.out.println("Pracovnik so zadanym ID neexistuje.");
        }
    }
}
