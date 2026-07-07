package sk.uniza.fri.sklad;

import sk.uniza.fri.sklad.miestnosti.Miestnost;
import sk.uniza.fri.sklad.miestnosti.MalySklad;
import sk.uniza.fri.sklad.miestnosti.VelkySklad;
import sk.uniza.fri.sklad.osoby.Pracovnik;
import sk.uniza.fri.sklad.osoby.Riaditel;
import sk.uniza.fri.sklad.pracaSoSuborom.ZapisovacSuboru;
import sk.uniza.fri.sklad.terminaly.TerminalRiaditela;
import sk.uniza.fri.sklad.terminaly.TerminalPracovnika;
import sk.uniza.fri.sklad.terminaly.TerminalZakaznika;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

/**
 *  Trieda sklad vytvara zakladnu strukturu skladu(miestnosti, zoznam pracovnikov, riaditela skladu), vytvara terminal
 *  pre danu osobu a obsahuje metodu na ulozenie konfiguracie skladu do suboru.
 * @author Juraj
 */
public class Sklad implements Serializable {
    private final HashMap<String, Miestnost> zoznamMiestnosti;      //zoznam miestnosti skladu
    private final HashMap<String, Pracovnik> zoznamPracovnikov;     //zoznam pracovnikov skladu
    private final Riaditel riaditelSkladu;                          //riaditel skladu

    /**
     * Vytvori prazdny zoznam pracovnikov, naplni zoznam miestnosti s miestnostami skladu a vytvori riaditela.
     */
    public Sklad() {
        this.zoznamMiestnosti = new HashMap<>();
        this.zoznamPracovnikov = new HashMap<>();
        this.riaditelSkladu = new Riaditel("Jaruj", "Juraj",
                this.zoznamMiestnosti, this.zoznamPracovnikov, "123");

        VelkySklad skladTovaru = new VelkySklad("skladTovaru");
        MalySklad prijemTovaru = new MalySklad("prijemTovaru");
        MalySklad vydajTovaru = new MalySklad("vydajTovaru");
        this.zoznamMiestnosti.put(skladTovaru.getPopisMiestnosti(), skladTovaru);
        this.zoznamMiestnosti.put(prijemTovaru.getPopisMiestnosti(), prijemTovaru);
        this.zoznamMiestnosti.put(vydajTovaru.getPopisMiestnosti(), vydajTovaru);
    }

    /**
     * Vypise uvodne menu ktore ziada o identifkiaciu pouzivatela, nasledne vytvori dany terminal pre pouzivatela.
     */
    public void vypisUvodneMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println(
                """

                                           _____         _    \s
                                          |_   _|       | |   \s
                         _____ __ ___   __ _| | ___  ___| |__ \s
                        |_  / '_ ` _ \\ / _` | |/ _ \\/ __| '_ \\\s
                         / /| | | | | | (_| | |  __/ (__| | | |
                        /___|_| |_| |_|\\__,_\\_/\\___|\\___|_| |_|""");

        System.out.println("Vyber prosim, za koho sa chces prihlasit do systemu:");
        System.out.println("-1- Pracovnik");
        System.out.println("-2- Zakaznik");
        System.out.println("-3- Riaditel");
        System.out.println("Pre ukoncenie zadaj lubovolny iny vstup.");

        System.out.print("> ");
        String vstup = sc.next();

        switch (vstup) {
            case "1" -> new TerminalPracovnika(this);
            case "2" -> new TerminalZakaznika(this);
            case "3" -> new TerminalRiaditela(this);
            default -> System.exit(0);
        }

    }

    /**
     * Vracia zoznam miestnosti skladu
     * @return zoznam miestnosti
     */
    public HashMap<String, Miestnost> getZoznamMiestnosti() {
        return this.zoznamMiestnosti;
    }

    /**
     * Vracia zoznam pracovnikov skladu
     * @return zoznam pracovnikov
     */
    public HashMap<String, Pracovnik> getZoznamPracovnikov() {
        return this.zoznamPracovnikov;
    }

    /**
     * Vracia riaditela skladu
     * @return riaditel skladu
     */
    public Riaditel getRiaditelSkladu() {
        return this.riaditelSkladu;
    }

    /**
     * Ulozi do suboru aktualnu konfiguraciu skladu.
     */
    public void ulozZmeny() {
        new ZapisovacSuboru().zapis(this);
    }
}
