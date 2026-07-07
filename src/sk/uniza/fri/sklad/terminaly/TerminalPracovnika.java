package sk.uniza.fri.sklad.terminaly;

import sk.uniza.fri.sklad.Sklad;
import sk.uniza.fri.sklad.miestnosti.VelkySklad;
import sk.uniza.fri.sklad.osoby.Pracovnik;

import java.util.Scanner;

/**
 *  Terminal pracovnika sluzi na citanie vstupu z terminalu a nasledne spracovanie tohto vstupu. Terminal pracuje
 *  s pracovnikom ktory sa do neho prihlasil, ktory vykonava zadane prikazy.
 * @author Juraj
 */
public class TerminalPracovnika implements ITerminal {

    private final Sklad sklad;              //sklad s ktorym terminal pracuje
    private Pracovnik aktualnyPracovnik;    //prihlaseny pracovnik
    private final Scanner scanner;          //scanner vstupu
    private int pocetPokusov;               //pocet pokusov na prihlasenie

    /**
     * Nastavi pociatocne hodnoty atributov, naplni atribut sklad z parametra a spusti terminal.
     * @param sklad sklad s ktorym terminal pracuje
     */
    public TerminalPracovnika(Sklad sklad) {
        this.pocetPokusov = 0;
        this.sklad = sklad;
        this.aktualnyPracovnik = null;
        this.scanner = new Scanner(System.in);
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
     * Poziada pracovnika o identifikaciu, ak sa najde pracovnik so zadanym id, vypise sa uvodne menu. Ak nie, pracovnik
     * ma este 2 pokusy na prihlasenie.
     */
    @Override
    public void spustiTerminal() {
        if (this.sklad.getZoznamPracovnikov().isEmpty()) {
            System.out.println("Sklad nema pracovnikov.");
            this.zatvorTerminal();
        }
        System.out.println("\nZadaj prosim svoje ID: ");
        String id = this.nacitajVstup();

        for (Pracovnik p : this.sklad.getZoznamPracovnikov().values()) {
            if (p.getId().equals(id)) {
                this.aktualnyPracovnik = p;
                this.pocetPokusov = 0;
                this.vypisUvodneMenu();
            }
        }

        if (this.pocetPokusov < 2) {
            System.out.println("Pracovnik so zadanym ID neexistuje.");
            this.pocetPokusov++;
            this.spustiTerminal();
        } else {
            System.out.println("Zadal si prilis vela neplatnych pokusov o prihlasenie.");
            this.pocetPokusov = 0;
            this.zatvorTerminal();
        }
    }

    /**
     * Uvita prihlaseneho pracovnika a vypise zoznam moznych prikazov, ktore moze pracovnik vykonat. Poziada o zadanie prikazu
     * ktory chce pracovnik vykonat a vykona ho.
     */
    public void vypisUvodneMenu () {
        System.out.println("\nVitaj " + this.aktualnyPracovnik.getMeno() + ".");
        System.out.println("Prave sa nachadzas v miestnosti " +
                this.aktualnyPracovnik.getAktualnaMiestnost().getPopisMiestnosti() + ". Vyber cinnost, ktoru chces vykonat:");
        System.out.println("-1- Chod do miestnosti");
        System.out.println("-2- Zober tovar");
        System.out.println("-3- Uloz tovar");
        System.out.println("-4- Vypis tovar v miestnosti");
        System.out.println("-5- Vypis moj tovar");
        System.out.println("Pre odhlasenie zadaj lubovolny iny vstup.");

        switch (this.nacitajVstup()) {
            case "1":
                this.chodDoMiestnosti();
                break;
            case "2":
                this.zoberTovar();
                break;
            case "3":
                System.out.println();
                this.aktualnyPracovnik.ulozTovar();
                break;
            case "4":
                System.out.println();
                this.aktualnyPracovnik.vypisZoznamRegalovAktualnejMiestnosti();
                break;
            case "5":
                System.out.println();
                this.aktualnyPracovnik.vypisMojTovar();
                break;
            default:
                if (this.aktualnyPracovnik.mamTovar()) {
                    System.out.println("Najskor musim ulozit tovar.");
                } else {
                    this.zatvorTerminal();
                }
                break;
        }
        this.sklad.ulozZmeny();
        this.vypisUvodneMenu();
    }

    /**
     * Ak je pracovnik vo velkom sklade, terminal si vyziada o zadanie indexu regalu. Ak zada spravny index, terminal poziada o zadanie ID
     * tovaru. Ak najde tovar so zadanym ID, tovar si pracovnik zoberie. Ak je pracovnik v malom sklade, terminal poziada o zadanie
     * ID tovaru, ktore ak najde tak ho zoberie.
     */
    private void zoberTovar() {
        System.out.println();
        if ( this.aktualnyPracovnik.getAktualnaMiestnost() instanceof VelkySklad) {
            System.out.println("Zadaj cislo regalu: ");
            String cisloRegalu = this.nacitajVstup();

            if (cisloRegalu == null) {
                System.out.println("Zadal si neplatny vstup.");
                return;
            } else {
                for (int i = 0; i < cisloRegalu.length(); i++) {
                    if (!Character.isDigit(cisloRegalu.charAt(i))) {
                        System.out.println("Zadal si neplatny vstup.");
                        return;
                    }
                }
            }
            int cislo = Integer.parseInt(cisloRegalu);
            if (cislo > ((VelkySklad)this.aktualnyPracovnik.getAktualnaMiestnost()).getZoznamRegalov().size() || cislo < 1) {
                System.out.println("Zadal si cislo neexistujuceho regalu.");
                return;
            }
            System.out.println("Zadaj ID tovaru: ");
            String id = this.nacitajVstup();
            this.aktualnyPracovnik.zoberTovar(id, cislo);
        } else {
            System.out.println("Zadaj ID tovaru: ");
            String id = this.nacitajVstup();
            this.aktualnyPracovnik.zoberTovar(id);
        }
    }

    /**
     * Vypise menu s moznymi miestnostami, poziada o vstup a presunie pracovnika do vybranej miestnosti.
     */
    private void chodDoMiestnosti() {
        System.out.println();
        System.out.println("Vyber miestnost do ktorej chces ist: ");
        System.out.println("-1- Sklad tovaru");
        System.out.println("-2- Prijem tovaru");
        System.out.println("-3- Vydaj tovaru");
        System.out.println("Pre zrusenie zadaj lubovolny iny vstup.");

        switch (this.nacitajVstup()) {
            case "1" -> this.aktualnyPracovnik.chodDoMiestnosti("skladTovaru");
            case "2" -> this.aktualnyPracovnik.chodDoMiestnosti("prijemTovaru");
            case "3" -> this.aktualnyPracovnik.chodDoMiestnosti("vydajTovaru");
            default -> this.vypisUvodneMenu();
        }
    }

    /**
     * Zatvori terminal pracovnika.
     */
    @Override
    public void zatvorTerminal() {
        this.sklad.vypisUvodneMenu();
    }
}
