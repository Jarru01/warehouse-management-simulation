package sk.uniza.fri.sklad.osoby;

import sk.uniza.fri.sklad.IIdentifikovatelny;
import sk.uniza.fri.sklad.miestnosti.ISkladovaMiestnost;
import sk.uniza.fri.sklad.miestnosti.MalySklad;
import sk.uniza.fri.sklad.miestnosti.Miestnost;
import sk.uniza.fri.sklad.miestnosti.VelkySklad;
import sk.uniza.fri.sklad.predmety.Tovar;

import java.util.HashMap;

/**
 * Pracovnik je potomok Osoby, ktory obsahuje dalsie atributy a metody specificke pre pracovnika. Pracovnik sa vie
 * pohybovat po sklade a interagovat s regalmi/tovarom.
 * @author Juraj
 */
public class Pracovnik extends Osoba implements IIdentifikovatelny {
    private final HashMap<String, Miestnost> zoznamMiestnosti;  //zoznam miestnosti skladu
    private final String id;                                    //id pracovnika
    private Miestnost aktualnaMiestnost;                        //miestnost v ktorej sa pracovnik nachadza
    private Tovar aktualnyTovar;                                //tovar ktory pracovnik drzi

    /**
     * Vytvara pracovnika s menom, priezivskom, zoznamomMiestnosti a ID z parametra.
     * @param meno meno pracovnika
     * @param priezvisko priezvisko pracovnika
     * @param zoznamMiestnosti zoznam miestnosti skladu
     * @param id id pracovnika
     */
    public Pracovnik(String meno, String priezvisko, HashMap<String, Miestnost> zoznamMiestnosti, String id) {
        super(meno, priezvisko);
        this.zoznamMiestnosti = zoznamMiestnosti;
        this.id = id;
        this.aktualnaMiestnost = this.zoznamMiestnosti.get("skladTovaru");
        this.aktualnaMiestnost.pridajPracovnika(this);
        this.aktualnyTovar = null;
    }

    /**
     * Vracia id pracovnika
     * @return id
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Vracia aktualnu miestnost v ktorej sa pracovnik nachadza.
     * @return aktualna miestnost
     */
    public Miestnost getAktualnaMiestnost() {
        return this.aktualnaMiestnost;
    }

    /**
     * Pyta sa pracovnika ci drzi tovar. Ak ano, vrati true.
     * @return boolean
     */
    public boolean mamTovar() {
        return this.aktualnyTovar != null;
    }

    /**
     * Odoberie pracovnika z aktualnej miestnosti a presunie ho do miestnosti z parametra.
     * @param miestnost miestnost do ktorej ma pracovnik ist
     */
    public void chodDoMiestnosti(String miestnost) {
        this.aktualnaMiestnost.odoberPracovnika(this);
        this.aktualnaMiestnost = this.zoznamMiestnosti.get(miestnost);
        this.aktualnaMiestnost.pridajPracovnika(this);
        System.out.println("Presiel si do miestnosti " + this.aktualnaMiestnost.getPopisMiestnosti() + ".");
    }

    /**
     * Ak je pracovnik v malom sklade, pokusi sa zobrat tovar so zadanym id z regalu.
     * @param id id tovaru
     */
    public void zoberTovar(String id) {
        if (this.aktualnyTovar != null) {
            System.out.println("Mam plne ruky.");
            return;
        }
        if (this.aktualnaMiestnost instanceof MalySklad) {
            if (((ISkladovaMiestnost)this.aktualnaMiestnost).getRegal().getTovar(id) != null) {
                this.aktualnyTovar = ((ISkladovaMiestnost)this.aktualnaMiestnost).getRegal().getTovar(id);
                ((ISkladovaMiestnost)this.aktualnaMiestnost).getRegal().odoberTovar(id);
            }
        }
    }

    /**
     * Ak je pracovnik vo velkom sklade, pokusi sa zobrat tovar s id zo zadaneho regalu.
     * @param id id tovaru
     * @param indexRegalu index regalu
     */
    public void zoberTovar(String id, int indexRegalu) {
        if (this.aktualnyTovar != null) {
            System.out.println("Mam plne ruky.");
            return;
        }
        if (this.aktualnaMiestnost instanceof VelkySklad) {
            if (((VelkySklad)this.aktualnaMiestnost).getRegal(indexRegalu).getTovar(id) != null) {
                this.aktualnyTovar = ((VelkySklad)this.aktualnaMiestnost).getRegal(indexRegalu).getTovar(id);
                ((VelkySklad)this.aktualnaMiestnost).getRegal(indexRegalu).odoberTovar(id);
            }
        }
    }

    /**
     * Pokusi sa ulozit tovar do volneho regalu. Ak sa podari, vrati true.
     * @return boolean
     */
    public boolean ulozTovar() {
        if (((ISkladovaMiestnost)this.aktualnaMiestnost).getRegal() != null) {
            ((ISkladovaMiestnost)this.aktualnaMiestnost).getRegal().ulozTovar(this.aktualnyTovar);
            this.aktualnyTovar = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vypise informacie o tovaru ktory pracovnik drzi.
     */
    public void vypisMojTovar() {
        if (this.aktualnyTovar != null) {
            System.out.println("Moj aktualny tovar:");
            System.out.println(this.aktualnyTovar);
        } else {
            System.out.println("Nemam ziaden tovar.");
        }
    }

    /**
     * Vypise vsetky regale a tovar v nich v aktualnej miestnosti.
     */
    public void vypisZoznamRegalovAktualnejMiestnosti() {
        System.out.println(((ISkladovaMiestnost)this.aktualnaMiestnost).vypisZoznamuRegalov());
    }

    /**
     * Vrati string s udajmi o pracovnikovi.
     * @return string s udajmi o pracovnikovi.
     */
    @Override
    public String toString() {
        return "[Meno: " + this.getMeno() + ", " + "Priezvisko: " + this.getPriezvisko() + ", " + "ID: " + this.getId() +
                "]";

    }

}
