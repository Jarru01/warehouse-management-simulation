package sk.uniza.fri.sklad.pracaSoSuborom;

import sk.uniza.fri.sklad.Sklad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Trieda ZapisovacSuboru sluzi k zapisaniu/ulozeniu beziacej konfiguracie skladu do textoveho suboru.
 * @author Juraj
 */
public class ZapisovacSuboru {

    public ZapisovacSuboru() {
    }

    /**
     * Pokusi sa zapisat/ulozit konfiguraciu skladu z parametra do textoveho suboru.
     * @param sklad sklad na ulozenie
     */
    public void zapis(Sklad sklad) {
        try {
            FileOutputStream f = new FileOutputStream("sklad.txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(sklad);

            o.close();
            f.close();


        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error initializing stream");
        }

    }
}
