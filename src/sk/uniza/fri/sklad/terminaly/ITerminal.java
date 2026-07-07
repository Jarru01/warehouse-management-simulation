package sk.uniza.fri.sklad.terminaly;

/**
 * Interface ITerminal implementuju triedy ktore sluzia na interakciu s pouzivatelom.
 * @author Juraj
 */
public interface ITerminal {
    /**
     * Sluzi na nacitanie vstupu zadaneho do terminalu.
     * @return vstup
     */
    String nacitajVstup();

    /**
     * Sluzi na prve spustenie terminalu.
     */
    void spustiTerminal();

    /**
     * Sluzi na zatvorenie terminalu.
     */
    void zatvorTerminal();

    /**
     * Sluzi na vypisanie menu s moznostami ktore ma pouzivatel k dispozicii.
     */
    void vypisUvodneMenu();
}
