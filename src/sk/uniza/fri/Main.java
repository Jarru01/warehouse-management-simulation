package sk.uniza.fri;

import sk.uniza.fri.sklad.Sklad;
import sk.uniza.fri.sklad.pracaSoSuborom.CitacSuboru;

/**
 * Trieda main a jej metoda sluzi na spustenie programu.
 */
public class Main {

    /**
     * Zo suboru sa nacita ulozeny sklad a vypise sa uvodne menu.
     * @param args argumenty
     */
    public static void main(String[] args) {
        Sklad sklad = new CitacSuboru().nacitaj();
        sklad.vypisUvodneMenu();
    }
}
