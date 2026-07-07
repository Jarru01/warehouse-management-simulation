package sk.uniza.fri.sklad.terminaly;

import sk.uniza.fri.sklad.VytvaracTovaru;
import sk.uniza.fri.sklad.Sklad;
import sk.uniza.fri.sklad.miestnosti.MalySklad;
import sk.uniza.fri.sklad.osoby.Zakaznik;
import sk.uniza.fri.sklad.predmety.Tovar;

import java.util.Scanner;

/**
 *  Terminal zakaznika sluzi na citanie vstupu z terminalu a nasledne spracovanie tohto vstupu. Terminal pracuje
 *  so zakaznikom ktory sa do neho prihlasil, ktory vykonava zadane prikazy.
 * @author Juraj
 */
public class TerminalZakaznika implements ITerminal {

    private final Sklad sklad;       //sklad s ktorym terminal pracuje
    private final Scanner scanner;   //scanner vstupu
    private Zakaznik zakaznik;       //prihlaseny zakaznik

    /**
     * Nastavi pociatocne hodnoty atributov, naplni atribut sklad z parametra a spusti terminal.
     * @param sklad sklad s ktorym terminal pracuje
     */
    public TerminalZakaznika(Sklad sklad) {
        this.sklad = sklad;
        this.scanner = new Scanner(System.in);
        this.zakaznik = null;
        this.spustiTerminal();
    }

    /**
     * Nacita vstup z terminalu
     * @return vstup
     */
    @Override
    public String nacitajVstup() {
        System.out.print("> ");
        return this.scanner.next();
    }

    /**
     * Poziada zakaznika o zadanie mena a priezviska. Naplni atribut zakaznik s novym zakaznikom ktory sa prave prihlasil
     * a vypise uvodne menu.
     */
    @Override
    public void spustiTerminal() {
        System.out.println("Zadaj prosim svoje meno:");
        String meno = this.nacitajVstup();
        if (meno.equals("ukonci")) {
            this.zatvorTerminal();
        }
        for (int i = 0; i < meno.length(); i++) {
            if (!Character.isLetter(meno.charAt(i))) {
                System.out.println("Zadal si neplatne meno.");
                this.spustiTerminal();
            }
        }

        System.out.println("Zadaj prosim svoje priezvisko:");
        String priezvisko = this.nacitajVstup();
        if (priezvisko.equals("ukonci")) {
            this.zatvorTerminal();
        }
        for (int i = 0; i < priezvisko.length(); i++) {
            if (!Character.isLetter(priezvisko.charAt(i))) {
                System.out.println("Zadal si neplatne priezvisko.");
                this.spustiTerminal();
            }
        }

        this.zakaznik = new Zakaznik(meno, priezvisko, (MalySklad)this.sklad.getZoznamMiestnosti().get("prijemTovaru"),
                (MalySklad)this.sklad.getZoznamMiestnosti().get("vydajTovaru"));
        this.vypisUvodneMenu();
    }

    /**
     * Uvita prihlaseneho zakaznika a vypise zoznam moznych prikazov, ktore moze vykonat. Poziada o zadanie prikazu
     * ktory chce zakaznik vykonat a vykona ho.
     */
    public void vypisUvodneMenu() {
        System.out.println("\n Vitaj " + this.zakaznik.getMeno() + ". Vyber prosim cinnost, ktoru chces vykonat:");
        System.out.println("-1- Uloz tovar");
        System.out.println("-2- Vyzdvihni tovar");
        System.out.println("Pre odhlasenie zadaj lubovolny iny vstup.");

        switch (this.nacitajVstup()) {
            case "1" -> this.ulozTovar();
            case "2" -> this.vyzdvihniTovar();
            default -> this.zatvorTerminal();
        }
        this.sklad.ulozZmeny();
        this.vypisUvodneMenu();
    }

    /**
     * Vytvori dialogove okno v ktorom zakaznik zadava udaje o tovaru ktory chce ulozit. Po zatvoreni okna ulozi tovar
     * v sklade.
     */
    private void ulozTovar() {
        System.out.println();
        VytvaracTovaru forma = new VytvaracTovaru(this.sklad.getZoznamMiestnosti(), this.zakaznik);
        Tovar tovar = forma.vytvorTovar();
        if (tovar != null) {
            this.zakaznik.ulozTovar(tovar);
        }

    }

    /**
     * Zakaznik zada id tovaru ktory chce vyzdvihnut. Ak sa tovar s ID nachadza v mietnosti na vydaj tovaru a patri mu,
     * tovar vyzdvihne.
     */
    private void vyzdvihniTovar() {
        MalySklad vydajTovaru = (MalySklad)this.sklad.getZoznamMiestnosti().get("vydajTovaru");
        System.out.println();
        System.out.println("Zadaj id tovaru:");
        String id = this.nacitajVstup();
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                System.out.println("Zadal si neplatne ID.");
                return;
            }
        }
        Tovar tovarNaVyzdvihnutie = vydajTovaru.getRegal().getTovar(id);
        if (tovarNaVyzdvihnutie != null) {
            if (tovarNaVyzdvihnutie.getPrijemca().getMeno().equals(this.zakaznik.getMeno()) &&
                    tovarNaVyzdvihnutie.getPrijemca().getPriezvisko().equals(this.zakaznik.getPriezvisko())) {
                this.zakaznik.vyzdvihniTovar(tovarNaVyzdvihnutie);
            } else {
                System.out.println("Tovar ktory chces vyzdvihnut ti nepatri.");
            }
        }
    }

    /**
     * Zatvori terminal.
     */
    @Override
    public void zatvorTerminal() {
        this.sklad.vypisUvodneMenu();
    }
}

