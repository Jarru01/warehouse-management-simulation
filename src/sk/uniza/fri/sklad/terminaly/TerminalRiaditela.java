package sk.uniza.fri.sklad.terminaly;

import sk.uniza.fri.sklad.Sklad;
import sk.uniza.fri.sklad.osoby.Pracovnik;
import sk.uniza.fri.sklad.osoby.Riaditel;

import java.util.Scanner;

/**
 * Terminal riaditela sluzi na citanie vstupu z terminalu a nasledne spracovanie tohto vstupu. Terminal pracuje
 * s riaditelom ktory sa do neho prihlasil, ktory vykonava zadane prikazy.
 * @author Juraj
 */
public class TerminalRiaditela implements ITerminal {

    private final Sklad sklad;          //sklad s ktorym terminal pracuje
    private final Scanner scanner;      //scanner vstupu
    private Riaditel riaditel;          //prihlaseny riaditel
    private int pocetPokusov;           //pocet pokusov na prihlasenie

    /**
     * Nastavi pociatocne hodnoty atributov, naplni atribut sklad z parametra a spusti terminal.
     * @param sklad sklad s ktorym terminal pracuje
     */
    public TerminalRiaditela(Sklad sklad) {

        this.sklad = sklad;
        this.riaditel = null;
        this.scanner = new Scanner(System.in);
        this.pocetPokusov = 0;
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
     * Poziada Riaditela o identifikaciu, ak zadane identikacne cislo je spravne, vypise sa uvodne menu. Ak nie, riaditel
     * ma este 2 pokusy na prihlasenie.
     */
    @Override
    public void spustiTerminal() {
        System.out.println("\nZadaj prosim svoje ID: ");
        String id = this.nacitajVstup();

        if (this.sklad.getRiaditelSkladu().getId().equals(id)) {
            this.riaditel = this.sklad.getRiaditelSkladu();
            this.pocetPokusov = 0;
            this.vypisUvodneMenu();
        }

        if (this.pocetPokusov < 2) {
            System.out.println("Zadal si nespravne ID.");
            this.pocetPokusov++;
            this.spustiTerminal();
        } else {
            System.out.println("Zadal si prilis vela neplatnych pokusov o prihlasenie.");
            this.pocetPokusov = 0;
            this.zatvorTerminal();
        }
    }

    /**
     * Uvita prihlaseneho riaditela a vypise zoznam moznych prikazov, ktore moze vykonat. Poziada o zadanie prikazu
     * ktory chce riaditel vykonat a vykona ho.
     */
    public void vypisUvodneMenu() {
        System.out.println("\nVitaj " + this.riaditel.getMeno() + ". Vyber prosim cinnost, ktoru chces vykonat:");
        System.out.println("-1- Vypis pracovnikov");
        System.out.println("-2- Vypis tovar");
        System.out.println("-3- Pridaj regal");
        System.out.println("-4- Odstran regal");
        System.out.println("-5- Prijmi pracovnika");
        System.out.println("-6- Vyluc pracovnika");
        System.out.println("Pre odhlasenie zadaj lubovolny iny vstup.");

        switch (this.nacitajVstup()) {
            case "1" -> this.riaditel.vypisZoznamPracovnikov();
            case "2" -> this.riaditel.vypisTovar();
            case "3" -> this.pridajRegal();
            case "4" -> this.odstranRegal();
            case "5" -> this.prijmiPracovnika();
            case "6" -> this.vylucPracovnika();
            default -> this.zatvorTerminal();
        }
        this.sklad.ulozZmeny();
        this.vypisUvodneMenu();
    }

    /**
     * Terminal poziada o zadanie ID pracovnika ktoreho chce riaditel vylucit, ak sa pracovnik najde,
     * riaditel ho vyluci.
     */
    private void vylucPracovnika() {
        System.out.println();
        if (this.riaditel.getZoznamPracovnikov().isEmpty()) {
            System.out.println("Sklad nema pracovnikov.");
            return;
        }
        System.out.println("Zadaj ID pracovnika:");
        String idp = this.nacitajVstup();
        for (int i = 0; i < idp.length(); i++) {
            if (!Character.isDigit(idp.charAt(i))) {
                System.out.println("Zadal si neplatne ID.");
                return;
            }
        }
        this.riaditel.vylucPracovnika(idp);
    }

    /**
     * Terminal poziada o zadanie ID pracovnia ktory sa nenachadza v sklade. Nasledovne riaditel zada
     * meno a priezvisko pracovnika a prijme ho.
     */
    private void prijmiPracovnika() {
        System.out.println();
        System.out.println("Zadaj ID pracovnika:");
        String id = this.nacitajVstup();
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                System.out.println("Zadal si neplatne ID.");
                return;
            }
        }
        if (this.riaditel.getZoznamPracovnikov().containsKey(id)) {
            System.out.println("Pracovnik so zadanym ID uz existuje.");
            return;
        }
        System.out.println("Zadaj meno pracovnika:");
        String meno = this.nacitajVstup();
        for (int i = 0; i < meno.length(); i++) {
            if (!Character.isLetter(meno.charAt(i))) {
                System.out.println("Zadal si neplatne meno.");
                return;
            }
        }

        System.out.println("Zadaj priezvisko pracovnika:");
        String priezvisko = this.nacitajVstup();
        for (int i = 0; i < priezvisko.length(); i++) {
            if (!Character.isLetter(priezvisko.charAt(i))) {
                System.out.println("Zadal si neplatne priezvisko.");
                return;
            }
        }
        this.riaditel.prijmiPracovnika(new Pracovnik(meno, priezvisko, this.sklad.getZoznamMiestnosti(), id));
    }

    /**
     * Prida regal do velkeho skladu s danou kapacitou.
     */
    private void pridajRegal() {
        System.out.println();
        System.out.println("Zadaj kapacitu regala(max 20):");
        String kapacita = this.nacitajVstup();
        for (int i = 0; i < kapacita.length(); i++) {
            if (!Character.isDigit(kapacita.charAt(i))) {
                System.out.println("Zadal si neplatny vstup.");
                return;
            }
        }
        int cislo = Integer.parseInt(kapacita);
        if (cislo > 20 || cislo < 1) {
            System.out.println("Zadal si neplatnu kapacitu regala.");
            return;
        }
        this.riaditel.pridajRegal(cislo);
    }

    /**
     * Odstrani regal z velkeho skladu so zadanym indexom.
     */
    private void odstranRegal() {
        System.out.println();
        System.out.println("Zadaj index regalu:");
        String index = this.nacitajVstup();
        for (int i = 0; i < index.length(); i++) {
            if (!Character.isDigit(index.charAt(i))) {
                System.out.println("Zadal si neplatny vstup.");
                return;
            }
        }
        int cislo = Integer.parseInt(index);
        this.riaditel.odstranRegal(cislo);
    }

    /**
     * Zatvori terminal Riaditela.
     */
    @Override
    public void zatvorTerminal() {
        this.sklad.vypisUvodneMenu();
    }
}
